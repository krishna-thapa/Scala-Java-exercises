package essentialScala.Demo

import scala.collection.parallel.CollectionConverters._

object FoldInParCollection extends App {

  val numList = List.range(0, 10)

  val r1 = numList.foldLeft(0)((acc, value) => {
    /*
     * You can see that foldLeft
     * picks elements from left to right.
     * It means foldLeft does sequence operation
     */
    println("foldLeft: adding accumulator=" + acc + ", value=" + value + " => " + (acc + value))
    acc + value
  })


  println("Result of foldleft in linear: " + r1)
  println()
  println("#######################")


  val r2 = numList.par.foldLeft(0)((acc, value) => {
    /*
      Same as above
     */
    println("fold: adding accumulator=" + acc + ", value=" + value + " => " + (acc + value))
    acc + value
  })

  println("Result of fold in linear: " + r2)
  println()
  println("#######################")

  val r3 = numList.par.fold(0)((acc, value) => {
    /*
   * You can see from the output that,
   * fold process the elements of parallel collection in parallel
   * So it is parallel not linear operation.
     */

    println("fold in par: adding accumulator=" + acc + ", value=" + value + " => " + (acc + value))
    acc + value
  })
  println("Result of fold in parallel: " + r3)
  println()
  println("#######################")

}


/*

  fold, contrary to foldRight and foldLeft, does not offer any guarantee about the order in which
  the elements of the collection will be processed.

  use fold, with its more constrained signature, with parallel collections, where the lack of guaranteed processing
  order helps the parallel collection implements folding in a parallel way.

  The reason for changing the signature is similar: with the additional constraints, it's easier to make a parallel fold.

  fold() does parallel processing so does not guarantee the processing order

  you do not have control over the execution order of your parallel computation.
  Hence, you should make sure that your expression will produce the same result if it is split
  and fan-out to multiple cores in a non- deterministic way.

  Folds the elements of this sequence using the specified associative binary operator.
  The order in which operations are performed on elements is unspecified and may be nondeterministic.
 */