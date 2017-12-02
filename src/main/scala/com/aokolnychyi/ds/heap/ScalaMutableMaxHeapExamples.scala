package com.aokolnychyi.ds.heap

object ScalaMutableMaxHeapExamples {

  def main(args: Array[String]): Unit = {
    val heap = new ScalaMutableMaxHeap[Int](Seq(1, 4, 6, 3, 2, 1, 3))
    println(heap)
    println(heap.element())
    println(heap.peek())
    heap += 4
    println(heap)
    println(heap.element())
    println(heap.peek())
    heap += 12
    heap += -1
    heap += -12
    println(heap)
    println(heap.element())
  }
}
