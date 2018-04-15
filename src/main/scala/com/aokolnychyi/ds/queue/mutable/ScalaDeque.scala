package com.aokolnychyi.ds.queue.mutable

import scala.collection.mutable.ArrayBuffer

class ScalaDeque[T](capacity: Int = 4) {

  private val elements = ArrayBuffer.fill[Option[T]](capacity)(None)
  private var headIndex = -1
  private var tailIndex = -1

  // O(1) time
  def dequeue(): T = {
    if (size == 0) throw new NoSuchElementException("cannot dequeue from an empty deque")
    val Some(head) = elements(headIndex)
    elements(headIndex) = None
    // if there is only one element and it is removed
    if (size == 1) {
      headIndex = -1
      tailIndex = -1
    } else {
      headIndex = (headIndex + 1) % capacity
    }
    head
  }

  // O(1) time
  def enqueue(element: T): Unit = {
   insert(element)
  }

  private def insert(element: T): Unit = {
    if (size == capacity) throw new RuntimeException("cannot insert into a full deque")
    tailIndex = (tailIndex + 1) % capacity
    elements(tailIndex) = Some(element)
    // handle insertions to an empty deque
    if (headIndex == -1) headIndex = 0
  }

  // O(1) time
  def push(element: T): Unit = {
    insert(element)
  }

  // O(1) time
  def pop(): T = {
    if (size == 0) throw new NoSuchElementException("deque is empty")

    val Some(tail) = elements(tailIndex)
    elements(tailIndex) = None
    if (size == 1) {
      tailIndex = -1
      headIndex = -1
    } else if (tailIndex == 0) {
      tailIndex = headIndex + size - 2
    } else {
      tailIndex -= 1
    }

    tail
  }

  def size: Int = {
    if (headIndex == -1 && tailIndex == -1) {
      0
    } else if (headIndex == tailIndex) {
      1
    } else if (headIndex < tailIndex) {
      tailIndex - headIndex + 1
    } else {
      capacity - headIndex + tailIndex + 1
    }
  }
}
