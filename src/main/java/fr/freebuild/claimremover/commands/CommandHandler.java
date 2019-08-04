package fr.freebuild.claimremover.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import fr.freebuild.claimremover.ClaimRemover;
import fr.freebuild.claimremover.commands.consumers.AnalyzeConsumer;
import fr.freebuild.claimremover.commands.consumers.HelpConsumer;
import fr.freebuild.claimremover.commands.consumers.ReloadConsumer;
import fr.freebuild.claimremover.commands.consumers.RemoveConsumer;
import fr.freebuild.claimremover.commands.consumers.VersionConsumer;
import fr.freebuild.claimremover.permissions.Permissions;
import fr.freebuild.claimremover.utils.PlayerUtils;

public class CommandHandler implements CommandExecutor, TabCompleter {

  private CommandNode command = new CommandNode("claimremover", Permissions.CMD, false, false);

  public CommandHandler() {
    // Help - /claimremover help
    this.command.setCommand(new HelpConsumer());
    this.command.addSubNode(new CommandNode("help", Permissions.CMD, false, false))
      .setCommand(new HelpConsumer());

    // Version - /claimremover version
    this.command.addSubNode(new CommandNode("version", Permissions.CMD, false, false))
      .setCommand(new VersionConsumer());

    // Reload - /claimremover reload
    this.command.addSubNode(new CommandNode("reload", Permissions.CMD, false, false))
      .setCommand(new ReloadConsumer());

    // Analyze - /claimremover analyze
    this.command.addSubNode(new CommandNode("analyze", Permissions.CMD, false, false))
      .setCommand(new AnalyzeConsumer());

    // Remove - /claimremover remove
    this.command.addSubNode(new CommandNode("remove", Permissions.CMD, false, false))
      .setCommand(new RemoveConsumer());

  }

  /**
   * Override onCommand from CommandExecutor - Execute the command claimremover
   */
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
    Boolean succeed = this.command.execute(String.format("/%s %s", msg, StringUtils.join(args, " ")), sender, args);
    if (!succeed) {
      PlayerUtils.sendMessageConfig(sender, "Commands.SeeHelp");
    }
    return succeed;
  }

  /**
   * Override onTabComplete from TabCompleter - return the list of parameters for tab completion
   */
  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {
    return this.command.onTabComplete(sender, args);
  }
}
