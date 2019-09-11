package fr.freebuild.claimremover.commands

import org.bukkit.command.CommandSender

object DeleteCommand extends Command {

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    args.headOption match {
      case Some("confirm") => println("Yes sure")
      case _ => println("Are you sure ?")
    }
    true
  }
}
