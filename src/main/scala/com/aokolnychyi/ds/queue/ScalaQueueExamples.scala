package com.aokolnychyi.ds.queue

object ScalaQueueExamples {

  def main(args: Array[String]): Unit = {
    var queue = ScalaQueue[Int]()

    queue = queue.enqueue(1)
    queue = queue.enqueue(2)
    queue = queue.enqueue(3)

    println(queue.dequeue())
    println(queue.peek())
    val (_, newQueue) = queue.dequeue()
    println(newQueue.dequeue())
  }

}
