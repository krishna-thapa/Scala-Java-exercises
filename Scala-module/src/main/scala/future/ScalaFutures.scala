package future

import com.typesafe.scalalogging.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ Await, Future }
import scala.util.{ Failure, Success, Try }

object ScalaFutures extends App {

  private val logger = Logger(getClass)

  // Article: https://www.baeldung.com/scala/futures

  // Future represents a result of an asynchronous computation that may or may not be available yet.
  /*
      - ExecutionContext
      This specifies how and on which thread pool the Future code will execute.
      There is a global built-in ExecutionContext, which uses ForkJoinPool
      with its parallelism level set to the number of available processors.

      Future.apply takes it as a by-name parameter.
      It moves evaluation into a thread provided by implicit ExecutionContext.
   */

  private val generateRandomInt: () => Int = () => {
    val randomInt = scala.util.Random.nextInt(100)
    randomInt
  }

  /*
     - Future.fromTry() function:
     If Try {} block throws an exception, the Future.fromTry() is equivalent to
     the Future.failed() function. Otherwise, it’ll be somewhat similar to the
     Future.successful() function.
   */
  def tryDivide(divider: Int): Future[Int] = {
    Future.fromTry(Try {
      generateRandomInt() / divider
    })
  }

  /*
    - Await for Future
    Await.result blocks the main thread and waits a defined duration for the result of the given Future.
    If it’s not ready after that time or complete with a failure, Await.result throws an exception.
    Because Await.result blocks the main thread, we should only use it when we really need to wait.
   */

  val result: Int = Await.result(tryDivide(5), 2.seconds)
  logger.info(s"Result when Await success: $result")
  logger.info("================================")

  /*
    - Callbacks
    - onComplete: Returns Try type from future
   */

  def printResult[A](result: Try[A], methodType: String): Unit =
    result match {
      case Success(value) => logger.info(s"Success for method type: $methodType: $value")
      case Failure(exception) =>
        logger.info(
          s"Failed for method type: $methodType with an exception: ${exception.getMessage}"
        )
    }

  tryDivide(5).onComplete(printResult(_, "onComplete"))
  tryDivide(0).onComplete(printResult(_, "onComplete")) // Failed

  /*
    - Callbacks
    - foreach: call a callback only when the Future completes successfully
   */
  def printSucceedResult[A](result: A): Unit =
    logger.info(s"Success for method type foreach: $result")
  tryDivide(5).foreach(printSucceedResult)

  /*
    - Error handling
    - failed: If we have a use-case that depends on knowing if a given Future failed with
    the appropriate exception, we want to treat failure as success
    It tries to transform a failed Future into a successfully completed one with Throwable as a result.
   */

  val failedF: Future[Int]        = Future.failed(new IllegalArgumentException("BOOOMMM!!!"))
  val failureF: Future[Throwable] = failedF.failed

  /*
    - Error handling
    - fallbackTo: It takes an alternative Future in case of a failure of the current one and
    evaluates them simultaneously. If both fail, the resulting Future will fail with the
    Throwable taken from the current one.
   */

  tryDivide(0).fallbackTo(tryDivide(5)).onComplete(printResult(_, "fallbackTo"))

  /*
    - Error handling
    - recover: handle a particular exception by providing an alternative value
    It takes a partial function that turns any matching exception into a successful result.
    Otherwise, it will keep the original exception.
   */

  tryDivide(0)
    .recover {
      case _: ArithmeticException => 0
    }
    .onComplete(printResult(_, "recover"))

  /*
    - Error handling
    - recoverWith: handle a particular exception with another Future
   */
  tryDivide(0)
    .recoverWith {
      case _: ArithmeticException => tryDivide(5)
    }
    .onComplete(printResult(_, "recoverWith"))

  /*
    - Transform Future
    - map: transform its successful result without blocking the main thread:
    creates a new Future[T] by applying the new function with the use of future result
   */

  val mapResult: Future[Int] = tryDivide(5).map(_ * 2)
  mapResult.onComplete(printResult(_, "map"))

  /*
    - Transform Future
    - flatMap: transform a Future using a function that returns Future
    behaves in the same way as the map method but keeps the resulting Future flat,
   */

  val mapWithResultFunc: Future[Future[Int]] = tryDivide(5).map(tryDivide)
  val flatMapResult: Future[Int]             = tryDivide(5).flatMap(tryDivide)
  flatMapResult.onComplete(printResult(_, "flatMap"))

  /*
    - Transform Future
    - transform: can map both successful and failed cases with the transform(f: Try[T] => Try[S]) function
    the transform() method accepts a function that takes the Future result as the input and returns a Try
    instance as the output
   */

  val resultTransform: Future[String] = tryDivide(0).transform {
    case Success(value)     => Success(s"Success: $value")
    case Failure(exception) => Failure(new Exception(s"Error: ${exception.getMessage}"))
  }
  resultTransform.onComplete(printResult(_, "transform"))

  /*
    - Transform Future
    - transformWith: similar to the transform() function, the transformWith(f: Try[T] => Future[S]) accepts
    a function as the input
    this function, however, converts the given Try instance directly to a Future instance:
   */

  val resultTransformWith: Future[String] = tryDivide(5).transformWith {
    case Success(value)     => Future.successful(s"Success: $value")
    case Failure(exception) => Future.failed(new Exception(s"Error: ${exception.getMessage}"))
  }
  resultTransformWith.onComplete(printResult(_, "transformWith"))

  /*
    - Transform Future
    - andThen: applies a side-effecting function to the given Future and returns the same Future:
    returns the same Future after applying the given function, we can chain multiple andThen() invocations together
   */

  val andThenResult: Future[Int] = tryDivide(5)
    .andThen {
      case Success(value) => logger.info(s"Success inside method type: andThen: $value")
    }
    .andThen {
      case Success(value) => logger.info(s"Success inside method type: andThen: $value")
    }

  andThenResult.onComplete(printResult(_, "andThen"))

  //NOTE: As shown above, the variable andThenResult still contains the original Future after applying
  // two side-effecting functions.

  /*
    - Combining Futures
    - zip: For combing successful results of two independent Futures into a pair(Tuple2)
    if any of them fail, the resulting Future will also fail with the same reason as the leftmost of them.
   */

  val zipResult: Future[(Int, Int)] = tryDivide(5).zip(tryDivide(2))
  zipResult.onComplete(printResult(_, "zip"))

  /*
    - Combining Futures
    - zipWith: combine the results of two independent Futures into something other than a pair(Tuple2)
    It uses the given function for combining successful results of both Futures.
   */

  def areEqual(x: Int, y: Int): Boolean = x == y
  def tryDivideWithNum(divider: Int)    = Future.fromTry(Try { 25 / divider })

  val zipWithResult: Future[Boolean] = tryDivideWithNum(5).zipWith(tryDivideWithNum(5))(areEqual)
  zipWithResult.onComplete(printResult(_, "zipWith"))

  // NOTE: use the areEqual method for zipping, which checks if both numbers (taken from tryDivide methods) are equal.

  /*
    - Combining Futures
    - traverse: use the Future.traverse method, which performs a parallel map of multiple elements:
    It uses the given function for combining successful results of both Futures.
   */

  val magicDividers: List[Int]          = List(1, 2, 3, 4)
  val traverseResult: Future[List[Int]] = Future.traverse(magicDividers)(tryDivide)
  traverseResult.onComplete(printResult(_, "traverse"))

  /*
    NOTE: It calls the tryDivide method for each of the given magic dividers and combines them into a single Future.
    Each of those evaluations happens on a different thread taken from the ExecutionContext.
    If any of them fail, the resulting Future will also fail.
   */
}
