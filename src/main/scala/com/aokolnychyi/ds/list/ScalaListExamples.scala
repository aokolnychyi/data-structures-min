package com.aokolnychyi.ds.list

object ScalaListExamples extends App {
  val list = Cons(1, Cons(2, Cons(3, Nil)))
  println(list.toString)
  println(list.map(element => element % 2).toString)
}
