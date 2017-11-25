package com.aokolnychyi.ds.tree

sealed trait Tree[+A] {
  def size(): Int
  def depth(): Int
  def map[B](f: A => B): Tree[B]
  def foldPreOrder[B](z: B)(op: (B, A) => B): B
  def foldPostOrder[B](z: B)(op: (B, A) => B): B
  def sizeViaFold(): Int = foldPreOrder(0)((currentSize, _) => currentSize + 1)
}

case class Leaf[A](value: A) extends Tree[A] {
  override def size(): Int = 1
  override def depth(): Int = 1
  override def map[B](f: A => B): Tree[B] = Leaf(f(value))
  override def foldPreOrder[B](z: B)(op: (B, A) => B): B = op(z, value)
  override def foldPostOrder[B](z: B)(op: (B, A) => B): B = op(z, value)
}

case object Empty extends Tree[Nothing] {
  override def size() = 0
  override def depth() = 0
  override def map[B](f: Nothing => B): Tree[B] = Empty
  override def foldPreOrder[B](z: B)(op: (B, Nothing) => B): B = z
  override def foldPostOrder[B](z: B)(op: (B, Nothing) => B): B = z
}

case class Node[A](value: A, leftSubTree: Tree[A], rightSubTree: Tree[A]) extends Tree[A] {
  override def size(): Int = leftSubTree.size() + rightSubTree.size() + 1
  override def depth(): Int = Math.max(leftSubTree.depth(), rightSubTree.depth()) + 1
  override def map[B](f: (A) => B) = Node(f(value), leftSubTree.map(f), rightSubTree.map(f))
  override def foldPreOrder[B](z: B)(op: (B, A) => B): B = {
    val foldValue = op(z, value)
    val leftSubTreeFoldValue = leftSubTree.foldPreOrder(foldValue)(op)
    rightSubTree.foldPreOrder(leftSubTreeFoldValue)(op)
  }
  override def foldPostOrder[B](z: B)(op: (B, A) => B): B = {
    val leftSubTreeFoldValue = leftSubTree.foldPostOrder(z)(op)
    val rightSubTreeFoldValue = rightSubTree.foldPostOrder(leftSubTreeFoldValue)(op)
    op(rightSubTreeFoldValue, value)
  }
}

case object Implicits {
  implicit class ComparableTree[A](tree: Tree[A])(implicit i: A => Ordered[A]) {
    def max(): Option[A] = tree match {
      case Empty => None
      case Leaf(value) => Some(value)
      case node @ Node(value, _, _) =>
        val maxChildValue = getMaxChildValue(node)
        if (maxChildValue > value) Some(maxChildValue) else Some(value)
    }

    private def getMaxChildValue(node: Node[A]): A = {
      val leftMaxValueOp = node.leftSubTree.max()
      val rightMaxValueOp = node.rightSubTree.max()

      (leftMaxValueOp, rightMaxValueOp) match {
        case (Some(leftMax), Some(rightMax)) => if (leftMax > rightMax) leftMax else rightMax
        case (Some(leftMax), None) => leftMax
        case (None, Some(rightMax)) => rightMax
        case (None, None) => throw new RuntimeException("At least one child value must be present")
      }
    }
  }
}
