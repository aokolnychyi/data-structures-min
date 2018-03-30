package com.aokolnychyi.ds.trie.mutable

import scala.annotation.tailrec
import scala.collection.mutable

class ScalaTrie {

  private val rootNode = new Node()

  def +=(word: String): Unit = {
    require(word.length != 0, "Tries do not support empty strings")
    rootNode.add(word)
  }

  def contains(word: String): Boolean = {
    require(word.length != 0, "Tries do not support empty strings")
    rootNode.contains(word, 0)
  }

  def getWordsWithPrefix(prefix: String): Seq[String] = {
    var node = rootNode
    for (char <- prefix) {
      if (!node.children.contains(char)) return Seq.empty
      node = node.children(char)
    }
    getWords(node, prefix)
  }

  private def getWords(node: Node, prefix: String): Seq[String] = {
    val words = mutable.ArrayBuffer.empty[String]
    node.children.foreach { case (childChar, childNode) =>
      words ++= getWords(childNode, prefix + childChar)
    }
    if (node.isLastWordChar) words += prefix
    words
  }

  private class Node(val character: Option[Char] = None, var isLastWordChar: Boolean = false) {

    private[mutable] val children = mutable.Map.empty[Char, Node]

    def add(word: String, index: Int = 0): Unit = {
      val newChar = word(index)
      children.get(newChar) match {
        case None =>
          val newNode = new Node(character = Some(newChar))
          children += newChar -> newNode
        case _ =>
      }
      val childNode = children(newChar)
      if (index == word.length - 1) {
        childNode.isLastWordChar = true
      } else {
        childNode.add(word, index + 1)
      }
    }

    @tailrec
    final def contains(word: String, index: Int): Boolean = {
      val currentChar = word(index)
      children.get(currentChar) match {
        case None => false
        case Some(node) =>
          if (index == word.length - 1) node.isLastWordChar else node.contains(word, index + 1)
      }
    }

  }

}
