package api

import java.time.LocalDate

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json._
import Json._

case class DateTest(date: LocalDate)

class FormatSpec extends FlatSpec with Matchers {
  implicit val testFormat: Format[DateTest] = format[DateTest]

  "LocalDate format" should "write date" in {
    Json.toJson(LocalDate.of(2017, 3, 8)) shouldBe JsString("2017-03-08")
  }

  it should "read date" in {
    JsString("2017-03-12").validate[LocalDate] should matchPattern {
      case JsSuccess(date: LocalDate, _) if date.isEqual(LocalDate.of(2017, 3, 12)) =>
    }
  }

  it should "read date gracefully" in {
    JsString("2017-3-1").validate[LocalDate] should matchPattern {
      case JsSuccess(date: LocalDate, _) if date.isEqual(LocalDate.of(2017, 3, 1)) =>
    }
  }

  it should "not accept invalid date format" in {
    JsString("abcd-3-1").validate[LocalDate] should matchPattern {
      case x: JsError if x.errors == Seq((__, Seq(JsonValidationError("expected.localDate")))) =>
    }
  }

  it should "not accept invalid date" in {
    JsString("2017-2-30").validate[LocalDate] should matchPattern {
      case x: JsError if x.errors == Seq((__, Seq(JsonValidationError("expected.validDate")))) =>
    }
  }

  it should "set correct path for an error" in {
    JsObject(Map(
      "date" -> JsString("2017-2-30")
    )).validate[DateTest] should matchPattern {
      case x: JsError if x.errors == Seq((__ \ "date", Seq(JsonValidationError("expected.validDate")))) =>
    }
  }
}
