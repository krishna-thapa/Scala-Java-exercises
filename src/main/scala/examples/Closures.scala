package examples

object Closures {

  /**
    *  A closure is a function which uses one or more variables declared outside this function.
    */
  var number = 10

  /*What is pure and impure closure?
   when the variable number is var, its impure and when it is val its pure since the same input arg in add will
  produce the same result */

  val add = (x: Int) => x + number //this function uses the variable declared outside (number)

  //What happens when the variable is changed inside the add, it will give the latest updated value of variable

  def main(args: Array[String]): Unit = {
    println(add(20))
    number = 100 //what happens when the variable is changed, closure will take the last value of variable
    println(add(20))
  }
}
