package com.aokolnychyi.ds.trie

object ScalaPrefixTreeExamples extends App {
  var prefixTree = PrefixTree()
  prefixTree += "ananas"
  prefixTree += "banana"
  prefixTree += "bench"
  prefixTree += "ben"
  println(prefixTree)
  println(prefixTree.contains("ben"))
  println(prefixTree.contains("benc"))
  println(prefixTree.contains("bencha"))
  println(prefixTree.contains("banana"))
  println(prefixTree.contains("da"))
  println(prefixTree.contains("Ana"))
  println(prefixTree.rootNode)
  println(prefixTree.rootNode.getClass)
}
