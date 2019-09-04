package fr.freebuild.claimremover.configurations

case class InfoMessages(pluginReloading: String, pluginReloaded: String, affectedClaims: String)

case class ErrorMessages(permissionNotAllowed: String, invalidCommand: String, playerOnly: String, noAnalyze: String)

case class HelpCommand(usage: String, help: String, reload: String, version: String)
case class Commands(seeHelp: String, helpCommand: HelpCommand)

/**
 * Language file
 */
case class Language(infoMessages: InfoMessages, errorMessages: ErrorMessages, commands: Commands)
