package steps

import cucumber.api.scala.{EN, ScalaDsl}
import steps.World._
import org.hamcrest.Matchers._

class ReservationSteps extends ScalaDsl with EN with Server {
  When("""([^ ]+) makes a reservation of ([0-9]+) seats on ([0-9]{2})-([0-9]{2})-([0-9]{4})""") { (actor: String, seats: Int, day: Int, month: Int, year: Int) =>
    reservationResponse = Some(post("/reservations",
      s"""
        |{
        |  "date": "$year-$month-$day",
        |  "name": "$actor",
        |  "seats": $seats
        |}
      """.stripMargin))
  }

  Then("""the reservation is accepted""") { () =>
    assert(reservationResponse.isDefined, "Reservation response is undefined")
    reservationResponse.get
      .statusCode(202)
      .contentType("application/json")
  }
}