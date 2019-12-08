package fr.freebuild.claimremover.csv
import better.files.File
import fr.freebuild.claimremover.RegionsAnalysis

import scala.jdk.CollectionConverters._
import scala.collection.mutable

object CSVPlayersExporter extends CSVExporter[List[String]] {
  override val fileName: String = "players.csv"
  override private[csv] val header = List("PlayerName", "UUID")

  override def exportCSV(file: File, analysis: RegionsAnalysis): Unit = {
    val writer = initWriter(file)
    val affectedPlayers = analysis.regions.foldLeft(new mutable.HashSet[(String, String)]())((acc, region) => {
      acc.addAll(region.getLeaders.asScala.map(player => (player.getPlayerName, player.getUUID)))
      acc
    }).toSet
    affectedPlayers.foreach(player => writer.writeRow(player.productIterator.toList))
    writer.close()

  }

  override def importCSV(file: File): List[String] = ???
}
