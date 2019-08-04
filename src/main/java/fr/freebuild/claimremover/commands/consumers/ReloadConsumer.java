package fr.freebuild.claimremover.commands.consumers;

import org.bukkit.command.CommandSender;

import fr.freebuild.claimremover.ClaimRemover;
import fr.freebuild.claimremover.commands.CommandConsumer;
import fr.freebuild.claimremover.commands.CommandNode;

/**
 * Command that reload the plugin.
 *
 * Command : /claimremover reload
 * Permission: claimremover.commands.reload
 */
public class ReloadConsumer implements CommandConsumer {

  /**
   * Method called when consumer is executed
   *
   * @param node Command node
   * @param command Command executed
   * @param sender Command's executor (player or console)
   * @param args Arguments of command
   */
  public Boolean accept(CommandNode node, String command, CommandSender sender, String[] args) {
    ClaimRemover.plugin.reload(sender);
    return true;
  }
}