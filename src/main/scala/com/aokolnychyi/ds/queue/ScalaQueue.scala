package com.aokolnychyi.ds.queue

class ScalaQueue[T](in: List[T], out: List[T]) {

  def enqueue(element: T): ScalaQueue[T] = ScalaQueue(element :: in, out)

  def dequeue(): (T, ScalaQueue[T]) = out match {
    case outHead :: outTail => (outHead, ScalaQueue(in, outTail))
    case Nil if in != Nil => ScalaQueue(Nil, in.reverse).dequeue()
    case Nil => throw new NoSuchElementException("Cannot dequeue from an empty queue")
  }

  def peek(): Option[T] = out match {
    case outHead :: _ => Some(outHead)
    case Nil if in != Nil => ScalaQueue(Nil, in.reverse).peek()
    case Nil => None
  }

  override def toString: String = s"{${out.mkString(",") + in.reverse.mkString(",")}}"
}

object ScalaQueue {

  def apply[T](xs: T*) = new ScalaQueue[T](Nil, out = xs.toList)

  def apply[T](in: List[T], out: List[T]) = new ScalaQueue[T](in, out)

  object EmptyQueue extends ScalaQueue[Nothing](Nil, Nil) {}

}
