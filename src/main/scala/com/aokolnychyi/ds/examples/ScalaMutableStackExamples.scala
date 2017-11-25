package com.aokolnychyi.ds.examples

import scala.util.Try

import com.aokolnychyi.ds.stack.ScalaMutableStack

object ScalaMutableStackExamples {

  def main(args: Array[String]): Unit = {
    val stack = ScalaMutableStack[Int]()
    stack.push(1)
    stack.push(2)
    stack.push(3)
    stack.push(4)
    println(stack.top())
    println(stack.pop())
    println(stack.pop())
    println(stack.pop())
    println(stack.pop())
    println(stack.top())
    println(Try(stack.pop()))
  }

}
