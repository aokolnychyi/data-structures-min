package com.aokolnychyi.ds.stack.mutable

import scala.util.Try

object ScalaMutableStackExamples {

  def main(args: Array[String]): Unit = {
    val stack = ScalaStack[Int]()
    stack.push(1)
    stack.push(2)
    stack.push(3)
    stack.push(4)
    println(stack.peek())
    println(stack.pop())
    println(stack.pop())
    println(stack.pop())
    println(stack.pop())
    println(stack.peek())
    println(Try(stack.pop()))
  }

}
