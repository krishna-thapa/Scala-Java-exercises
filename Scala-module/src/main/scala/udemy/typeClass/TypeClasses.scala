package udemy.typeClass

object TypeClasses extends App {

  trait HTMLWritable {
    def toHTML: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHTML: String =
      s"<div> $name, $age years old, < a href=$email/> </div>"
  }

  User("John", 25, "hello@world.com").toHTML
  /*
    Problem:
      - only works for the type we write
      - ONE implementation out of quite a number
   */

  // Option 2: Pattern matching
  object HTMLSerializerPM {
    def serializeToHtml(value: Any) = value match {
      case User(a, b, c) =>
      //case java.util.Date =>
      case _ =>
    }
  }

  /*
    Problem:
      - lost type safety
      - need to modify the code every time
      still ONE implementation
   */

  // better approach by type class
  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  object userSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String =
      s"<div> ${user.name}, ${user.age} years old, < a href=${user.email}/> </div>"
  }

  val john = User("John", 25, "hello@world.com")
  println(userSerializer.serialize(john))

  // 1. Pros: can define serializers for other types
  import java.util.Date
  object dateSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String =
      s"<div>${date.toString} today right now</div>"
  }

  println(dateSerializer.serialize(new Date()))

  // 2. we can define MULTIPLE serializers
  // example here don't have to implement whole User class
  object PartialUserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div>${user.name}</div>"
  }

  // TYPE CLASS template
  trait TypeClass[T] {
    def action(value: T): String //return anything
  }

  // Any type class instances need to implement def action

  /*
    Exercise Equality
   */

  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  object FullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean =
      a.name == b.name && a.email == b.email
  }

}
