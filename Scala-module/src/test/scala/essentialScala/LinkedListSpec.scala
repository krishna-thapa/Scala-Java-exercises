package essentialScala

import essentialScala.Functions.{ Pair, End }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LinkedListSpec extends AnyFlatSpec with Matchers {

  val example: Pair[Int] = Pair(1, Pair(2, Pair(3, End())))

  behavior of "IntListGen"

  it should "be true if it contains the element in the list" in {
    val actual = example.contains(2)
    actual shouldBe true
  }

  it should "throw an empty list exception" in {
    val thrown = the [IndexOutOfBoundsException] thrownBy example(4)
    thrown.getMessage should equal ("Empty list")

  }


}
