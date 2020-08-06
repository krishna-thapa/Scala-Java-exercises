package udemy.implicitExamples

object PimpMyLibrary extends App {

  // 2.isPrime

  // implicit class take one and only argument
  implicit class RichInt(val value: Int) extends AnyVal { // extends AnyVal for memory optimization
    def isEven: Boolean = value % 2 == 0

    def sqrt: Double = Math.sqrt(value)

    // what is auxually functions
    // this method is taking an anonymous function and running over n times with return unit
    def times(function: () => Unit): Unit = {
      @scala.annotation.tailrec
      def timesAuxn(n: Int): Unit =
        if (n <= 0) ()
        else {
          function()
          timesAuxn(n - 1)
        }

      timesAuxn(value)
    }

    def *[T](list: List[T]): List[T] = {
      def concatenate(n: Int): List[T] =
        if (n <= 0) List()
        else concatenate(n - 1) ++ list

      concatenate(value)
    }
  }

  new RichInt(42).sqrt //as normal

  // compiler searches for all the implicit classes or the implicit conversions that can wrap a type int
  // into something that contains method isEven
  42.isEven // compiler will do: new RichInt(42).isEven
  // called as type enriching = pimping

  //Examples:
  1 to 3
  //Examples:
  import scala.concurrent.duration._
  3.seconds

  // Note: Compiler doesn't do multiple implicit searches
  implicit class RicherInt(richInt: RichInt) {
    def isOdd: Boolean = richInt.value % 2 != 0
  }

  /*
    Exercise:
      - Enrich String class
        - asInt
        - encrypt
   */

  implicit class RichString(string: String) {
    def asInt: Int = Integer.valueOf(string)
    def encrypt(cypherDistance: Int): String =
      string.map(s => (s + cypherDistance).asInstanceOf[Char])

  }

  println("3".asInt + 4)
  println("John".encrypt(2))

  // compiler looks for a implicit class that takes int as a parameter and that has method of times
  3.times(() => println("Hello world"))
  println(4 * List(1, 2))

  // auto convert string to int: "4"/2 -> 2
  // implicit conversion are discouraged
  implicit def stringToInt(string: String): Int = Integer.valueOf(string)
  println("4" / 2) //compiler will look into def that takes string and return int with / method

  // this is generic class with implicit conversion which is equal to implicit class:
  // implicit class RickAltInt(value: Int)
  class RichAltInt(value: Int)
  implicit def enrich(value: Int): RichAltInt = new RichAltInt(value)

  // Although implicit conversion methods are powerful but are discouraged
  // danger zone
  // use any int == 1 as boolean
  implicit def intToBoolean(i: Int): Boolean = i == 1
  val conditionVal = if (3) "Ok" else "something is wrong"
  println(conditionVal)

  // problem is that with implicit def it is very very hard to find the origin of an error

  // BEST practices:
  /*
    1. Keep type enrichment to implicit classes and type classes
    2. avoid implicit defs as much as possible
    3. package implicits clearly, bring into scope only what you need
    4. IF you need conversions, make them specific
 */
}
