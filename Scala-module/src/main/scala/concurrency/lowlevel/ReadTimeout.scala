package concurrency.lowlevel

import io.StdIn._

// Introduction to Scala threads
// Shows the use threads and how to start
object ReadTimeout extends App {
  val countThread = new Thread {
    override def run(): Unit = {
      for (i <- 10 to 1 by -1) {
        println(i)
        Thread.sleep(1000)
      }
      println("Times up")
      sys.exit(0)
    }
  }

  println("Enter your age: Got 10 seconds to do: ")
  countThread.start()
  val age = readInt
  if (age < 18) println("Sorry, you can't enter.") else println("Welcome")
  sys.exit(0)
}
