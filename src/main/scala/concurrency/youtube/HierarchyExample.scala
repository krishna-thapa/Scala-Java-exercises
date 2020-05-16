package concurrency.youtube

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object HierarchyExample extends App {

  case object CreateChild
  case class SignalChildren(order: Int)
  case class PrintSignal(order: Int)

  class ParentActor extends Actor {
    private var number = 0
    // Just for the demo purpose, not the way it should be done
    // Context keeps track of the children, no need to create a mutable Buffer
    //private val children = collection.mutable.Buffer[ActorRef]()

    override def receive: Receive = {
      case CreateChild =>
        // every time we create a child, we add in buffer
        // children += context.actorOf(Props[ChildActor], "child" + number)

        // since every child should have unique name
        context.actorOf(Props[ChildActor], "child" + number)
        number += 1
      case SignalChildren(n) =>
        // for each child we are sending print signal
        context.children.foreach(_ ! PrintSignal(n))
    }
  }

  class ChildActor extends Actor {
    override def receive: Receive = {
      case PrintSignal(n) => println(n + " " + self.path)
    }
  }

  val system = ActorSystem("HierarchyExample")
  val actor = system.actorOf(Props[ParentActor], "Parent1")
  val actor2 = system.actorOf(Props[ParentActor], "Parent2")

  actor ! CreateChild
  //actor ! SignalChildren
  actor ! CreateChild
  //actor ! SignalChildren
  actor ! CreateChild
  actor ! SignalChildren(1)

  actor2 ! CreateChild

  // get hold of that child
  val child0 =
    system.actorSelection("akka://HierarchyExample/user/Parent2/child0")
  child0 ! PrintSignal(2)

  system.terminate()
}
