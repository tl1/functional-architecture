import java.time.LocalDate

import reservations.ReservationCommand
import play.api.libs.json._
import Json._

import scala.util.Try

package object api {

  implicit lazy val localDateFormat: Format[LocalDate] = new Format[LocalDate] {
    private val dateRegex = "([0-9]{4})-([0-9]{1,2})-([0-9]{1,2})".r
    override def writes(o: LocalDate): JsValue = JsString(f"${o.getYear}-${o.getMonthValue}%02d-${o.getDayOfMonth}%02d")
    override def reads(json: JsValue): JsResult[LocalDate] = json match {
      case JsString(dateRegex(year, month, day)) =>
        Try(LocalDate.of(year.toInt, month.toInt, day.toInt)).fold(
          _ => JsError("expected.validDate"),
          date => JsSuccess(date)
        )
      case _ =>
        JsError("expected.localDate")
    }
  }

  implicit lazy val reservationCommandReads: Reads[ReservationCommand] = reads[ReservationCommand]

}
