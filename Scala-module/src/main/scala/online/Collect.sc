// Use of identity function: simply returns its argument

def squareIf(test: Boolean) = {
  List(1,2,3,4,5).map(if(test) x => x * x else identity)
}
squareIf(true)
squareIf(false)

// convert List[Option[A]] to List[A]

val l = List(Some("Hello"), None, Some("World"))
l.flatMap(el => el)
l.flatMap[String](identity)
for(x <- l; y <- x) yield y
l.flatten[String] // best and simple approach

val e: Either[Double, Float] = Right(1.0f)
e.fold(identity, _.toDouble)

// Use of collect method in scala collection
val myList = List("hello", 1, true, "world", Some(1))

// filter and map returns List[Any]
myList.filter(_.isInstanceOf[String])
myList.filter(_.isInstanceOf[String]).map(identity)

// flatMap returns List[String]
myList.flatMap {
  case s: String => Some(s)
  case _ => None
}

// collect returns List[String] and is concise
myList.collect{case s: String => s }

// A instance of Seq, Set or Map is actually a partial function.
// So, can get index elements
Seq(0,1,2,8).collect(myList)