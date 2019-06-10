package api

import akka.stream.{Materializer, OverflowStrategy}
import akka.stream.scaladsl.{Keep, Sink, Source}
import javax.inject.Inject
import play.api.libs.json.{JsError, JsObject, JsSuccess}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}
import envelopes._
import reservations._

class BookingsApi @Inject() (
  cc: ControllerComponents,
  ec: ExecutionContext,
  mat: Materializer
) extends AbstractController(cc) {

  private implicit val _mat = mat
  private implicit val _ec = ec

  val (actor, publisher) = Source
    .actorRef(1000, OverflowStrategy.dropNew)
    .toMat(Sink.asPublisher[ReservationCommand](false))(Keep.both)
    .run()

  publisher.subscribe(doEnvelop(doReservation))

  def reservations = Action(parse.json).async { request =>
    request.body.validate[ReservationCommand] match {
      case JsSuccess(reservation, _) =>
        Future {
          actor ! reservation
          Accepted(JsObject(Seq.empty))
        }
      case JsError(errors) =>
        Future {
          BadRequest(JsError.toJson(errors))
        }
    }
  }

}
