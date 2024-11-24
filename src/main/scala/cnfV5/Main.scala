package cnfV5

import cats.Semigroup
import cats.data.NonEmptyList
import cats.implicits.*
import cats.syntax.all.*
import eu.timepit.refined.*
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.And
import eu.timepit.refined.numeric.*
import eu.timepit.refined.string.*

type ValidPort   = And[Greater[0], LessEqual[9999]]
type Port        = Int Refined ValidPort
type EndpointUrl = String Refined Url
final case class Config(endpointUrl: EndpointUrl, port: Port)

enum ConfigError(message: String) extends Throwable(message):
  case MissingKey(name: String)     extends ConfigError(s"Missing key: $name")
  case BrokenURL(url: String)       extends ConfigError(s"Broken URL: $url")
  case InvalidPort(message: String) extends ConfigError(message)
  case AllErrors(configErrors: NonEmptyList[ConfigError])
      extends ConfigError(s"Bunch of errors: ${configErrors.toList.mkString(", ")}")
import cnfV5.ConfigError.*

object Config:
  private def readPort(raw: String): Either[InvalidPort, Port] =
    refineV[ValidPort](raw.toInt).leftMap(InvalidPort.apply)

  private def readURL(raw: String): Either[BrokenURL, EndpointUrl] =
    refineV[Url](raw).leftMap(_ => BrokenURL(raw))

  private def readEnv(name: String): Either[MissingKey, String] =
    Option(System.getenv(name))
      .filter(_.nonEmpty)
      .toRight(MissingKey(name))

  private given Semigroup[ConfigError] = (x: ConfigError, y: ConfigError) => AllErrors(NonEmptyList.of(x, y))

  def fromEnvironmentAll: Either[ConfigError, Config] = (
    readEnv("ENDPOINT_URL").flatMap(readURL),
    readEnv("PORT").flatMap(readPort)
  ).parMapN(Config.apply)

object `🥔🥔🥔`:
  def callWith(config: Config) =
    val text = requests.get(config.endpointUrl.toString, params = Map("port" -> config.port.toString)).text()
    println(s"Got response: $text")

@main def hello(): Unit =
  val config = Config.fromEnvironmentAll.toTry.get
  `🥔🥔🥔`.callWith(config)
