package examples

object StringInterpolation {

  def main(args: Array[String]): Unit = {
    val name = "Krishna"
    val age = 27
    println("Hello World")

    /**
      * using + concat similar like java
      */
    println(name + " is " + age + " years old.")

    /**
      * String Interpolator - Prepending s to any string
      * literal allows the usage of variables directly in the string
      */
    println(s"$name is $age years old.")

    /**
      * f String Interpolator - Prepending f to any string literal allows the creation
      * of simple formatted strings. It is similar to C languages style printf .
      * When using the f interpolator, all variable references should be followed by
      * a printf style format string, like %d, %s, %f etc.
      */
    println(f"$name%s is $age%d years old.")

    /**
      * raw String Interpolator - The raw interpolator is similar to the s interpolator
      * except that it performs “No escaping of literals within the string”
      */
    println(s"Hello \n World")
    println(raw"Hello \n world")

    //using the string formatting using printf and format method of scala
    val num1 = 25
    val num2 = 56.89
    val str1 = "Krishna"

    printf("(%d -- %f -- %s)", num1, num2, str1)
    println("(%d -- %f -- %s)".format(num1, num2, str1))

  }
}
