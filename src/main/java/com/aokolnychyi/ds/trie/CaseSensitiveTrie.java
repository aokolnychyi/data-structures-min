package com.aokolnychyi.ds.trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaseSensitiveTrie implements Trie {

  private TrieNode rootNode = new CaseSensitiveTrieNode();

  public TrieNode getRootNode() {
    return rootNode;
  }

  public class CaseSensitiveTrieNode implements TrieNode {
    private CaseSensitiveTrieNode parent;
    private Map<Character, CaseSensitiveTrieNode> children;
    private boolean isLeaf; // Quick way to check if any children exist
    private boolean isLastCharacterInWord; // Does this node represent the last character of a word
    private char character; // The character this node represents

    public CaseSensitiveTrieNode() {
      children = new HashMap<>();
      isLeaf = true;
      isLastCharacterInWord = false;
    }

    public CaseSensitiveTrieNode(char character) {
      this();
      this.character = character;
    }

    public boolean isWord() {
      return isLastCharacterInWord;
    }

    public boolean hasChild(char character) {
      return children.containsKey(character);
    }

    public CaseSensitiveTrieNode getChild(char character) {
      return children.get(character);
    }

    /**
     * Adds a word to this node.
     *
     * This method is called recursively and
     * adds child nodes for each successive letter in the word, therefore
     * recursive calls will be made with partial words.
     *
     * It takes linear time to insert a word.
     *
     * @param word the word to add
     */
    public void addWord(String word) {
      isLeaf = false;
      final char currentChar = word.charAt(0);

      if (!children.containsKey(currentChar)) {
        final CaseSensitiveTrieNode newCaseSensitiveTrieNode = new CaseSensitiveTrieNode(currentChar);
        newCaseSensitiveTrieNode.parent = this;
        children.put(currentChar, newCaseSensitiveTrieNode);
      }

      final CaseSensitiveTrieNode currentCaseSensitiveTrieNode = children.get(currentChar);

      if (word.length() > 1) {
        final String remainingWordPart = word.substring(1);
        currentCaseSensitiveTrieNode.addWord(remainingWordPart);
      } else {
        currentCaseSensitiveTrieNode.isLastCharacterInWord = true;
      }
    }

    public List<String> getWords() {
      // Create a list to return
      final List<String> list = new ArrayList<>();

      // If this node represents a word, add it
      if (isLastCharacterInWord) {
        // takes O(prefixLength)
        final String currentWord = getCurrentWord();
        list.add(currentWord);
      }

      // O(26^lengthOfLongestWordWithThisPrefix) in the worst case
      // If any children
      if (!isLeaf) {
        // Add any words belonging to any children
        final Collection<CaseSensitiveTrieNode> childrenNodes = children.values();
        childrenNodes.forEach(childNode -> {
          final List<String> childWords = childNode.getWords();
          list.addAll(childWords);
        });
      }

      return list;
    }

    @Override
    public String toString() {
      return getCurrentWord();
    }

    /**
     * Gets the String that this node represents.
     *
     * For example, if this node represents the character t, whose parent
     * represents the character a, whose parent represents the character
     * c, then the String would be "cat".
     */
    private String getCurrentWord() {
      return parent == null ? "" : parent.toString() + character;
    }

  }
}
