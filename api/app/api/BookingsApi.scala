package api

import javax.inject.Inject
import play.api.libs.json.JsObject
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.Future

class BookingsApi @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def reservations = Action(parse.json).async { request =>
    Future.successful(Accepted(JsObject(Seq.empty)))
  }

}
