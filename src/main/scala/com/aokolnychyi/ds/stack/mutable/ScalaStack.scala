package com.aokolnychyi.ds.stack.mutable

import scala.collection.mutable.ArrayBuffer

class ScalaStack[T] {

  private val elements = ArrayBuffer[T]()
  private var topIndex = -1

  def push(element: T): Unit = {
    elements.append(element)
    topIndex += 1
  }

  def pop(): T = {
    if (topIndex < 0) throw new NoSuchElementException("Cannot pop from an empty stack")
    val topElement = elements.remove(topIndex)
    topIndex -= 1
    topElement
  }

  def top(): Option[T] = elements.lastOption

}


object ScalaStack {
  def apply[T](elements: T*): ScalaStack[T] = {
    val stack = new ScalaStack[T]()
    for (element <- elements) stack.push(element)
    stack
  }
}
