package fr.freebuild.claimremover.commands

import org.bukkit.command.CommandSender

trait Command {
  def execute(sender: CommandSender, args: Seq[String]): Boolean
}
