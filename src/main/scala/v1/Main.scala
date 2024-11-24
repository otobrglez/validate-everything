package v1

val X: String = "X"
val O: String = "O"

type Game = Seq[(String, (Int, Int))]

def engine(game: Game): Either[Throwable, String] = Right("No winner.")

@main def hello(): Unit =
  val game: Seq[(String, (Int, Int))] = Seq(
    X -> (0, 0),
    O -> (1, 1),
    X -> (1, 0)
  )

  engine(game)
