import scala.concurrent.duration.Duration


sealed trait PiMessage
case object Calculate extends PiMessage
case class Work(start: Int, nrOfElements: Int) extends PiMessage
case class Result(value: BigDecimal) extends PiMessage
case class PiApproximation(pi: BigDecimal, duration: Duration)



