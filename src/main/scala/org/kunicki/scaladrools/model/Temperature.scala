package org.kunicki.scaladrools.model

case class Temperature(value: Int)

case class TooLow(temperature: Temperature)

case class TooHigh(temperature: Temperature)