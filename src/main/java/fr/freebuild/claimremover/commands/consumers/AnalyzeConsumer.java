package fr.freebuild.claimremover.commands.consumers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

import br.net.fabiozumbi12.RedProtect.Bukkit.RedProtect;
import br.net.fabiozumbi12.RedProtect.Bukkit.Region;
import br.net.fabiozumbi12.RedProtect.Core.region.PlayerRegion;
import fr.freebuild.claimremover.AnalyzeRegions;
import fr.freebuild.claimremover.ClaimRemover;
import fr.freebuild.claimremover.commands.CommandConsumer;
import fr.freebuild.claimremover.commands.CommandNode;
import fr.freebuild.claimremover.utils.PlayerUtils;
import fr.freebuild.claimremover.utils.RegionUtils;


/**
 * Command that list all shop group name.
 *
 * Command : /claimremover analyze
 * Permission: claimremover.commands.list
 */
public class AnalyzeConsumer implements CommandConsumer {

  /**
   * Method called when consumer is executed
   *
   * @param node Command node
   * @param command Command executed
   * @param sender Command's executor (player or console)
   * @param args Arguments of command
   */
  public Boolean accept(CommandNode node, String command, CommandSender sender, String[] args) {
    AnalyzeRegions ana = new AnalyzeRegions();
    Integer maxSize = ClaimRemover.plugin.getConfigLoader().getConfig().getInt("MaxSizeClaim", 500);
    Set<Region> regions = RedProtect.get().getAPI().getAllRegions();
    Set<String> toExclude = new HashSet<>();

    ana.setTotalClaims(regions.size());

    regions.forEach(reg -> {
      if (reg.getArea() > maxSize)
        toExclude.addAll(RegionUtils.getLeaderUuids(reg));
    });

    System.out.println("TO EXCLUDE");
    toExclude.forEach(e -> System.out.println("- " + PlayerUtils.getPlayerName(e)));

    ana.getRegions().addAll(regions.stream().filter(reg -> this.canBeDeleted(reg, toExclude)).collect(Collectors.toSet()));
    System.out.println("AFFECTED");
    ana.getAffectedPlayers().forEach(e -> System.out.println("- " + PlayerUtils.getPlayerName(e)));
    ClaimRemover.plugin.setAnalyze(ana);
    return true;
  }

  private Boolean canBeDeleted(Region region, Set<String> toExclude) {
    Integer maxSize = ClaimRemover.plugin.getConfigLoader().getConfig().getInt("MaxSizeClaim", 500);
    Integer inactivity = ClaimRemover.plugin.getConfigLoader().getConfig().getInt("InactivityMonths", 12);
    Set<String> leadersUuid = RegionUtils.getLeaderUuids(region);

    return (
      region.getArea() <= maxSize &&
      toExclude.stream().noneMatch(exclude -> leadersUuid.contains(exclude)) &&
      leadersUuid.stream().allMatch(uuid -> new Date().compareTo(PlayerUtils.getLastConnection(uuid)) > (inactivity * 1000 * 60 * 60 * 24 * 30))
    );
  }
}