// https://rockthejvm.com/blog/211732/typeclasses
// https://scalac.io/typeclasses-in-scala/
// Type classes are these super-abstract concepts in functional programming

// Problem:

/*def processMyList[T](list: List[T]): T = {
  ???
}*/

/*
  We can to create a sum method that produces the
  sum of an integer and concatenation of string and error for any other types.
 */

// Plugging an implicit
trait Summable[T] {
  //defines the capability of aggregating a list into a single element
  def sumElements(list: List[T]): T
}

implicit object IntSummable extends Summable[Int] {
  override def sumElements(list: List[Int]): Int = list.sum
}

implicit object StringSummable extends Summable[String] {
  override def sumElements(list: List[String]): String = list.mkString("")
}

//  two very different implementations of the "sum" we can perform.
//  We can then enhance the original method like this

def processMyList[T](list: List[T])(implicit summable: Summable[T]): T = {
  summable.sumElements(list)
}

processMyList(List(1,2,3,4))
processMyList(List("abc","def","."))
//processMyList(List(true, false))  // Compiler error

/*
In this way, the implicit works as both a capability enhancer and a type constraint,
because if the compiler cannot find an implicit instance of a ListAggregation of that
particular type, i.e. your specialized implementation, then it's certain that the code can't run.

The behavior we've just implemented is called "ad hoc polymorphism" because the sumElements ability is
unlocked only in the presence of an implicit instance of the trait which provides the method definition,
right there when it's called, hence the "ad hoc" name. "Polymorphism" because the implementations we can
provide can obviously be different for different types, as we did with Int and String.
 */