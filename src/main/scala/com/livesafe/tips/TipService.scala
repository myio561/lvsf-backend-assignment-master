package com.livesafe.tips

import java.util.UUID

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import com.livesafe.tips.TipService.Tip

trait TipService {

  def getTips(): Future[List[Tip]]
  def getTip(id: UUID): Future[Tip]

}

class UnimplementedTipService extends TipService {

  /**
   * FIXME: Replace this with your implementation. This stub is provided as an example of how to load the legacy data from [[LegacyTipsService]]
   *
   * @return
   */
  def getTips(): Future[List[Tip]] = {
    for {
      rawLegacy1Tips <- LegacyTipsService.fetchLegacy1Tips
      rawLegacy2Tips <- LegacyTipsService.fetchLegacy2Tips
    } yield {
      val rawLegacyData = ("Raw legacy data" ::  rawLegacy1Tips :: rawLegacy2Tips :: Nil).mkString("\n")
      List(rawLegacyData)
    }
  }

    /**
   * FIXME: Replace this with your implementation. This stub is provided as an example of how to load the legacy data from [[LegacyTipsService]]
     *
     * @return
   */
  def getTip(id: UUID): Future[Tip] = {
    for {
      rawLegacy1Tips <- LegacyTipsService.fetchLegacy1Tips
      rawLegacy2Tips <- LegacyTipsService.fetchLegacy2Tips
    } yield {
      ("Raw legacy data" :: rawLegacy1Tips :: rawLegacy2Tips :: Nil).mkString("\n")
    }
  }

}

object TipService {
  /**
   * FIXME: We need a proper data model for 'Tip'!
   */
  type Tip = String
}
