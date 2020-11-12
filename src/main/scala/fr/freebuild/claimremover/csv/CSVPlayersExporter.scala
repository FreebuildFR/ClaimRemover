package fr.freebuild.claimremover.csv

import better.files.File
import fr.freebuild.claimremover.ClaimRemoverPlugin.configs
import fr.freebuild.claimremover.RegionsAnalysis
import fr.freebuild.claimremover.utils.PlayerUtils
import ru.tehkode.permissions.bukkit.PermissionsEx

import scala.collection.mutable
import scala.jdk.CollectionConverters._

object CSVPlayersExporter extends CSVExporter[RegionsAnalysis, List[String]] {
  override val fileName: String = "players.csv"
  override private[csv] val header = List("PlayerName", "UUID", "Permission groups", "Last Connection")

  /**
   * Export list of affected players from analysis to csv file
   *
   * @param file File on which write the list of players
   * @param analysis Analysis to export
   */
  override def exportCSV(file: File, analysis: RegionsAnalysis): Unit = {
    val writer = initWriter(file)
    val affectedPlayers = analysis.regions.foldLeft(new mutable.HashSet[(String, String, String, Long)]())((acc, region) => {
      acc.addAll(region.getLeaders.asScala.map(player => (player.getPlayerName, player.getUUID, getPermissionGroups(player.getUUID), PlayerUtils.getLastConnection(player.getUUID).getTime)))
      acc
    }).toSet
    affectedPlayers.foreach(player => writer.writeRow(player.productIterator.toList))
    writer.close()

  }

  override def importCSV(file: File, analysisName: String): List[String] = List.empty

  /**
   * Get PEX groups of player
   *
   * @param playerUUID Uuid of a player
   * @return Groups serialized
   */
  private def getPermissionGroups(playerUUID: String): String = {
    if (configs.config.permissions.enable)
      PermissionsEx.getUser(playerUUID).getGroupNames.mkString(", ")
    else ""
  }
}
