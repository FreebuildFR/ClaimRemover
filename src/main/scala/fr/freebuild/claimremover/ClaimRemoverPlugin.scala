package fr.freebuild.claimremover

import fr.freebuild.claimremover.configurations.loaders._
import fr.freebuild.claimremover.configurations.models._
import xyz.janboerman.scalaloader.plugin.description.{Scala, ScalaVersion}
import xyz.janboerman.scalaloader.plugin.{ScalaPlugin, ScalaPluginDescription}
import better.files._
import fr.freebuild.claimremover.utils.PlayerUtils

@Scala(version = ScalaVersion.v2_13_0)
object ClaimRemoverPlugin
  extends ScalaPlugin(new ScalaPluginDescription("ClaimRemover", "0.0.1")) {

  private var _configs: ConfigsStore = _
  var analysis: Option[RegionsAnalysis] = None

  override def onEnable(): Unit = {
    // getServer.getPluginManager.registerEvents(PlayerJoinListener, this)
    getCommand("claimremover").setExecutor(ClaimRemoverCommandExecutor)
    loadResources()
    if (isPluginDisabled("RedProtect")) {
      PlayerUtils.sendMessage(getServer.getConsoleSender, configs.language.errorMessages.pluginMissing.format("RedProtect"))
      disable()
    } else {
      checkOptionalPlugins()
    }
  }

  override def onDisable(): Unit = {
    analysis = None
  }

  def configs: ConfigsStore = _configs

  def disable(): Unit = getPluginLoader.disablePlugin(this)

  def reload(): Unit = {
    onDisable()
    onEnable()
  }

  /**
   * Load all resources needed
   */
  private def loadResources(): Unit = {

    val store = for {
      config <- {
        saveResource("config.yml")
        ConfigLoader(s"$getDataFolder/config.yml").load
      }
      language <- {
        saveResource("language.yml")
        LanguageLoader(s"$getDataFolder/language.yml").load
      }
    } yield ConfigsStore(config, language)

    if (store.isEmpty)
      disable()
    else
      _configs = store.get

  }

  /**
   * Save one resource from it's path
   *
   * @param path Path to resource
   */
  private def saveResource(path: String): Unit =
    if (!s"$getDataFolder/$path".toFile.exists)
      saveResource(path, false)

  /**
   * Check that optional plugins are active or not to disable functionalities
   */
  private def checkOptionalPlugins(): Unit = {
    val errorMessages = configs.language.errorMessages
    if (configs.config.permissions.enable && isPluginDisabled("PermissionsEx")) {
      PlayerUtils.sendMessage(getServer.getConsoleSender,
        f"${errorMessages.pluginMissing.format("PermissionsEx")}\n${errorMessages.disablePermissionCheck}")
      configs.config.permissions.enable = false
    }


  }

  /**
   * Define if another plugin exists and is enabled
   *
   * @param pluginName Name of plugin to verify
   * @return if plugin is enabled
   */
  private def isPluginDisabled(pluginName: String): Boolean =
    Option(getServer.getPluginManager.getPlugin(pluginName)).flatMap(plugin =>
      if (plugin.isEnabled)
        Some(plugin)
      else
        None
    ).isEmpty
}
