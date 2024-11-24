package v3

opaque type Symbol = Char
object Symbol:
  def apply(raw: Char): Symbol =
    val validSymbols = Set('X', 'O')
    Option
      .when(validSymbols.contains(raw))(raw)
      .getOrElse(throw new RuntimeException(s"Invalid symbol $raw, only ${validSymbols.mkString(", ")} are allowed."))

val X: Symbol = 'X'
val O: Symbol = 'O'

type Position = (Int, Int)

type Game = Seq[(Symbol, Position)]

def engine(game: Game): Either[Throwable, String] = Right("TODO")

@main def hello(): Unit =
  val game: Game = Seq(
    Symbol('0') -> (0, 0),
    '9'         -> (1, 1),
    X           -> (1, 0)
  )

  engine(game)
