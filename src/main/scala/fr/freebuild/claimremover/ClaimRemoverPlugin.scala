package fr.freebuild.claimremover

import xyz.janboerman.scalaloader.plugin.description.{Scala, ScalaVersion}
import xyz.janboerman.scalaloader.plugin.{ScalaPlugin, ScalaPluginDescription}

@Scala(version = ScalaVersion.v2_13_0_M5)
object ClaimRemoverPlugin
    extends ScalaPlugin(new ScalaPluginDescription("_", "_")) {

    override def onEnable(): Unit = {
        getServer.getPluginManager.registerEvents(PlayerJoinListener, this)
        getCommand("claimremover").setExecutor(ClaimRemoverCommandExecutor)
    }

}
