package com.aokolnychyi.ds.stack

class ScalaStack[T](elements: List[T]) {

  def this() {
    this(Nil)
  }

  def push(element: T): ScalaStack[T] = ScalaStack(element :: elements)

  def pop(): (T, ScalaStack[T]) = elements match {
    case head :: tail => (head, ScalaStack(tail))
    case Nil => throw new NoSuchElementException("Cannot pop from an empty stack")
  }

  def peek(): Option[T] = elements.headOption

  override def toString: String = elements.mkString("{", ",", "}")

}

object ScalaStack {
  def apply[T](elements: List[T]): ScalaStack[T] = new ScalaStack(elements)
}
