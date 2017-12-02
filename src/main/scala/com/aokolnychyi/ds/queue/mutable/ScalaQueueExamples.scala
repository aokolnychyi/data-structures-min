package com.aokolnychyi.ds.queue.mutable

import scala.util.Try

object ScalaQueueExamples {

  def main(args: Array[String]): Unit = {
    val queue = new ScalaQueue[Char](capacity = 4)
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
