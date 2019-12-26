package fr.freebuild.claimremover.utils

import java.util.{Date, UUID}

import fr.freebuild.claimremover.ClaimRemoverPlugin
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import fr.freebuild.claimremover.utils.ColorUtils._

object PlayerUtils {
  def getOfflinePlayer(uuid: String): OfflinePlayer = ClaimRemoverPlugin.getServer.getOfflinePlayer(UUID.fromString(uuid))

  def getLastConnection(uuid: String): Date = {
    val player = getOfflinePlayer(uuid)
    if (player.isOnline)
      new Date()
    else
      new Date(player.getLastPlayed)
  }

  def sendMessage(sender: CommandSender, message: String): Unit = sender.sendMessage(message.toColor)
}
