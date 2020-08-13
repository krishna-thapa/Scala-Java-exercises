package online

object PatternMatching extends App {

  /*
  A sealed class/trait is a superclass that is aware of every
  single class extending it. This behavior is possible using the same single file to express
  the sealed class and all of its subclasses.
   */

  /*sealed trait Animal {
    def name: String
  }*/

  /*
  Abstract class vs Trait:
  https://www.geeksforgeeks.org/difference-between-traits-and-abstract-classes-in-scala/
  */

  sealed abstract class Animal {
    def name: String
  }

  final case class Mammal(name: String, fromSea: Boolean) extends Animal
  final case class Bird(name: String = "Eagle")           extends Animal
  final case class Fish(name: String)                     extends Animal

  /*
  Another name for this kind of pattern matching is Constructor matching,
  meaning that the constructor is used in this case to make the match possible.
   */
  def matchAnimals(animal: Animal): String = {
    animal match {
      case Mammal(name, _) => s"Mammal: $name"
      case Bird(name)      => s"Bird: $name"
      case Fish(name)      => s"Fish: $name"
      //case _               => s"No animal"
      /*
        match may not be exhaustive.
        It would fail on the following input: Fish(_)
        animal match {
       */
    }
  }

  println(matchAnimals(Bird())) // default bird name eagle
  println(matchAnimals(Bird("Seagull")))
  println(matchAnimals(Fish("Seagull"))) // compiler should warn

  /*
  Scala uses constants to define numbers or boolean values.
  Patterns can consist of constants.
  Arrays, Lists, and Vectors consist of elements.
  These sequences and their elements are also used to form patterns.
   */
  def genericMatch(constant: Any): String = {
    constant match {
      case 0           => "Equal to zero"                               //constantsPatternMatching
      case false       => "Equal to boolean"                            //constantsPatternMatching
      case List(x)     => s"Equal to list of single element $x"         //sequencesPatternMatching
      case Seq(_, _*)  => "Equal to multiple elements in the seq"       //sequencesPatternMatching
      case (f, s)      => s"Equal to tuple with two elements $f and $s" //tuplesPatternMatching
      case str: String => s"Equal to string: $str"                      //typedPatternMatching
      case int: Int    => s"Equal to int: $int"                         //typedPatternMatching
      case Some(x)     => s"Equal to Option of $x"                      //optionsPatternMatching
      case None        => "Equal to None"                               //optionsPatternMatching

      case _ => "Equal to unknown"
    }
  }

  //we’re simply ignoring the values by using the underscore character

  println(genericMatch(0))
  println(genericMatch(true))  //Equal to unknown
  println(genericMatch(false)) //Equal to unknown
  println(genericMatch(Seq(1)))
  println(genericMatch(Seq(1, 2, 3)))

  println(genericMatch((123, "abc")))

  println(genericMatch("Hello"))
  println(genericMatch(123))

  println(genericMatch(Some(0)))
  println(genericMatch(None))
}
