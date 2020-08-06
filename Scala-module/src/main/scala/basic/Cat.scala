package basic

// Traits
// Similar to Java interfaces, traits define an object type and method
// signatures. Scala allows partial implementation of those methods.
// Constructor parameters are not allowed. Traits can inherit from other
// traits or classes without parameters.

trait Cat {

  def breed: String
  def color: String
  def bark: Boolean = true
  def bite: Boolean

}

trait Meow {
  def meow: String = "Meow"
}

// A trait can also be used as Mixin. The class "extends" the first trait,
// but the keyword "with" can add additional traits.

class SaintBernard extends Cat with Meow {
  val breed = "Saint Bernard"
  val color = "brown"
  def bite = false
}
