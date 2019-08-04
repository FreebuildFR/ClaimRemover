package fr.freebuild.claimremover.utils;

import java.util.Set;
import java.util.stream.Collectors;

import br.net.fabiozumbi12.RedProtect.Bukkit.Region;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RegionUtils {

  public Set<String> getLeaderUuids(Region region) {
    return region.getLeaders().stream().map(leader -> leader.getUUID()).collect(Collectors.toSet());
  }
}