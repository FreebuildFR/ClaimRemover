package fr.freebuild.claimremover.commands

import fr.freebuild.claimremover.ClaimRemoverPlugin
import fr.freebuild.claimremover.ClaimRemoverPlugin.configs
import fr.freebuild.claimremover.utils.PlayerUtils
import org.bukkit.command.CommandSender

object ClearCommand extends Command {

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    ClaimRemoverPlugin.analysis = None
    PlayerUtils.sendMessage(sender, configs.language.infoMessages.analyzeRemoved)
    true
  }
}
