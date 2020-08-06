package scalaForImpatient

object HigherOrderFunctionsEx extends App {

  /**
    * Task 1:
    *
    * Write a function `values(fun: (Int) => Int, low: Int, high: Int)` that yields a collection
    * of function inputs and outputs in a given range. For example, `values(x => x * x, -5, 5)`
    * should produce a collection of pairs `(-5, 25)`, `(-4, 16)`, `(-3, 9)`, ..., `(5, 25)`.
    */
  def values(fun: Int => Int, low: Int, high: Int): Seq[(Int, Int)] = {
    for (i <- low to high) yield (i, fun(i))
  }

  val result = values(x => x * x, -5, 5)
  println(result)

  /**
    * Task 2:
    *
    * How do you get the largest element of an array with `reduceLeft`?
    */
  val arrayEx: Array[Int] = Array(1, 23, 67, 2, 1, 45, 67, 2)
  //arrayEx.max
  println(arrayEx.reduceLeft((a, b) => if (a > b) a else b))
  println(arrayEx.reduceRight((a, b) => if (a > b) a else b))

  /**
    * Task 3:
    *
    * Implement the `factorial` function using `to` and `reduceLeft`, without a loop or recursion.
    */
  val factorial = (i: Int) => if (i < 1) 1 else (1 to i).reduceLeft(_ * _)
  println(factorial(2))

  /**
    * Task 4:
    *
    * The previous implementation needed a special case when `n < 1`. Show how you can avoid this
    * with `foldLeft`. (Look at the Scaladoc for `foldLeft`. It’s like `reduceLeft`, except that
    * the first value in the chain of combined values is supplied in the call.)
    */
  val factorial2 = (i: Int) => (1 to i).foldLeft(2)(_ * _)
  println(factorial2(0))

  /**
    * Task 5:
    *
    * Write a function `largest(fun: (Int) => Int, inputs: Seq[Int])` that yields the largest
    * value of a function within a given sequence of inputs. For example,
    * `largest(x => 10 * x - x * x, 1 to 10)` should return `25`. Don't use a loop or recursion.
    */
  def largest(fun: (Int) => Int, inputs: Seq[Int]) = {}
}
