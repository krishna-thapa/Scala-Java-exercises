package examples

import java.util.Date;

object PartiallyAppliedFunction {

  /**
    * Use of log to use partially applied function
    * @param date, message
    */
  def log(date: Date, message: String): Unit = {
    println(s"$date     $message")
  }

  def main(args: Array[String]): Unit = {

    //what is fully applied function
    val sum = (a: Int, b: Int, c: Int) => a + b + c
    println("Fully applied function: " + sum(1, 2, 3)) //function that calls all the inout ags at once

    //Now instead of calling all at once, can call one or two and rest later on
    val f = sum(1, 2, _: Int) //_ is wildcard that is replaced when f is called
    println("Partially applied function: " + f(3))

    val date = new Date();
    val newLog = log(date, _: String)
    newLog("Message number 1")

    // Do some calculation here

    newLog("Message number 2") //just need to call with diff message and date will be updated to new one
  }
}
