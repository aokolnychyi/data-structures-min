package com.aokolnychyi.ds.stack

class ScalaStack[T](elements: List[T]) {

  def this() {
    this(Nil)
  }

  // O(1) time
  def push(element: T): ScalaStack[T] = ScalaStack(element :: elements)

  // O(1) time
  def pop(): (T, ScalaStack[T]) = elements match {
    case head :: tail => (head, ScalaStack(tail))
    case Nil => throw new NoSuchElementException("Cannot pop from an empty stack")
  }

  // O(1) time
  def peek(): Option[T] = elements.headOption

  override def toString: String = elements.mkString("{", ",", "}")

}

object ScalaStack {
  def apply[T](elements: List[T]): ScalaStack[T] = new ScalaStack(elements)
}
