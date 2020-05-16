package udemy.implicitExamples

object OrganisingImplicit extends App {
  // This implicit will override and makes the list to sort in descending order
  // even if val or def - works fine
  // but if you put def and parentheses, it stops working
  implicit def reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)

  // will give an error since compiler is confused which implicit to use
  //implicit val normalOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _)
  println(List(1, 5, 3, 8, 2, 7, 8).sorted) // takes an implicit ordering[B] from scala.Predef

  /*
    Implicits: Only defined within a class, object or a trait -> cannot be defined as top level
    Used as implicit parameters:
      - val/var
      - object
      - access methods = defs with no parentheses
   */

  // Exercise
  case class Person(name: String, age: Int)

  val persons = List(
    Person("John", 23),
    Person("Don", 20),
    Person("Sam", 32),
    Person("Harry", 56)
  )

  // Define implicit before so that it can be pass or sorted call
  /*implicit def sortWithName: Ordering[Person] =
  Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)*/

  //if define inside companion object, implicit should work fine
  object Person {
    implicit def sortWithName: Ordering[Person] =
      //Ordering.fromLessThan(_.name < _.name)
      Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  // But if you define in any other object, it won't work since it is out of scope for an implicit
  /*  object Other {
    implicit def sortWithName: Ordering[Person] =
      //Ordering.fromLessThan(_.name < _.name)
      Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }*/

  implicit def sortWithAge: Ordering[Person] =
    Ordering.fromLessThan((a, b) => a.age < b.age)

  println(persons.sorted)

  /*
    Implicit scope priority:
      - normal scope = LOCAL SCOPE
      - imported scope
      - companions of all types involved in the method signature
        Example: def sorted[B >: A](implicit ord: Ordering[B]): List[B]
        - List
        - Ordering
        - All the types involved = A or any supertype
 */
}
