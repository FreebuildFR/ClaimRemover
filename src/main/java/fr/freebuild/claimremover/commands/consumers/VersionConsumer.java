package fr.freebuild.claimremover.commands.consumers;

import org.bukkit.command.CommandSender;

import fr.freebuild.claimremover.ClaimRemover;
import fr.freebuild.claimremover.commands.CommandConsumer;
import fr.freebuild.claimremover.commands.CommandNode;
import fr.freebuild.claimremover.utils.PlayerUtils;

/**
 * Command that show the version of the plugin.
 *
 * Command : /claimremover version
 * Permission: claimremover.commands
 */
public class VersionConsumer implements CommandConsumer {

  /**
   * Method called when consumer is executed
   *
   * @param node Command node
   * @param command Command executed
   * @param sender Command's executor (player or console)
   * @param args Arguments of command
   */
  public Boolean accept(CommandNode node, String command, CommandSender sender, String[] args) {
    PlayerUtils.sendMessage(sender, ClaimRemover.plugin.getDescription().getFullName());
    return true;
  }
}