package fr.freebuild.claimremover.configurations.models

case class ClaimSize(enable: Boolean, maxClaimSize: Int, totalClaimSize: Int)

case class Period(years: Int, months: Int, days: Int, hours: Int, minutes: Int)

object Period {
  /**
   * Get the inactivity period to milliseconds from Period
   *
   * @param period Period to convert
   * @return Time in milliseconds
   */
  def getInactivity(period: Period): Long = {
    val years = period.years * 365  * 24 * 60 * 60 * 1000
    val months = period.months * 30 * 24 * 60 * 60 * 1000
    val days = period.days * 24 * 60 * 60 * 1000
    val hours = period.hours * 60 * 60 * 1000
    val minutes = period.minutes * 60 * 1000
    years + months + days + hours + minutes
  }
}

case class Inactivity(enable: Boolean, period: Period)

case class Bans(enable: Boolean)

case class Permissions(enable: Boolean, ignoreGroups: List[String])

/**
 * Config file
 */
case class Config(claimSize: ClaimSize, inactivity: Inactivity, bans: Bans,  permissions: Permissions)
