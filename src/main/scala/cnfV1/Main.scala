package cnfV1

final case class Config(
  endpointUrl: String,
  port: Int
)

object Config:
  def fromEnvironment =
    Config(
      endpointUrl = System.getenv("ENDPOINT_URL"),
      port = System.getenv("PORT").toInt
    )

object `🥔🥔🥔`:
  def callWith(config: Config) =
    val text = requests.get(config.endpointUrl, params = Map("port" -> config.port.toString)).text()
    println(s"Got response: $text")

@main def hello(): Unit =
  val config = Config.fromEnvironment
  `🥔🥔🥔`.callWith(config)
