package fr.freebuild.claimremover

import fr.freebuild.claimremover.ClaimRemoverPlugin.configs
import fr.freebuild.claimremover.utils.PlayerUtils
import org.bukkit.command.CommandSender

case class Permission(value: String) {
  def isSetOn(sender: CommandSender): Boolean = sender.hasPermission(value)

  def isSetOnWithMessage(sender: CommandSender): Boolean = {
    val isSet = isSetOn(sender)
    if (!isSet) PlayerUtils.sendMessage(sender, configs.language.errorMessages.permissionNotAllowed)
    isSet
  }
}

object Permissions {
  val CMD_ANALYZE = Permission("claimremover.commands.analyze")
  val CMD_CLEAR = Permission("claimremover.commands.clear")
  val CMD_CONFIG = Permission("claimremover.commands.config")
  val CMD_DELETE = Permission("claimremover.commands.delete")
  val CMD_EXPORT = Permission("claimremover.commands.export")
  val CMD_IMPORT = Permission("claimremover.commands.import")
  val CMD_INFO = Permission("claimremover.commands.info")
  val CMD_RELOAD = Permission("claimremover.commands.reload")
  val CMD_VERSION = Permission("claimremover.commands.version")
}
