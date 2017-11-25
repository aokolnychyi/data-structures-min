package com.aokolnychyi.ds.examples

import scala.util.Try

import com.aokolnychyi.ds.queue.ScalaMutableQueue

object ScalaMutableQueueExamples {

  def main(args: Array[String]): Unit = {
    val queue = new ScalaMutableQueue[Char](capacity = 4)
    queue.enqueue('a')
    println(queue.dequeue())
    queue.enqueue('a')
    queue.enqueue('b')
    println(queue.dequeue())
    queue.enqueue('c')
    queue.enqueue('d')
    queue.enqueue('e')
    println(queue.dequeue())
    println(queue.dequeue())
    println(queue.dequeue())
    println(queue.dequeue())
    println(Try(queue.dequeue()))
  }

}
