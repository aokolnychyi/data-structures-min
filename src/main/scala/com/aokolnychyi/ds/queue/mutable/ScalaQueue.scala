package com.aokolnychyi.ds.queue.mutable

import scala.collection.mutable.ArrayBuffer

class ScalaQueue[T](capacity: Int = 4) {

  private val elements = ArrayBuffer.fill[Option[T]](capacity)(None)
  private var headIndex = -1
  private var tailIndex = -1

  // O(1) time
  def dequeue(): T = {
    if (size == 0) throw new NoSuchElementException("cannot dequeue from an empty queue")
    val head = elements(headIndex)
    elements(headIndex) = None
    // if there is only one element and it is removed
    if (headIndex == tailIndex) {
      headIndex = -1
      tailIndex = -1
    } else {
      headIndex = (headIndex + 1) % capacity
    }
    head.get
  }

  // O(1) time
  def enqueue(element: T): Unit = {
    if (size == capacity) throw new RuntimeException("cannot insert into a full queue")
    tailIndex = (tailIndex + 1) % capacity
    elements(tailIndex) = Some(element)
    // handle insertions to an empty queue
    if (headIndex == -1) headIndex = 0
  }

  // O(1) time
  def peek(): Option[T] = if (headIndex == -1) None else elements(headIndex)

  def size: Int = {
    if (headIndex == -1 && tailIndex == -1) {
      0
    } else if (headIndex < tailIndex) {
      tailIndex - headIndex + 1
    } else {
      capacity - headIndex + tailIndex + 1
    }
  }
}
