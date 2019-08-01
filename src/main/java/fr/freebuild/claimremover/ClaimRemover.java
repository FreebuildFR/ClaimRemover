package fr.freebuild.claimremover;

import org.bukkit.plugin.java.JavaPlugin;


public class ClaimRemover extends JavaPlugin {
  public static ClaimRemover plugin;

  public ClaimRemover() {
    ClaimRemover.plugin = this;
  }


  /**
   * Called when the plugin is enabling
   */
  @Override
  public void onEnable() {
    this.saveResource("config.yml", false);
  }
}