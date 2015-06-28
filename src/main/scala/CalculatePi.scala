import akka.actor.{ActorSystem, Props}

object CalculatePi extends App {

  val system = ActorSystem("pi")

  val piListener = system.actorOf(Props[Listener])

  val boss = system.actorOf(Props(new Boss(20, 10000, 5000, piListener)), "boss")

  boss ! Calculate
}
