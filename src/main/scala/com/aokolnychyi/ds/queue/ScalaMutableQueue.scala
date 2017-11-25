package com.aokolnychyi.ds.queue

import scala.collection.mutable.ArrayBuffer

class ScalaMutableQueue[T](capacity: Int = 4) {

  private val elements = ArrayBuffer.fill[Option[T]](capacity)(None)
  private var headIndex = -1
  private var tailIndex = -1

  def dequeue(): T = {
    if (size() == 0) throw new NoSuchElementException("cannot dequeue from an empty queue")
    val head = elements(headIndex)
    elements.update(headIndex, None)
    // if there is only one element and it is removed
    if (headIndex == tailIndex) {
      headIndex = -1
      tailIndex = -1
    } else {
      headIndex = (headIndex + 1) % capacity
    }
    head.get
  }

  def enqueue(element: T): Unit = {
    if (size() == capacity) throw new RuntimeException("cannot insert into a full queue")
    if (tailIndex == capacity - 1) tailIndex = 0 else tailIndex = (tailIndex + 1) % capacity
    elements.update(tailIndex, Some(element))
    // handle insertions to an empty queue
    if (headIndex == -1) headIndex = 0
  }

  def peek(): Option[T] = if (headIndex == -1) None else elements(headIndex)

  def size(): Int = {
    if (headIndex == -1 && tailIndex == -1) {
      0
    } else if (headIndex < tailIndex) {
      tailIndex - headIndex + 1
    } else {
      capacity - headIndex + tailIndex + 1
    }
  }
}
