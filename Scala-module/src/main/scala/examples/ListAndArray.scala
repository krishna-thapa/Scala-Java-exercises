package examples

import Array._
object ListAndArray {

  /*
    1. Array
    Array is a data structure which can stored fixed size sequential elements of same
    data type
   */

  val myArray: Array[Int] = new Array[Int](3)
  val myArray2 = new Array[String](3)
  val myArray3 = Array(11, 22, 33, 44)

  /*
    2. Lists are composed of values linked together. All access starts from the head
    (first element) and follows links. Random access takes linear time.
    Lists are immutable
   */

  val myList: List[Int] = List(1, 2, 3, 4, 5)
  val myListNames: List[String] = List("krishna", "Ram", "John")

  def main(args: Array[String]): Unit = {
    myArray(0) = 7
    myArray(1) = 2
    myArray(2) = 6
    for (x <- myArray) println(x)

    //using regular for loop
    for (i <- 0 to myArray3.length - 1) println(myArray3(i))

    val sortedArray = myArray.sorted(Ordering[Int].reverse) //sort an array in reverse order
    val resultConcat = concat(sortedArray, myArray3)
    for (x <- resultConcat) println("Result from concat: " + x)

    //for list operations
    println(myList)
    println(myListNames)
    //myList(0) = 8   will get an error since list is immutable

    //using cons :: in list
    println(0 :: myList) //insert 0 in first in list but the list remains same, use while creating new list
    println(myList)
    println(1 :: 2 :: 3 :: 5 :: Nil) //Nil represents empty list and 1,2,3,5 are added in top of it

    println(myList.head) //first value of the list
    println(myList.tail) //beside the first, shows all the remaining values as list
    println(myList.isEmpty) //boolean for empty or not
    println(myList.reverse) //reverse the list
    println(myList.max) //max value
    println(List.fill(5)("Apple")) //fill the list with string apple with 5 elements on it

    myList.foreach(println) // for each loop in list
    var sum = 0
    myList.foreach(sum += _) //iterate each element of list and add each in sum variable
    println("Sum of the each element in list: " + sum)

    for (name <- myListNames) println(name) //using simple for loop
    println(myListNames(0)) //can access the index element also
  }
}
