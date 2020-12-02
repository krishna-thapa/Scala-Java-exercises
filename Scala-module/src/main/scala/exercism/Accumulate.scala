package exercism

import scala.annotation.tailrec

class Accumulate {
  // Using recursive method
  def accumulate[A, B](f: A => B, list: List[A]): List[B] = {
    list match {
      case Nil          => Nil
      case head :: tail => f(head) :: accumulate(f, tail)
    }
  }

  // Using tail recursion method
  // A tail-recursive function is just a function whose very last action is a call to itself.

  def accumulateTail[A, B](f: A => B, list: List[A]): List[B] = {
    @tailrec
    def _accumulateTail(list: List[A], acc: List[B]): List[B] = {
      list match {
        case Nil          => acc
        case head :: tail => _accumulateTail(tail, acc :+ f(head))
      }
    }
    _accumulateTail(list, Nil)
  }
}
// Resources for tail recursion
// https://exercism.io/tracks/scala/exercises/accumulate/solutions/3fdbbd11a1e5456282d3ef16fc3a8981
// https://alvinalexander.com/scala/fp-book/tail-recursive-algorithms/
// https://www.youtube.com/watch?v=_JtPhF8MshA
// https://nrinaudo.github.io/scala-best-practices/unsafe/recursion.html
