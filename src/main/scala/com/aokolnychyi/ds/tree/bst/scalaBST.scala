package com.aokolnychyi.ds.tree.bst

sealed abstract class ScalaBST[T](implicit imp: T => Ordered[T]) {
  def insert(newValue: T): ScalaBST[T]
  def remove(target: T): ScalaBST[T]
  def contains(value: T): Boolean
  def find(value: T): Option[ScalaBST[T]]
  def min: Option[T]
  def printInOrder(): Unit
  def successor(root: Node[T]): Option[T] = {
    if (!isSubtree(root, this)) throw new IllegalArgumentException("Invalid root!")
    findSuccessor(root)
  }

  private[bst] def findSuccessor(root: Node[T]): Option[T] = None

  private[bst] def isSubtree(root: ScalaBST[T], subTree: ScalaBST[T]): Boolean = root match {
    case Leaf() => root eq subTree
    case Node(_, left, right) =>
      (root eq subTree) || isSubtree(left, subTree) || isSubtree(right, subTree)
  }
}

case class Leaf[T]()(implicit imp: T => Ordered[T]) extends ScalaBST[T] {
  override def insert(newValue: T): ScalaBST[T] = Node(newValue, Leaf[T](), Leaf[T]())
  override def remove(target: T): ScalaBST[T] = this
  override def contains(value: T): Boolean = false
  override def find(value: T): Option[ScalaBST[T]] = None
  override def min: Option[T] = None
  override def printInOrder(): Unit = {}
}

case class Node[T](
    value: T,
    left: ScalaBST[T],
    right: ScalaBST[T])(implicit imp: T => Ordered[T]) extends ScalaBST[T] {

  override def insert(newValue: T): ScalaBST[T] = newValue match {
    case `value` => this
    case _ if newValue > value => Node(value, left, right.insert(newValue))
    case _ => Node(value, left.insert(newValue), right)
  }

  override def remove(target: T): ScalaBST[T] = target match {
    case `value` => this match {
      case Node(_, Leaf(), Leaf()) => Leaf()
      case Node(_, Leaf(), rightChild) => rightChild
      case Node(_, leftChild, Leaf()) => leftChild
      case Node(_, leftChild, rightChild) =>
        // the right subtree cannot be Leaf at this point and it is safe to assume that there
        // will always be a min element in the right subtree
        val rightChildMin = rightChild.min.get
        Node(rightChildMin, leftChild, rightChild.remove(rightChildMin))
    }
    case _ if target > value => right.remove(target)
    case _ => left.remove(target)
  }

  override def contains(valueToFind: T): Boolean = find(valueToFind).isDefined

  override def find(valueToFind: T): Option[ScalaBST[T]] = valueToFind match {
    case `value` => Some(this)
    case _ if valueToFind < value => left.find(valueToFind)
    case _ => right.find(valueToFind)
  }

  override lazy val min: Option[T] = this match {
    case Node(_, Leaf(), _) => Some(value)
    case _ => left.min
  }

  override def printInOrder(): Unit = {
    left.printInOrder()
    print(value + " ")
    right.printInOrder()
  }

  override def findSuccessor(root: Node[T]): Option[T] = this match {
    case Node(_, _, right: Node[T]) => right.min
    case _ => findSuccessor(this, root, None).map(_.value)
  }

  private def findSuccessor(
      nodeToFind: Node[T],
      current: ScalaBST[T],
      successor: Option[Node[T]]): Option[Node[T]] = current match {
    // case `nodeToFind` => successor
    case _ if current eq nodeToFind => successor
    case n @ Node(currentValue, currentLeft, _) if nodeToFind.value < currentValue =>
      findSuccessor(nodeToFind, currentLeft, Some(n))
    case Node(_, _, currentRight) => findSuccessor(nodeToFind, currentRight, successor)
  }

}
