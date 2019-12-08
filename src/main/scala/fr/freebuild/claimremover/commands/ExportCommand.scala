package fr.freebuild.claimremover.commands

import java.util.Date

import br.net.fabiozumbi12.RedProtect.Bukkit.Region
import com.github.tototoshi.csv.CSVWriter
import fr.freebuild.claimremover.ClaimRemoverPlugin
import better.files._
import fr.freebuild.claimremover.ClaimRemoverPlugin.configs
import fr.freebuild.claimremover.csv.CSVHandler
import fr.freebuild.claimremover.utils.PlayerUtils
import org.bukkit.command.CommandSender

import scala.collection.mutable

object ExportCommand extends Command {

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    if (ClaimRemoverPlugin.analysis == null) {
      PlayerUtils.sendMessage(sender, configs.language.errorMessages.cantExecuteCommand)
      PlayerUtils.sendMessage(sender, configs.language.errorMessages.noAnalyze)
      false
    } else {
      CSVHandler.exportAnalysis(ClaimRemoverPlugin.analysis)
      true
     }
  }
}
