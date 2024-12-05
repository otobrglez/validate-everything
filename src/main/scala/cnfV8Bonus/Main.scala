package cnfV8Bonus

import zio.config.*
import zio.{Config, ConfigProvider, ZIOAppDefault}

import java.net.URI

final case class AppConfig(endpointUrl: URI, port: Int)

object AppConfig:
  private val appConfigDefinition = (Config.uri("ENDPOINT_URL") zip Config.int("PORT")).to[AppConfig]
  def fromEnvironment             = ConfigProvider.envProvider.load(appConfigDefinition)

object `🥔🥔🥔`:
  def callWith(config: AppConfig) =
    val text = requests.get(config.endpointUrl.toString, params = Map("port" -> config.port.toString)).text()
    println(s"Got response: $text")

object Main extends ZIOAppDefault:
  def run = for
    config <- AppConfig.fromEnvironment
    _       = `🥔🥔🥔`.callWith(config)
  yield ()
