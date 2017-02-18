package com.aokolnychyi.data_structure.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaseSensitiveTrie {

  private TrieNode rootNode = new TrieNode();

  public TrieNode getRootNode() {
    return rootNode;
  }

  /**
   * Adds a word to the CaseInsensitiveTrie.
   * <p>
   * It takes O(n), where n is the length of the word.
   */
  public void addWord(String word) {
    rootNode.addWord(word);
  }

  /**
   * Get the words in the CaseInsensitiveTrie with the given prefix. O(prefixLength +
   * 26^lengthOfLongestWordWithThisPrefix)
   */
  public List<String> getWordsWithPrefix(final String prefix) {
    // Find the node which represents the last letter of the prefix
    TrieNode currentLastNode = rootNode;
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
  public boolean contains(String word) {
    // Find the node which represents the last letter of the word
    TrieNode currentLastNode = rootNode;
    for (int index = 0; index < word.length(); index++) {
      final char currentWordChar = word.charAt(index);
      currentLastNode = currentLastNode.getChild(currentWordChar);

      // If no node matches, then no words exist, return empty list
      if (currentLastNode == null) return false;
    }
    return currentLastNode.isWord();
  }

  public class TrieNode {
    private TrieNode parent;
    private Map<Character, TrieNode> children;
    private boolean isLeaf; // Quick way to check if any children exist
    private boolean isLastCharacterInWord; // Does this node represent the last character of a word
    private char character; // The character this node represents

    /**
     * Constructor for top level root node.
     */
    public TrieNode() {
      children = new HashMap<>();
      isLeaf = true;
      isLastCharacterInWord = false;
    }

    /**
     * Constructor for child node.
     */
    public TrieNode(char character) {
      this();
      this.character = character;
    }

    public boolean isWord() {
      return isLastCharacterInWord;
    }

    public boolean hasChild(char character) {
      return children.containsKey(character);
    }

    /**
     * Returns the child TrieNode representing the given char,
     * or null if no node exists.
     */
    public TrieNode getChild(char character) {
      return children.get(character);
    }

    /**
     * Adds a word to this node. This method is called recursively and
     * adds child nodes for each successive letter in the word, therefore
     * recursive calls will be made with partial words.
     * <p>
     * It takes linear time to insert a word.
     * Time complexity is O(lengthOfTheWord).
     *
     * @param word the word to add
     */
    public void addWord(String word) {
      isLeaf = false;
      final char currentChar = word.charAt(0);

      if (!children.containsKey(currentChar)) {
        final TrieNode newTrieNode = new TrieNode(currentChar);
        newTrieNode.parent = this;
        children.put(currentChar, newTrieNode);
      }

      final TrieNode currentTrieNode = children.get(currentChar);

      if (word.length() > 1) {
        final String remainingWordPart = word.substring(1);
        currentTrieNode.addWord(remainingWordPart);
      } else {
        currentTrieNode.isLastCharacterInWord = true;
      }
    }

    /**
     * Returns a List of String objects which are lower in the
     * hierarchy that this node.
     * <p>
     * Takes O(prefixLength + lengthOfLongestWordWithThisPrefix)
     */
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
        final Collection<TrieNode> childrenNodes = children.values();
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
     * <p>
     * For example, if this node represents the character t, whose parent
     * represents the character a, whose parent represents the character
     * c, then the String would be "cat".
     */
    private String getCurrentWord() {
      return parent == null ? "" : parent.toString() + character;
    }

  }
}
