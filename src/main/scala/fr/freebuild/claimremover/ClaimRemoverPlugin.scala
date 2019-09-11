package fr.freebuild.claimremover

import fr.freebuild.claimremover.configurations.loaders._
import fr.freebuild.claimremover.configurations.models._
import xyz.janboerman.scalaloader.plugin.description.{Scala, ScalaVersion}
import xyz.janboerman.scalaloader.plugin.{ScalaPlugin, ScalaPluginDescription}

@Scala(version = ScalaVersion.v2_13_0)
object ClaimRemoverPlugin
  extends ScalaPlugin(new ScalaPluginDescription("ClaimRemover", "0.0.1-SNAPSHOT")) {

  private var _configs: ConfigsStore = _
  var analysis = RegionsAnalysis(null)

  override def onEnable(): Unit = {
    // getServer.getPluginManager.registerEvents(PlayerJoinListener, this)
    getCommand("claimremover").setExecutor(ClaimRemoverCommandExecutor)
    this.loadResources()
  }

  /**
   * Load all resources needed
   */
  private def loadResources(): Unit = {

    val store = for {
      config <- {
        saveResource("config.yml", false)
        ConfigLoader(s"$getDataFolder/config.yml").load
      }
      language <- {
        saveResource("language.yml", false)
        LanguageLoader(s"$getDataFolder/language.yml").load
      }
    } yield ConfigsStore(config, language)

    if (store.isEmpty)
      disable()
    else
      _configs = store.get

  }

  def configs: ConfigsStore = _configs

  def disable(): Unit = getPluginLoader.disablePlugin(this)

  def reload(): Unit = {
    onDisable()
    onEnable()
  }
}
