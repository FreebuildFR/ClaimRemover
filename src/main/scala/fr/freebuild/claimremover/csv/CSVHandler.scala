package fr.freebuild.claimremover.csv

import fr.freebuild.claimremover.RegionsAnalysis

object CSVHandler {
  /**
   * Make all exports from analysis
   *
   * @param analysis Analysis to export
   */
  def exportAnalysis(analysis: RegionsAnalysis): Unit = {
    CSVRegionsExporter.exportCSV(CSVRegionsExporter.loadFileAndCreate(analysis.name), analysis)
    CSVPlayersExporter.exportCSV(CSVPlayersExporter.loadFileAndCreate(analysis.name), analysis)
  }

  /**
   * Import an analysis from its name
   *
   * @param analysisName Name of analysis
   * @return Analysis rebuilt
   */
  def importAnalysis(analysisName: String): RegionsAnalysis = {
    CSVRegionsExporter.importCSV(CSVRegionsExporter.loadFile(analysisName), analysisName)
  }

}
