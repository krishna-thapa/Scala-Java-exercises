package udemy.typeClass

import krishna.udemy.typeClass.TypeClasses.User

object EqualityPlayground extends App {

  // Equality
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  // if you want both object to be implicit, have to be in different packages since compiler will be confused
  object FullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean =
      a.name == b.name && a.email == b.email
  }

  // companion object
  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean =
      equalizer.apply(a, b)
  }

  val sam = User("sam", 45, "ram@")
  val anotherSam = User("sam", 35, "sam@")

  // Compiler will get apply method and try to find implicit object that extends Equal and takes User as parameters
  println(Equal(anotherSam, sam)) // can remove apply keyword

  // Above example is called AD-HOC polymorphism: in sense that if two distinct or potentially unrelated types have
  // equalizer is implemented then we can call this equal apply on them regardless of their type
  // Compiler will take care to fetch the correct type class instance for our types

}
