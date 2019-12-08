package fr.freebuild.claimremover.commands

import java.text.SimpleDateFormat
import java.util.Date

import br.net.fabiozumbi12.RedProtect.Bukkit.{RedProtect, Region}
import fr.freebuild.claimremover.utils.PlayerUtils
import fr.freebuild.claimremover.ClaimRemoverPlugin.{configs, analysis}
import fr.freebuild.claimremover.RegionsAnalysis
import org.bukkit.command.CommandSender

import scala.jdk.CollectionConverters._
import scala.collection.mutable

object AnalyzeCommand extends Command {
  private val dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss")

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    PlayerUtils.sendMessage(sender, configs.language.infoMessages.startAnalyze)
    val maxSize = configs.config.claimSize.maxClaimSize
    val allRegions = RedProtect.get().getAPI.getAllRegions.asScala

    val excludedPlayers = allRegions.foldLeft(new mutable.HashSet[String]())((acc, region) => {
      if (region.getArea > maxSize)
        acc.addAll(region.getLeaders.asScala.map(_.getUUID))
      acc
    }).toSet

    analysis = RegionsAnalysis(
      allRegions.filter(canBeDeleted(_, excludedPlayers)).toList,
      dateFormat.format(new Date())
    )

    PlayerUtils.sendMessage(sender, configs.language.infoMessages.endAnalyze)
    true
  }

  private def canBeDeleted(region: Region, excludedPlayers: Set[String]): Boolean = {
    val leaders = region.getLeaders.asScala.map(_.getUUID)
    val inactivity = configs.config.inactivity.inactivityMonths * 1000 * 60 * 60 * 24 * 30
    (
      excludedPlayers.forall(!leaders.contains(_)) /*&&
      leaders.forall((uuid) => new Date().compareTo(PlayerUtils.getLastConnection(uuid)) > inactivity)*/
    )
  }
}
