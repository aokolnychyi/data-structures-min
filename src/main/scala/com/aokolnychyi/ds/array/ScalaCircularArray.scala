package com.aokolnychyi.ds.array

import scala.annotation.tailrec

// class ScalaCircularArray1[T: Manifest](val capacity: Int) {
//   val items = new Array[T](capacity)
// }

// class ScalaCircularArray2[T: ClassTag](val capacity: Int) {
//   val items = new Array[T](capacity)
// }

case class ScalaCircularArray[T](private val items: Array[T], private val headIndex: Int = 0) {

  def rotateLeft(shift: Int): ScalaCircularArray[T] = {
    val newHeadIndex = convertIndex(shift)
    ScalaCircularArray(items, newHeadIndex)
  }

  def get(index: Int): T = {
    val actualIndex = convertIndex(index)
    items(actualIndex)
  }

  def update(index: Int, value: T): Unit = {
    val actualIndex = convertIndex(index)
    // Note the syntax `xs(i) = x` is a shorthand for `xs.update(i, x)`.
    items(actualIndex) = value
  }

  override def toString: String = {
    val firstPart = items.slice(headIndex, items.length).mkString(",")
    if (headIndex == 0) {
      s"ScalaCircularArray: {$firstPart}"
    } else {
      val secondPart = items.slice(0, headIndex).mkString(",")
      s"ScalaCircularArray: {$firstPart,$secondPart}"
    }
  }

  @tailrec
  private def convertIndex(shift: Int): Int = {
    if (shift < 0) {
      convertIndex(shift + items.length)
    } else {
      (headIndex + shift) % items.length
    }
  }
}
