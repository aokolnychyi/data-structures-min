package com.aokolnychyi.ds.trie;

import java.util.ArrayList;
import java.util.List;

public interface Trie {

  TrieNode getRootNode();

  default void addWord(String word) {
    getRootNode().addWord(word);
  }

  default List<String> getWordsWithPrefix(final String prefix) {
    // Find the node which represents the last letter of the prefix
    TrieNode currentLastNode = getRootNode();
    for (int index = 0; index < prefix.length(); index++) {
      final char currentPrefixChar = prefix.charAt(index);
      currentLastNode = currentLastNode.getChild(currentPrefixChar);

      // If no node matches, then no words exist, return empty list
      if (currentLastNode == null) return new ArrayList<>();
    }

    // Return the words which go from the last node
    return currentLastNode.getWords();
  }

  // O(lengthOfWord)
  default boolean contains(String word) {
    // Find the node which represents the last letter of the word
    TrieNode currentLastNode = getRootNode();
    for (int index = 0; index < word.length(); index++) {
      final char currentWordChar = word.charAt(index);
      currentLastNode = currentLastNode.getChild(currentWordChar);

      // If no node matches, then no words exist, return empty list
      if (currentLastNode == null) return false;
    }
    return currentLastNode.isWord();
  }

}
