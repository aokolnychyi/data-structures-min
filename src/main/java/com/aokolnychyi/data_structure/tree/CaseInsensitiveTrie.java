package com.aokolnychyi.data_structure.tree;

import java.util.ArrayList;
import java.util.List;

// Also known as a Prefix Tree
// The complexity of creating a trie is O(W*L),
// where W is the number of words, and L is an average length of the word
public class CaseInsensitiveTrie {

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
    final String lowerCaseWord = word.toLowerCase();
    rootNode.addWord(lowerCaseWord);
  }

  /**
   * Get the words in the CaseInsensitiveTrie with the given prefix. O(prefixLength +
   * 26^lengthOfLongestWordWithThisPrefix)
   */
  public List<String> getWordsWithPrefix(final String prefix) {
    // Find the node which represents the last letter of the prefix
    TrieNode currentLastNode = rootNode;
    for (int i = 0; i < prefix.length(); i++) {
      final char currentPrefixChar = prefix.charAt(i);
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
    for (int i = 0; i < word.length(); i++) {
      final char currentWordChar = word.charAt(i);
      currentLastNode = currentLastNode.getChild(currentWordChar);

      // If no node matches, then no words exist, return empty list
      if (currentLastNode == null) return false;
    }
    return currentLastNode.isWord();
  }

  public class TrieNode {
    private TrieNode parent;
    private TrieNode[] children;
    private boolean isLeaf; // Quick way to check if any children exist
    private boolean isLastCharacterInWord; // Does this node represent the last character of a word
    private char character; // The character this node represents

    /**
     * Constructor for the top level root node.
     */
    public TrieNode() {
      children = new TrieNode[26];
      isLeaf = true;
      isLastCharacterInWord = false;
    }

    /**
     * Constructor for a child node.
     */
    public TrieNode(char character) {
      this();
      this.character = character;
    }

    public boolean isWord() {
      return isLastCharacterInWord;
    }

    public boolean hasChild(char character) {
      final int charIndex = getCharIndex(character);
      return children[charIndex] != null;
    }

    /**
     * Returns the child TrieNode representing the given char,
     * or null if no node exists.
     */
    public TrieNode getChild(char character) {
      final char lowerCaseChar = Character.toLowerCase(character);
      final int charIndex = getCharIndex(lowerCaseChar);
      return children[charIndex];
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
      final int charIndex = getCharIndex(currentChar);

      // Important check that is done to avoid creation
      // of new nodes if they are already there
      if (children[charIndex] == null) {
        children[charIndex] = new TrieNode(currentChar);
        children[charIndex].parent = this;
      }

      if (word.length() > 1) {
        children[charIndex].addWord(word.substring(1));
      } else {
        // Do not forget to mark the end!
        children[charIndex].isLastCharacterInWord = true;
      }
    }

    /**
     * Returns a List of String objects which are lower in the hierarchy that this node.
     * <p>
     * Takes O(prefixLength + 26^lengthOfLongestWordWithThisPrefix.
     * This time complexity represents the worst case. Usually, it will be much master since you
     * won't call the function recursively 26 times at each iteration.
     * In addition, lengthOfLongestWordWithThisPrefix should not be big.
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

      // O(26^lengthOfLongestWordWithThisPrefix)
      // If any children
      if (!isLeaf) {
        // Add any words belonging to any children
        for (TrieNode child : children) {
          if (child != null) {
            final List<String> childWords = child.getWords();
            list.addAll(childWords);
          }
        }
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
     * represents the charater a, whose parent represents the character
     * c, then the String would be "cat".
     */
    private String getCurrentWord() {
      return parent == null ? "" : parent.toString() + character;
    }

    private int getCharIndex(final char character) {
      return Character.toLowerCase(character) - 'a';
    }

  }
}
