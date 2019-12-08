package fr.freebuild.claimremover.csv

import fr.freebuild.claimremover.utils.FileUtils
import fr.freebuild.claimremover.{ClaimRemoverPlugin, RegionsAnalysis}

object CSVHandler {
  def exportAnalysis(analysis: RegionsAnalysis): Unit =  {
    CSVRegionsExporter.exportCSV(CSVRegionsExporter.loadFile(analysis.name), analysis)
    CSVPlayersExporter.exportCSV(CSVPlayersExporter.loadFile(analysis.name), analysis)
  }

  def importAnalysis(path: String): RegionsAnalysis = {
    CSVRegionsExporter.importCSV(FileUtils.loadFile(path))
  }

}
