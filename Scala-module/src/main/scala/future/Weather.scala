package future

sealed trait Weather

object Weather {
  def apply(s: String): Weather =
    s match {
      case "Sunny"  => Sunny
      case "Cloudy" => Cloudy
      case "Rainy"  => Rainy
      case "Windy"  => Windy
      case "Snowy"  => Snowy
    }
}

case object Sunny  extends Weather
case object Cloudy extends Weather
case object Rainy  extends Weather
case object Windy  extends Weather
case object Snowy  extends Weather
