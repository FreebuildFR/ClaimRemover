package fr.freebuild.claimremover.commands

import fr.freebuild.claimremover.ClaimRemoverPlugin.configs
import fr.freebuild.claimremover.configurations.models.Period
import fr.freebuild.claimremover.utils.PlayerUtils
import org.bukkit.command.CommandSender

object ConfigCommand extends Command {
  val separator = "&3==================&7"
  val enabledConfig = "&f[&7%s&f] &2Enabled&7"
  val disabledConfig = "&f[&7%s&f] &4Disabled&7"

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    PlayerUtils.sendMessage(sender,
      s"""|${separator}
          |${getConfigTitle("ClaimSize", configs.config.claimSize.enable)}
          |  &6maxClaimSize:&7 ${configs.config.claimSize.maxClaimSize}
          |${getConfigTitle("Inactivity", configs.config.inactivity.enable)}
          |  &6period:&7 ${getInactivity(configs.config.inactivity.period)}
          |${getConfigTitle("Permissions", configs.config.permissions.enable)}
          |  &6ignoreGroups:&7 ${configs.config.permissions.ignoreGroups.mkString(", ")}
          |${getConfigTitle("Bans", configs.config.bans.enable)}
          |${separator}""".stripMargin
    )
    true
  }

  private def getConfigTitle(name: String, enabled: Boolean): String =
    if (enabled) enabledConfig.format(name) else disabledConfig.format(name)

  private def getInactivity(period: Period) =
    f"${period.years} years ${period.months} months ${period.days} days ${period.hours} hours ${period.minutes} minutes"
}
