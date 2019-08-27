package fr.freebuild.claimremover.commands

trait Command {
  def execute(args: Array[String]): Boolean
}
