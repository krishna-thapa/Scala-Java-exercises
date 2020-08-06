package concurrency.youtube

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.concurrent.duration._

object FutureTest extends App {
  println("Printing in now")
  val f = Future {
    println("Printing in future")
  }

  // this line might print before the future call if above thread.sleep is not defined
  println("Printing in current")

  val f2 = Future {
    for (i <- 1 to 20) yield ParallelCollect.fib(i)
    //throw new RuntimeException  //force to throw an exception
  }

  //f2.foreach(println) // once future will complete, will print the output

  /*f2.onComplete { //return Try
    case Success(value)     => println("Success", value)
    case Failure(exception) => println("Error: ", exception)
  }

  Thread.sleep(3000) // Will wait until the future is done*/

  //println(Await.result(f2, 5.seconds)) //Waits until 5 seconds to complete the future: blocks the thread for 5 seconds

  val patge1 = Future {
    "Google" + io.Source.fromURL("http://www.google.com").take(100).mkString
  }
  val patge2 = Future {
    "Facebook" + io.Source.fromURL("http://www.facebook.com").take(100).mkString
  }
  val patge3 = Future {
    "Youtube" + io.Source.fromURL("http://www.youtube.com").take(100).mkString
  }

  val pages = List(patge1, patge2, patge3)

  //val firstPage = Future.firstCompletedOf(pages) // returns the future that completes the first
  //firstPage.foreach(println)

  // Very useful method
  val allPages = Future.sequence(pages) //returns result all combined once all of them are completed
  allPages.foreach(println)

  Thread.sleep(2000)
}
