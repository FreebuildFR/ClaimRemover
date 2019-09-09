package fr.freebuild.claimremover

import fr.freebuild.claimremover.commands.{AnalyzeCommand, DeleteCommand, ExportCommand, ImportCommand, ReloadCommand}
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
          case "analyze" => AnalyzeCommand.execute(sender, args)
          case "export" => ExportCommand.execute(sender, args)
          case "import" => ImportCommand.execute(sender, args)
          case "delete" => DeleteCommand.execute(sender, args)
          case "reload" => ReloadCommand.execute(sender, args)
          case _ => false
        }
    }
}
