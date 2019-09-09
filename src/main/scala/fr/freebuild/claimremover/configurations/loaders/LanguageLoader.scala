package fr.freebuild.claimremover.configurations.loaders

import io.circe.generic.auto._

import fr.freebuild.claimremover.configurations.models.Language

case class LanguageLoader(path: String) extends YamlLoader[Language] {

  /**
   * Load the wanted file
   *
   * @return An option of loaded file
   */
  override def load: Option[Language] =
    loadFromPath(path).flatMap(_.as[Language].toTry).recoverWith(handleErrors(path)).toOption
}

