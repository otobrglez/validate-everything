package cnfV7

import zio.prelude.Validation
import eu.timepit.refined.*
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.And
import eu.timepit.refined.numeric.*
import eu.timepit.refined.predicates.all.{Contains, Not}
import eu.timepit.refined.string.*

import scala.util.Try

type ValidPort        = Greater[1000] And LessEqual[2999]
type Port             = Int Refined ValidPort
type ValidEndpointUrl = Url And StartsWith["https://"]
type EndpointUrl      = String Refined ValidEndpointUrl
final case class Config private (endpointUrl: EndpointUrl, port: Port)

enum ConfigError(message: String) extends Throwable(message):
  case MissingKey(name: String)              extends ConfigError(s"Missing key: $name")
  case BrokenURL(url: String)(error: String) extends ConfigError(s"Broken URL: $url with error: $error")
  case InvalidPort(message: String)          extends ConfigError(message)
import ConfigError.*

object Config:
  private def readPort(raw: String): Validation[InvalidPort, Port] =
    Try(raw.toInt).toEither.fold(
      error => Validation.fail(InvalidPort(s"Invalid port: '$raw' - $error")),
      port => Validation.fromEither(refineV[ValidPort](port)).mapError(InvalidPort.apply)
    )

  private def readURL(raw: String): Validation[BrokenURL, EndpointUrl] =
    Validation.fromEither(refineV[ValidEndpointUrl](raw)).mapError(BrokenURL(raw))

  private def readEnv(name: String): Validation[MissingKey, String] =
    Validation
      .fromOption(Option(System.getenv(name)).filter(_.nonEmpty))
      .mapError(_ => MissingKey(name))

  def fromEnvironmentAll: Validation[ConfigError, Config] =
    Validation.validateWith(
      readEnv("ENDPOINT_URL").flatMap(readURL),
      readEnv("PORT").flatMap(readPort)
    )(Config.apply)

object `🥔🥔🥔`:
  def callWith(config: Config) =
    val text = requests.get(config.endpointUrl.toString, params = Map("port" -> config.port.toString)).text()
    println(s"Got response: $text")

@main def hello(): Unit =
  Config.fromEnvironmentAll.toEitherWith(_.toList) match
    case Left(errors)  => errors.foreach(System.err.println)
    case Right(config) => `🥔🥔🥔`.callWith(config)
