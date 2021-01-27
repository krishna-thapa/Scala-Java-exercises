package essentialScala.Demo

import scala.collection.parallel.CollectionConverters._

object FoldInParCollection extends App {

  val numList = List(1, 2, 3, 4, 5)

  val r2 = numList.foldLeft(0)((acc, value) => {
    println("adding accumulator=" + acc + ", value=" + value + " => " + (acc + value))
    acc + value
  })
  //println("foldLeft(): " + r2)
 // println("#######################")
  /*
   * You can see that foldLeft
   * picks elements from left to right.
   * It means foldLeft does sequence operation
   *
   * adding accumulator=0, value=1 => 1
   * adding accumulator=1, value=2 => 3
   * adding accumulator=3, value=3 => 6
   * adding accumulator=6, value=4 => 10
   * adding accumulator=10, value=5 => 15
   * foldLeft(): 15
   * #######################
   */


   // Parallel collections in Scala

    val linearList = List.range(0, 10)
    linearList.foreach(print)

    val parallelList = linearList.par
    println()
    parallelList.foreach(print)

    println()
    val foldLeftOnLinear = linearList.foldLeft(0)(_ - _)
    println(s"Result from foldLeft on Linear list: $foldLeftOnLinear")

    val foldLeftOnParallel = parallelList.foldLeft(0)(_ - _)
    println(s"Result from foldLeft on Parallel list: $foldLeftOnParallel")

    val foldRightOnLinear = linearList.foldRight(0)(_ - _)
    println(s"Result from foldRight on Linear list: $foldRightOnLinear")

    val foldRightOnParallel = parallelList.foldRight(0)(_ - _)
    println(s"Result from foldRight on Parallel list: $foldRightOnParallel")

    val foldOnLinear = linearList.fold(0)(_ - _)
    println(s"Result from fold on Linear list: $foldOnLinear")

    val foldOnParallel = parallelList.fold(0)(_ - _)
    println(s"Result from fold on Parallel list: $foldOnParallel")

    val foldOnParallel2 = parallelList.fold(0)(_ - _)
    println(s"Result from fold on Parallel list: $foldOnParallel2")


    val r1 = numList.par.fold(0)((acc, value) => {
      println("adding accumulator=" + acc + ", value=" + value + " => " + (acc - value))
      acc - value
    })
    println("fold(): " + r1)
    println("#######################")

//   println(List(1,2,3).foldLeft(0)(_ - _))
//   println(List(1,2,3).foldRight(0)(_ - _))
//   println(List(1,2,3,4,5,6,7).par.fold(0)(_ - _))
//   println(List(1,2,3,4,5,6,7).par.fold(0)(_ - _))
}


/*

 * You can see from the output that,
 * fold process the elements of parallel collection in parallel
 * So it is parallel not linear operation.

  fold, contrary to foldRight and foldLeft, does not offer any guarantee about the order in which
  the elements of the collection will be processed. You'll probably want to use fold, with its
  more constrained signature, with parallel collections, where the lack of guaranteed processing
  order helps the parallel collection implements folding in a parallel way. The reason for changing
  the signature is similar: with the additional constraints, it's easier to make a parallel fold.

  fold() does parallel processing so does not guarantee the processing order
 */