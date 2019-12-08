package fr.freebuild.claimremover.utils

import better.files.File
import better.files._
import fr.freebuild.claimremover.ClaimRemoverPlugin

object FileUtils {
  /**
   * Build the path to file from filename and date
   *
   * @param fileName Name of file
   * @param analysisName Name of analysis to use to build the directory
   * @return The path built
   */
  def buildPath(fileName: String, analysisName: String): String = s"exports/${analysisName}/${fileName}"

  /**
   * Load file from path
   *
   * @param path Path to file
   * @param create Define if the path must be created when load it
   * @return File
   */
  def loadFile(path: String, create: Boolean = false): File = {
    val file = s"${ClaimRemoverPlugin.getDataFolder}/${path}".toFile
    if (create) file.createFileIfNotExists(createParents = true)
    file
  }
}
