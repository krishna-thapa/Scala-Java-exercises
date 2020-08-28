// Generic Linked List invariant sum type pattern

sealed trait LinkedList[A] {
  def fold[B](end: B) (pair: (A, B) => B): B =
    this match {
    /*
    end has no parameters (as End stores no values) and returns B. Thus its
    type is () => B, which we can optimise to just a value of type B
     */
      case End() => end
    /*
    pair has two parameters, one for the list head and one for the tail. The
    argument for the head has type A, and the tail is recursive and thus has
    type B. The final type is therefore (A, B) => B.
     */
      case Pair(hd, tl) => pair(hd, tl.fold(end)(pair))
    }
}

final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]
final case class End[A]() extends LinkedList[A]

/*
  Fold is just an adaptation of structural recursion where we allow the user to
  pass in the functions we apply at each case.
 */

// Placeholder syntax

(_: Int) * 2
(a: Int) => a * 2

// Converting methods to functions
object Sum {
  def sum(x: Int, y: Int) = x + y
}

Sum.sum _

// Multiple Parameter Lists
def example(x: Int)(y: Int) = x + y
example(2)(3)

/*
   ease type inference
 */

// 5.3.4.1 Tree

sealed trait Tree[A] {
  def fold[B](leaf: A => B)(node: (B, B) => B): B

  def foldTrait[B](leaf: A => B)(node: (B, B) => B): B = {
    this match {
      case Leaf(element) => leaf(element)
      case Node(left, right) => node(left.fold(leaf)(node), right.fold(leaf)(node))
    }
  }
}
case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A] {
  def fold[B](leaf: A => B)(node: (B, B) => B): B =
    node(left.fold(leaf)( node), right.fold(leaf)(node))
}
case class Leaf[A](element: A) extends Tree[A] {
  def fold[B](leaf: A => B)(node: (B, B) => B): B =
    leaf(element)
}

val tree: Tree[String] =
  Node(Node(Leaf("To"), Leaf("iterate")),
    Node(Node(Leaf("is"), Leaf("human,")),
      Node(Leaf("to"), Node(Leaf("recurse"), Leaf("divine")))))

val result: String = tree.fold[String](leaf => leaf)((left, right) => left + " " + right)
println(result)

val usingTraitMethod: String = tree.foldTrait[String](leaf => leaf)((left, right) => s"$left $right")
println(usingTraitMethod)

// Fold is returning different type[B]=Int than the one compare to input type[A] = String
val returnDiffType: Int = tree.foldTrait[Int](leaf => leaf.length)((left, right) => left + right)
println(returnDiffType)

val sumInt: Tree[Int] =
  Node(Node(Leaf(1), Leaf(2)),
    Node(Node(Leaf(3), Leaf(4)),
      Node(Leaf(5), Node(Leaf(6), Leaf(7)))))

val result2: Int = sumInt.fold[Int](leaf => leaf)((left, right) => left + right)
println(result2)

// (1-(2-(3-0))) = 2
val demo:Seq[Int] = Seq(1,2,3)
demo.foldRight(0)((x,y) => x-y)