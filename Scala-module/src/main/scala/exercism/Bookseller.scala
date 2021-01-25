package exercism

// https://www.codewars.com/kata/54dc6f5a224c26032800005c/train/scala

object Bookseller extends App {

  val b = Array("BBAR 150", "CDXE 515", "BKWR 250", "BTSQ 890", "DRTY 600")
  val c = Array("A", "B", "C", "D")
  val expected = "(A : 0) - (B : 1290) - (C : 515) - (D : 600)"

  var output: Map[String, Int] = Map()

  def stockSummary(lstOfArt: Array[String], lstOfCat: Array[String]): String = {
    val foo: Array[Array[String]] = lstOfArt.map(_.trim.split(" "))

    foo.foldLeft("")((_, b) => {
      lstOfCat.foreach { bar =>
        if(b.head.startsWith(bar)) {
          val preSum = output.getOrElse(bar, 0)
          output = output ++ Map(bar -> (preSum + b(1).toInt))
        }
      }
      lstOfCat.map(x => x -> output.getOrElse(x, 0)).mkString(" - ").replaceAll("," , " : ")
    })
  }

    val result = stockSummary(b ,c)
    println(result)


  // -------------------------- Second: Functional approach

  def stockSummaryPart2(lstOfArt: Array[String], lstOfCat: Array[String]): String = {
    val foo = lstOfArt.map(parseArt).groupBy(_._1).view.mapValues(countArts)
    lstOfCat.map(x => x -> foo.getOrElse(x.head, 0)).mkString(" - ").replaceAll("," , " : ")
  }

  type Art = (Char, Int)

  private def parseArt(s: String): Art = {
    val Array(a, b) = s.split(" ")
    (a.head, b.toInt)
  }

  private def countArts (arts: Array[Art]): Int = {
    arts.map(_._2).sum
  }

  val result2 = stockSummaryPart2(b ,c)
  println(result2)

}
