package com.aokolnychyi.ds.tree.mutable

case class ScalaBinaryTreeNode[T](
    element: T,
    leftChild: Option[ScalaBinaryTreeNode[T]],
    rightChild: Option[ScalaBinaryTreeNode[T]]) {

  def minHeight: Int = combineHeight(heights => heights.min)

  def maxHeight: Int = combineHeight(heights => heights.max)

  private def combineHeight(op: Seq[Int] => Int): Int = this match {
    case ScalaBinaryTreeNode(_, None, None) => 1
    case ScalaBinaryTreeNode(_, Some(leftNode), None) => leftNode.combineHeight(op) + 1
    case ScalaBinaryTreeNode(_, None, Some(rightNode)) => rightNode.combineHeight(op) + 1
    case ScalaBinaryTreeNode(_, Some(leftNode), Some(rightNode)) =>
      val heights = Seq(leftNode.combineHeight(op), rightNode.combineHeight(op))
      op(heights) + 1
  }

  def isBalanced: Boolean = this match {
    case ScalaBinaryTreeNode(_, None, None) => true
    case ScalaBinaryTreeNode(_, Some(leftNode), None) => leftNode.maxHeight <= 1
    case ScalaBinaryTreeNode(_, None, Some(rightNode)) => rightNode.maxHeight <= 1
    case ScalaBinaryTreeNode(_, Some(leftNode), Some(rightNode)) =>
      Math.abs(rightNode.maxHeight - leftNode.minHeight) <= 1 &&
      Math.abs(leftNode.maxHeight - rightNode.minHeight) <= 1 &&
      leftNode.isBalanced && rightNode.isBalanced
  }
}

object ScalaBinaryTreeNodeExamples {

  def main(args: Array[String]): Unit = {
    test1()
    test2()
    test3()
    test4()
  }

  def test1(): Unit = {
    val node1 = ScalaBinaryTreeNode(1, None, None)
    val node2 = ScalaBinaryTreeNode(2, Some(node1), None)
    val node4 = ScalaBinaryTreeNode(4, None, None)
    val node3 = ScalaBinaryTreeNode(3, Some(node2), Some(node4))
    println(node3.minHeight)
    println(node3.maxHeight)
    println(node3.isBalanced)
  }

  def test2(): Unit = {
    val node1 = ScalaBinaryTreeNode(1, None, None)
    val node2 = ScalaBinaryTreeNode(2, Some(node1), None)
    val node3 = ScalaBinaryTreeNode(3, Some(node2), None)
    val node4 = ScalaBinaryTreeNode(4, Some(node3), None)
    println(node4.minHeight)
    println(node4.maxHeight)
    println(node4.isBalanced)
  }

  def test3(): Unit = {
    val node1 = ScalaBinaryTreeNode(1, None, None)
    val node2 = ScalaBinaryTreeNode(2, None, Some(node1))
    val node8 = ScalaBinaryTreeNode(8, None, None)
    val node9 = ScalaBinaryTreeNode(9, None, None)
    val node7 = ScalaBinaryTreeNode(7, Some(node8), Some(node9))
    val node5 = ScalaBinaryTreeNode(5, None, None)
    val node10 = ScalaBinaryTreeNode(10, None, None)
    val node6 = ScalaBinaryTreeNode(6, Some(node7), Some(node10))
    val node4 = ScalaBinaryTreeNode(4, Some(node5), Some(node6))
    val node3 = ScalaBinaryTreeNode(3, Some(node2), Some(node4))

    println(node3.minHeight)
    println(node3.maxHeight)
    println(node3.isBalanced)
  }

  def test4(): Unit = {
    val node1 = ScalaBinaryTreeNode(1, None, None)
    val node2 = ScalaBinaryTreeNode(2, None, None)
    val node3 = ScalaBinaryTreeNode(3, Some(node1), Some(node2))
    val node4 = ScalaBinaryTreeNode(4, None, Some(node3))

    val node7 = ScalaBinaryTreeNode(7, None, None)
    val node8 = ScalaBinaryTreeNode(8, None, None)
    val node6 = ScalaBinaryTreeNode(6, Some(node7), Some(node8))

    val node5 = ScalaBinaryTreeNode(5, Some(node4), Some(node6))

    println(node5.minHeight)
    println(node5.maxHeight)
    println(node5.isBalanced)
  }
}