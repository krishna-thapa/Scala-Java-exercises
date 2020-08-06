package concurrency.youtube

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

// Communicating actors
object ActorCountDown extends App {

  case class StartCounting(n: Int, other: ActorRef)
  case class CountDown(n: Int)

  class CountDownActor extends Actor {
    override def receive: Receive = {
      case StartCounting(n, other) =>
        println("Start the countdown ", n)
        other ! CountDown(n - 1) // tell other actor to count down from n-1
      case CountDown(n) =>
        println(self) //current actorRef
        if (n > 0) {
          println("Continue ", n) //print count down
          sender ! CountDown(n - 1) //send message back to who send the message
        } else {
          // all actor have context
          context.system.terminate() //cannot just call for system.terminate()
        }
    }
  }
  val system = ActorSystem("CountDownActor")
  val actor1 = system.actorOf(Props[CountDownActor], "CountDown1")
  val actor2 = system.actorOf(Props[CountDownActor], "CountDown2")

  actor1 ! StartCounting(10, actor2)

}
