package future

import com.typesafe.scalalogging.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{ Failure, Success, Try }

// NOTE: Article: https://www.baeldung.com/scala/future-success-failure

object FutureTransform extends App {

  private val logger = Logger(getClass)

  def printResult[A](result: Try[A], methodType: String): Unit =
    result match {
      case Success(value) => logger.info(s"Success for method type: $methodType: $value")
      case Failure(exception) =>
        logger.info(
          s"Failed for method type: $methodType with an exception: ${exception.getMessage}"
        )
    }

  /*
    Calls to external services through the internet can have a very high cost in terms of I/O, waiting for a
    response for many milliseconds (or even seconds!).
   */

  // Trivial implementation of the HTTP client
  class HttpClient {
    def get(url: String): Future[String] = {
      if (url.contains("today")) Future("Sunny")
      else if (url.contains("yesterday")) Future("Windy")
      else if (url.contains("tomorrow")) Future("Snowy")
      else Future(throw new RuntimeException)
    }
  }

  // Implement the forecast service that uses the HttpClient to retrieve the weather

  // Recovering with a Synchronous Computation

  /*
    The transform method creates a new Future by applying the specified function to the result of this Future.
    Indeed, with a function that accepts a Try value as input, we can handle both a Future completed successfully
    and a Future completed exceptionally. Moreover, the input function returns another Try value,
    which means that we can decide to recover from a Failure, mapping the Future value in a new value,
    or we can decide to simply leave the failure state.
   */

  class WeatherForecastService(val http: HttpClient) {
    var lastWeatherValue: Weather = Sunny

    def forecast(date: String): Future[Weather] = {
      http
        .get(s"http://weather.now?when=$date")
        .transform {
          case Success(value) =>
            val retrieved = Weather(value)
            lastWeatherValue = retrieved
            Try(retrieved)
          case Failure(_) => Try(lastWeatherValue)
        }
    }
  }

  val httpClient: HttpClient           = new HttpClient
  val weatherService                   = new WeatherForecastService(httpClient)
  val todayWeather: Future[Weather]    = weatherService.forecast("today")
  val tomorrowWeather: Future[Weather] = weatherService.forecast("tomorrow")
  val errorWeather: Future[Weather]    = weatherService.forecast("error")

  todayWeather.onComplete(printResult(_, "Transform"))
  tomorrowWeather.onComplete(printResult(_, "Transform"))
  errorWeather.onComplete(printResult(_, "Transform")) // will gave the same result from above

  // Recovering with an Asynchronous Computation
  /*
    TransformWith method creates a new Future by applying the specified function,
    which produces a Future, to the result of this Future.
    Ultimately, the method works like a flatMap on the Future type, which works both in a Future completed successfully
    and a Future completed exceptionally.
   */

  // use the transformWith method by retrieving a forecast from a different fallback service in case of error

  class WeatherForecastServiceWith(val http: HttpClient) {

    def forecast(date: String, fallbackUrl: String): Future[Weather] = {
      http
        .get(s"http://weather.now?when=$date")
        .transformWith {
          case Success(value) =>
            val retrieved = Weather(value)
            Future(retrieved)
          case Failure(exception) =>
            logger.error(s"Error in response: ${exception.getMessage}")
            http.get(fallbackUrl).map(Weather(_))
        }
    }
  }
  // The user of our forecast service can now have the requested information,
  // even if the primary forecast HTTP server is down!

  val weatherServiceTransformWith = new WeatherForecastServiceWith(httpClient)
  val todayWeatherTransformWith: Future[Weather] =
    weatherServiceTransformWith.forecast("today", "notNeeded")
  val errorWeatherTransformWith: Future[Weather] =
    weatherServiceTransformWith.forecast("error", "yesterday")

  todayWeatherTransformWith.onComplete(printResult(_, "TransformWith"))
  errorWeatherTransformWith.onComplete(printResult(_, "TransformWith"))

  /*
    Recover in Older Versions of Scala
    Before Scala 2.12, the transform method had a different signature: there was no way to turn a failure
    into a value different from an exception
    Indeed, the workaround existed, and it was calling first the map method to deal with the happy path and
    then calling the recover method. The latter allows us to recover a Future that completed exceptionally,
    turning it into something different from an exception.
   */

  class WeatherForecastServiceRecover(val http: HttpClient) {

    var lastWeatherValue: Weather = Sunny

    def forecast(date: String): Future[Weather] = {
      http
        .get(s"http://weather.now?when=$date")
        .map { result =>
          val retrieved = Weather(result)
          lastWeatherValue = retrieved
          retrieved
        }
        .recover {
          case e: Exception =>
            logger.error(s"Error in response for Recover: ${e.getMessage}")
            lastWeatherValue
        }
    }
  }

  /*
    Moreover, it is also possible to mimic transformWith behavior.
    Instead of using a simple map, we must use a flatMap. Remember, the transformWith takes a function
    returning a Future as input. Then, we can call the recoverWith method, which behaves exactly as we need
   */

  class WeatherForecastServiceRecoverWith(val http: HttpClient) {

    var lastWeatherValue: Weather = Sunny

    def forecast(date: String, fallBackUrl: String): Future[Weather] = {
      http
        .get(s"http://weather.now?when=$date")
        .flatMap { result =>
          val retrieved = Weather(result)
          lastWeatherValue = retrieved
          Future(retrieved)
        }
        .recoverWith {
          case e: Exception =>
            logger.error(s"Error in response for RecoverWith: ${e.getMessage}")
            http.get(fallBackUrl).map(Weather(_))
        }
    }
  }

  val weatherServiceRecover = new WeatherForecastServiceRecover(httpClient)
  val todayWeatherRecover: Future[Weather] =
    weatherServiceRecover.forecast("today")
  val errorWeatherRecover: Future[Weather] =
    weatherServiceRecover.forecast("error") // will gave the same result from above

  todayWeatherRecover.onComplete(printResult(_, "Recover"))
  errorWeatherRecover.onComplete(printResult(_, "Recover"))

  val weatherForecastServiceRecoverWith = new WeatherForecastServiceRecoverWith(httpClient)
  val todayWeatherRecoverWith: Future[Weather] =
    weatherForecastServiceRecoverWith.forecast("today", "notNeeded")
  val errorWeatherRecoverWith: Future[Weather] =
    weatherForecastServiceRecoverWith.forecast("error", "yesterday")

  todayWeatherRecoverWith.onComplete(printResult(_, "RecoverWith"))
  errorWeatherRecoverWith.onComplete(printResult(_, "RecoverWith"))
}
