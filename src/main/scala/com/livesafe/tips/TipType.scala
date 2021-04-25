package com.livesafe.tips

object TipType {
 sealed abstract class TipCategory (val names : Array[String])

 case object DISTURBANCE extends TipCategory(Array("DISTURBANCE"))
 case object SUSPICIOUSACTIVITY extends TipCategory(Array("SUSPICIOUS","SUSPICIOUSACTIVITY"))
 case object SMELL extends TipCategory(Array("SMELL"))
 case object ARSON extends TipCategory(Array("ARSON"))
 case object VIOLENCE extends TipCategory(Array("VIOLENCE"))

 val tipTypes = Seq(DISTURBANCE, SUSPICIOUSACTIVITY, SMELL, ARSON, VIOLENCE)

 def apply(name: String) = {
  tipTypes.find( _.names.contains(name.toUpperCase)).get
 }
}