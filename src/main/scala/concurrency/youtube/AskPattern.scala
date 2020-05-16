package concurrency.youtube

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern._
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

/*
  ask pattern provides a way to send a message to an actor and expect to receive a
  response from it. The result of ask will be a Future that represents a possible reply.
 */
object AskPattern extends App {

  case object AskName
  case class NameResponse(name: String)
  case class AskNameOf(other: ActorRef)

  class AskActor(val name: String) extends Actor {
    implicit val ex
      : ExecutionContextExecutor = context.system.dispatcher //ExecutionContext.Implicits.global from Actor system

    override def receive: Receive = {
      case AskName => sender ! NameResponse(name)
      case AskNameOf(other) =>
        val f = other ? AskName
        f.onComplete {
          case Success(NameResponse(n)) => println("Their name is: ", n)
          case Success(s)               => println("Not the name type: ", s)
          case Failure(exception)       => println("Error", exception.getMessage)
        }

        // Using future with sender have to first store in current variable val
        // if not sender can be updated anytime
        val currentSender = sender
        Future {
          currentSender ! "message"
        }
    }
  }

  val system = ActorSystem("AskActor")
  // constructor takes an argument
  val actor = system.actorOf(Props(new AskActor("Pat")), "AskActor1")
  val actor2 = system.actorOf(Props(new AskActor("Val")), "AskActor2")

  implicit val ex: ExecutionContextExecutor = system.dispatcher
  implicit val timeout: Timeout = Timeout(1.seconds)

  val answer = actor ? AskName

  answer.foreach(n => println("Name is: ", n))

  actor ! AskNameOf(actor2) //Pat ask Val for Val's name

  // Be-careful: system can terminate before the actor response
  system.terminate()
}
