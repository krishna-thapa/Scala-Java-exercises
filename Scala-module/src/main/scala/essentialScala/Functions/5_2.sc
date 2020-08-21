

/*
Functions allow us to abstract over methods, turning methods into values that
we can pass around and manipulate within our programs.
*/
sealed trait IntList {
  def length: Int =
    this match {
      case End => 0
      case Pair(hd, tl) => 1 + tl.length
    }

  def product: Int =
    this match {
      case End => 1
      case Pair(hd, tl) => hd * tl.product
    }

  def sum: Int =
    this match {
      case End => 0
      case Pair(hd, tl) => hd + tl.sum
    }

  def double: IntList =
    this match {
      case End => End
      case Pair(hd, tl) => Pair(hd * 2, tl.double)
    }
}

case object End extends IntList
final case class Pair(hd: Int, tl: IntList) extends IntList

val example1 = Pair(1, Pair(9, Pair(3, End)))
println(s"Length: ${example1.length}")
println(s"Product: ${example1.product}")
println(s"Sum: ${example1.sum}")
println(s"Double: ${example1.double}")

/*
A function is like a method: we can call it with parameters and it evaluates
to a result. Unlike a method a function is value. We can pass a function to a
method or to another function. We can return a function from a method, and
so on.
 */

/*
We write a function type like (A, B) => C where A and B are the types of
the parameters and C is the result type.
 */

val addSingle = (x: Int) => (x + 1).toString: String
val addDouble = (x:Int, y: Int) => x + y

// 5.2.3.1 A Better Abstraction

// Rename this function to fold, which is the name it is usually known as, and
//finish the implementation.

sealed trait IntListFold {
  def fold(end: Int, f: (Int, Int) => Int): Int =
    this match {
      case EndFold => end
      case PairFold(hd, tl) => f(hd, tl.fold(end, f))
    }

  // Now reimplement sum, length, and product in terms of fold.
  def length: Int = fold(0, (_, tl) => 1 + tl)
  def product: Int = fold(1, (hd, tl) => hd * tl)
  def sum: Int = fold(0, (hd, tl) => hd + tl)
}
case object EndFold extends IntListFold
final case class PairFold(hd: Int, tl: IntListFold) extends IntListFold

val example2 = PairFold(1, PairFold(9, PairFold(3, EndFold)))
println(s"Length: ${example2.length}")
println(s"Product: ${example2.product}")
println(s"Sum: ${example2.sum}")

/*
Why can’t we write our double method in terms of fold? Is it feasible we
could if we made some change to fold? -> No since def fold returns Int but double
returns IntListFold
 */

/*
Implement a generalised version of fold and rewrite double in terms of it.
 */

sealed trait IntListGen {
  def fold[T](end: T, f: (Int, T) => T): T =
    this match {
      case EndFoldGen => end
      case PairFoldGen(hd, tl) => f(hd, tl.fold(end, f))
    }

  def length: Int = fold[Int](0, (_, tl) => 1 + tl)
  def product: Int = fold[Int](1, (hd, tl) => hd * tl)
  def sum: Int = fold[Int](0, (hd, tl) => hd + tl)
  def double: IntListGen = fold[IntListGen](EndFoldGen, (hd, tl) => PairFoldGen(hd * 2, tl.double))
}

case object EndFoldGen extends IntListGen
final case class PairFoldGen(hd: Int, tl: IntListGen) extends IntListGen

val example3 = PairFoldGen(1, PairFoldGen(9, PairFoldGen(3, EndFoldGen)))
println(s"Length: ${example3.length}")
println(s"Product: ${example3.product}")
println(s"Sum: ${example3.sum}")
println(s"Double: ${example3.double}")

val example4 = Pair(1, Pair(9, Pair(3, End)))
