package online

object FunctionalComposition extends App {

  // https://benmosheron.gitlab.io/blog/2018/12/24/functional-composition.html

  /*
    The essence of composition - combining functions together without involving variables.
   */

  def addOne(x: Int): Int = x + 1
  def square(x: Int): Int = x * x
  def double(x: Int): Int = x * 2

  //def allThree = addOne.compose(square.compose(double))
  // Above gives a compiler error: Unapplied methods are only converted to functions when a function type is expected.

  // Note: the underscores after the method names are indicating that they should be passed as functions.
  def allThree = (addOne _).compose(square _).compose(double _)
  println(s"Result with method: ${allThree(2)}") // Note: apply function from right to left

  /*
    Reason why give an error: methods and functions are not the same.
    The methods we defined earlier, using def, are linked with the class in which they are defined.
    Functions are different. They are completely self-contained
   */

  val addOneF = (x: Int) => x + 1
  val squareF = (x: Int) => x * x
  val doubleF = (x: Int) => x * 2

  val allThreeF = addOneF.compose(squareF.compose(doubleF))
  println(s"Result with function: ${allThreeF(2)}")

  // Simple demo to assign a method to a function
  //val newAddOneF = addOne()  // Won't compile until parameter is passed
  val newAddOneF              = addOne _ // Have to pass underscore after a space
  val newAddOneF2: Int => Int = addOne   // Also work if it has type defined

  // Scala’s compose is defined on the Function1 trait, means we need to start with a function,
  // and use the function’s compose method to join it with others. The compiler should be able
  // to convert methods passed to compose into functions:

  // Compiler will convert methods square and double to functions.
  def allThree2 = (addOne _).compose(square).compose(double)
  def allThree3 = newAddOneF.compose(square).compose(double)

  // More examples:
  // A range of ints
  val ints = 1 to 10

  val allThreeWithMap = ints.map(newAddOneF2.compose(square).compose(double))
  println(s"Result in map: $allThreeWithMap")

// From left to right
  val allThreeWithMapLeftToRight = ints.map(newAddOneF2.andThen(square).andThen(double))
  println(s"Result in map: $allThreeWithMapLeftToRight")
}
