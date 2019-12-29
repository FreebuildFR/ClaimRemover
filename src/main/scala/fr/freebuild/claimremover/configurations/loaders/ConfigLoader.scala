package fr.freebuild.claimremover.configurations.loaders

import fr.freebuild.claimremover.configurations.models.Config
import io.circe.generic.auto._

case class ConfigLoader(path: String) extends YamlLoader[Config] {

  /**
   * Load the wanted file
   *
   * @return An option of loaded file
   */
  override def load: Option[Config] =
    loadFromPath(path).flatMap(_.as[Config].toTry).recoverWith(handleErrors(path)).toOption
}
