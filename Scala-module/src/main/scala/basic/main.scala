package basic

object main extends App {

  val mydog = new Dog("greyhound")
  println(mydog.breed) // => "greyhound"
  println(mydog.bark) // => "Woof, woof!"

  println(Dog.allKnownBreeds)

  // Create a new instance. Note cases classes don't need "new"
  val george = Person("George", "1234")
  val kate = Person("Kate", "4567")

  // With case classes, you get a few perks for free, like getters:
  println(george.phoneNumber) // => "1234"

  // Per field equality (no need to override .equals)
  println(Person("George", "1234") == Person("Kate", "1236")) // => false

  // Easy way to copy
  // otherGeorge == Person("George", "9876")
  val otherGeorge = george.copy(phoneNumber = "9876")

  println(new SaintBernard().breed)
  println(new SaintBernard().meow)

  // Pattern matching is a powerful and commonly used feature in Scala. Here's how
  // you pattern match a case class. NB: Unlike other languages, Scala cases do
  // not need breaks, fall-through does not happen.
  def matchPerson(person: Person): String = person match {
    // Then you specify the patterns:
    case Person("George", number) => "We found George! His number is " + number
    case Person("Kate", number)   => "We found Kate! Her number is " + number
    case Person(name, number) =>
      "We matched someone : " + name + ", phone : " + number
  }

  println(matchPerson(Person("Kate", "123654")))

  // Regular expressions are also built in.
  // Create a regex with the `r` method on a string:
  val emailRegex = "(.*)@(.*)".r

  // Pattern matching might look familiar to the switch statements in the C family
  // of languages, but this is much more powerful. In Scala, you can match much
  // more:
  def matchEverything(obj: Any): String = obj match {
    // You can match values:
    case "Hello world" => "Got the string Hello world"

    // You can match by type:
    case x: Double => "Got a Double: " + x

    // You can specify conditions:
    case x: Int if x > 10000 => "Got a pretty big number!"

    // You can match case classes as before:
    case Person(name, number) => s"Got contact info for $name!"

    // You can match tuples:
    case (a: Int, b: Double, c: String) => s"Got a tuple: $a, $b, $c"

    // You can match data structures:
    case List(1, b, c) =>
      s"Got a list with three elements and starts with 1: 1, $b, $c"

    // You can nest patterns:
    case List(List((1, 2, "YAY"))) => "Got a list of list of tuple"

    // Match any case (default) if all previous haven't matched
    case _ => "Got unknown object"
  }

  // In fact, you can pattern match any object with an "unapply" method. This
  // feature is so powerful that Scala lets you define whole functions as
  // patterns:
  val patternFunc: Person => String = {
    case Person("George", number) => s"George's number: $number"
    case Person(name, number)     => s"Random person's number: $number"
  }

  println(patternFunc(Person("Random", "123654")))

}
