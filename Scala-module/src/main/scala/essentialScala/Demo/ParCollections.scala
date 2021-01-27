package essentialScala.Demo

import scala.collection.parallel.CollectionConverters.ImmutableIterableIsParallelizable
import scala.collection.parallel.immutable.ParVector

object ParCollections extends App {

  // Linear collections in Scala

  val linearList = List.range(0, 10)
  println(s"Linear list $linearList")
  linearList.foreach(print)
  val res = linearList.map(_*2)
  println(res)
  println()

  // Scala Parallel collections
  // 1. Convert an existing linear collection to a parallel collection
  val parallelList = linearList.par
  println(s"Parallel list $parallelList")

  // using an algorithm that runs concurrently
  parallelList.foreach(print)
  println()
  val resParallel = parallelList.map { x =>
    Thread.sleep(100);
    println(x)
    x * 2
  }
  println(resParallel)
  println()

  // 2. Create a parallel collection
  val newParallelList = ParVector.range(0, 10)
  newParallelList.foreach(print)

  // There are both immutable and mutable parallel collections

  // How
  // imagine a collection being split into different chunks;
  // your algorithm is then applied to the chunks,
  // and at the end of the operation, the different chunks are recombined.

  // When
  // okay when your algorithm receives elements in an arbitrary order.
  // This means that algorithms like sum, max, min, mean, and filter will all work fine.

  // algorithm that depends on the collection elements being received in a
  // predictable order should not be used with a parallel collection

  // Performance
  // Using parallel collections won’t always make your code faster.
  // For a parallel algorithm to provide a benefit, a collection usually needs to be fairly large.
  // This is because using parallel collection has some overhead for distributing (fork) and gathering (join) the data between cores.
  // Due to the overhead of creating new threads, better performance is only obtained
  //  for operations which are CPU heavy per item in the collection, or when the
  //  collections are quite large


  // https://mycourses.aalto.fi/pluginfile.php/392900/mod_resource/content/1/lecture9.pdf
}
