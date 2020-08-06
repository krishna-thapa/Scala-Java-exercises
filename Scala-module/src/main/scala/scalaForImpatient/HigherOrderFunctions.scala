package scalaForImpatient

object HigherOrderFunctions extends App {
  /*
    Functions are first-class citizens, just like any other type you can pass around and manipulate
    Cannot manipulate methods, only functions
    Anything defined with def in class or object, is a method not a function
    With function: You can
      - Call it
      - Pass it around, bu storing in a variable or give it to a function as a parameter
   */

  // store a function in a variable that takes two parameters and results a char value
  val f: (String, Int) => Char = _.charAt(_)

  val tuples: Seq[(String, Int)] = Seq(("Hello", 2), ("World", 1))

  // calls a variable with required input parameters and pass to a map function as a parameter
  // Normal function call syntax us used on f, but it is a variable containing a function
  val result: Seq[Char] = tuples.map(tuple => f(tuple._1, tuple._2))
  println(result)

  // Anonymous Function
  val af = (s: String, i: Int) => s.charAt(i)
  val result2: Seq[Char] = tuples.map(tuple => f(tuple._1, tuple._2))
  println(result2)

  // def method
  def afMethod(s: String, i: Int): Char = {
    s.charAt(i)
  }
  val result3: Seq[Char] = tuples.map(tuple => afMethod(tuple._1, tuple._2))
  println(result3)

  // Functions with parameters:
  // Function that receives a function as a parameter, it is called a higher-order function
  //Map takes a function that has (String, Int) => Char and returns List[Char]
  tuples.map(tuple => f(tuple._1, tuple._2))
  // ((String, Int) => Char) => List[Char]

  //(Double) => (Double => Double))
  def mulBy(fac: Double): Double => Double = (x: Double) => fac * x
  val funcOfMulBy = mulBy(5)
  val result4 = funcOfMulBy(10)
  println(result4)

  // Parameter Inference: Scala helps you by deducing types when possible
  val fun = 3 * (_: Double) //Anonymous function: Double => Double
  println(fun(2))

  // Specifying the type of _ is useful for turning methods into functions
  val lengthFun = (_: String).length // functions with : String => Int

  ((9 to 1) by -1).map("*" * _).foreach(println)

  // reduceleft takes a binary function - function with two parameters abd applies it to all the elements of a sequence
  // going from left to the right

  (1 to 9).reduceLeft(_ * _) // (...((1*2)*3)*....9)

  // example of binary sorting
  val result5: Array[String] =
    "Hello to this world".split(" ").sortWith(_.length < _.length)
  result5.foreach(println)

  // Closures: are functions which uses one or more free variables and the return value of
  // this function is dependent of these variable.
  // https://www.geeksforgeeks.org/scala-closures/
  // https://alvinalexander.com/scala/how-to-use-closures-in-scala-fp-examples/

  val triple = mulBy(3)
  val half = mulBy(0.5)

  // Above variables are declared and stored, now
  println(s"${triple(14)} ${half(9)}")
  // Each of the return functions has its own setting for factor
  // Above, 14 and 9 are free variables (nonlocal): Closure consists of code together with the definitions of any
  // non local variables that the code uses

  // Currying: process of turning a functions that takes two arguments into a function that takes one argument
  // That functions returns a function that consumes the second argument

  val mul = (x: Int, y: Int) => x * y

  // Anonymous function : Int => (Int => Int)
  // Must use the multiple arrows, not multiple parentheses
  val mulOneAtATime = (x: Int) => ((y: Int) => x * y)
  println(mulOneAtATime(3)(5))

  // as a method: use multiple parentheses
  def mulOneAtATimeMethod(x: Int)(y: Int): Int = x * y
  println(mulOneAtATimeMethod(3)(5))

  // Example
  // (_.equalsIgnoreCase(_) passed as a curried parameters
  val a = Array("Hello", "World")
  val b = Array("World", "Hello")
  val result6 = a.corresponds(b)(_.equalsIgnoreCase(_))
  println(result6)
}
