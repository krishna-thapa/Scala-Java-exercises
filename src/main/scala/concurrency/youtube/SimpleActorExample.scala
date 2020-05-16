package concurrency.youtube

import akka.actor.{Actor, ActorSystem, Props}

object SimpleActorExample extends App {

  class SimpleActor extends Actor {
    override def receive: Receive = {
      case s: String => println("String: ", s)
      case i: Int    => println("Number", i)
    }

    def foo = println("Normal method")
  }

  val system = ActorSystem("SimpleSystem")

  // default Actor constructor
  val actor = system.actorOf(Props[SimpleActor], "SimpleActor") //cannot create instance of actor with new

  // actor.foo  //this is wrong and gives an error since actor is a ref not instance of a class

  // The following actor messages happens in different threads
  println("Before messages")
  actor ! "Hi there" //call bang to send the message to an actor
  println("After string message")
  actor ! 42
  println("After int message")
  actor ! 'a'
  println("After char message")

  // Above outputs runs in different threads hence the order of output doesn't match

  system.terminate()
}
