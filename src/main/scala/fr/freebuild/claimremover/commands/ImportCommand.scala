package fr.freebuild.claimremover.commands

import better.files._
import br.net.fabiozumbi12.RedProtect.Bukkit.Region
import com.github.tototoshi.csv.{CSVReader, CSVWriter}
import fr.freebuild.claimremover.ClaimRemoverPlugin
import org.bukkit.command.CommandSender

object ImportCommand extends Command {

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    System.out.println("Import")

    val file = s"${ClaimRemoverPlugin.getDataFolder}/exports/regions".toFile
    val reader =  CSVReader.open(file.toJava)
    reader.foreach(line => System.out.println(line))
    true
  }
}
