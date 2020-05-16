package udemy.typeClass

import krishna.udemy.typeClass.TypeClasses.User

object TypeClasses2 extends App {

  // part 1
  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String =
      s"<div> ${user.name}, ${user.age} years old, < a href=${user.email}/> </div>"
  }

  val john = User("John", 25, "hello@world.com")
  println(UserSerializer.serialize(john))

  // part 2: companion object
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    def apply[T](implicit serializer: HTMLSerializer[T]): HTMLSerializer[T] =
      serializer
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div>$value</div>"
  }

  // Works since we are passing explicit IntSerializer
  //println(HTMLSerializer.serialize(42)(IntSerializer))

  //Define IntSerializer as an implicit, compiler will look into implicit object that has serialize method that takes
  // int as a value
  println(HTMLSerializer.serialize(42))

  println(HTMLSerializer.serialize(john))
  // apply method will give access to entire type class interface
  // Instead of HTMLSerializer.serialize(), can simply use apply to get all the type class interface
  println(HTMLSerializer[User].serialize(john))

  //-------- part 3
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)
  }

  val hero = User("hero", 25, "hero@world.com")
  println(hero.toHTML) // println(new HTMLEnrichment[User](hero).toHTML(UserSerializer))
  println(3.toHTML)

}
