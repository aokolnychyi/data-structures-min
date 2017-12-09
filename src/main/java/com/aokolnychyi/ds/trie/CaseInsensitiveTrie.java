package com.aokolnychyi.ds.trie;

import java.util.ArrayList;
import java.util.List;

public class CaseInsensitiveTrie implements Trie {

  private TrieNode rootNode = new CaseInsensitiveTrieNode();

  public TrieNode getRootNode() {
    return rootNode;
  }

  @Override
  public void addWord(String word) {
    final String lowerCaseWord = word.toLowerCase();
    rootNode.addWord(lowerCaseWord);
  }

  public class CaseInsensitiveTrieNode implements TrieNode {
    private CaseInsensitiveTrieNode parent;
    private CaseInsensitiveTrieNode[] children;
    private boolean isLeaf; // Quick way to check if any children exist
    private boolean isLastCharacterInWord; // Does this node represent the last character of a word
    private char character; // The character this node represents

    public CaseInsensitiveTrieNode() {
      children = new CaseInsensitiveTrieNode[26];
      isLeaf = true;
      isLastCharacterInWord = false;
    }

    public CaseInsensitiveTrieNode(char character) {
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

    public CaseInsensitiveTrieNode getChild(char character) {
      final char lowerCaseChar = Character.toLowerCase(character);
      final int charIndex = getCharIndex(lowerCaseChar);
      return children[charIndex];
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
      final int charIndex = getCharIndex(currentChar);

      // Important! avoid creation of new nodes if they are already there
      if (children[charIndex] == null) {
        children[charIndex] = new CaseInsensitiveTrieNode(currentChar);
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
     *
     * Takes O(prefixLength + 26^lengthOfLongestWordWithThisPrefix).
     * This time complexity represents the worst case. Usually, it will be much better since you
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
        for (CaseInsensitiveTrieNode child : children) {
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
