package fr.freebuild.claimremover.commands.consumers;

import org.bukkit.command.CommandSender;

import fr.freebuild.claimremover.AnalyzeRegions;
import fr.freebuild.claimremover.ClaimRemover;
import fr.freebuild.claimremover.commands.CommandConsumer;
import fr.freebuild.claimremover.commands.CommandNode;
import fr.freebuild.claimremover.utils.LangUtils;
import fr.freebuild.claimremover.utils.PlayerUtils;


/**
 * Command that show the group of claims analyzed.
 *
 * Command : /claimremover remove
 * Permission: claimremover.commands
 */
public class RemoveConsumer implements CommandConsumer {

  /**
   * Method called when consumer is executed
   *
   * @param node Command node
   * @param command Command executed
   * @param sender Command's executor (player or console)
   * @param args Arguments of command
   */
  public Boolean accept(CommandNode node, String command, CommandSender sender, String[] args) {
    final AnalyzeRegions analyze = ClaimRemover.plugin.getAnalyze();
    if (analyze == null) {
      PlayerUtils.sendMessageConfig(sender, "ErrorMessages.NoAnalyze");
      return true;
    }
    PlayerUtils.sendMessage(sender, String.format(LangUtils.get("InfoMessages.AffectedClaims"), analyze.getTotalClaims(), analyze.getRegions().size()));;
    return true;
  }
}