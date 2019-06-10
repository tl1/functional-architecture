import java.time.OffsetDateTime
import java.util.UUID

import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import org.reactivestreams.Subscriber

package object envelopes {

  case class Envelope[T](
    id: UUID,
    created: OffsetDateTime,
    message: T
  )

  private def envelop[T] =
    (id: UUID, created: OffsetDateTime, message: T) => Envelope(id, created, message)

  private def envelopWithDefaults[T] =
    (message: T) => envelop(UUID.randomUUID(), OffsetDateTime.now(), message)

  def envelopSubscriber[T](next: Subscriber[Envelope[T]])(implicit mat: Materializer): Subscriber[T] = Flow[T]
    .map(envelopWithDefaults)
    .to(Sink.fromSubscriber(next))
    .runWith(Source.asSubscriber[T])

}
