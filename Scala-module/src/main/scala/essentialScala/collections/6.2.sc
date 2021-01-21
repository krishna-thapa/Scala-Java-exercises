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

/*
ASSOCIATIVITY:
https://stackoverflow.com/a/25589282
http://web.deu.edu.tr/doc/oreily/java/langref/ch04_14.htm
The associativity of an operator (e.g. addition or substraction) refers to whether you work left-to-right (aka 'left-associative') or right-to-left ('right-associative').
+, -, * and / are all left-associative, so ...
5 + 10 + 15
(5 + 10) + 15
15 + 15
30
5 + 10 + 15
5 + (10 + 15)
5 + 25
30
commutative
5 - 10 - 15
(5 - 10) - 15
-5 - 15
-20
5 - 10 - 15
5 - (10 - 15)
5 - -5
10 ... mad universe
5 * 10 * 15
50 * 15
75
5 / 10 / 15
(5 / 10) / 15
(1/2) / 15
1/30
[ order of operations, precedence...
1 + (2 * 3)
]
= (aka 'the assignment operator') is right-associative ...
var a = 3
var b = 4
a = b = 5
So what is the value of a in this case? Because the assignment operator is right-associative, the scala compiler solves it in this order:
var a = 3
var b = 4
a = b = 5
a = (b = 5)
a = (5)
What would the value of a be if we lived in a world where the assignment operator was left associative?
var a = 3
var b = 4
a = b = 5
(a = b) = 5
a = 5
:(
FOLDLEFT VS FOLDRIGHT:
https://stackoverflow.com/questions/6253978/difference-between-fold-and-foldleft-or-foldright/6255094#6255094
foldRight is right-associative, meaning we work right-to-left. I.e. elements will be accumulated in right-to-left order:
List(a,b,c).foldRight(z)(f) = f(a, f(b, f(c, z)))
foldLeft is left-associative, meaning we work left-to-right. I.e. an accumulator will be initialized and elements will be added to the accumulator in left-to-right order:
List(a,b,c).foldLeft(z)(f) = f(f(f(z, a), b), c)
 */

println("foldRight")
List(5,10,15).foldRight(100)((x,y) => x - y)
List(5,10,15).foldRight(100)(_ - _)

println("foldLeft")
List(5,10,15).foldLeft(100)((x,y) => x - y)
List(5,10,15).foldLeft(100)(_ - _)

/*
FOLDRIGHT STEPS:
List(a,b,c).foldRight(z)(f) = f(a, f(b, f(c, z)))
List(5,10,15).foldRight(100)((x, y) => x - y) = f(a, f(b, f(c, z)))
List(5,10,15).foldRight(100)(_ - _) = f(5, f(10, f(15, 100)))
List(5,10,15).foldRight(100)(_ - _) = f(5, f(10, (15 - 100)))
List(5,10,15).foldRight(100)(_ - _) = f(5, f(10, -85))
List(5,10,15).foldRight(100)(_ - _) = f(5, (10 - -85))
List(5,10,15).foldRight(100)(_ - _) = f(5, (10 + 85))
List(5,10,15).foldRight(100)(_ - _) = f(5, 95)
List(5,10,15).foldRight(100)(_ - _) = (5 - 95)
List(5,10,15).foldRight(100)(_ - _) = -90
*/

println("foldRight steps")
val fun = (x: Int, y: Int) => x - y
fun(15, 100)
fun(10, fun(15, 100))
fun(5, fun(10, fun(15, 100)))

/*
FOLDLEFT STEPS:
List(a,b,c).foldLeft(z)(f) = f(f(f(z, a), b), c)
List(5,10,15).foldLeft(100)((x, y) => x - y) = (f(f(z, a), b), c)
List(5,10,15).foldLeft(100)(_ - _) = ???
As an exercise let's work out the rest of the steps together...
 */

val f = (x: Int, y: Int) => x - y

f(100, 5) // 95
f(f(100, 5), 10)
f(f(f(100, 5), 10), 15)

"string".foldLeft("")((x, y) => x + y.toUpper)