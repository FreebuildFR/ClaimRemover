package fr.freebuild.claimremover.commands

import br.net.fabiozumbi12.RedProtect.Bukkit.{RedProtect, Region}
import fr.freebuild.claimremover.ClaimRemoverPlugin.{analysis, configs}
import fr.freebuild.claimremover.utils.PlayerUtils
import org.bukkit.command.CommandSender

import scala.concurrent.{ExecutionContext, Future, blocking}

object DeleteCommand extends Command {

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    analysis match {
      case Some(analysis) => {
        args.headOption match {
          case Some("confirm") => removeClaims(sender, analysis.regions)
          case _ => PlayerUtils.sendMessage(sender, configs.language.infoMessages.confirmDelete.format(analysis.regions.size))
        }
        true
      }
      case None => {
        PlayerUtils.sendMessage(sender, configs.language.errorMessages.cantExecuteCommand)
        PlayerUtils.sendMessage(sender, configs.language.errorMessages.noAnalyze)
        false
      }
    }
  }

  /**
   * Remove all claims available inside analysis
   *
   * @param sender  Player or console that executed the command
   * @param regions Regions analyzed
   */
  private def removeClaims(sender: CommandSender, regions: List[Region]): Unit = {
    Future {
      blocking {
        PlayerUtils.sendMessage(sender, configs.language.infoMessages.claimsToDelete.format(regions.size))
        val gap = Math.round(regions.size / 10)
        regions.zipWithIndex.foreach {
          case (region, index) => {
            if (gap > 0 && (index + 1) % gap == 0) {
              PlayerUtils.sendMessage(sender, configs.language.infoMessages.claimsDeleted.format(index + 1, regions.size))
            }
            RedProtect.get().getAPI.removeRegion(region)
          }
        }
        PlayerUtils.sendMessage(sender, configs.language.infoMessages.claimsDeleted.format(regions.size, regions.size))
      }
    }(ExecutionContext.global)
  }
}
