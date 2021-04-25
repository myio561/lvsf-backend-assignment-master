package com.livesafe.tips

object TipsBoot extends App {

  /**
   * Fixme: Provide your own tip service
   */
  val tipService = new UnimplementedTipService()

  val tipHttpService = new TipsHttpService(tipService)

  tipHttpService.start()

}
