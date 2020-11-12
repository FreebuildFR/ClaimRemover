package fr.freebuild.claimremover.commands

import fr.freebuild.claimremover.ClaimRemoverPlugin
import fr.freebuild.claimremover.utils.PlayerUtils
import org.bukkit.command.CommandSender

object VersionCommand extends Command {
  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    PlayerUtils.sendMessage(sender, ClaimRemoverPlugin.getDescription.getFullName)
    true
  }
}
