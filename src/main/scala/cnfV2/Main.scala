package cnfV2

final case class Config(
  endpointUrl: String,
  port: Int
)

object Config:
  private val readPort: String => Either[Throwable, Int] =
    _.toIntOption.toRight(new IllegalArgumentException(s"Invalid or missing port"))

  private def readEndpointUrl(raw: String): Either[Throwable, String] =
    Option.when(raw.nonEmpty)(raw).toRight(new IllegalArgumentException(s"Invalid or missing endpoint URL"))

  def fromEnvironment: Either[Throwable, Config] = for
    endpointUrl <- readEndpointUrl(System.getenv("ENDPOINT_URL"))
    port        <- readPort(System.getenv("PORT"))
  yield Config(endpointUrl, port)

object `🥔🥔🥔`:
  def callWith(config: Config) =
    val text = requests.get(config.endpointUrl, params = Map("port" -> config.port.toString)).text()
    println(s"Got response: $text")

@main def hello(): Unit =
  val config = Config.fromEnvironment.toTry.get
  `🥔🥔🥔`.callWith(config)
