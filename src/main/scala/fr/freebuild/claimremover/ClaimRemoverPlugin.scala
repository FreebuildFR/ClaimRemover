package fr.freebuild.claimremover

import java.net.URL

import fr.freebuild.claimremover.configurations.{Config, ConfigLoader, Language}
import xyz.janboerman.scalaloader.plugin.description.{Scala, ScalaVersion}
import xyz.janboerman.scalaloader.plugin.{ScalaPlugin, ScalaPluginDescription}
import io.circe.yaml.parser
import io.circe._
import io.circe.generic.auto._
import cats.syntax.either._
import io.circe.yaml

@Scala(version = ScalaVersion.v2_13_0)
object ClaimRemoverPlugin
  extends ScalaPlugin(new ScalaPluginDescription("ClaimRemover", "0.0.1-SNAPSHOT")) {

  var analysis = RegionsAnalysis(null)

  override def onEnable(): Unit = {
    getServer.getPluginManager.registerEvents(PlayerJoinListener, this)
    getCommand("claimremover").setExecutor(ClaimRemoverCommandExecutor)
    this.loadResources()
  }

  /**
   * Load all resources needed
   */
  private def loadResources(): Unit = {
    Array(
      "config.yml",
      "lang.yml"
    ).foreach(ConfigLoader.saveResource(getClassLoader, _))

    val config: Config = ConfigLoader.loadResource("config.yml").as[Config].valueOr(throw _)
    val lang: Language = ConfigLoader.loadResource("lang.yml").as[Language].valueOr(throw _)

    System.out.println(config.claimSize.maxClaimSize)
    System.out.println(lang.errorMessages.noAnalyze)
  }

}
