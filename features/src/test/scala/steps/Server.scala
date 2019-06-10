package steps

import com.typesafe.config.ConfigFactory
import io.restassured.RestAssured._
import io.restassured.response.ValidatableResponse

import scala.collection.JavaConverters._
import scala.concurrent.duration._
import scala.util.Try

trait Server {
  val config = ConfigFactory.load()
  val host = config.getString("server.host")
  val port = config.getInt("server.port")

  def post(path: String, json: String): ValidatableResponse = {
    given()
      .baseUri(s"http://$host:$port")
      .contentType("application/json")
      .body(json)
      .post(path)
      .then()
  }

  def get(path: String, params: Map[String, String] = Map.empty): ValidatableResponse = {
    given()
      .baseUri(s"http://$host:$port")
      .params(params.asJava)
      .get(path)
      .then()
  }

  def retry(step: Any => Unit, tries: Int = 5, initialWait: Duration = 100 millis): Unit = {
    def compose(step: Any => Unit, tries: Int, wait: Duration): Try[Unit] = {
      val oneTry = Try {
        Thread.sleep(wait.toMillis)
        step()
      }
      if (tries == 1) oneTry
      else oneTry.recoverWith {
        case _: Throwable =>
          compose(step, tries - 1, wait * 2)
      }
    }

    compose(step, tries, initialWait).get
  }
}
