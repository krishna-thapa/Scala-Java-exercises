package future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ Await, Future }
import scala.io.Source
import scala.util.{ Failure, Success, Try }

// Article: https://www.baeldung.com/scala/synchronous-handling-of-futures

object SynchronousFuture extends App {

  // A Future starts running concurrently upon creation and returns a result at some point in the future.
  // We can create a Future by calling the apply method on the Future object.
  def fetchDataFrom(url: String, waitTime: Long = 0L): Future[Try[String]] = {
    Future {
      Thread.sleep(waitTime)
      scala.util.Using(Source.fromURL(url)) {
        _.getLines().mkString
      }
    }
  }

  /*
    - Future Completion
    Using Thread.sleep(), is not an ideal solution because we won’t always know what the response time will be.
    Now, what if there was some way we could tell the main thread to wait until a Future completes and
    only continue execution after? This is where the Await API comes in.
   */

  // Await.ready
  // The Await class contains a ready method that blocks the calling thread until the Future completes or times-out.

  val futureData = fetchDataFrom("https://www.baeldung.com/")
  assert(!futureData.isCompleted)

  val completedFuture = Await.ready(futureData, 2.seconds)
  assert(completedFuture.isCompleted)
  assert(completedFuture.isInstanceOf[Future[Try[String]]])

  val result: Option[Serializable] = completedFuture.value.map {
    case Success(value) => value
    case Failure(exception) => exception.getMessage
  }
  println(result)
}
