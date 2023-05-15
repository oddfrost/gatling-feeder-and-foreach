package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.util.concurrent.ThreadLocalRandom

class ForEachSimulation extends Simulation {

  val feeder = csv("search.csv").random

  val httpProtocol =
    http.baseUrl("https://computer-database.gatling.io")
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .acceptEncodingHeader("gzip, deflate")
      .userAgentHeader(
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0"
      )

  val scn = scenario("test")
    .feed(feeder, 5)
    .foreach("#{searchCriterion}", "attr"){
      exec{
        session =>
          println( "Single element:" + session("attr").as[String])
          //do smth..
          session
      }
    }


  setUp(
    scn.inject(atOnceUsers(1)),
  ).protocols(httpProtocol)
}
