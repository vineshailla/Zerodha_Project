package org.example

import org.slf4j.LoggerFactory

object Sample extends App{
  val logger = LoggerFactory.getLogger(getClass)
  logger.info(s"table is created")
}
