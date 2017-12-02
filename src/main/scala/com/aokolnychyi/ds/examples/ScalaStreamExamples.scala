package com.aokolnychyi.ds.examples

import com.aokolnychyi.ds.stream.{Cons, Empty}

object ScalaStreamExamples extends App {
  val stream = Cons(1, Cons(2, Cons(3, Empty)))
  println(stream)
}
