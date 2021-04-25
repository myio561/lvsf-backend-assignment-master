package com.livesafe.tips

import org.scalatest.{FreeSpec, Matchers}

import java.util.UUID
import java.text.SimpleDateFormat
import java.util.{Date, NoSuchElementException}

class TipSpec extends FreeSpec with Matchers {
  "Tip construction" - {
    val legacyTipService = new LegacyTipService

    "legacy1 format" in {
      val tips: List[Tip] = legacyTipService.extractLegacyTips(
        """
          |tipid,created,Type,msg
          |3dad9bfc-2a4c-475d-b214-30fd5b3f5f47,1511140150,Disturbance,I'd like to report some baz
          |""".stripMargin)
      tips shouldBe List(Tip( UUID.fromString("3dad9bfc-2a4c-475d-b214-30fd5b3f5f47"), new Date(1511140150), TipType.DISTURBANCE, "I'd like to report some baz"))
    }

    "legacy2 format" in {
      val tips: List[Tip] = legacyTipService.extractLegacyTips(
        """
          |tipId,message,createdAt,type
          |ca51adaa-f024-4615-bac7-6f893e390ba1,I'd like to report some quux,2017-10-16T05:42:19.017Z,suspicious
          |""".stripMargin)
      val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

      tips shouldBe List(Tip(UUID.fromString("ca51adaa-f024-4615-bac7-6f893e390ba1"), format.parse("2017-10-16T05:42:19.017Z"), TipType.SUSPICIOUSACTIVITY, "I'd like to report some quux"))
    }
  }

  "TipType Lookup" - {
    "valid tip type different case" in {
      TipType("Smell") shouldBe TipType.SMELL
    }
    "valid tip type matching case" in {
      TipType("SMELL") shouldBe TipType.SMELL
    }
    "invalid tip type" in {
      assertThrows[NoSuchElementException] {
        TipType("Bug")
      }
    }
  }
}
