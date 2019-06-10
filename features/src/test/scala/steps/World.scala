package steps

import io.restassured.response.ValidatableResponse

object World {
  var givenCapacity: Option[Int] = None
  var availabilityResponse: Option[ValidatableResponse] = None
  var reservationResponse: Option[ValidatableResponse] = None
}
