// For Comprehensions

// A general for comprehension
//  for {
//  x <- a
//  y <- b
//  z <- c
//  } yield e

// translates to:
// a.flatMap(x => b.flatMap(y => c.map(z => e)))

