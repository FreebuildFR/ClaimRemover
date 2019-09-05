package fr.freebuild.claimremover.configurations

import java.io.{InputStream, InputStreamReader}

import better.files._
import fr.freebuild.claimremover.ClaimRemoverPlugin
import xyz.janboerman.scalaloader.plugin.ScalaPluginClassLoader
import fr.freebuild.claimremover.utils.ColorUtils._
import io.circe.yaml.parser
import io.circe._
import io.circe.generic.auto._
import cats.syntax.either._
import io.circe.yaml

import scala.util.Try

object ConfigLoader {

  /**
   * Get resource from path
   *
   * @param classLoader ClassLoader to get URL of file
   * @param path Path to file
   * @return Return an option of InputStream opened
   */
  private def getResource(classLoader: ScalaPluginClassLoader, path: String): Option[InputStream] = {
    Option(classLoader.findResource(path)).flatMap { resourceUrl =>
      Try(resourceUrl.openConnection).map { connection =>
        connection.setUseCaches(false)
        connection.getInputStream
      }.toOption
    }
  }

  /**
   * Save a resource from jar to plugin folder
   *
   * @param classLoader ClassLoader to get URL of file
   * @param path Path to file
   */
  def saveResource(classLoader: ScalaPluginClassLoader, path: String): Unit = {
    val file = s"${ClaimRemoverPlugin.getDataFolder}/$path".toFile

    if (!file.exists) {
      getResource(classLoader, path) match {
        case Some(stream) => file.createIfNotExists(false, true).writeBytes(stream.bytes)
        case None => ClaimRemoverPlugin.getLogger.warning(s"File $path doesn't exist")
      }
    }
  }

  def loadResource(path: String): Json = {
    val inputStream = new InputStreamReader(s"${ClaimRemoverPlugin.getDataFolder}/$path".toFile.newInputStream)
    parser.parse(inputStream).leftMap(err => err: Error).valueOr(throw _)
  }
}
