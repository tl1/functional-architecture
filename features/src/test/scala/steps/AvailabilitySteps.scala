package steps

import java.time.YearMonth

import cucumber.api.scala.{EN, ScalaDsl}
import World._
import org.hamcrest.Matchers._

class AvailabilitySteps extends ScalaDsl with EN with Server {
  Given("""a capacity of ([0-9]+) seats""") { capacity: Int =>
    givenCapacity = Some(capacity)
  }

  When("""([^ ]+) asks for availabilities in ([0-9]{2})-([0-9]{4})""") { (_: String, month: Int, year: Int) =>
    availabilityResponse = Some(get(s"/availability/$year/$month"))
  }

  When("""([^ ]+) asks for availabilities on ([0-9]{2})-([0-9]{2})-([0-9]{4})""") { (_: String, day: Int, month: Int, year: Int) =>
    availabilityResponse = Some(get(s"/availability/$year/$month/$day"))
  }

  Then("""on each day in ([0-9]{2})-([0-9]{4}) there are ([0-9]+) seats available""") { (month: Int, year: Int, capacity: Int) =>
    assert(availabilityResponse.isDefined, "Availability response is undefined")
    val noOfDays = YearMonth.of(year, month).lengthOfMonth()
    availabilityResponse.get
      .statusCode(200)
      .contentType("application/json")
      .body("$", hasSize(noOfDays))
    (1 to noOfDays).foreach { day =>
      val ix = day - 1
      availabilityResponse.get
        .body(s"[$ix].seats", is(capacity))
        .body(s"[$ix].date", is(f"$year%04d-$month%02d-$day%02d"))
    }
  }

  Then("""on ([0-9]{2})-([0-9]{2})-([0-9]{4}) there are ([0-9]+) seats available""") { (day: Int, month: Int, year: Int, capacity: Int) =>
    assert(availabilityResponse.isDefined, "Availability response is undefined")
    availabilityResponse.get
      .statusCode(200)
      .contentType("application/json")
      .body("$", hasSize(1))
      .body(s"[0].seats", is(capacity))
      .body(s"[0].date", is(f"$year%04d-$month%02d-$day%02d"))
  }

  Then("""the availabilities on ([0-9]{2})-([0-9]{2})-([0-9]{4}) are reduced to ([0-9]+) seats""") { (day: Int, month: Int, year: Int, capacity: Int) =>
    retry { _ =>
      get(s"/availability/$year/$month/$day")
        .statusCode(200)
        .contentType("application/json")
        .body("$", hasSize(1))
        .body(s"[0].seats", is(capacity))
        .body(s"[0].date", is(f"$year%04d-$month%02d-$day%02d"))
    }
  }
}