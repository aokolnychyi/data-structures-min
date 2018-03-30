package com.aokolnychyi.ds.trie.mutable

object ScalaTrieExamples {

  def main(args: Array[String]): Unit = {
    val trie = new ScalaTrie()
    trie += "banana"
    trie += "ban"
    trie += "cat"
    trie += "banner"
    println("--- contains ---")
    println(trie.contains("ba"))
    println(trie.contains("ban"))
    println(trie.contains("banana"))
    println(trie.contains("bananan"))
    println(trie.contains("catt"))
    println(trie.contains("cat"))
    println("--- words with prefix ---")
    println(trie.getWordsWithPrefix("ban"))
  }

}
