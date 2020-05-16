package udemy.implicitExamples

object OrganisingImplicit1 extends App {

  // Exercise
  case class Person(name: String, age: Int)

  val persons = List(
    Person("John", 23),
    Person("Don", 20),
    Person("Sam", 32),
    Person("Harry", 56)
  )

  object NameOrdering {
    implicit val nameOrdering: Ordering[Person] =
      Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }

  object AgeOrdering {
    implicit val ageOrdering: Ordering[Person] =
      Ordering.fromLessThan(_.age < _.age)
  }

  // if more than one implicit needed, import the one needed
  import AgeOrdering._ //import NameOrdering._
  println(persons.sorted)

  // Exercises
  /*
    - totalPrice = use most often 50%
    - by unit count = 40%
    - by unit price = 15%
   */
  case class Purchase(nUnits: Int, unitPrice: Double)

  object Purchase {
    //if this is used most often then just put in companion object, every time sorted method is called this ordering used
    implicit val totalOrdering: Ordering[Purchase] = Ordering.fromLessThan(
      (a, b) => a.nUnits * a.unitPrice < b.nUnits * b.unitPrice
    )
  }

  object CountOrdering {
    // if it is second use most, put it in different object and can be used as import when needed
    implicit val countOrdering: Ordering[Purchase] =
      Ordering.fromLessThan(_.nUnits < _.nUnits)
  }

  // if it is least used then just declare right there when needed
  implicit val priceOrdering: Ordering[Purchase] =
    Ordering.fromLessThan(_.unitPrice < _.unitPrice)

}
