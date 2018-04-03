package com.aokolnychyi.ds.list.mutable

class List[T] {

  private var headNode: Node = _
  private var lastNode: Node = _
  private var length = 0

  def +=:(element: T): Unit = {
    val oldHeadNode = headNode
    val newNode = Node(element, oldHeadNode)
    headNode = newNode
    if (oldHeadNode == null) {
      lastNode = newNode
    }
    length += 1
  }

  def +=(element: T): Unit = {
    val oldLastNode = lastNode
    val newNode = new Node(element, null)
    lastNode = newNode
    if (oldLastNode == null) {
      headNode = newNode
    } else {
      oldLastNode.next = newNode
    }
    length += 1
  }

  def head: T = {
    if (headNode == null) throw new NoSuchElementException("head on an empty list")
    headNode.element
  }

  def last: T = {
    if (lastNode == null) throw new NoSuchElementException("last on an empty list")
    lastNode.element
  }

  def size: Int = length

  def removeDuplicates(): Unit = {
    var currentNode = headNode

    while (currentNode != null) {
      var runnerNode = currentNode
      var nextRunnerNode = runnerNode.next
      while (nextRunnerNode != null) {
        if (nextRunnerNode.element == currentNode.element) {
          runnerNode.next = nextRunnerNode.next
        }
        if (lastNode eq nextRunnerNode) {
          lastNode = runnerNode
        }
        runnerNode = nextRunnerNode
        nextRunnerNode = runnerNode.next
      }
      currentNode = currentNode.next
    }

  }

  def partition(pivot: T)(implicit imp: T => Ordered[T]): Unit = {
    if (size <= 1) return
    var partitionHeadNode = headNode
    var partitionLastNode = headNode

    var currentNode = headNode.next
    while (currentNode != null) {
      val nextNode = currentNode.next
      if (currentNode.element < pivot) {
        currentNode.next = partitionHeadNode
        partitionHeadNode = currentNode
      } else {
        partitionLastNode.next = currentNode
        partitionLastNode = currentNode
      }
      currentNode = nextNode
    }

    headNode = partitionHeadNode
    lastNode = partitionLastNode
    lastNode.next = null
  }

  override def toString: String = {
    var currentNode = headNode
    val stringBuilder = new StringBuilder()
    while (currentNode != null) {
      stringBuilder.append(currentNode.element)
      currentNode = currentNode.next
    }
    stringBuilder.toString
  }

  private class Node(var element: T, var next: Node)

  private object Node {
    def apply(element: T, next: Node): Node =
      new Node(element, next)
  }

}
