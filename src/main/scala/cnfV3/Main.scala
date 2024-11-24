package cnfV3

import cats.implicits.catsSyntaxEitherId

import java.net.{URI, URL}
import scala.util.Try

final case class Config(
  endpointUrl: URL,
  port: Int
)

enum ConfigError(message: String) extends Throwable(message):
  case MissingKey(name: String)     extends ConfigError(s"Missing key: $name")
  case BrokenURL(url: String)       extends ConfigError(s"Broken URL: $url")
  case InvalidPort(message: String) extends ConfigError(message)
import ConfigError.*

object Config:
  private val readPort: String => Either[InvalidPort, Int] =
    _.toIntOption.toRight(InvalidPort(s"Invalid or missing port"))

  private def readURL(raw: String): Either[BrokenURL, URL] =
    Try(new URI(raw).toURL).toEither.fold(th => ConfigError.BrokenURL(raw).asLeft, _.asRight)

  private def readEnv(name: String): Either[MissingKey, String] = Option
    .when(System.getenv(name).nonEmpty)(System.getenv(name))
    .toRight(MissingKey(name))

  def fromEnvironment: Either[ConfigError, Config] = for
    endpointUrl <- readEnv("ENDPOINT_URL").flatMap(readURL)
    port        <- readEnv("PORT").flatMap(readPort)
  yield Config(endpointUrl, port)

object `🥔🥔🥔`:
  def callWith(config: Config) =
    val text = requests.get(config.endpointUrl.toString, params = Map("port" -> config.port.toString)).text()
    println(s"Got response: $text")

@main def hello(): Unit =
  val config = Config.fromEnvironment.toTry.get
  `🥔🥔🥔`.callWith(config)
