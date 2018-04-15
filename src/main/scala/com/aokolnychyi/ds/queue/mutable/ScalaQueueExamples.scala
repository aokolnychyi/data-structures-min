package com.aokolnychyi.ds.queue.mutable

import scala.util.Try

object ScalaQueueExamples {

  def main(args: Array[String]): Unit = {
    println("==== Use Case 1 ====")
    val deque1 = new ScalaDeque[Char](capacity = 4)
    deque1.enqueue('a')
    println(deque1.dequeue())
    deque1.enqueue('a')
    deque1.enqueue('b')
    println(deque1.dequeue())
    deque1.enqueue('c')
    deque1.enqueue('d')
    deque1.enqueue('e')
    println(deque1.dequeue())
    println(deque1.dequeue())
    println(deque1.dequeue())
    println(deque1.dequeue())
    println(Try(deque1.dequeue()))
    println("==== Use Case 2 ====")
    val deque2 = new ScalaDeque[Int](capacity = 3)
    deque2.push(-1)
    deque2.push(0)
    deque2.push(1)
    println(deque2.dequeue())
    println(deque2.dequeue())
    deque2.enqueue(2)
    println(deque2.pop())
    println("size: " + deque2.size)
    println(deque2.dequeue())
    println(deque2.size)
  }

}
