package examples

object OptionsType {

  /*
  In Scala Options are the collections which Represents optional values.
  Instances of `Option` are either an instance of $some or the object $none.
   */

  val list = List(1, 2, 3, 4, 5)
  val map = Map(1 -> "John", 2 -> "Nithin", 3 -> "Alfie")

  def main(args: Array[String]): Unit = {
    println(list.find(_ > 3)) //give the result in option type, either some or none
    println(map.get(2))
    println(list.find(_ > 6)) //give result none type

    println(list.find(_ > 3).get) //to get the actual result (4,5)
    println(map.get(2).get)
  }
}
