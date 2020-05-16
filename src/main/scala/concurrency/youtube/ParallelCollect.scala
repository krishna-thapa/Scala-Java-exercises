package concurrency.youtube

import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.immutable.ParVector

object ParallelCollect extends App {

  def fib(n: Int): Int = if (n < 2) 1 else fib(n - 1) + fib(n - 2)

  // In a single thread
  for (i <- 30 to 10 by -1) {
    println(fib(i))
  }

  println("---------------------")
  // Use of parallel collection: see outputs in random orders
  // Use of par divides the operation in groups and combines the outputs
  for (i <- (30 to 10 by -1).par) {
    println(fib(i))
  }
  println("---------------------")

  val demo = ParVector.range(0, 10)
  val output: Unit = demo.foreach { e =>
    Thread.sleep(100)
    println(e)
  }

  /*
    To summarize the basic concept:
      - Collection elements are split into different groups
      - The operation is performed
      - The elements are recombined
    The impact of this approach is that it must be okay that your algorithm receives elements
    in an arbitrary order. This means that algorithms like sum, max, min, mean, and
    filter will all work fine.
   */

  // Problem using par: race condition
  var i = 0
  for (j <- (1 to 100000).par) {
    // load i from memory
    // add 1 to register
    // store i back to memory
    i += 1
  }
  println("---------------------")
  // Will never be exact solution coz of race condition
  // use of immutalbe with par will give race condition where more than one threads try to access the same memory register
  println(i)
}
