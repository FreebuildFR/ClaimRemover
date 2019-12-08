package fr.freebuild.claimremover.csv

import java.io.File
import java.util.{Calendar, Date}

import br.net.fabiozumbi12.RedProtect.Bukkit.Region
import fr.freebuild.claimremover.utils.FileUtils
import fr.freebuild.claimremover.{ClaimRemoverPlugin, RegionsAnalysis}

import scala.util.{Failure, Success, Try}

object CSVHandler {
  def exportAnalysis(analysis: RegionsAnalysis): Unit =  {
    CSVRegionsExporter.exportCSV(CSVRegionsExporter.loadFile(analysis.name), analysis)
    CSVPlayersExporter.exportCSV(CSVPlayersExporter.loadFile(analysis.name), analysis)
  }

  def importAnalysis(path: String): RegionsAnalysis = {
    CSVRegionsExporter.importCSV(FileUtils.loadFile(path))
  }

}
