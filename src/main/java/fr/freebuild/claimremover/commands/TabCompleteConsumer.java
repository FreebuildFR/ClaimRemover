package fr.freebuild.claimremover.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface TabCompleteConsumer {
  /**
   * Method called for tab completion
   *
   * @param sender Command's executor (player or console)
   * @param args Arguments of command
   */
  List<String> apply(CommandSender player, String[] args);
}