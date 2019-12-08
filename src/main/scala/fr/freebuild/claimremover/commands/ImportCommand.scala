package fr.freebuild.claimremover.commands

import better.files._
import br.net.fabiozumbi12.RedProtect.Bukkit.Region
import com.github.tototoshi.csv.{CSVReader, CSVWriter}
import fr.freebuild.claimremover.ClaimRemoverPlugin
import fr.freebuild.claimremover.csv.CSVHandler
import org.bukkit.command.CommandSender

object ImportCommand extends Command {

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    //val file = s"${ClaimRemoverPlugin.getDataFolder}/exports/regions".toFile
    //CSVHandler.importAnalysis(file.toJava)
    /*
    val reader =  CSVReader.open(file.toJava)
    val t = reader.allWithOrderedHeaders()
    println("FIRST")
    t._1.foreach(println(_))
    println("SECOND")
    t._2.foreach(println(_))
    */
    // reader.foreach(line => System.out.println(line))
    true
  }


}
