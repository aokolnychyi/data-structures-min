package com.aokolnychyi.ds.array

object ScalaCircularArrayExamples extends App {
  println(ScalaCircularArray(Array(1, 2, 3, 4, 5)))
  println(ScalaCircularArray(Array(1, 2, 3, 4, 5)).rotateLeft(2))
  println(ScalaCircularArray(Array(1, 2, 3, 4, 5)).rotateLeft(2).get(0))
  println(ScalaCircularArray(Array(1, 2, 3, 4, 5)).rotateLeft(-2))
  println(ScalaCircularArray(Array(1, 2, 3, 4, 5)).rotateLeft(-2).get(2))
  val arr = ScalaCircularArray(Array(1, 2, 3, 4, 5)).rotateLeft(-2)
  arr.update(index = 2, value = -11)
  arr(2) = -12
  println(arr.get(2))
  println(ScalaCircularArray(Array(1, 2, 3, 4, 5)).rotateLeft(-2).rotateLeft(2))
  println(ScalaCircularArray(Array(1, 2, 3, 4, 5)).rotateLeft(-7))
}
