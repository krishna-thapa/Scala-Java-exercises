package online

object FinalAbstract extends App {

  /*
  If the class in Scala is final then it cannot inherit to derived class. Inheritance restriction will be added by final keyword.
   */

  // Scala program of using final class
  final class Shapes
  {
    // Makes all variables and functions as final
    val height:Int = 0
  }

  // Cannot inherit Shapes class
  /*class Rectangle extends Shapes
  {
    println(height)
  }*/

  /*
  Final method in the parent class indicate that, method cannot override in a child class.
   */

  class Animal {
    val name: String = "names"

    final def sound(): String = {
      "Sound os an animal"
    }
  }

  class Tiger extends Animal {
    println(sound())  // Works since we are not overriding the method

   /* override def sound() = {
      "Doesn't work"
    }*/
  }

  // Should I use the final modifier when declaring case classes?

  case class Foo(v:Int)
  class Bar(v: Int, val x: Int) extends Foo(v)
  println(new Bar(1, 1) == new Bar(1, 2))  // Gives true

  /*
  Case classes will generate correct implementations of these methods(equals, hasCode, toString) - equals, for example, will run a field-for-field comparison.
  The problem is that this breaks when you extend a case class: the subclass will inherit these methods,
  which are not aware of any field you might have added.
  This is the reason, you can no longer extend a case class by another case class, it has been forbidden since, I think scala 2.11.
  Extending a case class by a non-case class is still allowed, but isn't really a good idea.
   */

  // not allowed to create the instance of the abstract class
  // an abstract class can contain final methods (methods that cannot be overridden)
  abstract class myauthor
  {
    // abstract method
    def details()
  }

  // GFG class extends abstract class
  class GFG extends myauthor
  {
    def details()
    {
      println("Author name: Ankita Saini")
      println("Topic name: Abstract class in Scala")
    }
  }
  /*
    An abstract class is useful:
    - When we want to construct a base class which needs constructor arguments.
    - When our code will be called from Java code.
   */

  val obj = new GFG()
  obj.details()

  sealed abstract case class PositiveInt(value: Int)

  object PositiveInt {
    def fromInt(i: Int): Option[PositiveInt] =
      if(i > 0) Some(new PositiveInt(i) {})
      else      None

  }

  // Can extent abstract case class but only one
  class Example(val x: Int) extends PositiveInt(x)

  /*
  Since PositiveInt is abstract, the compiler can’t generate a constructor, a copy method or an apply method on the companion object
  and you effectively control the values that get inside it. But this requires it not to be final.
   */
}


/*
Resources:
  - https://www.geeksforgeeks.org/scala-final/
  - https://nrinaudo.github.io/scala-best-practices/tricky_behaviours/final_case_classes.html
 */