package fr.freebuild.claimremover.commands

import fr.freebuild.claimremover.ClaimRemoverPlugin
import org.bukkit.command.CommandSender

object ClearCommand extends Command {

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    ClaimRemoverPlugin.analysis = None
    true
  }
}
