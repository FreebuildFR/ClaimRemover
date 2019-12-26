package fr.freebuild.claimremover.csv
import better.files.File
import fr.freebuild.claimremover.RegionsAnalysis
import ru.tehkode.permissions.bukkit.PermissionsEx

import scala.jdk.CollectionConverters._
import scala.collection.mutable

import fr.freebuild.claimremover.ClaimRemoverPlugin.configs

object CSVPlayersExporter extends CSVExporter[List[String]] {
  override val fileName: String = "players.csv"
  override private[csv] val header = List("PlayerName", "UUID", "Permission groups")

  override def exportCSV(file: File, analysis: RegionsAnalysis): Unit = {
    val writer = initWriter(file)
    val affectedPlayers = analysis.regions.foldLeft(new mutable.HashSet[(String, String, String)]())((acc, region) => {
      acc.addAll(region.getLeaders.asScala.map(player => (player.getPlayerName, player.getUUID, getPermissionGroups(player.getUUID))))
      acc
    }).toSet
    affectedPlayers.foreach(player => writer.writeRow(player.productIterator.toList))
    writer.close()

  }

  override def importCSV(file: File, analysisName: String): List[String] = List.empty

  private def getPermissionGroups(playerUUID: String): String = {
    if (configs.config.permissions.enable)
      PermissionsEx.getUser(playerUUID).getGroupNames.mkString(", ")
    else ""
  }
}
