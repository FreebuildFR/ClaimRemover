package fr.freebuild.claimremover.permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.freebuild.claimremover.utils.PlayerUtils;

/**
 * Class that handle permissions of plugin
 */
public enum Permissions {
  CMD("claimremover.commands");

  private String perm;

  /**
   * Enum constructor
   * @param perm String permission
   */
  Permissions(String perm) {
    this.perm = perm;
  }

  /**
   * Define if the permission is set
   *
   * @param player Player on which check the permissions
   * @param permission Permission to check
   * @return Return a boolean to define if the permission is set
   */
  public static Boolean isSetOn(Player player, String permission) {
    return (player != null && player.hasPermission(permission));
  }

  /**
   * Define if the permission is set
   *
   * @param player Player on which check the permissions
   * @return Return a boolean to define if the permission is set
   */
  public Boolean isSetOn(Player player) {
    return (player != null && player.hasPermission(this.perm));
  }

  /**
   * Define if the permission is set on the CommandSender
   * If the CommandSender is not a Player (ex: console) the result is set with the value
   * of param defaultSender
   *
   * @param sender CommandSender on which check the permissions
   * @param defaultSender Value returned if sender is not Player
   * @return Return a boolean to define if the permission is set
   */
  public Boolean isSetOn(CommandSender sender, Boolean defaultSender) {
    if (sender instanceof Player)
      return this.isSetOn((Player) sender);
    return defaultSender;
  }

  /**
   * Define if the permission is set on the CommandSender
   * If the CommandSender is not a Player (ex: console) the result is set to true
   *
   * @param sender CommandSender on which check the permissions
   * @return Return a boolean to define if the permission is set
   */
  public Boolean isSetOn(CommandSender sender) {
    return this.isSetOn(sender, true);
  }

  /**
   * Define if the permission is set and print an error message
   *
   * @param player Player on which check the permissions
   * @return Return a boolean to define if the permission is set
   */
  public Boolean isSetOnWithMessage(Player player) {
    final Boolean isSet = this.isSetOn(player);

    if (!isSet) {
      Permissions.sendMessage(player);
    }

    return isSet;
  }

  /**
   * Send permission denied to the player
   *
   * @param player Player that must receive the message
   */
  public static final void sendMessage(Player player) {
    PlayerUtils.sendMessageConfig(player, "ErrorMessages.PermissionNotAllowed");
  }
}
