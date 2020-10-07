package exercism

object WordCount extends App {

  val inputString:String =  "\"That's the password: 'PASSWORD 123'!\", cried the Special Agent.\\nSo I fled"
  println(wordCounts(inputString))

  def wordCounts(inputString: String): Map[String, Int] = {
    val result = inputString.split(" ")
      .map(word =>
       word
         .trim
         .toLowerCase()
      )
    val count = result.count(_.contains("so"))
    Map(inputString -> count)
  }
}
