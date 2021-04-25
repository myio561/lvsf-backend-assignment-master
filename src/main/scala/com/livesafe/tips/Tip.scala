package com.livesafe.tips

import com.livesafe.tips.TipType._

import java.text.SimpleDateFormat
import java.util.{Date, UUID}
import scala.beans.BeanProperty

// Because the JSON library is Java it assumes there is a
// standard bean getter method for each field
// we have to add the @BeanProperty annotation for Scala
// to generate this method for us, without it the JSON
// printer will not be able to retrieve a value
case class Tip(
                @BeanProperty val tipId: UUID,
                @BeanProperty val createdAt: Date,
                @BeanProperty val tipType: TipCategory,
                @BeanProperty val message: String)


object Tip {

  /**
   * Creates a new Tip object from 4 string inputs. Since the TipTypes are a known
   * limited set of values, it is used to evaluate whether the string is from
   * Legacy 1 or 2 format
   * @param str1 always the UUID
   * @param str2 either the createDate or message
   * @param str3 either the createDate or TipType
   * @param str4 either the TipType or message
   * @return
   */
  def apply (str1: String, str2: String, str3: String, str4: String): Tip = {
    // if the TipType is in position 4, it is legacy 2 format
    if (TipType.tipTypes.exists(_.names.contains(str4.toUpperCase))) {
      val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
      new Tip(tipId = UUID.fromString(str1), createdAt = format.parse(str3), tipType = TipType(str4), message = str2)
     } else {
      // else this data is in legacy 1 format
      new Tip(tipId = UUID.fromString(str1), createdAt = new Date(str2.toInt), tipType = TipType(str3), message = str4)
    }
  }

}