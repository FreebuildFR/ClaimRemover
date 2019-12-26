package fr.freebuild.claimremover.utils

import org.bukkit.ChatColor

import fr.freebuild.claimremover.ClaimRemoverPlugin.analysis

object ColorUtils {
  val pluginPrefix = "&9[ClaimRemover%s•&9] &7"

  implicit class ColorExtension(str: String) {
    def toColor: String = {
      val prefix = pluginPrefix.format(if (analysis.isEmpty) "&8" else "&a")
      ChatColor.translateAlternateColorCodes('&', f"${prefix}${str.replace("\n", f"\n${prefix}")}")
    }
  }
}
