package fr.freebuild.claimremover

import java.util

import fr.freebuild.claimremover.commands.{AnalyzeCommand, ClearCommand, ConfigCommand, DeleteCommand, ExportCommand, ImportCommand, InfoCommand, ReloadCommand, VersionCommand}
import fr.freebuild.claimremover.Permissions
import org.bukkit.command.{Command, CommandExecutor, CommandSender, TabCompleter}

import scala.jdk.CollectionConverters._
import better.files._

object ClaimRemoverCommandExecutor extends CommandExecutor with TabCompleter {
  private val mainCommands = Seq("analyze", "config", "clear", "export", "import", "info", "delete", "reload", "version")
  private val deleteSubCommands = Seq("confirm")
  private val commands = Map(
    "analyze" -> (Permissions.CMD_ANALYZE, AnalyzeCommand),
    "export" -> (Permissions.CMD_EXPORT, ExportCommand),
    "import" -> (Permissions.CMD_IMPORT, ImportCommand),
    "delete" -> (Permissions.CMD_DELETE, DeleteCommand),
    "reload" -> (Permissions.CMD_RELOAD, ReloadCommand),
    "info" -> (Permissions.CMD_INFO, InfoCommand),
    "config" -> (Permissions.CMD_CONFIG, ConfigCommand),
    "clear" -> (Permissions.CMD_CLEAR, ClearCommand),
    "version" -> (Permissions.CMD_VERSION, VersionCommand)
  )

  /**
   * Called when ClaimRemover command is executed
   *
   * @param sender Command sender
   * @param command Command
   * @param label Label
   * @param args Parameters after command
   * @return
   */
    override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
      args.toSeq match {
        case head +: tail =>
          commands.get(head).exists(command => {
            val (permission, executor) = command
            if (permission.isSetOnWithMessage(sender)) executor.execute(sender, tail) else true
          })
        case _ => false
      }
    }

  /**
   * Called when tab complete the command
   *
   * @param sender Command sender
   * @param command Command
   * @param alias Alias
   * @param args Parameters after command
   * @return
   */
  override def onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array[String]): util.List[String] = {
    (args.toSeq match {
      case head +: Nil => filterCommands(commands, head, sender)
      case "delete" +: tail => tail match {
        case deleteHead +: Nil => filterCommands(deleteSubCommands, deleteHead)
        case _ => Seq.empty
      }
      case "import" +: tail => tail match {
        case importHead +: Nil => filterAnalysisDirectory(importHead)
        case _ => Seq.empty
      }
      case _ => Seq.empty
    }).toList.asJava
  }

  /**
   * Filter a Seq of commands with startwith search
   *
   * @param commands List of commands
   * @param search Search sentence
   * @return Sequence filtered
   */
  private def filterCommands(commands: Seq[String], search: String) = commands.filter(_.startsWith(search))

  /**
   * Filter a Seq of main commands with startwith search
   *
   * @param commands List of commands
   * @param search Search sentence
   * @param sender Command executor
   * @return Sequence filtered
   */
  private def filterCommands(commands: Map[String, (Permission, fr.freebuild.claimremover.commands.Command)], search: String, sender: CommandSender) = commands.filter(cmd => {
    val (name, command) = cmd
    name.startsWith(search) && command._1.isSetOn(sender)
  }).keys

  /**
   * Filter analysis directory inside exports directory
   * @param search Directory analysis name to search
   * @return Directory listed
   */
  private def filterAnalysisDirectory(search: String): Seq[String] = {
    Option(s"${ClaimRemoverPlugin.getDataFolder}/exports/".toFile).filter(_.exists).map(file =>
      file.list(_.name.startsWith(search), 1).filterNot(_ == file).map(_.name).toSeq
    ).getOrElse(Seq.empty)
  }
}
