package fr.freebuild.claimremover.configurations.models

case class InfoMessages(
  pluginReloading: String,
  pluginReloaded: String,
  affectedClaims: String,
  claimsToDelete: String,
  claimsDeleted: String,
  confirmDelete: String,
  startAnalyze: String,
  endAnalyze: String,
  startExport: String,
  endExport: String,
  endImport: String
)

case class InfoDisplay(
  analysisDate: String,
  playersNumber: String,
  claimsNumber: String,
  worldDetails: String
)

case class ErrorMessages(
  pluginMissing: String,
  disablePermissionCheck: String,
  permissionNotAllowed: String,
  invalidCommand: String,
  cantExecuteCommand: String,
  playerOnly: String,
  noAnalyze: String
)

case class HelpCommand(
  usage: String,
  help: String,
  reload: String,
  version: String,
  `import`: String
)

case class Commands(
  seeHelp: String,
  helpCommand: HelpCommand
)

/**
 * Language file
 */
case class Language(
  infoMessages: InfoMessages,
  infoDisplay: InfoDisplay,
  errorMessages: ErrorMessages,
  commands: Commands
)
