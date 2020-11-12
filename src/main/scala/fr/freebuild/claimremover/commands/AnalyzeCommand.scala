package fr.freebuild.claimremover.commands

import java.text.SimpleDateFormat
import java.util.{Date, UUID}

import br.net.fabiozumbi12.RedProtect.Bukkit.{RedProtect, Region}
import fr.freebuild.claimremover.ClaimRemoverPlugin.{analysis, configs}
import fr.freebuild.claimremover.RegionsAnalysis
import fr.freebuild.claimremover.configurations.models.Period
import fr.freebuild.claimremover.utils.PlayerUtils
import litebans.api.Database
import org.bukkit.command.CommandSender
import ru.tehkode.permissions.bukkit.PermissionsEx

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future, blocking}
import ExecutionContext.Implicits.global
import scala.jdk.CollectionConverters._

object AnalyzeCommand extends Command {
  private val dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss")

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    Future {
      blocking {
        PlayerUtils.sendMessage(sender, configs.language.infoMessages.startAnalyze)
        analysis = analyzeClaims(RedProtect.get().getAPI.getAllRegions.asScala)
        PlayerUtils.sendMessage(sender, configs.language.infoMessages.endAnalyze)
        InfoCommand.execute(sender, Seq.empty)
      }
    }

    true
  }

  /**
   * Analyze RedProtect claims
   *
   * @param allRegions Regions to analyze
   * @return Return an option of analysis
   */
  private def analyzeClaims(allRegions: mutable.Set[Region]): Option[RegionsAnalysis] = {
    val maxSize = configs.config.claimSize.maxClaimSize

    val excludedPlayers = allRegions.foldLeft(new mutable.HashSet[String]())((acc, region) => {
      if (region.getArea > maxSize) {
        acc.addAll(region.getLeaders.asScala.map(_.getUUID))
      }
      acc
    }).toSet

    Some(RegionsAnalysis(
      allRegions.filter(canBeDeleted(_, excludedPlayers)).toList,
      dateFormat.format(new Date())
    ))
  }

  /**
   * Define if a claim can be deleted
   *
   * @param region          Claim
   * @param excludedPlayers Set of excluded players
   * @return
   */
  private def canBeDeleted(region: Region, excludedPlayers: Set[String]): Boolean = {
    val leaders = region.getLeaders.asScala.map(_.getUUID).toSet
    val inactivity = Period.getInactivity(configs.config.inactivity.period)
    isNotExcluded(leaders, excludedPlayers) && isPermissionGroupsIgnored(leaders) && isInactive(leaders, inactivity)
  }

  /**
   * Define if all leaders are not excluded
   *
   * @param leaders         Set of leaders
   * @param excludedPlayers Set of excluded players
   * @return True if none of leaders are excluded
   */
  private def isNotExcluded(leaders: Set[String], excludedPlayers: Set[String]): Boolean =
    leaders.forall(!excludedPlayers.contains(_))

  /**
   * Define all leaders has not ignored groups
   *
   * @param leaders Set of leaders
   * @return True if none of leaders has ignored group
   */
  private def isPermissionGroupsIgnored(leaders: Set[String]): Boolean =
    !configs.config.permissions.enable ||
      leaders.map(PermissionsEx.getUser).forall(_.getGroupNames.intersect(configs.config.permissions.ignoreGroups).length == 0)

  /**
   * Define if leaders are inactive since a specific period
   *
   * @param leaders    Set of leaders affected
   * @param inactivity Inactivity in milliseconds
   * @return True if all leaders are inactive since a period
   */
  private def isInactive(leaders: Set[String], inactivity: Long): Boolean =
    !configs.config.inactivity.enable ||
      leaders.forall((uuid) => new Date().getTime - inactivity > PlayerUtils.getLastConnection(uuid).getTime)

  /**
   * Define if all leaders are banned
   * "SELECT * FROM {bans} WHERE uuid=? AND until<=0 AND active=true"
   *
   * @param leaders Set of leaders affected
   * @return True if all leaders are banned
   */
  // TODO Not yet used
  private def isBanned(leaders: Set[String]): Boolean =
    !configs.config.bans.enable ||
      leaders.forall((uuid) => Database.get.isPlayerBanned(UUID.fromString(uuid), null))
}
