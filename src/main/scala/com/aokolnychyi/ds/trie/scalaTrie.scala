package com.aokolnychyi.ds.trie

trait PrefixTree {
  val char: Char
  val isLastCharInWord: Boolean
  val children: Map[Char, PrefixTree]
  def withNewChildren(children: Map[Char, PrefixTree]): PrefixTree
  def contains(word: String): Boolean = contains(word, 0)
  def +(word: String): PrefixTree = {
    require(word.nonEmpty, "Cannot add an empty string")
    addWord(word, 0)
  }

  private[trie] def buildTree(word: String, index: Int): PrefixTree = {
    val currentChar = word(index)
    if (index == word.length - 1) {
      Node(currentChar, Map.empty, isLastCharInWord = true)
    } else {
      val child = buildTree(word, index + 1)
      Node(currentChar, Map(child.char -> child), isLastCharInWord = false)
    }
  }

  private[trie] def addWord(word: String, index: Int): PrefixTree = {
    val currentChar = word(index)
    if (!children.contains(currentChar)) {
      val newChild = buildTree(word, index)
      withNewChildren(children + (newChild.char -> newChild))
    } else if (index == word.length - 1) {
      // adding a word with the existing path
      val updatedChild = children(currentChar).asInstanceOf[Node].copy(isLastCharInWord = true)
      withNewChildren(children + (updatedChild.char -> updatedChild))
    } else {
      val updatedChild = children(currentChar).addWord(word, index + 1)
      withNewChildren(children + (updatedChild.char -> updatedChild))
    }
  }

  private[trie] def contains(word: String, index: Int): Boolean = {
    val lastCharIndex = word.length - 1
    if (index < lastCharIndex) {
      children.get(word(index)).exists(_.contains(word, index + 1))
    } else if (index == lastCharIndex) {
     children.get(word(index)).exists(_.isLastCharInWord)
    } else {
      throw new RuntimeException("Unexpected situation")
    }
  }
}

object PrefixTree {
  def apply(): PrefixTree = Empty
}

case object Empty extends PrefixTree {
  override lazy val char = throw new NoSuchElementException()
  override lazy val isLastCharInWord = throw new NoSuchElementException()
  override lazy val children: Map[Char, PrefixTree] = Map.empty
  override def withNewChildren(children: Map[Char, PrefixTree]): PrefixTree = this
  override def contains(word: String): Boolean = false
  override def +(word: String): PrefixTree = {
    val child = buildTree(word, 0)
    Root(Map(child.char -> child))
  }
}

case class Root(children: Map[Char, PrefixTree]) extends PrefixTree {
  override lazy val char = throw new NoSuchElementException()
  override lazy val isLastCharInWord = throw new NoSuchElementException()
  override def withNewChildren(children: Map[Char, PrefixTree]): PrefixTree = copy(children)
}

case class Node(
    char: Char,
    children: Map[Char, PrefixTree],
    isLastCharInWord: Boolean) extends PrefixTree {

  override def withNewChildren(children: Map[Char, PrefixTree]): PrefixTree =
    copy(children = children)
}

object Test extends App {
  var tree = PrefixTree()
  tree += "ananas"
  tree += "banana"
  tree += "bench"
  tree += "ben"
  println(tree)
  println(tree.contains("ben"))
}
