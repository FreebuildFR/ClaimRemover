package fr.freebuild.claimremover.csv

import better.files.File
import com.github.tototoshi.csv.CSVWriter
import fr.freebuild.claimremover.utils.FileUtils

trait CSVExporter[T, U] {
  val fileName: String
  private[csv] val header: List[String]

  def exportCSV(file: File, analysis: T)
  def importCSV(file: File, analysisName: String): U

  def loadFileAndCreate(analysisName: String): File = FileUtils.loadFile(FileUtils.buildPath(fileName, analysisName), create = true)
  def loadFile(analysisName: String): File = FileUtils.loadFile(FileUtils.buildPath(fileName, analysisName))
  def initWriter(file: File): CSVWriter = {
    val writer =  CSVWriter.open(file.toJava)
    writer.writeRow(header)
    writer
  }
}
