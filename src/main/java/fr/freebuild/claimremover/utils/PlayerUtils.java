package fr.freebuild.claimremover.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import fr.freebuild.claimremover.ClaimRemover;
import litebans.api.Database;
import lombok.Getter;
import lombok.experimental.UtilityClass;

/**
 * Utility Class for player action
 */
@UtilityClass
public class PlayerUtils {

  @Getter
  private static final String prefix = Utils.toColor("&d[ClaimRemover] &7");
  /**
   * Get a player from is UUID
   */
  public OfflinePlayer getOfflinePlayer(UUID playerUUID) {
    return ClaimRemover.plugin.getServer().getOfflinePlayer(playerUUID);
  }

  /**
   * Get the uuid of a player into string
   *
   * @param player
   * @return
   */
  public String getUUIDToString(OfflinePlayer player) {
    return player.getUniqueId().toString();
  }

  /**
   * Get the player name from is uuid in string format
   *
   * @param uuid
   * @return Player name
   */
  public String getPlayerName(String uuid) {
    if (uuid == null)
      return null;
    OfflinePlayer pl = PlayerUtils.getOfflinePlayer(UUID.fromString(uuid));
    if (pl.getName() == null)
      return "Unknown Player";
    return pl.getName();
  }

  /**
   * Send a message in chat to a player
   *
   * @param pl Target player of the message
   * @param message Message to send
   */
  public void sendMessage(Player pl, String message) {
    pl.sendMessage(PlayerUtils.getPrefix() + Utils.toColor(message));
  }

  /**
   * Send a message in chat to a player from a config language variable
   *
   * @param pl Target player of the message
   * @param path Language variable path
   */
  public void sendMessageConfig(Player pl, String path) {
    pl.sendMessage(PlayerUtils.getPrefix() + LangUtils.get(path));
  }

  /**
   * Send a message to a command sender (can be player and console)
   *
   * @param pl Target command sender of the message
   * @param message Message to send
   */
  public void sendMessage(CommandSender pl, String message) {
    pl.sendMessage(PlayerUtils.getPrefix() + Utils.toColor(message));
  }

  /**
   * Send a message in chat to a command sender from a config language variable
   *
   * @param pl Target command sender of the message
   * @param path Language variable path
   */
  public void sendMessageConfig(CommandSender pl, String path) {
    pl.sendMessage(PlayerUtils.getPrefix() + LangUtils.get(path));
  }

  /**
   * Send a message in chat to a player and to console
   *
   * @param pl Target command sender of the message
   * @param message Message to send
   */
  public void sendMessageAndConsole(CommandSender pl, String message) {
    if (pl instanceof Player) {
      PlayerUtils.sendMessage(pl, message);
    }
    PlayerUtils.sendMessage(ClaimRemover.plugin.getServer().getConsoleSender(), message);
  }

  /**
   * Define if there is enough place to add the item inside player inventory
   *
   * @param i Inventory of the player
   * @param item Itemstack that must be put in
   * @return
   */
  public Boolean hasEnoughPlace(PlayerInventory i, ItemStack item) {
    ItemStack[] items = i.getStorageContents();
    return Arrays.asList(items).stream().reduce(0, (res, val) -> {
      if (val == null)
        return res + item.getMaxStackSize();
      if (val.isSimilar(item))
        return res + (val.getMaxStackSize() - val.getAmount());
      return res;
    }, (s1, s2) -> s1 + s2) >= item.getAmount();
  }

  /**
   * Get the head of a player
   *
   * @param player Offline player
   * @return Return itemstack of player head
   */
  public ItemStack getPlayerHead(OfflinePlayer player) {
    final Material headMaterial = Material.getMaterial("PLAYER_HEAD");
    final ItemStack playerHead = new ItemStack(headMaterial, 1, (short) SkullType.PLAYER.ordinal());
    final SkullMeta headMeta = (SkullMeta) playerHead.getItemMeta();

    headMeta.setOwningPlayer(player);
    headMeta.setDisplayName(player.getName());
    playerHead.setItemMeta(headMeta);

    return playerHead;
  }

  /**
   * Check if the player is banned
   *
   * @param uuid Uuid of player to verify
   * @return Return if player is banned
   */
  public Boolean isBan(String uuid) {
    return Database.get().isPlayerBanned(UUID.fromString(uuid), null);
  }

  /**
   * Get the last time a player was connected
   *
   * @param uuid Uuid of player to verify
   * @return Date of the last the player was connected
   */
  public Date getLastConnection(String uuid) {
    final long date = PlayerUtils.getOfflinePlayer(UUID.fromString(uuid)).getLastPlayed();
    return new Date(date);
  }
}
