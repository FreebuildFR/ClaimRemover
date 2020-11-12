package fr.freebuild.claimremover.configurations.models

case class ClaimSize(enable: Boolean, maxClaimSize: Int, totalClaimSize: Int)

case class Period(years: Long, months: Long, days: Long, hours: Long, minutes: Long)

object Period {
  /**
   * Get the inactivity period to milliseconds from Period
   *
   * @param period Period to convert
   * @return Time in milliseconds
   */
  def getInactivity(period: Period): Long = {
    val years: Long = period.years * 365 * 24 * 60 * 60 * 1000
    val months: Long = period.months * 30 * 24 * 60 * 60 * 1000
    val days: Long = period.days * 24 * 60 * 60 * 1000
    val hours: Long = period.hours * 60 * 60 * 1000
    val minutes: Long = period.minutes * 60 * 1000
    years + months + days + hours + minutes
  }
}

case class Inactivity(enable: Boolean, period: Period)

case class Bans(enable: Boolean)

case class Permissions(var enable: Boolean, ignoreGroups: List[String])

/**
 * Config file
 */
case class Config(claimSize: ClaimSize, inactivity: Inactivity, bans: Bans, permissions: Permissions)
