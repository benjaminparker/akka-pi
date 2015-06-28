import akka.actor._
import akka.routing.RoundRobinRouter
import scala.concurrent.duration._

class Boss(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int, listener: ActorRef) extends Actor {
  var pi = BigDecimal(0.0)
  var nrOfResults: Int = _
  val start: Long = System.currentTimeMillis

  val workerRouter = context.actorOf(
    Props[Worker].withRouter(RoundRobinRouter(nrOfWorkers)), name = "workerRouter")

  override def receive = {
    case Calculate => {
      println (s"Using $nrOfWorkers workers. Sending $nrOfMessages messages. For each message worker calculates $nrOfElements elements.")
      for (i ← 0 until nrOfMessages) workerRouter ! Work(i * nrOfElements, nrOfElements)
    }
    case Result(value) => {
      pi += value
      nrOfResults += 1
      if (nrOfResults == nrOfMessages) {
        listener ! PiApproximation(pi, duration = (System.currentTimeMillis - start).millis)
        context.stop(self)
      }
    }
  }
}

class Worker extends Actor {
  override def receive = {
    case Work(start, noOfElems) => sender ! Result(calculatePiFor(start, noOfElems))
  }

  def calculatePiFor(start: Int, nrOfElements: Int): BigDecimal = {
    var acc = BigDecimal(0.0)
    for (i <- start until (start + nrOfElements)) {
      acc += BigDecimal(4.0) * (1 - (i % 2) * 2) / (2 * i + 1)
    }
    acc
  }
}

class Listener extends Actor {
  def receive = {
    case PiApproximation(pi, duration) =>
      println(f"Pi approximation: $pi%2.30f")
      println(s"Calculation time: $duration")
      context.system.shutdown()
  }
}
