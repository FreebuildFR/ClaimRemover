package fr.freebuild.claimremover.utils

import org.bukkit.ChatColor

object ColorUtils {
  implicit class ColorExtension(str: String) {
    def toColor: String = ChatColor.translateAlternateColorCodes('&', str)
  }
}
