// Sequencing computation

// Map
//Note: Map allows you to change the type of the elements but will have same collection type
sealed trait LinkedList[A] {
  def map[B](f: A => B): LinkedList[B] =
    this match {
      case Pair(hd, tl) => Pair(f(hd), tl.map(f))
      case End() => End[B]()
    }
}

final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]
final case class End[A]() extends LinkedList[A]

// Flatmap
sealed trait Maybe[A] {
  def flatMap[B](f: A => Maybe[B]): Maybe[B] = {
    this match {
      case Full(v) => f(v)
      case Empty() => Empty[B]()
    }
  }

  def map[B](f: A => B): Maybe[B] =
    this match {
      case Full(v) => Full(f(v))
      case Empty() => Empty[B]()
    }
}

final case class Full[A](value: A) extends Maybe[A]
final case class Empty[A]() extends Maybe[A]

/*
  We use map when we want to transform the value within the context to a new
  value, while keeping the context the same. We use flatMap when we want
  to transform the value and provide a new context
 */

// 5.5.4.1 Mapping Lists
val list: LinkedList[Int] = Pair(1, Pair(2, Pair(3, End())))
println(list.map(_*2))
println(list.map(_+1))
println(list.map(_/3))

// 5.5.4.2 Mapping Maybe.
// See above in Maybe trait

// 5.5.4.3 Sequencing Computations
val listFoo = List(1, 2, 3)
/*println(listFoo.flatMap(x => List(x, -x)))
println(listFoo.map(x => List(x, -x)))*/

println(listFoo.flatMap(x => List(x, -x)))
println(listFoo.map(x => List(x, -x)))

val listBoo: List[Maybe[Int]] = List(Full(3), Full(2), Full(1))
