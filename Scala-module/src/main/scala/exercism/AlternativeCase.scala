package exercism

object AlternativeCase extends App {

  // we are alone in this world
  // We ArE aLoNe In ThIs WoRlD

  val input: String = "This is a !!!!! test - of the emergency? broadcast System."

  var flag   = true
  var result = input.toCharArray

  for (index <- 0 until input.length) {
    if (flag && result(index).isLetter) {
      result(index) = result(index).toUpper
      flag = false
    } else if (!flag && result(index).isLetter) {
      flag = true
    }
  }
  println(result.mkString)

  val charArray: Array[Char] =
    "This is a !!!!! test - of the emergency? broadcast System.".toCharArray

  val rr = charArray.foldLeft[String]("")((a, b) => {
    val len = a.length - a.count(!_.isLetter)
    val c   = if (b.isLetter && len % 2 == 0) b.toUpper else b
    s"$a$c"
  })
  println(rr)
}
