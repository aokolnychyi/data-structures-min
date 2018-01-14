package com.aokolnychyi.ds.trie;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CaseInsensitiveTrieExamples {

  public static void printSentence(String string, Set<String> dictionary) {
    // build a trie of words, O(dictionarySize * dictionaryWordLength) time
    final Trie trie = new CaseInsensitiveTrie();
    dictionary.forEach(trie::addWord);

    // init an array with split indices, all elements are initially equal to -1
    // O(wordLength) time
    final int stringLength = string.length();
    final int[] splitIndexes = new int[stringLength + 1];
    Arrays.fill(splitIndexes, -1);

    // compute the split indices using the trie
    // a value >= 0 at a specific index indicates that we have a start of a word ther
    // for instance, "ilikeicecream" will have the following array of split indices
    // [-1, 0, -1, -1, -1, 1, 5, -1, 5, -1, -1, -1, -1, 8]
    // 0 indicates that we have a word that started at position 0 and ended one position before (0)
    // 1 indicates that we have a word that started at position 1 and ended one position before (4)
    // O(wordLength^2) time
    final TrieNode rootNode = trie.getRootNode();
    for (int startIndex = 0; startIndex < stringLength; startIndex++) {
      if (startIndex == 0 || splitIndexes[startIndex] >= 0) {
        computeSplitIndices(string, startIndex, splitIndexes, rootNode);
      }
    }

    // O(wordLength) time
    if (splitIndexes[stringLength] != -1) {
      System.out.println("It is possible to break the string!");
      StringBuilder dividedSentence = new StringBuilder(string);
      for (int index = stringLength; index > 0; index--) {
        final int splitIndex = splitIndexes[index];
        if (splitIndex != -1 && splitIndex != 0 && dividedSentence.charAt(splitIndex) != ' ') {
          dividedSentence.insert(splitIndex, ' ');
        }
      }
      System.out.println(dividedSentence);
    } else {
      System.out.println("It is impossible to break the string!");
    }

  }

  private static void computeSplitIndices(
      String string,
      int startIndex,
      int startWordIndexes[],
      TrieNode rootNode) {

    TrieNode trieNode = rootNode;
    int currentIndex = startIndex;
    while (currentIndex < string.length()) {
      final char currentCharacter = string.charAt(currentIndex);
      if (trieNode.hasChild(currentCharacter)) {
        trieNode = trieNode.getChild(currentCharacter);
        if (trieNode.isWord()) {
          // pay attention here that you set start index, not current one.
          startWordIndexes[currentIndex + 1] = startIndex;
        }
        currentIndex++;
      } else {
        break;
      }
    }
  }

  public static void main(String[] args) {
    final Trie trie = new CaseInsensitiveTrie();
    trie.addWord("Afterwards");
    trie.addWord("Liverpool");
    trie.addWord("Afterparty");
    trie.addWord("Live");
    trie.addWord("Most");
    trie.addWord("Lile");

    System.out.println("Contains");
    System.out.println(trie.contains("Lile"));
    System.out.println(trie.contains("lile"));
    System.out.println(trie.contains("Lily"));
    System.out.println(trie.contains("Lil"));

    System.out.println("Words with prefix Li");
    System.out.println(trie.getWordsWithPrefix("Li"));

    System.out.println("Words with prefix After");
    System.out.println(trie.getWordsWithPrefix("After"));

    System.out.println("==================");

    final Set<String> dictionary = new HashSet<>();
    dictionary.add("mobile");
    dictionary.add("samsung");
    dictionary.add("sam");
    dictionary.add("sung");
    dictionary.add("man");
    dictionary.add("mango");
    dictionary.add("icecream");
    dictionary.add("and");
    dictionary.add("go");
    dictionary.add("i");
    dictionary.add("like");
    dictionary.add("ice");
    dictionary.add("cream");

    printSentence("ilikeicecream", dictionary);
    printSentence("ilikelikeimangoiii", dictionary);
    printSentence("samsungandmango", dictionary);
    printSentence("samsungandmangok", dictionary);

  }
}
