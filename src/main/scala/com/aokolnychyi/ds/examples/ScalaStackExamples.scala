package com.aokolnychyi.ds.examples

import com.aokolnychyi.ds.stack.ScalaStack

object ScalaStackExamples {

  def main(args: Array[String]): Unit = {
    var stack = ScalaStack(List(2, 3, 4, 5))
    stack = stack.push(1)
    stack = stack.push(0)
    println(stack.peek())
    val (top, remainingStack) = stack.pop()
    println("Top: " + top)
    println("Remaining stack: " + remainingStack)
    println(remainingStack.pop())
  }

}
