package essentialScala

import essentialScala.Functions.{ EndFoldGen, PairFoldGen }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IntListGenSpec extends AnyFlatSpec with Matchers {

  val example: PairFoldGen = PairFoldGen(1, PairFoldGen(9, PairFoldGen(3, EndFoldGen)))

  behavior of "IntListGen"

  it should "return correct length of the list" in {
    val actual = example.length
    val expected = 3
    actual shouldBe expected
  }

  it should "return correct sum of the list" in {
    val actual = example.sum
    val expected = 13
    actual shouldBe expected
  }

  it should "return correct product of the list" in {
    val actual = example.product
    val expected = 27
    actual shouldBe expected
  }

  it should "return correct list of double" in {
    val actual = example.double
    val expected = PairFoldGen(2,PairFoldGen(36,PairFoldGen(48,EndFoldGen)))
    actual shouldBe expected
  }

}
