package fr.freebuild.claimremover.utils

import java.util.{Date, UUID}

import fr.freebuild.claimremover.ClaimRemoverPlugin
import fr.freebuild.claimremover.utils.ColorUtils._
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender

import scala.util.Try

object PlayerUtils {
  def getLastConnection(uuid: String): Date = {
    Try {
      val player = getOfflinePlayer(uuid)
      if (player.isOnline)
        new Date()
      else
        new Date(player.getLastPlayed)
    } getOrElse (new Date())
  }

  def getOfflinePlayer(uuid: String): OfflinePlayer = ClaimRemoverPlugin.getServer.getOfflinePlayer(UUID.fromString(uuid))

  def sendMessage(sender: CommandSender, message: String): Unit = sender.sendMessage(message.toColor)
}
