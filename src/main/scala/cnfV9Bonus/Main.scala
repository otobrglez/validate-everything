package cnfV9Bonus

import eu.timepit.refined.*
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.And
import eu.timepit.refined.numeric.*
import eu.timepit.refined.string.*
import zio.config.*
import zio.config.magnolia.*
import zio.config.refined.*
import zio.{ConfigProvider, ZIOAppDefault}
import eu.timepit.refined.auto.autoUnwrap

type ValidPort        = Greater[1000] And LessEqual[2999]
type Port             = Int Refined ValidPort
type ValidEndpointUrl = Url And StartsWith["https://"]
type EndpointUrl      = String Refined ValidEndpointUrl

final case class AppConfig(endpointURL: EndpointUrl, port: Port)

object AppConfig:
  private val appConfigDefinition = deriveConfig[AppConfig]
  def fromEnvironment             = ConfigProvider.envProvider.load(appConfigDefinition)

object `🥔🥔🥔`:
  def callWith(config: AppConfig) =
    val text = requests.get(config.endpointURL, params = Map("port" -> config.port.toString)).text()
    println(s"Got response: $text")

object Main extends ZIOAppDefault:
  def run = for
    config <- AppConfig.fromEnvironment
    _       = `🥔🥔🥔`.callWith(config)
  yield ()
