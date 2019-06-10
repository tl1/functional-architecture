import java.time.LocalDate

package object bookings {

  case class ReservationCommand(
    date: LocalDate,
    name: String,
    seats: Int
  )

}
