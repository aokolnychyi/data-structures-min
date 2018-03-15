package com.aokolnychyi.ds.map.mutable

object ScalaMutableHashMapExamples {

  def main(args: Array[String]): Unit = {
    val map = new ScalaHashMap[Int, String]()
    map += (5, "5")
    map += (2, "2")
    map += (4, "4")
    map += (9, "9")
    map += (1, "1")
    map += (13, "13")

    println(map.get(3))
    println(map.get(2))
    println(map.get(5))
    println(map.get(1))
    println(map.get(9))
    println(map.get(13))

    map += (1, "updated-1")

    println(map.get(1))
  }

}
