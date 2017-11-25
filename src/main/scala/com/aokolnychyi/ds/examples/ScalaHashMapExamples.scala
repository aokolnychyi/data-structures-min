package com.aokolnychyi.ds.examples

import com.aokolnychyi.ds.map.ScalaHashMap

object ScalaHashMapExamples {

  implicit class CustomInt(val value: Int) {

    override def equals(other: Any): Boolean = other match {
      case that: CustomInt => value == that.value
      case _ => false
    }

    // introduce some collisions
    override def hashCode(): Int = if (value % 2 == 0) value else 31
  }

  def main(args: Array[String]): Unit = {
    var map = ScalaHashMap[Int, String]()
    println(map.get(1))
    println(map.put(1, "1").get(1))

    for (value <- 0 to 100000) {
      map = map + (value -> value.toString)
    }

    println(map.get(100))
    println(map.get(500))
    println(map.get(2000))
    println(map.get(20000))
    println(map.get(99999))
    println(map.get(21000))
    println(map.get(999))

    println(map.get(100999))
    println(map.get(29391999))

    // test collisions
    var customIntMap = ScalaHashMap[CustomInt, String]()
    for (value <- 0 to 500) {
      customIntMap = customIntMap.put(value, value.toString)
    }

    println(customIntMap.get(34))
    println(customIntMap.get(35))
    println(customIntMap.get(36))
    println(customIntMap.get(37))
    println(customIntMap.get(38))
    println(customIntMap.get(39))
  }

}
