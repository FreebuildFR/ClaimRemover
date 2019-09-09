package fr.freebuild.claimremover.configurations.loaders

import io.circe.generic.auto._

import fr.freebuild.claimremover.configurations.models.Config

case class ConfigLoader(path: String) extends YamlLoader[Config] {

  /**
   * Load the wanted file
   *
   * @return An option of loaded file
   */
  override def load: Option[Config] =
    loadFromPath(path).flatMap(_.as[Config].toTry).recoverWith(handleErrors(path)).toOption
}

