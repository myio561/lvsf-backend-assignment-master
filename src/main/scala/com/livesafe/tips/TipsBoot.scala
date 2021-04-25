package com.livesafe.tips

object TipsBoot extends App {

  val tipService = new LegacyTipService()

  val tipHttpService = new TipsHttpService(tipService)

  tipHttpService.start()

}
