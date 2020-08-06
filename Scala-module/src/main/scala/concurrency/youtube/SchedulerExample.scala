package concurrency.youtube

import akka.actor.{Actor, ActorSystem, Props}
import scala.concurrent.duration._

object SchedulerExample extends App {

  case object Count

  class SchedulerExample extends Actor {
    var n = 0
    override def receive: Receive = {
      case Count =>
        n += 1
        println(n)
    }
  }

  val system = ActorSystem("SchedulerExample")

  // default Actor constructor
  val actor = system.actorOf(Props[SchedulerExample], "SchedulerExample")
  //cannot create instance of actor with new
  implicit val ec = system.dispatcher

  actor ! Count

  // run the actor bang after a one second just once
  system.scheduler.scheduleOnce(delay = 1.second)(actor ! Count)

  // run at an regular interval duration with time interval
  val regScheduler = system.scheduler.scheduleAtFixedRate(
    0.second,
    100.millisecond,
    actor,
    Count
  )

  Thread.sleep(1000)
  regScheduler.cancel //cancel before gets to system terminate

  system.terminate()
}
