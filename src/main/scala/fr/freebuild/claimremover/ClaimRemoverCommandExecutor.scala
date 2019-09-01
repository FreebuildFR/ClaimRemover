package fr.freebuild.claimremover

import fr.freebuild.claimremover.commands.{AnalyzeCommand, ExportCommand, ImportCommand}
import org.bukkit.command.{Command, CommandExecutor, CommandSender}

object ClaimRemoverCommandExecutor extends CommandExecutor {

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
      if (args.length < 1)
        false
      else
        args(0) match {
          case "analyze" => AnalyzeCommand.execute(args)
          case "export" => ExportCommand.execute(args)
          case "import" => ImportCommand.execute(args)
          case "delete" => true
          case _ => false
        }
    }
}
