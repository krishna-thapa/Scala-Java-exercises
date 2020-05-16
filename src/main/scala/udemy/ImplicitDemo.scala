package udemy

import basic.Dog

object ImplicitDemo extends App {
  /* WARNING WARNING: Implicits are a set of powerful features of Scala, and
   * therefore it is easy to abuse them. Beginners to Scala should resist the
   * temptation to use them until they understand not only how they work, but also
   * best practices around them. We only include this section in the tutorial
   * because they are so commonplace in Scala libraries that it is impossible to
   * do anything meaningful without using a library that has implicits. This is
   * meant for you to understand and work with implicits, not declare your own.
   */

  // Any value (vals, functions, objects, etc) can be declared to be implicit by
  // using the, you guessed it, "implicit" keyword. Note we are using the Dog
  // class from section 5 in these examples.
  implicit val myImplicitInt = 100
  implicit def myImplicitFunction(breed: String) = new Dog("Golden " + breed)

  // By itself, implicit keyword doesn't change the behavior of the value, so
  // above values can be used as usual.
  myImplicitInt + 2 // => 102
  myImplicitFunction("Pitbull").breed // => "Golden Pitbull"

  // The difference is that these values are now eligible to be used when another
  // piece of code "needs" an implicit value. One such situation is implicit
  // function arguments:
  def sendGreetings(toWhom: String)(implicit howMany: Int) =
    s"Hello $toWhom, $howMany blessings to you and yours!"

  // If we supply a value for "howMany", the function behaves as usual
  println(sendGreetings("John")(1000)) // => "Hello John, 1000 blessings to you and yours!"

  // But if we omit the implicit parameter, an implicit value of the same type is
  // used, in this case, "myImplicitInt":
  println(sendGreetings("Jane")) // => "Hello Jane, 100 blessings to you and yours!"

  // Implicit function parameters enable us to simulate type classes in other
  // functional languages. It is so often used that it gets its own shorthand. The
  // following two lines mean the same thing:
  // def foo[T](implicit c: C[T]) = ...
  // def foo[T : C] = ...

  // Another situation in which the compiler looks for an implicit is if you have
  //   obj.method(...)
  // but "obj" doesn't have "method" as a method. In this case, if there is an
  // implicit conversion of type A => B, where A is the type of obj, and B has a
  // method called "method", that conversion is applied. So having
  // myImplicitFunction above in scope, we can say:
  println("Retriever".breed) // => "Golden Retriever"
  println("Sheperd".bark) // => "Woof, woof!"

  // Here the String is first converted to Dog using our function above, and then
  // the appropriate method is called. This is an extremely powerful feature, but
  // again, it is not to be used lightly. In fact, when you defined the implicit
  // function above, your compiler should have given you a warning, that you
  // shouldn't do this unless you really know what you're doing.
}
