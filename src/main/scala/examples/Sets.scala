package examples

object Sets {

  /*
    In Scala sets are the un-ordered collections of
    unique elements of same data types.
    Scala Sets can be mutable or immutable.
    By default, all the sets are immutable
   */

  val mySet: Set[Int] = Set(1, 2, 3, 4, 2, 3, 4)
  val mySet2 = Set(5, 7, 8, 5, 4)
  var mySetMutable = scala.collection.mutable.Set(1, 2, 3, 4, 5, 2, 3) //to define mutable set

  def main(args: Array[String]): Unit = {
    println(mySet) //only prints the unique element from the set
    println("10 is added in the set: " + mySet + 10) //adds 10 in the set but the set remains immutable
    print(mySet) //10 will not be no longer there

    //Set is un-ordered collections, elements are stored in random locations unlike insertion order

    println(mySet(4)) //checks whether the int 4 is present in the set or not
    println(mySet.head)
    println(mySet.tail)
    println(mySet.isEmpty)

    println(mySet ++ mySet2) //to concat the two set
    println(mySet.++(mySet2)) // same as previous

    println(mySet.&(mySet2)) //intersection of two set, print elements that are present on both sets
    println(mySet.intersect(mySet2)) //same as previous

    println(mySet.min) //can use min and max

    mySet.foreach(println) //using the foreach function of the set

    for (x <- mySet2) println(x) //using the for loop

    val sortedSet = mySet2.toSeq.sorted
    println("Sorted set: " + sortedSet)
  }
}
