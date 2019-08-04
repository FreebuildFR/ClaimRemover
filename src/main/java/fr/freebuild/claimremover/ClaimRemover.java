package fr.freebuild.claimremover;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.freebuild.claimremover.commands.CommandHandler;
import fr.freebuild.claimremover.configuration.ConfigLoader;
import fr.freebuild.claimremover.exceptions.CantLoadConfigException;
import fr.freebuild.claimremover.utils.LangUtils;
import fr.freebuild.claimremover.utils.PlayerUtils;
import lombok.Getter;
import lombok.Setter;


public class ClaimRemover extends JavaPlugin {
  public static ClaimRemover plugin;
  @Getter
  private final ConfigLoader configLoader = new ConfigLoader();
  @Getter @Setter
  private AnalyzeRegions analyze = null;


  public ClaimRemover() {
    ClaimRemover.plugin = this;
  }

  /**
   * Called when the plugin is enabling
   */
  @Override
  public void onEnable() {
    try {
      this.configLoader.loadFiles();
    } catch(CantLoadConfigException e) {
      e.printStackTrace();
      this.disable();
      return;
    }

    if (!this.isRedProtectActive()) {
      this.getServer().getLogger().warning("RedProtect is not present or active");
      this.disable();
    }

    getCommand("ClaimRemover").setExecutor(new CommandHandler());
  }

  /**
   * Reload the plugin and send message to player
   *
   * @param sender Sender to send messages
   */
  public void reload(CommandSender sender) {
    PlayerUtils.sendMessage(sender, LangUtils.get("InfoMessages.PluginReloading"));
    this.setEnabled(false);
    this.setEnabled(true);
    PlayerUtils.sendMessage(sender, LangUtils.get("InfoMessages.PluginReloaded"));
  }

  /**
   * Disable the plugin
   */
  public void disable() {
    this.setEnabled(false);
    this.getLogger().log(Level.WARNING, "Plugin ClaimRemover disabled");
  }

  private boolean isRedProtectActive(){
  	Plugin redProtect = Bukkit.getPluginManager().getPlugin("RedProtect");
  	if (redProtect != null && redProtect.isEnabled()){
  		return true;
  	}
  	return false;
  }
}