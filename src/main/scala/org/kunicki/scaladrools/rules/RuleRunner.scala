package org.kunicki.scaladrools.rules

import org.drools._
import org.drools.builder._
import org.drools.io.ResourceFactory
import org.drools.runtime._
import org.kunicki.scaladrools.model.Temperature
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions._

object RuleRunner extends App {

  val logger = LoggerFactory.getLogger(RuleRunner.getClass)

  logger.info("Creating Knowledge Session")
  val knowledgeSession = createKnowledgeSession("WeatherRules.drl")

  logger.info("Creating and inserting Temperature")
  val shouldBeTooHot = Temperature(100)
  val shouldBeTooCold = Temperature(20)
  knowledgeSession.insert(shouldBeTooHot)
  knowledgeSession.insert(shouldBeTooCold)

  logger.info("Firing all rules")
  knowledgeSession.fireAllRules()

  def createKnowledgeSession(fileName: String): StatefulKnowledgeSession = {

    def createKnowledgeBuilder: KnowledgeBuilder = {
      val knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder()
      knowledgeBuilder.add(ResourceFactory.newClassPathResource(fileName), ResourceType.DRL)

      val errors = knowledgeBuilder.getErrors
      if (errors.size() > 0) {
        for (error <- errors) logger.error(error.getMessage)
        throw new IllegalArgumentException("Problem with the Knowledge base")
      }
      knowledgeBuilder
    }

    val knowledgeBuilder = createKnowledgeBuilder

    def createKnowledgeBase: KnowledgeBase = {
      val knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase()
      knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages)
      knowledgeBase
    }

    val knowledgeBase = createKnowledgeBase

    def createKnowledgeSession: StatefulKnowledgeSession = {
      val session: StatefulKnowledgeSession = knowledgeBase.newStatefulKnowledgeSession()
      session.setGlobal("logger", LoggerFactory.getLogger(fileName))
      session
    }

    createKnowledgeSession
  }
}