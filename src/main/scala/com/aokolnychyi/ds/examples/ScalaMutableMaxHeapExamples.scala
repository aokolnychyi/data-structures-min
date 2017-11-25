package com.aokolnychyi.ds.examples

import com.aokolnychyi.ds.heap.ScalaMutableMaxHeap

object ScalaMutableMaxHeapExamples {

  def main(args: Array[String]): Unit = {
    val heap = new ScalaMutableMaxHeap[Int](Seq(1, 4, 6, 3, 2, 1, 3))
    println(heap)
    heap += 4
    println(heap)
    heap += 12
    heap += -1
    heap += -12
    println(heap)
  }
}
