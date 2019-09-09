package fr.freebuild.claimremover.configurations

import java.io.{InputStream, InputStreamReader}

import better.files._
import fr.freebuild.claimremover.ClaimRemoverPlugin
import xyz.janboerman.scalaloader.plugin.ScalaPluginClassLoader

import scala.util.Try

object ConfigurationLoader {

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
        case Some(stream) => file.createIfNotExists(asDirectory = false, createParents = true).writeBytes(stream.bytes)
        case None => ClaimRemoverPlugin.getLogger.warning(s"File $path doesn't exist")
      }
    }
  }
}
