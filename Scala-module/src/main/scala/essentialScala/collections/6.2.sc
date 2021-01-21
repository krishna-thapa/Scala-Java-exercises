// 6.2 Working with Sequences

// This returns an Iterable, which is a bit like a Java Iterator. We’re going to
// look at iterables in more detail later. For now all we need to know is that we
// can call the toList method to convert an Iterable to a List
// List[String]
"dog".toSeq.permutations.toList

// Seq[List[String]]
Seq("a", "dog").map(_.toSeq.permutations.toList)

// Seq[String]
Seq("a", "dog").flatMap(_.toSeq.permutations.toList)

//flatMap is similar to map except that it expects our function to return a sequence.
// The end result is (nearly) always the same type as the original sequence
Seq(1, 2, 3).map(num => Seq(num, num * 10))
Seq(1, 2, 3).flatMap(num => Seq(num, num * 10))

// Folds

// commutative (i.e. a+b == b+a)

// We have We provide We want Method
// Seq[A] A => Unit Unit foreach
// Seq[A] A => B Seq[B] map
// Seq[A] A => Seq[B] Seq[B] flatMap
// Seq[A] B and (B, A) => B B foldLeft
// Seq[A] B and (A, B) => B B foldRight

"string".foldLeft("")((x, y) => x + y.toUpper)