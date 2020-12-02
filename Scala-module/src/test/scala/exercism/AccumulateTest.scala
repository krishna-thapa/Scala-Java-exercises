package exercism

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/** @version created manually **/
class AccumulateTest extends AnyFlatSpec with Matchers {

  it should "allow empty accumulation" in {
    val accumulate   = new Accumulate
    val accumulation = accumulate.accumulate[Int, Int](x => x * x, List.empty)
    accumulation should be(List.empty)
  }

  it should "accumulate squares" in {
    val accumulate   = new Accumulate
    val accumulation = accumulate.accumulate[Int, Int](x => x * x, List(1, 2, 3))
    accumulation should be(List(1, 4, 9))
  }

  it should "accumulate upcases" in {
    val accumulate = new Accumulate
    val accumulation =
      accumulate.accumulate[String, String](_.map(_.toUpper), List("hello", "world"))
    accumulation should be(List("HELLO", "WORLD"))
  }

  it should "accumulate reversed strings" in {
    val accumulate = new Accumulate
    val accumulation =
      accumulate.accumulate[String, String](_.reverse, List("eht", "kciuq", "nworb", "xof", "cte"))
    accumulation should be(List("the", "quick", "brown", "fox", "etc"))
  }

  it should "allow different return type" in {
    val accumulate   = new Accumulate
    val accumulation = accumulate.accumulate[Int, String](_.toString, List(1, 2, 3))
    accumulation should be(List("1", "2", "3"))
  }

  /*  it should "allow for big lists but misses with trivial implementation" in {
    val accumulate = new Accumulate
    an[java.lang.StackOverflowError] should be thrownBy
      accumulate.accumulate[Int, Int](x => x, List.range(1, 7000)).sum
  }*/

  it should "allow for big lists and succeeds with tailrec" in {
    val accumulate       = new Accumulate
    val accumulation_sum = accumulate.accumulateTail[Int, Int](x => x, List.range(1, 7000)).sum
    accumulation_sum should be(24496500)
  }
}
