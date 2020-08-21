package essentialScala.Functions

sealed trait IntListGen {
  def fold[T](end: T, f: (Int, T) => T): T =
    this match {
      case EndFoldGen => end
      case PairFoldGen(hd, tl) => f(hd, tl.fold(end, f))
    }

  def length: Int = fold[Int](0, (_, tl) => 1 + tl)
  def product: Int = fold[Int](1, (hd, tl) => hd * tl)
  def sum: Int = fold[Int](0, (hd, tl) => hd + tl)
  def double: IntListGen = fold[IntListGen](EndFoldGen, (hd, tl) => PairFoldGen(hd * 2, tl.double))
}

case object EndFoldGen extends IntListGen
final case class PairFoldGen(hd: Int, tl: IntListGen) extends IntListGen
