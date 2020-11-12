package fr.freebuild.claimremover.csv

import better.files.File
import br.net.fabiozumbi12.RedProtect.Bukkit.Region
import br.net.fabiozumbi12.RedProtect.Core.region.PlayerRegion
import com.github.tototoshi.csv.CSVReader
import fr.freebuild.claimremover.{ClaimRemoverPlugin, RegionsAnalysis}
import org.bukkit.Location

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Try}

object CSVRegionsExporter extends CSVExporter[RegionsAnalysis, RegionsAnalysis] {
  override val fileName: String = "regions.csv"
  override val header: List[String] = List("Name", "World", "Size", "StartLocation", "EndLocation", "Leaders", "Admins")
  private val keyValueRegex = "(.+)=(.+)".r

  /**
   * Export analysis to csv file
   *
   * @param file File on which write the analysis
   * @param analysis Analysis to export
   */
  override def exportCSV(file: File, analysis: RegionsAnalysis): Unit = {
    val writer = initWriter(file)
    analysis.regions.foreach(reg => writer.writeRow(serializeRegion(reg)))
    writer.close()
  }

  /**
   * Import analysis from file
   *
   * @param file CSV file that contains analysis
   * @param analysisName Name of analysis
   * @return The analysis built from file
   */
  override def importCSV(file: File, analysisName: String): RegionsAnalysis = {
    val reader = CSVReader.open(file.toJava)
    val lines = reader.allWithHeaders()
    val regions = lines.flatMap(deserializeRegion)

    RegionsAnalysis(regions, analysisName)
  }

  /**
   * Serialize a redprotect Region to a list of value for csv columns
   *
   * @param region Region to build
   * @return List of value for one row of csv
   */
  private def serializeRegion(region: Region): List[String] = {
    List(
      region.getName,
      region.getWorld,
      region.getArea.toString,
      serializeLocation(region.getMinLocation),
      serializeLocation(region.getMaxLocation),
      region.getLeadersString,
      region.getAdminString
    )
  }

  /**
   * Serialize a location to a string
   *
   * @param location Location to serialize
   * @return Location serialized
   */
  private def serializeLocation(location: Location): String = serializeMap(location.serialize().asScala.toMap)

  /**
   * Serialize a map of key value to one string
   *
   * @param map Map to serialize
   * @return String seriliaed
   */
  private def serializeMap(map: Map[String, Any]): String = map.map[String] { case (key, value) => s"$key=$value" }.mkString(",")

  /**
   * Deserialize a row of csv to redprotect Region
   *
   * @param line Row to deserialize
   * @return Option of Region
   */
  private def deserializeRegion(line: Map[String, String]): Option[Region] = {
    (for {
      name <- Try(line("Name"))
      startLocation <- deserializeLocation(line("StartLocation"))
      endLocation <- deserializeLocation(line("EndLocation"))
      world <- Try(line("World"))
      leaders <- Try(line("Leaders"))
      admins <- Try(line("Admins"))
    } yield {
      val region = new Region(name, startLocation, endLocation, world)
      region.setLeaders(deserializeListPlayers(leaders).asJava)
      region.setAdmins(deserializeListPlayers(admins).asJava)
      region
    }).recoverWith(handleErrors(serializeMap(line))).toOption
  }

  /**
   * Deserialize a serialized location as string to a Bukkit Location
   *
   * @param location Location to deserialize
   * @return Location deserialized
   */
  private def deserializeLocation(location: String): Try[Location] = {
    val locationMap = location.split(",").map(line => {
      val keyValueRegex(key, value) = line
      (key, value.asInstanceOf[Any])
    }).toMap.asJava
    Try(Location.deserialize(locationMap))
  }

  /**
   * Deserialize a list of serialized players to a set of Player
   *
   * @param players List of players to deserialize
   * @return
   */
  private def deserializeListPlayers(players: String): Set[PlayerRegion] = {
    if (players.length > 0) {
      players.split(',').map(player => {
        val info = player.split('@')
        new PlayerRegion(info(0), info(1))
      }).toSet
    } else {
      Set.empty
    }
  }

  /**
   * Handle errors of deserialization
   *
   * @param line Line deserialize
   * @return Partial Function
   */
  private def handleErrors(line: String): PartialFunction[Throwable, Try[Region]] = {
    case e =>
      ClaimRemoverPlugin.getLogger.warning(s"Can't parse line '$line'")
      println(e)
      Failure(e)
  }

}
