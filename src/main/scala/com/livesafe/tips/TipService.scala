package com.livesafe.tips

import com.fasterxml.jackson.databind.ObjectMapper

import java.util.UUID
import scala.collection.immutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait TipService {

  def getTips(): Future[List[String]]

  def getTip(id: UUID): Future[String]

}

class LegacyTipService extends TipService {
  val mapper = new ObjectMapper()

  def extractLegacyTips(rawLegacy1Tips: String): List[Tip] = {
    rawLegacy1Tips.split("\n")
      // skip initial newline (from split) and the header row
      .drop(2).map { row => {
      // extract the fields
      val fields: Array[String] = row.split(",")
      Tip(fields(0), fields(1), fields(2), fields(3))
    }
    }.toList
  }

  /**
   * Retrieve a list of combined legacy 1 and 2 data
   *
   * @return
   */
  def getTips(): Future[List[String]] = {
    for {
      rawLegacy1Tips <- LegacyTipsService.fetchLegacy1Tips
      rawLegacy2Tips <- LegacyTipsService.fetchLegacy2Tips
    } yield {

      val tips1: Seq[Tip] = extractLegacyTips(rawLegacy1Tips)
      val tips2: Seq[Tip] = extractLegacyTips(rawLegacy2Tips)

      // convert the collections to standard Java types
      // so that the JSON library understands them
      import collection.JavaConverters._
      val tip1JSON = mapper.writeValueAsString(tips1.asJava)
      val tip2JSON = mapper.writeValueAsString(tips2.asJava)

      List( ("JSON Legacy data" :: tip1JSON :: tip2JSON :: Nil).mkString("\n") )
    }
  }

  /**
   * Given a valid Tip UUID the tip object is returned
   * @return
   */
  def getTip(id: UUID): Future[String] = {
    for {
      rawLegacy1Tips <- LegacyTipsService.fetchLegacy1Tips
      rawLegacy2Tips <- LegacyTipsService.fetchLegacy2Tips
    } yield {
      val tips: Seq[Tip] = extractLegacyTips(rawLegacy1Tips) ++ extractLegacyTips(rawLegacy2Tips)
      val tipJSON = tips.find(_.tipId.compareTo(id) == 0)
        .map(mapper.writeValueAsString)
        .get
      ("JSON legacy data" :: tipJSON :: Nil).mkString("\n")
    }
  }

}
