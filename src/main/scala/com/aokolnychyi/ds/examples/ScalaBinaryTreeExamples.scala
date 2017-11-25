package com.aokolnychyi.ds.examples

import com.aokolnychyi.ds.tree.{Empty, Leaf, Node}

object ScalaBinaryTreeExamples extends App {

  import com.aokolnychyi.ds.tree.Implicits._

  val leaf1 = Leaf(6)
  val leaf2 = Leaf(3)
  val node1 = Node(2, Empty, leaf1)
  val node2 = Node(4, leaf2, Empty)
  val rootNode = Node(1, node1, node2)

  println("Size: " + rootNode.size())
  println("Max element: " + rootNode.max())
  println("Depth: " + rootNode.depth())

  val newRootNode = rootNode.map(v => v + 1)
  println("New root node value: " + rootNode.value)
  println("New max element: " + newRootNode.max())

  val preOrderTraversal = rootNode.foldPreOrder("")(_ + _)
  println("Pre-order traversal: " + preOrderTraversal)
  val postOrderTraversal = rootNode.foldPostOrder("")(_ + _)
  println("Post-order traversal: " + postOrderTraversal)

  println("Size via fold: " + rootNode.sizeViaFold())
}
