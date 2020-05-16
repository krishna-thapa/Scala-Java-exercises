package examples

object Maps {

  /*
  In Scala maps are the collections of key value pairs.
  Scala maps can be mutable or immutable.
  Scala map keys should be unique.
  if same key is applied, latest key value pair will replace the old ones
   */

  val myMap: Map[Int, String] =
    Map(200 -> "John", 201 -> "Nithin", 202 -> "Alfie")
  val myMap2 = Map(203 -> "Sujan")

  def main(args: Array[String]): Unit = {
    println(myMap) //print all the map
    println(myMap(201)) //print the value key as per key
    println(myMap.keys) //print all the keys
    println(myMap.values) //print all the values
    println(myMap.isEmpty)

    myMap.keys.foreach { key =>
      println("Key value: " + key)
      println("Value for " + key + " key: " + myMap(key))
    }

    println(myMap.contains(201)) //to check whether the map contains that key or not

    println(myMap ++ myMap2) //concat of two maps
    println(myMap.size)
  }
}
