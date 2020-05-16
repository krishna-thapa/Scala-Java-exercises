package examples

object ConditionalLoop {

  /**
    * So what is a loop ? loops allow us to execute a statement or a block of
    * statements multiple times. Like conditional statements,
    * they are controlled by boolean expressions.
    */
  def main(agrs: Array[String]): Unit = {
    val x = 20; //immutable variable
    var result = ""; //mutable variable since we are updating result

    //And and OR (&& and ||) similar to Java
    if (x == 20) result = "True"
    else result = "False"

    println(result)

    //Can do in single line too, here result2 is val since just needed for initial set up only
    val result2 = if (x == 20) "True" else "False"
    println(result2)

    /**
      * The while Statement
      * A while statement has the following syntax: If the condition is true, the statement
      * is executed Then the condition is evaluated again, and if it is still true,
      * the statement is executed again The statement is executed repeatedly
      * until the condition becomes false
      * The do Statement: The body of a do loop executes at least once
      */
    var a = 0;
    while (a < 10) {
      println(" a = " + a)
      a += 1 //Unlike Java you can't write a++ or ++a
    }

    //The do Statement: The body of a do loop executes at least once
    var b = 0;
    do {
      println(" b = " + b)
      b += 1
    } while (b < 0)

    /**
      * Scala’s for is much more powerful than Java’s for
      * Consequently, it is used much more often than the other kinds of loops
      */
    for (i <- 1.to(5)) { //i will be var mutable variable by default and to is method with 5 as input arg
      println("i equal to = " + i)
    }

    // <- is called generator and 1 until 6 is a range
    for (i <- 1 until 6) { //until 6 which is 5
      println("i using until = " + i)
    }

    //Nested for loop in scala
    for (i <- 1 to 5; j <- 1 to 3) {
      println("i is = " + i + " and j is = " + j)
    }

    //use list in for loop and filter using if statement
    val lst = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    for (i <- lst; if i < 6) {
      println("From list: " + i)
    }

    //Use of for loop as an expression
    //yield: It is applied in combination with for and writes a new element into the resulting sequence.
    val res = for (i <- lst; if i < 6) yield {
      i * i
    }
    println(res)

    /**
      * In Scala match expressions are used to select between a list of
      * alternatives same as multiple if-statements or select statement,
      * if you are familiar with java.
      */
    val age = 29
    age match {
      case 25 => println("age: " + age)
      case 26 => println("age: " + age)
      case 27 => println("age: " + age)
      case _  => println("Default: age doesn't match to any of the case!")
    }

    //use of match as an expression and string variable
    val ageVal = "27"
    val resultAge = ageVal match {
      case "24" | "26" => ageVal + " is even"
      case "25" | "27" => ageVal + " is odd"
      case _           => "Default: age doesn't match to any of the case!"
    }
    println("Result is: " + resultAge)
  }
}
