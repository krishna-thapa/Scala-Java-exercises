package essentialScala.Functions

import java.time.Instant

import scala.util.Try

trait LinkedList [A] {
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
        throw new IndexOutOfBoundsException("Empty list")
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


  val parameters: Option[Map[String, String]] = Some(Map("date" -> "2020-12-06"))

  def getDateParameter(parameters: Option[Map[String, String]]): Try[Instant] = Try {
    parameters.fold(Instant.now())((parameter: Map[String, String]) =>
      Instant.parse(parameter("date")))
  }

}

final case class End[A]() extends LinkedList[A]
final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]
