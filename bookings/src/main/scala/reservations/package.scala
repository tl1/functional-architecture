import java.time.LocalDate

import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import envelopes.Envelope
import org.reactivestreams.Subscriber

package object reservations {

  case class ReservationCommand(
    date: LocalDate,
    name: String,
    seats: Int
  )

  def doReservation(implicit mat: Materializer): Subscriber[Envelope[ReservationCommand]] = Flow[Envelope[ReservationCommand]]
    .to(Sink.foreach { elem =>
      println(elem)
    })
    .runWith(Source.asSubscriber[Envelope[ReservationCommand]])

}
