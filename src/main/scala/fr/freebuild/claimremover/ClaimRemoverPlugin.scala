package fr.freebuild.claimremover

import java.net.URL

import xyz.janboerman.scalaloader.plugin.description.{Scala, ScalaVersion}
import xyz.janboerman.scalaloader.plugin.{ScalaPlugin, ScalaPluginDescription}

@Scala(version = ScalaVersion.v2_13_0)
object ClaimRemoverPlugin
  extends ScalaPlugin(new ScalaPluginDescription("ClaimRemover", "0.0.1-SNAPSHOT")) {

  var analysis = RegionsAnalysis(null)

  override def onEnable(): Unit = {
    getServer.getPluginManager.registerEvents(PlayerJoinListener, this)
    getCommand("claimremover").setExecutor(ClaimRemoverCommandExecutor)
  }

  /**
   * Load all resources needed
   */
  private def loadResources(): Unit = {
    Array(
      "config.yml",
      "lang.yml"
    ).foreach(ConfigLoader.saveResource(getClassLoader, _))
  }

}
