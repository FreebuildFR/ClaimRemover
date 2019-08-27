package fr.freebuild.claimremover.commands

import java.util.Date

import br.net.fabiozumbi12.RedProtect.Bukkit.{RedProtect, Region}
import fr.freebuild.claimremover.{ClaimRemoverPlugin, PlayerUtils, RegionsAnalysis}

import scala.collection.JavaConverters._
import scala.collection.mutable

object AnalyzeCommand extends Command {
  override def execute(args: Array[String]): Boolean = {
    val maxSize = ClaimRemoverPlugin.getConfig.getInt("MaxSizeClaim", 500)
    val allRegions = RedProtect.get().getAPI.getAllRegions.asScala

    val excludedPlayers = allRegions.foldLeft(new mutable.HashSet[String]())((acc, region) => {
      if (region.getArea > maxSize)
        acc.addAll(region.getLeaders.asScala.map(_.getUUID))
      acc
    }).toSet

    ClaimRemoverPlugin.analysis = RegionsAnalysis(allRegions.filter(canBeDeleted(_, excludedPlayers)).toList)
    ClaimRemoverPlugin.analysis.regions.foreach(System.out.println(_))
    true
  }

  private def canBeDeleted(region: Region, excludedPlayers: Set[String]): Boolean = {
    val leaders = region.getLeaders.asScala.map(_.getUUID)
    val inactivity = ClaimRemoverPlugin.getConfig.getInt("InactivityMonths", 12) * 1000 * 60 * 60 * 24 * 30
    (
      excludedPlayers.forall(!leaders.contains(_)) &&
      leaders.forall((uuid) => new Date().compareTo(PlayerUtils.getLastConnection(uuid)) > inactivity)
    )
  }
}
