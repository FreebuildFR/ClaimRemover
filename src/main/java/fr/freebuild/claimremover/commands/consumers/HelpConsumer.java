package fr.freebuild.claimremover.commands.consumers;

import org.bukkit.command.CommandSender;

import fr.freebuild.claimremover.commands.CommandConsumer;
import fr.freebuild.claimremover.commands.CommandNode;
import fr.freebuild.claimremover.utils.PlayerUtils;


/**
 * Command that show the help, with only commands available to the player.
 *
 * Command : /claimremover help
 * Permission: claimremover.commands
 */
public class HelpConsumer implements CommandConsumer {

  /**
   * Method called when consumer is executed
   *
   * @param node Command node
   * @param command Command executed
   * @param sender Command's executor (player or console)
   * @param args Arguments of command
   */
  public Boolean accept(CommandNode node, String command, CommandSender sender, String[] args) {
    PlayerUtils.sendMessageConfig(sender, "Commands.HelpCommand.Usage");
    PlayerUtils.sendMessageConfig(sender, "Commands.HelpCommand.Help");
    PlayerUtils.sendMessageConfig(sender, "Commands.HelpCommand.Version");
    PlayerUtils.sendMessageConfig(sender, "Commands.HelpCommand.Reload");
    return true;
  }
}