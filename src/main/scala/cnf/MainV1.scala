package cnf

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

@main def hello(): Unit =
  val config = Config.fromEnvironment