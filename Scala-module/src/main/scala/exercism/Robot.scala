package exercism

case class Robot(bearing: Bearing.Value, coordinates: (Int, Int)) {
  def turnRight: Robot = {
    val bearingId = bearing.id + 1
    val nextDirectionId = if (bearingId == 4) 0 else bearingId
    Robot(Bearing.apply(nextDirectionId), coordinates)
  }
  def turnLeft: Robot = {
    val bearingId = bearing.id - 1
    val nextDirectionId = if (bearingId == -1) Bearing.maxId-1 else bearingId
    Robot(Bearing.apply(nextDirectionId), coordinates)
  }
  def advance: Robot = {
    bearing match {
      case Bearing.North => Robot(bearing, (coordinates._1, coordinates._2+1))
      case Bearing.East => Robot(bearing, (coordinates._1+1, coordinates._2))
      case Bearing.South => Robot(bearing, (coordinates._1, coordinates._2-1))
      case Bearing.West => Robot(bearing, (coordinates._1-1, coordinates._2))
    }
  }

  def simulate(movement: String): Robot = {
    movement.foldLeft(this) {
      (robot, movement) => movement match {
        case 'A' => robot.advance
        case 'L' => robot.turnLeft
        case 'R' => robot.turnRight
      }
    }
  }
}
object Bearing extends Enumeration {
  type direction = Value
  val North, East, South, West = Value
}