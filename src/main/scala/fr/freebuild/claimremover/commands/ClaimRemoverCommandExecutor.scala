package fr.freebuild.claimremover.commands

import org.bukkit.command.{CommandExecutor, CommandSender}

object ClaimRemoverCommandExecutor extends CommandExecutor {

    override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
        args match {
            case head +: tail => head match {
                case "analyze" => AnalyzeCommand.execute(tail)
                case "export" => true
                case "import" => true
                case "delete" => true
                case _ => false
            }
            case _ => false
        }
    }
}
