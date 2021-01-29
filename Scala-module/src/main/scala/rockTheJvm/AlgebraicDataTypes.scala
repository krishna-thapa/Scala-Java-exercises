package rockTheJvm

object AlgebraicDataTypes extends App {

  // ADTs: Way of structuring your data
  sealed trait Weather // "Sum types
  case object Sunny  extends Weather
  case object Windy  extends Weather
  case object Rainy  extends Weather
  case object Cloudy extends Weather

  // Weather = Sunny + Windy + Rainy + Cloudy == SUM type

  def feeling(weather: Weather): String =
    weather match {
      case Sunny  => ":)"
      case Cloudy => ":|"
      case Rainy  => ":("
    }

  // Note: Compiler will give match not exhaustive since weather is sealed trait

  println(feeling(Sunny))

  case class WeatherForecastRequest(latitude: Double, longitude: Double)
  // (Double, Double) => WFR
  // type WFR = Double x Double = Product type

  // Hybrid type
  sealed trait WeatherForecastResponse // SUM type
  case class Valid(weather: Weather) extends WeatherForecastResponse // Product type
  case class Invalid(error: String, description: String) extends WeatherForecastResponse // Product type

  // Advantages
  // 1. illegal states are NOT representable
  // 2. highly composable
  // 3. immutable data structures
  // 4. just data, not functionality => structure our code

  type NaiveWeather = String
  def naiveFeeling(weather: String) = weather match {
    case "sunny" => ":)"
    // other cases
  }
  naiveFeeling("45 degree")
  // illegal states can be passed which should be avoided

  // complexity = number of possible values of that ADT
  // goal: to reduce complexity

  sealed trait WeatherServerError
  case object NotAvailable extends WeatherServerError
  // other cases

  // illegal states are NOT representable
  case class Invalid2(error: WeatherServerError, description: String) extends WeatherForecastResponse

  // Resources:
  // 1. https://www.youtube.com/watch?v=0wmcCdoExbM
  // 2. https://nrinaudo.github.io/scala-best-practices/definitions/adt.html

}
