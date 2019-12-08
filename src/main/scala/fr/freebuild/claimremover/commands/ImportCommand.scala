package fr.freebuild.claimremover.commands

import fr.freebuild.claimremover.ClaimRemoverPlugin
import fr.freebuild.claimremover.ClaimRemoverPlugin.configs
import fr.freebuild.claimremover.csv.CSVHandler
import fr.freebuild.claimremover.utils.{FileUtils, PlayerUtils}
import org.bukkit.command.CommandSender

import scala.util.Try

object ImportCommand extends Command {

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    args match {
      case head +: tail => {
        Try {
          ClaimRemoverPlugin.analysis = Some(CSVHandler.importAnalysis(head))
        }.recoverWith(FileUtils.handleErrors(sender))
        true
      }
      case _ => {
        PlayerUtils.sendMessage(sender, configs.language.commands.helpCommand.`import`)
        false
      }
    }
  }
}
