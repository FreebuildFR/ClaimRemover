package fr.freebuild.claimremover.commands

import java.util.Date

import br.net.fabiozumbi12.RedProtect.Bukkit.Region
import com.github.tototoshi.csv.CSVWriter
import fr.freebuild.claimremover.ClaimRemoverPlugin
import better.files._
import org.bukkit.command.CommandSender

import scala.collection.mutable

object ExportCommand extends Command {
  val header = List("Name","StartLocation","EndLocation","Size","Leaders","Admins")

  override def execute(sender: CommandSender, args: Array[String]): Boolean = {
    System.out.println("Export")

    System.out.println(header);
    val file = s"${ClaimRemoverPlugin.getDataFolder}/exports/regions".toFile.createFileIfNotExists(true)
    val writer =  CSVWriter.open(file.toJava)
    writer.writeRow(header)
    ClaimRemoverPlugin.analysis.regions.foreach(reg => writer.writeRow(serializeRegion(reg)))
    writer.close()
    true
  }


  private def serializeRegion(region: Region): List[String] = {
    List(
      region.getName,
      region.getMinLocation.toString,
      region.getMaxLocation.toString,
      region.getArea.toString,
      region.getLeadersString,
      region.getAdminString
    )
  }
}
