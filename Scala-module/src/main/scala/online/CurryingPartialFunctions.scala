package online

/*
  Note: https://stackoverflow.com/questions/8650549/using-partial-functions-in-scala-how-does-it-work/8650639#8650639
  Partial function VS Partially applied function VS Currying
 */

object CurryingPartialFunctions extends App {

  // Currying: decomposition of functions with multiple arguments into a chain of single-argument functions.
  //  A function returning another function that might return another function,
  //  but every returned function must take only one parameter at a time.
  def sum(a: Int, b: Int): Int = a + b
  def curriedSum               = (sum _).curried
  def res                      = curriedSum(10)

  println(s"Final result: ${res(20)}")

  // partially applied function – a function that was called with a fewer number of arguments
  // A partial function is a function that is valid for only a subset of values of those types you might pass in to it

  // A function returning another function that might return another function,
  // but each returned function can take several parameters.

  def normalF(n: Int, x: Int): Boolean = (x % n) == 0

  // Partially applied <function1> with one parameter defined
  val partialF: Int => Boolean = normalF(8, _)
  println(s"Partial applied function: ${partialF(8)}")

  // Currying
  def curriedM(n: Int)(x: Int) = (x % n) == 0
  val curriedF                 = curriedM(8) _ // Have to explicitly state that you want it curried
  println(s"Curried function: ${curriedF(8)}")

  //  partially applying a "normal" function results in a function that takes all parameters,
  //  whereas partially applying a function with multiple parameter lists creates a chain of functions,
  //  one per parameter list which, all return a new function:
  val pF: (Int, Int) => Boolean = normalF
  val cF: Int => Int => Boolean = curriedM

  //Curried function with three parameters
  def multiply(a: Int)(b: Int)(c: Int) = a * b * c
  // same as function that takes function that takes function
  def multiply2(a: Int) = (b: Int) => (c: Int) => a * b * c

  val firstResult: Int => Int => Int = multiply2(2)
  val secondResult: Int => Int       = firstResult(3)
  val finalResult: Int               = secondResult(3)
  println(s"Final answer: $finalResult")

  // https://alvinalexander.com/scala/fp-book/partially-applied-functions-currying-in-scala/

  // Note there is difference between partial functions with partially applied functions

  // use a partially-applied function, helping you create a specific function from a general function:

  def wrap(prefix: String)(html: String)(suffix: String) = {
    prefix + html + suffix
  }

  val result = wrap("<div>")("Hello world")("</div>")
  println(s"Result: $result")

  // Use of partially-applied function
  // necessary to specify the type of the missing parameter
  val wrapWithDiv: String => String = wrap("<div>")(_: String)("</div>")
  val result2                       = wrapWithDiv("Hello world")
  println(s"Result from partially-applied function: $result2")

  // Example with functional composition
  def add(x: Int)(y: Int): Int           = x + y
  def multiplyF(x: Int)(y: Int): Int     = x * y
  val add10multiply10Compose: Int => Int = multiplyF(10) _ compose add(10)
  println(s"add10multiply10Compose: ${add10multiply10Compose(20)}")
  val add10multiply10 = add(10) _ andThen multiplyF(10)
  println(s"add10multiply10: ${add10multiply10(20)}")

  // Partial Function in Scala
  // A partial function is a function that is valid for only a subset of values of those types you might pass in to it.
  // https://medium.com/@thejasbabu/partial-partially-applied-functions-in-scala-a0d179e7df3

  // Pattern Matching in Scala can be extended to create Partial functions,
  // a unary function that does not support every possible value that meets the input type.

  val squareRoot: PartialFunction[Int, Double] = {
    case x if x >= 0 => Math.sqrt(x)
  }

  val foo = List(1, 5, -4, 8)

  // The collect method takes a PartialFunction as argument and maps the values
  // defined for this partial function over it, skipping those outside the definition domain.
  val fooResult = foo.collect(squareRoot)
  println(s"fooResult: $fooResult") // compiler error on -4

  println(squareRoot.isDefinedAt(-4)) // false
  println(squareRoot.isDefinedAt(4))  // true

  val positive: PartialFunction[Int, Int] = {
    case x if x >= 0 => x
  }

  val odd: PartialFunction[Int, Boolean] = {
    case x if x % 2 == 1 => true
  }

  val even: PartialFunction[Int, Boolean] = {
    case x if x % 2 == 0 => true
  }

  val evenCheck: PartialFunction[Int, Boolean] = positive andThen even
  val oddCheck: PartialFunction[Int, Boolean]  = positive andThen odd

  println(evenCheck.isDefinedAt(-2))

  // This feature of Partial function becomes useful when implementing a validation system.
  // If there are series of checks implemented to verify if the input data meets certain guidelines.

  // val finalCheck = check1 andThen check2 andThen check3 ...

  // Scala allows Partial functions to be applied to collections
  val greaterThan20: PartialFunction[Any, Int] = {
    case i: Int if i > 20 => identity(i) // or just i
  }

  val greaterResultInList: Seq[Any] = List(123, 6, "foo", false).collect(greaterThan20)
  println(s"Result from greaterResultInList: $greaterResultInList")

  /*
    The benefit of Currying and Partially Applied function is the ability to create specialized functions
    based on general functions without introducing new code keeping the code free of duplication.
   */

}
