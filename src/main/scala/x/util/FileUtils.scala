package x.util
import java.nio.charset.CodingErrorAction
import scala.io.Codec
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets

object FileUtils {

    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE).onUnmappableCharacter(CodingErrorAction.REPLACE)

    /**
    * Readlines from UTF8 file, ignoring malformed characters and replacing unmappable characters.
    * */
    def fileLines(fname: String) = scala.io.Source.fromFile(fname)(decoder).getLines.toList

  /**
    * Writelines to UTF8 file.
    * */
    def write(fname:String, contents : String) = Files.write(Paths.get(fname), contents.getBytes(StandardCharsets.UTF_8))
}
