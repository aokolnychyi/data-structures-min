package com.aokolnychyi.ds.list

object ScalaListExamples extends App {
  val list = Cons(1, Cons(2, Cons(3, Nil)))
  println(list.toString)
  println(list.map(element => element % 2).toString)
  println("=== duplicates ===")
  println(list.distinct)
  println(Cons(1, list).distinct)
  println(Cons(1, Cons(1, list)).distinct)
  println(Cons(1, Cons(2, list)).distinct)
  println(Cons(3, Cons(2, list)).distinct)
  println("=== pairwise swap ===")
  println(Cons(3, Nil).swapPairwise())
  println(list.swapPairwise())
  println(Cons(1, list).swapPairwise())
  println(Cons(2, Cons(1, list)).swapPairwise())
}
