import scala.concurrent.duration.Duration


case object Calculate
case class Work(start: Int, nrOfElements: Int)
case class Result(value: BigDecimal)
case class PiApproximation(pi: BigDecimal, duration: Duration)



