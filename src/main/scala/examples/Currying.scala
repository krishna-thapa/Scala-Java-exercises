package examples

object Currying {

  /**
    * Currying is the technique of
    * transforming a function that takes multiple arguments into a function that takes a single argument.
    */
  //normal definition of function
  def add(x: Int, y: Int) = x + y

  //curring with single argument
  def add2(x: Int) = (y: Int) => x + y

  //default signature of currying function by scala
  def add3(x: Int)(y: Int) = x + y

  def main(args: Array[String]): Unit = {
    println("Normal function call: " + add(20, 30))
    println("Add2 function call: " + add2(20)(30))
    val add40 = add2(40) //use of the partially applied function on add2
    println("Partially applied add2: " + add40(10))
    println("Add3 function call: " + add3(20)(30))
    val add30 = add3(40) _ // _ wildcard need to use here unlike on add40
    println("Partially applied add3: " + add30(40))
  }
}
