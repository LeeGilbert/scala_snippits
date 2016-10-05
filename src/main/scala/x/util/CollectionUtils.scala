package x.util
import scala.collection._


object CollectionUtils {

  /**
    * Preserving sort order, while making a LinkedHashmap immutable.
    * @param lhm mutable.LinkedHashMap
    * @tparam A
    * @tparam B
    * @return immutable.ListMap
    */
  def toMap[A, B](lhm: mutable.LinkedHashMap[A, B]): immutable.ListMap[A, B] = immutable.ListMap(lhm.toSeq: _*)

}
