package com.aokolnychyi.ds.tree.bst

import scala.util.Try

object ScalaBSTExamples extends App {

  val bst = Leaf[Int]()
      .insert(5)
      .insert(1)
      .insert(3)
      .insert(0)
      .insert(6)

  println(bst.min)
  println(bst.contains(-1))
  println(bst.contains(3))
  println(bst.printInOrder())
  println(bst.find(3))
  println(bst.find(3).map(_.successor(bst.asInstanceOf[Node[Int]])))
  println(bst.find(6).map(_.successor(bst.asInstanceOf[Node[Int]])))
  println(bst.find(5).map(_.successor(bst.asInstanceOf[Node[Int]])))
  println(Try(bst.find(6).map(_.successor(bst.find(3).get.asInstanceOf[Node[Int]]))))
}
