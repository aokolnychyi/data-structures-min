package com.aokolnychyi.ds.examples

import com.aokolnychyi.ds.stream.{Cons, Nil}

object ScalaStreamExamples extends App {
  val stream = Cons(1, Cons(2, Cons(3, Nil)))
  println(stream)
}
