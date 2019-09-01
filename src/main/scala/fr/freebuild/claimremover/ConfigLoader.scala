package fr.freebuild.claimremover

import java.io.{IOException, InputStream}
import java.net.URLConnection

import better.files._
import fr.freebuild.claimremover.ClaimRemoverPlugin.getClassLoader
import xyz.janboerman.scalaloader.plugin.ScalaPluginClassLoader

import scala.io.Source


object ConfigLoader {

  /**
   * Get resource from path
   *
   * @param classLoader ClassLoader to get URL of file
   * @param path Path to file
   * @return Return an option of InputStream opened
   */
  private def getResource(classLoader: ScalaPluginClassLoader, path: String): Option[InputStream] = {
    val resourceUrl = classLoader.findResource(path);
    if (resourceUrl == null)
      None
    else
      try {
        val connection = resourceUrl.openConnection
        connection.setUseCaches(false)
        Some(connection.getInputStream)
      } catch {
        case e: IOException => None
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
}
