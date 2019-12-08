package fr.freebuild.claimremover.utils

import java.io.FileNotFoundException
import java.nio.file.FileAlreadyExistsException

import better.files.File
import better.files._
import fr.freebuild.claimremover.ClaimRemoverPlugin
import org.bukkit.command.CommandSender

import scala.util.{Failure, Try}

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
   * @param errorIfExists Define if the existence of the file must be an error
   * @param errorIfNotExists Define if the absence of the file must be an error
   * @return Try[File]
   */
  def loadFile(path: String, create: Boolean = false, errorIfExists: Boolean = false, errorIfNotExists: Boolean = false): File = {
    val file = s"${ClaimRemoverPlugin.getDataFolder}/${path}".toFile
    if (errorIfExists && file.exists) throw new FileAlreadyExistsException(s"File ${path} already exists")
    if (errorIfNotExists && !file.exists) throw new FileNotFoundException(s"File ${path} doesn't exists")
    if (create) file.createFileIfNotExists(createParents = true)
    file
  }

  /**
   * Partial function to print message when error come from load of file
   *
   * @param sender Sender of command
   * @return PartialFunction
   */
  def handleErrors(sender: CommandSender): PartialFunction[Throwable, Try[Any]] = {
    case e @ (_:FileNotFoundException | _:FileAlreadyExistsException) =>
      PlayerUtils.sendMessage(sender, s"&c${e.getMessage}")
      Failure(e)
  }
}
