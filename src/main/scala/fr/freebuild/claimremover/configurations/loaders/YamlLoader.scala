package fr.freebuild.claimremover.configurations.loaders

import java.io.InputStreamReader

import better.files._
import fr.freebuild.claimremover.ClaimRemoverPlugin
import io.circe.Json
import io.circe.yaml.parser

import scala.util.{Failure, Try}

trait YamlLoader[T] {

  /**
   * Load the wanted file
   *
   * @return An option of loaded file
   */
  def load: Option[T]

  /**
   * Load file from filesystem path
   *
   * @param path Path to file
   * @return Try of parsed filed
   */
  def loadFromPath(path: String): Try[Json] =
    for {
      stream <- getStream(path)
      json <- parser.parse(stream).toTry
    } yield json

  /**
   * Get the InputStream of wanted file
   *
   * @param path Path to file
   * @return A try of file InputStream
   */
  private def getStream(path: String): Try[InputStreamReader] =
    Try(new InputStreamReader(s"$path".toFile.newInputStream))

  /**
   * PartialFunction that handle load errors of file
   *
   * @param path Path to file
   * @return The Part
   */
  private[loaders] def handleErrors(path: String): PartialFunction[Throwable, Try[T]] = {
    case e =>
      ClaimRemoverPlugin.getLogger.warning(s"Can't load file: $path")
      e.printStackTrace()
      Failure(e)
  }
}
