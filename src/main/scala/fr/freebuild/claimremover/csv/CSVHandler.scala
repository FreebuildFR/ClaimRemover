package fr.freebuild.claimremover.csv

import fr.freebuild.claimremover.RegionsAnalysis

object CSVHandler {
  def exportAnalysis(analysis: RegionsAnalysis): Unit =  {
    CSVRegionsExporter.exportCSV(CSVRegionsExporter.loadFileAndCreate(analysis.name), analysis)
    CSVPlayersExporter.exportCSV(CSVPlayersExporter.loadFileAndCreate(analysis.name), analysis)
  }

  def importAnalysis(analysisName: String): RegionsAnalysis = {
    CSVRegionsExporter.importCSV(CSVRegionsExporter.loadFile(analysisName), analysisName)
  }

}
