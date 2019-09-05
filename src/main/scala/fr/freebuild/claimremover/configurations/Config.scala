package fr.freebuild.claimremover.configurations

case class ClaimSize(enable: Boolean, maxClaimSize: Int)

case class Inactivity(enable: Boolean, inactivityMonths: Int)

case class Bans(enable: Boolean)

/**
 * Config file
 */
case class Config(claimSize: ClaimSize, inactivity: Inactivity, bans: Bans)
