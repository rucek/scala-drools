package org.kunicki.scaladrools.rules

import org.kunicki.scaladrools.model.{TooLow, TooHigh, Temperature}
import org.scalatest.{ShouldMatchers, FlatSpec}

import scala.collection.JavaConversions._

class RuleRunnerSpec extends FlatSpec with ShouldMatchers {

  it should "run rules" in {
    // given
    val facts = Seq(
      Temperature(10),
      Temperature(50),
      Temperature(100)
    )

    val session = RuleRunner.createKnowledgeSession("WeatherRules.drl")

    // when
    facts.foreach(session.insert)
    session.fireAllRules
    val objects = session.getObjects
    val tooHighs = objects collect { case o: TooHigh => o }
    val tooLows = objects collect { case o: TooLow => o }

    // then
    tooLows should have size 1
    tooLows map(_.temperature) should contain(facts(0))
    tooHighs should have size 1
    tooHighs map(_.temperature) should contain(facts(2))
  }
}
