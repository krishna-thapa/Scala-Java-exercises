package rockTheJvm

object underscores {

  // 1 - ignoring stuff
  val _ = 5 // No use at all
  val onlyFives = (1 to 10).map(_ => 5)

  trait Singer
  // extends trait Actor should implement trait Singer also
  trait Actor { _: Singer =>
    // implementation
  }

  def processList(list: List[Option[_]]): Int = list.length

  // 2 - "everything" = wildcard

  val meaningOfLife: Any = 42
  meaningOfLife match {
    case _ => "I am fine with anything"
  }

  //import scala.concurrent.duration._    // import anything

  // 3 = default initializers

  var myString: String = _ //jmp initialize 0 for numeric, false for boolean and null for reference types

  // 4 - lambda sugar

  List(1,2,3,4,5).map(_ * 5)

  val sumFunction: (Int, Int) => Int =  _ + _ //(a,b) => a + b

  // 5 - Eta-expansion

  def incrementer(x: Int): Int = x + 1
  val incrementerFunction = incrementer _  //creating function with x => incrementer(x)
  incrementerFunction(12)

  // 6 - Higher Kind of Types -?

  class HigherKindJewel[M[_]]
  val myJewel = new HigherKindJewel[List]

  // 7 - vararg methods
  def makeSentence(words: String*) = words.toSeq.mkString(" ")
  makeSentence("i", "love", "scala")
  val words = List("i", "love", "scala")
  val love = makeSentence(words: _*)

  def main(args: Array[String]): Unit = {

    println(love)
  }
}
