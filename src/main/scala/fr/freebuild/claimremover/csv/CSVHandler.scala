package fr.freebuild.claimremover.csv

import fr.freebuild.claimremover.utils.FileUtils
import fr.freebuild.claimremover.RegionsAnalysis

object CSVHandler {
  def exportAnalysis(analysis: RegionsAnalysis): Unit =  {
    CSVRegionsExporter.exportCSV(CSVRegionsExporter.loadFile(analysis.name), analysis)
    CSVPlayersExporter.exportCSV(CSVPlayersExporter.loadFile(analysis.name), analysis)
  }

  def importAnalysis(analysisName: String): RegionsAnalysis = {
    CSVRegionsExporter.importCSV(FileUtils.loadFile(s"exports/${analysisName}/regions.csv", errorIfNotExists = true), analysisName)
  }

}
