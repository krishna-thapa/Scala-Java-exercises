// 6.2 Working with Sequences

// List[String]
"dog".toSeq.permutations.toList

// Seq[List[String]]
Seq("a", "dog").map(_.toSeq.permutations.toList)

// Seq[String]
Seq("a", "dog").flatMap(_.toSeq.permutations.toList)

//flatMap is similar to map except that it expects our function to return a sequence.

Seq(1, 2, 3).map(num => Seq(num, num * 10))
Seq(1, 2, 3).flatMap(num => Seq(num, num * 10))