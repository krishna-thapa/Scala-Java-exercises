package examples

object Functions {

  def main(args: Array[String]): Unit = {
    println(add(3, 4))
    println(subtract(3, 4))
    // no need to create new object instance since object itself is already initiated
    println(Math.multiply(3, 4)) //calling method within an object

    println(Math.square(3))
    //if the method has just one input argument then
    println(Math square 3) //called syntactic sugar in scala

    println("Using default function vals: " + Math.addNum()) //calling default values function
    println(Math.addNum(2)) //use the first x as 2 and y will be same as default

    //Use of the anonymous function in Scala (doesn't have function name and assign return to any variable)
    val addAnon = (x: Int, y: Int) => x + y
    println(addAnon(30, 50))
  }

  def add(x: Int, y: Int): Int = {
    x + y; //don't have to write return keyword, you can tho, last line of the function is consider as a return
  }

  def subtract(x: Int, y: Int): Int =
    x - y //if body has one line, can be written without curly brackets

  def divide(x: Int, y: Int) =
    x / y //if you know return is going got be Int, can remove type declaration

  //creating an object and method
  object Math {
    def addNum(x: Int = 5, y: Int = 5): Int = x + y
    def multiply(x: Int, y: Int): Int = x * y
    def square(x: Int): Int = x * x
  }
}
