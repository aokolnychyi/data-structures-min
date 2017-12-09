package com.aokolnychyi.ds.trie

case class PrefixTree(rootNode: PrefixTreeNode = Empty) {
  def contains(word: String): Boolean = rootNode.contains(word)
  def +(word: String): PrefixTree = PrefixTree(rootNode + word)
}

trait PrefixTreeNode {
  val char: Char
  val isLastCharInWord: Boolean
  val children: Map[Char, PrefixTreeNode]

  def contains(word: String): Boolean = contains(word, 0)

  def +(word: String): PrefixTreeNode = {
    require(word.nonEmpty, "Cannot add an empty string")
    addWord(word, 0)
  }

  private[trie] def withNewChildren(children: Map[Char, PrefixTreeNode]): PrefixTreeNode

  // O(L) time and O(L) space, where L is the length of a word
  private[trie] def contains(word: String, index: Int): Boolean = {
    val lastCharIndex = word.length - 1
    val currentChar = word(index)
    if (index < lastCharIndex) {
      children.get(currentChar).exists(_.contains(word, index + 1))
    } else if (index == lastCharIndex) {
      children.get(currentChar).exists(_.isLastCharInWord)
    } else {
      throw new RuntimeException("Unexpected situation")
    }
  }

  // this method relies on structural sharing in immutable hash maps
  // suppose you have a trie like this
  //
  //        root
  //       /    \
  //      a      b
  //     /        \
  //    n          a
  //                \
  //                 n
  //
  // if you add "banana" to the trie above, the "a-n" branch will be reused due to
  // the implementation of withNewChildren in Root
  // note that "b-a-n" will not be reused
  // O(n) time
  private[trie] def addWord(word: String, index: Int): PrefixTreeNode = {
    val currentChar = word(index)
    children.get(currentChar) match {
      case None =>
        val newChild = buildTree(word, index)
        withNewChildren(children + (newChild.char -> newChild))
      case Some(child) if index == word.length - 1 =>
        // there is already a path, we just need to mark that it forms a word
        val updatedChild = child.asInstanceOf[CharNode].copy(isLastCharInWord = true)
        withNewChildren(children + (updatedChild.char -> updatedChild))
      case Some(child) =>
        val updatedChild = child.addWord(word, index + 1)
        withNewChildren(children + (updatedChild.char -> updatedChild))
    }
  }

  // O(n) time and space
  private[trie] def buildTree(word: String, index: Int): PrefixTreeNode = {
    val currentChar = word(index)
    if (index == word.length - 1) {
      CharNode(currentChar, Map.empty, isLastCharInWord = true)
    } else {
      val child = buildTree(word, index + 1)
      CharNode(currentChar, Map(child.char -> child), isLastCharInWord = false)
    }
  }

}

private[trie] case object Empty extends PrefixTreeNode {
  override lazy val char = throw new NoSuchElementException()
  override lazy val isLastCharInWord = throw new NoSuchElementException()
  override lazy val children: Map[Char, PrefixTreeNode] = Map.empty

  override def withNewChildren(children: Map[Char, PrefixTreeNode]): PrefixTreeNode = this

  override def contains(word: String): Boolean = false

  override def +(word: String): PrefixTreeNode = {
    val child = buildTree(word, 0)
    Root(Map(child.char -> child))
  }
}

private[trie] case class Root(children: Map[Char, PrefixTreeNode]) extends PrefixTreeNode {
  override lazy val char = throw new NoSuchElementException()
  override lazy val isLastCharInWord = throw new NoSuchElementException()

  override def withNewChildren(children: Map[Char, PrefixTreeNode]): PrefixTreeNode = copy(children)
}

private[trie] case class CharNode(
    char: Char,
    children: Map[Char, PrefixTreeNode],
    isLastCharInWord: Boolean) extends PrefixTreeNode {

  override def withNewChildren(children: Map[Char, PrefixTreeNode]): PrefixTreeNode =
    copy(children = children)
}
