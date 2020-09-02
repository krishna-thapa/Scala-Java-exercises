// 5.4.1.1 Pairs

case class Pair[A, B](one: A, two: B)
val pair = Pair[String, Int]("hi", 2)
println(pair.one)
println(pair.two)

// Tuples
// A tuple is the generalisation of a pair to more terms.
val tuple = Tuple2("hello", 1)
println(tuple)

val tupleSugared = ("world", 1, false)
println(tupleSugared)

// input parameter for methods
def tuplized[A, B](in: (A, B)) = in._1
println(tuplized("string", 12))

("string", 12) match {
  case (a, b) => println(b)
}

("string", 12)._2

// 5.4.3.1
sealed trait Sum[A, B]
case class Left[A, B](value: A) extends Sum[A, B]
case class Right[A, B](value: String) extends Sum[A, B]

Left[Int, String](1).value
Right[Int, String]("foo").value

val sum: Sum[Int, String] = Right("foo")

sum match {
  case Left(x) => x.toString
  case Right(x) => x
}

sealed trait Maybe[A]
case class Full[A](value:A) extends Maybe[A]
case class Empty[A]() extends Maybe[A]

val none: Maybe[Int] = Empty[Int]
val some: Maybe[Int] = Full(1)

// The sum type is called Either, products
//are tuples, and optional values are modelled with Option

// 5.4.6.1
/*
  Inheritance-based approaches—traits and classes—allow us
  to create permanent data structures with specific types
  and names

  Generic data structures—Tuples, Options, Eithers, and
  so on—are extremely broad and general purpose. These classes
  are therefore better suited to quick, one-off pieces of data
  manipulation where defining our own types would introduce
  unnecessary verbosity to our codebase
 */

//5.4.6.2
sealed trait FoldMaybe[A] {
  def fold[B](empty: B)(full: A => B): B = {
    this match {
      case FoldEmpty() => empty
      case FoldFull(v) => full(v)
    }
  }
}
case class FoldFull[A](value:A) extends FoldMaybe[A]
case class FoldEmpty[A]() extends FoldMaybe[A]

//5.4.6.3
sealed trait FoldSum[A, B] {
  def fold[C](left: A => C)(right: B => C): C = {
    this match {
      case FoldLeft(a) => left(a)
      case FoldRight(b) => right(b)
    }
  }
}
case class FoldLeft[A, B](value: A) extends FoldSum[A, B]
case class FoldRight[A, B](value: B) extends FoldSum[A, B]

