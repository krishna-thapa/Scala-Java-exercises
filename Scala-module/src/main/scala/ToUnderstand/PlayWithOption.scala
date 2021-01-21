package ToUnderstand

object PlayWithOption extends App {

  val fun = (str: String) => {
    // No print when the inputField is None
    println(s"Input: $str")
    str.toUpperCase()
  }

  def validateOptionalField(inputField: Option[String], validation: String => String): String = {
     inputField.map(validation).getOrElse("")
  }

  // Same as above but longer, so use map
  def validateOptionalFieldWithCase(inputField: Option[String], validation: String => String): String = {
     inputField match {
       case Some(value: String) => validation(value)
       case None => ""
     }
  }

  println("Result: " + validateOptionalField(Some("foo"), fun))

  println("Result: " + validateOptionalField(Some(""), fun))

  println("Result: " + validateOptionalField(None, fun))
}
