package fr.freebuild.claimremover

import java.util

import fr.freebuild.claimremover.commands.{AnalyzeCommand, DeleteCommand, ExportCommand, ImportCommand, ReloadCommand}
import org.bukkit.command.{Command, CommandExecutor, CommandSender, TabCompleter}
import scala.jdk.CollectionConverters._
import better.files._

object ClaimRemoverCommandExecutor extends CommandExecutor with TabCompleter {
  private val mainCommands = Seq("analyze", "export", "import", "delete", "reload")
  private val deleteSubCommands = Seq("confirm")

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
        case head +: tail => head match {
          case "analyze" => AnalyzeCommand.execute(sender, tail)
          case "export" => ExportCommand.execute(sender, tail)
          case "import" => ImportCommand.execute(sender, tail)
          case "delete" => DeleteCommand.execute(sender, tail)
          case "reload" => ReloadCommand.execute(sender, tail)
          case "info" => InfoCommand.execute(sender, tail)
          case _ => false
        }
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
      case head +: Nil => filterCommands(mainCommands, head)
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
