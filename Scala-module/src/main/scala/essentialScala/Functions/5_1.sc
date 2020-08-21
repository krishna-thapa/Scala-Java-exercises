/*
  Generic types allow us to abstract over types
 */

final case class Box[A](value: A)

Box(2)
Box("String")

/*
  The syntax [A] is called a type parameter
 */

def generic[A](in: A): A = in

generic[String]("foo")

/*
  Generic types can be declared in a class or trait declaration in which
  case they are visible throughout the rest of the declaration.

  Alternatively they may be declared in a method declaration, in which
case they are only visible within the method.
 */

// If Result of type A is a Success or Failure:
sealed trait Result[A]
final case class Success[A](result: A) extends Result[A]
final case class Failure[A](reason: String) extends Result[A]

// 5.1.3.1 Generic List

// Change the name to LinkedList and make it generic in the type of data
//stored in the list.

sealed trait LinkedList[A] {
  def length: Int =
    this match {
      case Pair(hd, tl) => 1 + tl.length
      case End() => 0
    }

  def contains(input: A): Boolean =
    this match {
      case Pair(hd, tl) =>
        if (hd == input) true
        else {
          tl.contains(input)
        }
      case End() => false
    }

  def apply(index: Int): A =
    this match {
      case Pair(hd, tl) =>
        if(index == 0) hd
        else tl.apply(index - 1)
      case End() =>
        throw new Exception("Empty list")
    }

  def applyResult(index: Int): Result[A] =
    this match {
      case Pair(hd, tl) =>
        if(index == 0) Success(hd)
        else {
          tl.applyResult(index-1)
        }
      case End() =>
        Failure("Index out of bounds")
    }
}

final case class End[A]() extends LinkedList[A]
final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]

val example = Pair(1, Pair(2, Pair(3, End())))
assert(example.length == 3)
assert(example.tail.length == 2)
assert(End().length == 0)

assert(example.contains(3))
assert(!example.contains(4))
assert(!End().contains(0))

assert(example(0) == 1)
assert(example(1) == 2)
assert(example(2) == 3)
assert(try {
  example(3)
  false
} catch {
  case e: Exception =>
    println(e.getMessage)
    true
})


assert(example.applyResult(0) == Success(1))
assert(example.applyResult(1) == Success(2))
assert(example.applyResult(2) == Success(3))
assert(example.applyResult(3) == Failure("Index out of bounds"))