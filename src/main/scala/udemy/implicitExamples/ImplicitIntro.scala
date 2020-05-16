package udemy.implicitExamples

object ImplicitIntro extends App {

  val pair = "Hello" -> "world"
  val intPair = 123 -> 321
  // -> is a method defined in implicit class
  // It is an alternative syntax for creating a Tuple2
  /*
    scala> 1 -> 2
    res0: (Int, Int) = (1,2)

    scala> List().->(2)
    res1: (List[Nothing], Int) = (List(),2)

    scala> (1 -> 2) == ((1, 2))
    res2: Boolean = true
   */
  println(pair)

  case class Person(name: String) {
    def greet = s"hi, my name is $name"
  }

  implicit def stringToPerson(str: String): Person = Person(str)
  println("John".greet) //Same as println(stringToPerson("John").greet)

  // Won't work if there is another class with same method name
  /*class A {
    def greet: Int = 2
  }
  implicit def stringToA(str: String): A = new A*/

  // implicit parameters
  def increment(x: Int)(implicit amount: Int): Int = x + amount
  implicit val defaultAmount: Int = 12
  println(increment(12)) //take defaultAmount, not same as default argument

}
