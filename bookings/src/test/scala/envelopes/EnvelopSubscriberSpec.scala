package envelopes

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.testkit.{TestPublisher, TestSubscriber}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._

class EnvelopSubscriberSpec extends FlatSpec with Matchers with BeforeAndAfterAll {

  private implicit val system = ActorSystem("EnvelopSubscriberSpec")
  private implicit val materializer = ActorMaterializer()

  override protected def afterAll(): Unit = {
    materializer.shutdown()
    Await.result(system.terminate(), 2 seconds)
  }

  "EnvelopSubscriber" should "envelop message" in {
    val publisher = TestPublisher.probe[String]()
    val downstream = TestSubscriber.probe[Envelope[String]]()
    publisher.subscribe(doEnvelop(downstream))

    publisher
      .sendNext("message")
      .sendComplete()

    downstream.requestNext() should matchPattern {
      case Envelope(_, _, "message") =>
    }
  }
}
