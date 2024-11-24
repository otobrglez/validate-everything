package v1b

type Symbol = Char
val validMoves @ List(x, o) = 'X' :: 'O' :: Nil

enum Result:
  case Won(symbol: Symbol); case Tie; case Pending
import Result.*

type Position = (Int, Int)
type Move     = (Symbol, Position)
def engine(moves: Move*) =
  val range = 0 until 3
  printMoves(moves.toSeq*)

  def symbolsOf(symbol: Symbol) = range.map(_ => Some(symbol))

  val wonRows: Symbol => Boolean = symbol =>
    range
      .map(r => range.map(c => moves.collectFirst { case (`symbol`, (`r`, `c`)) => symbol }))
      .contains(symbolsOf(symbol))

  val wonColumns: Symbol => Boolean = symbol =>
    range
      .map(r => range.map(c => moves.collectFirst { case (`symbol`, (`c`, `r`)) => symbol }))
      .contains(symbolsOf(symbol))

  val leftDiagonal: Symbol => Boolean = symbol =>
    range
      .zip(range)
      .map((r, c) => moves.collectFirst { case (`symbol`, (`r`, `c`)) => symbol })
      .equals(symbolsOf(symbol))

  val rightDiagonal: Symbol => Boolean = symbol =>
    range
      .map(r => moves.collectFirst { case (`symbol`, (`r`, v)) if v == 2 - r => symbol })
      .forall(_.contains(symbol))

  val won: Symbol => Boolean = symbol => List(wonRows, wonColumns, leftDiagonal, rightDiagonal).exists(_(symbol))

  won(x) -> won(o) match
    case true -> false              => Won(x)
    case false -> true              => Won(o)
    case _ if moves.length == 3 * 3 => Tie
    case (_, _)                     => Pending

def printMoves(moves: Move*): Unit =
  println((0 to 2).map { r =>
    (0 to 2).map { c =>
      moves.collectFirst { case (symbol, (`r`, `c`)) => symbol }.getOrElse(" ")
    }.mkString(", ")
  }.mkString("\n"))

@main def hello(): Unit =
  val result = engine(
    // x -> (0, 2),
    // o -> (0, 1),
    // x -> (1, 1),
    // o -> (0, 2),
    // x -> (2, 0)
  )

  println(s"Result = $result")
