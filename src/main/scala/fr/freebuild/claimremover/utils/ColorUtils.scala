package fr.freebuild.claimremover.utils

import org.bukkit.ChatColor

object ColorUtils {
  val prefix = "&9[ClaimRemover] &7"

  implicit class ColorExtension(str: String) {
    def toColor: String = ChatColor.translateAlternateColorCodes('&', f"${prefix}${str.replace("\n", f"\n${prefix}")}")
  }
}
