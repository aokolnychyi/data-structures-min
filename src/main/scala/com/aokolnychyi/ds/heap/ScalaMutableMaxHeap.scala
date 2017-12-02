package com.aokolnychyi.ds.heap

import scala.collection.mutable.ArrayBuffer

class ScalaMutableMaxHeap[T](elements: Seq[T])(implicit imp: T => Ordered[T]) {

  private val heap: ArrayBuffer[T] = ArrayBuffer(elements: _*)

  // O(n) time
  for (index <- elements.indices if index < elements.size / 2) {
    maxHeapify(index)
  }

  // O(log n) time
  def +=(elem: T): this.type = {
    heap += elem
    bubbleUp(heap.size - 1)
    this
  }

  // O(1) time
  def peek(): Option[T] = {
    heap.headOption
  }

  // O(1) time
  def element(): T = {
    heap.head
  }

  private def bubbleUp(index: Int): Unit = {
    for (parentIndex <- parentIndexOp(index)) {
      if (heap(index) > heap(parentIndex)) {
        swap(heap, index, parentIndex)
        bubbleUp(parentIndex)
      }
    }
  }

  private def maxHeapify(index: Int): Unit = {
    val indices = Seq(Some(index), leftChildIndexOp(index), rightChildIndexOp(index)).flatten
    val maxElementIndex = indices.maxBy(heap(_))
    if (maxElementIndex != index) {
      val currentElement = heap(index)
      heap.update(index, heap(maxElementIndex))
      heap.update(maxElementIndex, currentElement)
      maxHeapify(maxElementIndex)
    }
  }

  private def swap(seq: ArrayBuffer[T], firstIndex: Int, secondIndex: Int): Unit = {
    val firstIndexElement = seq(firstIndex)
    seq.update(firstIndex, seq(secondIndex))
    seq.update(secondIndex, firstIndexElement)
  }

  private def parentIndexOp(index: Int): Option[Int] = {
    val parentIndex = (index - 1) / 2
    Some(parentIndex).filter(isIndexDefined)
  }

  private def leftChildIndexOp(index: Int): Option[Int] = {
    val leftChildIndex = 2 * index + 2
    Some(leftChildIndex).filter(isIndexDefined)
  }

  private def rightChildIndexOp(index: Int): Option[Int] = {
    val rightChildIndex = 2 * index + 2
    Some(rightChildIndex).filter(isIndexDefined)
  }

  private def isIndexDefined(index: Int): Boolean = {
    index >= 0 && index < heap.size
  }

  override def toString: String = heap.toString
}
