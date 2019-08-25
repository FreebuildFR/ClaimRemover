package fr.freebuild.claimremover.langs

import org.bukkit.configuration.file.YamlConfiguration

object LangsUtils {
  var langConfig: YamlConfiguration = new YamlConfiguration();

  def get(path: String): String = LangsUtils.getOrElse(path, "MISSING VAR " + path)

  def getOrElse(path: String, default: String): String = langConfig.getString(path, default)
}
