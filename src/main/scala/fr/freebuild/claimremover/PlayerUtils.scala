package fr.freebuild.claimremover

import java.util.{Date, UUID}

import org.bukkit.OfflinePlayer

object PlayerUtils {
  def getOfflinePlayer(uuid: String): OfflinePlayer = ClaimRemoverPlugin.getServer.getOfflinePlayer(UUID.fromString(uuid))

  def getLastConnection(uuid: String): Date = new Date(getOfflinePlayer(uuid).getLastPlayed)
}
