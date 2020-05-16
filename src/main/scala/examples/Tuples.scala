package examples

object Tuples {

  /*
  In Scala Tuples are the collections of fixed sized elements of different data types.
  Scala Tuples is immutable.
  Tuple can contain elements from 1 to 22 in Scala (max store limit is 22)
   */

  val myTuple = (1, 2, "String", true, 34.5)
  val myTuple2 = new Tuple3(1, 2, "String") //Tuple is not a keyword, have to write the length after key Tuple word
  val myTuple3 = new Tuple3(3, 6, ("String", 4)) //tuple inside the tuple, total 3 elements for myTuple3

  def main(args: Array[String]): Unit = {
    println(myTuple) //to print all the elements from tuple
    println(myTuple._1) //each element can be accessed using _index of ech element starts from 1
    println(myTuple._5)

    myTuple.productIterator
      .foreach { //to use foreach on tuple have to do like this
        i =>
          println(i)
      }

    println(myTuple3._3._2) //to print the element of tuple that is in inside of the main tuple
  }
}
