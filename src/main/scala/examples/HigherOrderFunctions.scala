package examples

object HigherOrderFunctions {

  /**
    * Higher-order functions in Scala are those functions that take other functions as parameters,
    * or whose result is a function (i.e are able to return functions) or do both.
    * e.g. map & ﬁlter in Scala.
    */
  def math(x: Double, y: Double, f: (Double, Double) => Double): Double =
    f(x, y)

  //Now with three input parameters instead of two
  def mathMulti(x: Double,
                y: Double,
                z: Double,
                f: (Double, Double) => Double): Double = f(f(x, y), z)
  //function takes one function as an argument and z for third argument variable

  def main(args: Array[String]): Unit = {
    val result = math(50, 20, (x, y) => x max y)
    println("Max value is: " + result)
    val add = math(50, 20, (x, y) => x + y)
    println("Add value is: " + add)

    val resultMulti = mathMulti(50, 20, 10, (x, y) => x min y)
    println("Min value out of three variables: " + resultMulti)
    val addWildCard = mathMulti(50, 20, 10, _ + _) //use of wildcard _ means something add something
    println("Result of all three variables: " + addWildCard)
    val maxWildCard = mathMulti(50, 20, 10, _ max _) //use of wildcard _ means something add something
    println("Result of max from all three variables: " + maxWildCard)
  }
}
