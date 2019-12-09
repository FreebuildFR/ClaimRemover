package fr.freebuild.claimremover.csv

import better.files.File
import br.net.fabiozumbi12.RedProtect.Bukkit.Region
import br.net.fabiozumbi12.RedProtect.Core.region.PlayerRegion
import com.github.tototoshi.csv.CSVReader
import fr.freebuild.claimremover.{ClaimRemoverPlugin, RegionsAnalysis}
import org.bukkit.Location

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Try}

object CSVRegionsExporter extends CSVExporter[RegionsAnalysis] {
  override val fileName: String = "regions.csv"
  override val header: List[String] = List("Name", "World", "Size", "StartLocation", "EndLocation", "Leaders", "Admins")
  private val keyValueRegex = "(.+)=(.+)".r

  override def exportCSV(file: File, analysis: RegionsAnalysis): Unit = {
    val writer = initWriter(file)
    analysis.regions.foreach(reg => writer.writeRow(serializeRegion(reg)))
    writer.close()
  }

  override def importCSV(file: File, analysisName: String): RegionsAnalysis = {
    val reader =  CSVReader.open(file.toJava)
    val lines = reader.allWithHeaders()
    val regions = lines.flatMap(deserializeRegion)

    RegionsAnalysis(regions, analysisName)
  }

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

  private def serializeLocation(location: Location): String = serializeMap(location.serialize().asScala.toMap)

  private def serializeMap(map: Map[String, Any]): String = map.map[String] { case (key, value) => s"$key=$value" }.mkString(",")

  private def deserializeLocation(location: String): Try[Location] = {
    val locationMap = location.split(",").map(line => {
      val keyValueRegex(key, value) = line
      (key, value.asInstanceOf[Any])
    }).toMap.asJava
    Try(Location.deserialize(locationMap))
  }

  private def deserializeListPlayers(line: String): Set[PlayerRegion] = {
    if (line.length > 0) {
      line.split(',').map(player => {
        val info = player.split('@')
        new PlayerRegion(info(0), info(1))
      }).toSet
    } else {
      Set.empty
    }
  }

  private def handleErrors(line: String): PartialFunction[Throwable, Try[Region]] = {
    case e =>
      ClaimRemoverPlugin.getLogger.warning(s"Can't parse line '$line'")
      println(e)
      Failure(e)
  }

}
