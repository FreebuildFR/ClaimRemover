package fr.freebuild.claimremover.commands

import fr.freebuild.claimremover.ClaimRemoverPlugin.{analysis, configs}
import fr.freebuild.claimremover.csv.CSVHandler
import fr.freebuild.claimremover.utils.{FileUtils, PlayerUtils}
import org.bukkit.command.CommandSender

import scala.util.Try

object ExportCommand extends Command {

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    analysis match {
      case Some(analysis) => {
        PlayerUtils.sendMessage(sender, configs.language.infoMessages.startExport)
        Try {
          CSVHandler.exportAnalysis(analysis)
          PlayerUtils.sendMessage(sender, configs.language.infoMessages.endExport)
        }.recoverWith(FileUtils.handleErrors(sender))
        true
      }
      case None => {
        PlayerUtils.sendMessage(sender, configs.language.errorMessages.cantExecuteCommand)
        PlayerUtils.sendMessage(sender, configs.language.errorMessages.noAnalyze)
        false
      }
    }
  }
}
