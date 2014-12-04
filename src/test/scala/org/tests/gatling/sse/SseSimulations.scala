package org.tests.gatling.sse

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
/**
 * @author ctranxuan
 */
class SseSimulation extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8080/app")
    .header("Accept", "text/event-stream")

  val scn = scenario(this.getClass.getSimpleName)
    .exec(sse("sse").get("/stocks/prices"))
    .pause(15)
    .exec(sse("close").close())

  setUp(scn.inject(atOnceUsers(2))).protocols(httpConf)
}

class SseWith1MeasureSimulation extends Simulation {

  val httpConf = http
                 .baseURL("http://localhost:8080/app")
                 .header("Accept", "text/event-stream")


  val scn = scenario(this.getClass.getSimpleName)
            .exec(sse("sse").get("/stocks/prices")
                  .check(wsAwait.within(10).until(1)))
            .pause(15)
            .exec(sse("close").close())

  setUp(scn.inject(atOnceUsers(2))).protocols(httpConf)
}


class SseWithRegexCheckSimulation extends Simulation {

  val httpConf = http
                 .baseURL("http://localhost:8080/app")
                 .header("Accept", "text/event-stream")

  val scn = scenario(this.getClass.getSimpleName)
            .exec(sse("sse").get("/stocks/prices")
            .check(wsAwait.within(10).until(1).regex(""""event":"snapshot(.*)"""")))
            .pause(15)
            .exec(sse("close").close())

  setUp(scn.inject(atOnceUsers(2))).protocols(httpConf)
}


class SseWithFailedRegexCheckSimulation extends Simulation {

  val httpConf = http
  .baseURL("http://localhost:8080/app")
  .header("Accept", "text/event-stream")
  .doNotTrackHeader("1")

  val scn = scenario(this.getClass.getSimpleName)
            .exec(sse("sse").get("/stocks/prices")
              .check(wsAwait.within(10).until(1).regex(""""event1":"snapshot(.*)"""")))
            .pause(15)
            .exec(sse("close").close())

  setUp(scn.inject(atOnceUsers(2))).protocols(httpConf)
}


class SseWithWsListenRegexCheckSimulation extends Simulation {
  val httpConf = http
                 .baseURL("http://localhost:8080/app")
                 .header("Accept", "text/event-stream")

  val scn = scenario(this.getClass.getSimpleName)
            .exec(sse("sse").get("/stocks/prices")
                  .check(wsListen.within(10).until(1).regex(""""event":"snapshot(.*)""""))
    )
            .pause(15)
            .exec(sse("close").close())

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}


class SseWithWsListenExpectCheckSimulation extends Simulation {
  val httpConf = http
                 .baseURL("http://localhost:8080/app")
                 .header("Accept", "text/event-stream")
                 

  val scn = scenario(this.getClass.getSimpleName)
            .exec(sse("sse").get("/stocks/prices")
                  .check(wsListen.within(30 seconds).expect(1))
    )
            .pause(15)
            .exec(sse("close").close())

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}


class SseWithWsAwaitExpectCheckSimulation extends Simulation {
  val httpConf = http
                 .baseURL("http://localhost:8080/app")
                 .header("Accept", "text/event-stream")
                 .doNotTrackHeader("1")

  val scn = scenario(this.getClass.getSimpleName)
            .exec(sse("sse").get("/stocks/prices")
                  .check(wsAwait.within(30 seconds).expect(1))
    )
            .pause(15)
            .exec(sse("close").close())

  setUp(scn.inject(atOnceUsers(5))).protocols(httpConf)
}


class SseWithRampWsAwaitExpectCheckSimulation extends Simulation {
  val httpConf = http
                 .baseURL("http://localhost:8080/app")
                 .header("Accept", "text/event-stream")
                 .doNotTrackHeader("1")

  val scn = scenario(this.getClass.getSimpleName)
            .exec(sse("sse").get("/stocks/prices")
                  .check(wsAwait.within(30 seconds).expect(1).message)
    )
            .pause(300)
            .exec(sse("close").close())

  setUp(scn.inject(rampUsers(100) over 120)).protocols(httpConf)
}
