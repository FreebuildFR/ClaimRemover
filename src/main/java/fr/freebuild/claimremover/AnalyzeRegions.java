package fr.freebuild.claimremover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.net.fabiozumbi12.RedProtect.Bukkit.Region;
import fr.freebuild.claimremover.utils.RegionUtils;
import lombok.Getter;
import lombok.Setter;

public class AnalyzeRegions {
  /* Regions to delete */
  @Getter
  private List<Region> regions = new ArrayList<>();
  @Getter @Setter
  private Integer totalClaims;


  public Set<String> getAffectedPlayers() {
    return this.regions.stream().reduce(new HashSet<String>(), (accumulator, element) -> {
      accumulator.addAll(RegionUtils.getLeaderUuids(element));
      return accumulator;
    }, (a, b) -> {
      return a;
    });
  }
}