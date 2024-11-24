package v2

type Symbol = Char
val X: Symbol = 'X'
val O: Symbol = 'O'

type Position = (Int, Int)

type Game = Seq[(Symbol, Position)]

def engine(game: Game): Either[Throwable, String] = Right("TODO")

@main def hello(): Unit =
  val game: Game = Seq(
    '0' -> (0, 0),
    O   -> (1, 1),
    X   -> (1, 0)
  )

  engine(game)
