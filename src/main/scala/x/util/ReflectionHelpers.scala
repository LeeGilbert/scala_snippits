package x.util

import scala.reflect.runtime.universe._

object ReflectionHelpers extends ReflectionHelpers

trait ReflectionHelpers {

  protected val classLoaderMirror = runtimeMirror(getClass.getClassLoader)

  /**
    * Reflectively invokes the constructor for a given case class type `T`.
    * Requires: libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.8"
    * @tparam T the type of the case class this factory builds
    */
  final class CaseClassFactory[T: TypeTag] {

    val tpe = typeOf[T]
    val classSymbol = tpe.typeSymbol.asClass
    if (!(tpe <:< typeOf[Product] && classSymbol.isCaseClass)) throw new IllegalArgumentException("CaseClassFactory only applies to case classes!")

    val classMirror = classLoaderMirror reflectClass classSymbol
    val constructorSymbol = tpe.decl(termNames.CONSTRUCTOR)
    val defaultConstructor =
      if (constructorSymbol.isMethod) constructorSymbol.asMethod
      else {
        val ctors = constructorSymbol.asTerm.alternatives
        ctors.map { _.asMethod }.find { _.isPrimaryConstructor }.get
      }
    val constructorMethod = classMirror reflectConstructor defaultConstructor

    /**
      * Attempts to create a new instance of the specified type by calling the constructor method with the supplied arguments.
      *
      * @param args the arguments to supply to the constructor method
      */
    def buildWith(args: Seq[_]): T = constructorMethod(args: _*).asInstanceOf[T]

  }
}

//////////////////////////////////////////////////////////////////////////////
// USAGE
//////////////////////////////////////////////////////////////////////////////

//case class Person(name: String, age: Int)
//val personFactory = new ReflectionHelpers.CaseClassFactory[Person]
//val result: Person = personFactory.buildWith(Seq("Connor", 27))
//val expected = Person("Connor", 27)
//assert(result == expected)
