package com.aokolnychyi.ds.trie;

public class CaseSensitiveTrieExamples {

  public static void main(String[] args) {
    final Trie trie = new CaseSensitiveTrie();
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
    System.out.println(trie.contains("lil"));

    System.out.println("Words with prefix Li");
    System.out.println(trie.getWordsWithPrefix("Li"));
    System.out.println(trie.getWordsWithPrefix("li"));

    System.out.println("Words with prefix After");
    System.out.println(trie.getWordsWithPrefix("After"));
    System.out.println(trie.getWordsWithPrefix("after"));

  }
}
