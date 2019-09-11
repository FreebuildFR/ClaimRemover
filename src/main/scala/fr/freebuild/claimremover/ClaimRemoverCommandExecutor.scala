package fr.freebuild.claimremover

import java.util

import fr.freebuild.claimremover.commands.{AnalyzeCommand, DeleteCommand, ExportCommand, ImportCommand, ReloadCommand}
import org.bukkit.command.{Command, CommandExecutor, CommandSender, TabCompleter}
import scala.jdk.CollectionConverters._

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
      case _ => Seq.empty
    }).toList.asJava
  }

  private def filterCommands(commands: Seq[String], search: String) = commands.filter(_.startsWith(search))
}
