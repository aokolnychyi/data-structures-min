package com.aokolnychyi.ds.trie;

import java.util.List;

interface TrieNode {

  boolean isWord();

  boolean hasChild(char character);

  TrieNode getChild(char character);

  void addWord(String word);

  List<String> getWords();

}
