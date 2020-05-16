package concurrency.youtube

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.routing.BalancingPool
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.image.{ImageView, PixelWriter, WritableImage}
import scalafx.scene.paint.Color

// https://simple.wikipedia.org/wiki/Mandelbrot_set
// http://www.scalafx.org/

object MandelbrotActors extends JFXApp {
  val MaxCount: Int = 10000
  val ImageSize: Int = 600
  val XMin: Double = -1.5
  val XMax: Double = 0.5
  val YMin: Double = -1.0
  val YMax: Double = 1.0

  case class Complex(real: Double, imag: Double) {
    def +(that: Complex): Complex = Complex(real + that.real, imag + that.imag)
    def *(that: Complex): Complex =
      Complex(
        real * that.real - imag * that.imag,
        real * that.imag + imag * that.real
      )
    def mag: Double = math.sqrt(real * real + imag * imag)
  }

  def mandelCount(c: Complex): Int = {
    var cnt = 0
    var z = Complex(0, 0)
    while (cnt < MaxCount && z.mag < 4) {
      z = z * z + c
      cnt += 1
    }
    cnt
  }

  case class Line(row: Int, y: Double)

  // Create a new Actor
  // Draw one line of mandlebrot set at a time
  class LineActor(pw: PixelWriter) extends Actor {
    override def receive: Receive = {
      case Line(row, y) =>
        for (j <- 0 until ImageSize) {
          val x = XMin + j * (XMax - XMin) / ImageSize
          val cnt = mandelCount(Complex(x, y))
          Platform.runLater {
            pw.setColor(
              j,
              row,
              if (cnt == MaxCount) Color.Black
              else {
                val scale = 10 * math.sqrt(cnt.toDouble / MaxCount) min 1.0
                Color(scale, 0, 0, 1)
              }
            )
          }
        }
    }
  }

  val system: ActorSystem = ActorSystem("MandelbrotSystem")

  stage = new JFXApp.PrimaryStage {
    title = "Actor Mandelbrot"
    scene = new Scene(ImageSize, ImageSize) {
      val image = new WritableImage(ImageSize, ImageSize)
      content = new ImageView(image)
      // pool vs group: pool creates its own sub-actors
      // https://doc.akka.io/api/akka/current/akka/routing/BalancingPool.html
      val router = system.actorOf(
        BalancingPool(16).props(Props(new LineActor(image.pixelWriter))),
        "Pool"
      )
      for (i <- 0 until ImageSize) {
        val y = YMin + i * (YMax - YMin) / ImageSize
        router ! Line(i, y)
      }
    }
  }
}

/*
  Messages can be sent via a router to efficiently route them to destination actors,
  known as its routees. A Router can be used inside or outside of an actor,
  and you can manage the routees yourselves or use a self contained router actor
  with configuration capabilities.
 */
