// 6.1 Sequences

// A sequence is a collection of items with a defined and stable order

val sequence = Seq(6,3,9,4)
// Seq[Int] but is implemented by a List.

sequence.apply(1)
sequence(1)
sequence.head
sequence.take(1)
sequence.tail
sequence.tail.head
sequence.headOption
sequence.headOption.isDefined
sequence.nonEmpty
sequence.last
sequence.lastOption
sequence.reduce(_ + _)
sequence.mkString("[",",","]")

sequence.length
sequence.zipWithIndex

sequence.contains(9)
sequence.find(_ == 9)
sequence.find(_ > 4)
sequence.filter(_ > 4)

//descending order
sequence.sortWith(_ > _)

sequence :+ 10 //append
/*
This is another of Scala’s general syntax rules—any method ending with a :
character becomes right associative when written as an infix operator. This
rule is designed to replicate Haskell-style operators for things like list prepend
(::) and list concatenation (:::).
 */
sequence.+:(12) //prepend
12 +: sequence

sequence ++ Seq(8,123)

// 6.1.6 Lists
// The default implementation of Seq is a List, which is a classic linked list data
//structure

Nil // empty list
val listDemo = 1 :: 2 :: 5 :: 3 :: Nil
6 :: 0 :: listDemo

val list = List(3,7,12,9)
listDemo ::: list

// :: and ::: are specific to lists whereas +:, :+ and ++ work on any type of
//sequence.

// `empty` is unbounded here
def someMethod = {
  import scala.collection.immutable.Vector.empty
  // `empty` is bound to Vector.empty
  empty[Int]
}
// `empty` is unbounded here again

// Seq is Scala’s general sequence datatype. It has a number of general subtypes
//such as List, Stack, Vector, Queue, and Array, and specific subtypes such
//as String.

// 6.1.9.2 Animals
val animals = Seq("cat", "dog", "penguin")
"mouse" +: animals :+ "tyrannosaurus"

// return a supertype (in this case Seq[Any])
2 +: animals


