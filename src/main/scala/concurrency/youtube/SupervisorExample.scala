package concurrency.youtube

import akka.actor.SupervisorStrategy.{Restart, Resume}
import akka.actor.{Actor, ActorSystem, OneForOneStrategy, Props}

// Fault tolerance and actor life-cycle of an actor
object SupervisorExample extends App {
  case object CreateChild
  case class SignalChildren(order: Int)
  case class PrintSignal(order: Int)
  case class DivideNumber(n: Int, d: Int)
  case object BadStuff

  class ParentActor extends Actor {
    private var number = 0

    override def receive: Receive = {
      case CreateChild =>
        // since every child should have unique name
        context.actorOf(Props[ChildActor], "child" + number)
        number += 1
      case SignalChildren(n) =>
        // for each child we are sending print signal
        context.children.foreach(_ ! PrintSignal(n))
    }

    // resume the actor when the exception occurs
    // Should be inside the parent actor since parent handles the exception when its child produces
    override val supervisorStrategy: OneForOneStrategy =
      OneForOneStrategy(loggingEnabled = false) {
        case ae: ArithmeticException =>
          Resume // other options: Restart, Stop, Escalate
        case _: Exception => Restart
      }
  }

  class ChildActor extends Actor {
    println("Child created.")
    override def receive: Receive = {
      case PrintSignal(n)     => println(n + " " + self.path)
      case DivideNumber(n, d) => println(n / d)
      case BadStuff =>
        throw new RuntimeException("Stuff happened") // should make restart
    }

    // Actor Life - cycle
    // preStart and postStop are used to start and close resources like database connection
    override def preStart() {
      super.preStart()
      println("preStart")
    }

    override def postStop(): Unit = {
      super.postStop()
      println("postStop")
    }

    // preRestart and postRestart are mostly untouched
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      super.preRestart(reason, message)
      println("preStart")
    }

    override def postRestart(reason: Throwable): Unit = {
      super.postRestart(reason)
      println("postRestart")
    }
  }

  val system = ActorSystem("SupervisorExample")
  val actor = system.actorOf(Props[ParentActor], "Parent1")
  val actor2 = system.actorOf(Props[ParentActor], "Parent2")

  actor ! CreateChild
  //actor ! CreateChild

  val child0 =
    system.actorSelection("/user/Parent1/child0")

  //child0 ! DivideNumber(4, 2)
  child0 ! DivideNumber(4, 0) //Gives an error
  child0 ! DivideNumber(4, 2)
  // In Akka when child fails, it notify the parent and parent can take control with supervisor strategy
  // One for one strategy
  // All for one strategy

  child0 ! BadStuff

  Thread.sleep(1000)
  system.terminate()
}

// Outputs:
/*
  Child created.      :Child is created
  preStart            :preStart hook is called
  2                   :Got the message and does print divide job and resume ArithmeticException ones
  postStop            :Bad stuff cause Exception and stops the actor
  preStart            :Gets prestart call cause of Bad stuff exception
  Child created.      :Creates a new child and dispose the old one
  preStart            :preStart hook is called
  postRestart         :sends message to new child why the old child was disposed
  postStop            :postStop the replaced child
 */
