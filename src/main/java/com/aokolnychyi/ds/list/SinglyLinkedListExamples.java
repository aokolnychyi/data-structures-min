package com.aokolnychyi.ds.list;

public class SinglyLinkedListExamples {

  public static void main(String[] args) {
    final SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<>();
    linkedList.addLast(1);
    linkedList.addFirst(2);
    linkedList.addLast(3);

    System.out.println(linkedList.getFirst());
    System.out.println(linkedList.getLast());

    System.out.println("The first pairwise swap");
    System.out.println(linkedList);
    linkedList.swapPairwiseIteratively();
    System.out.println(linkedList);

    System.out.println("The second pairwise swap");
    System.out.println(linkedList);
    linkedList.swapPairwiseRecursively();
    System.out.println(linkedList);

    System.out.println("Reverse");
    System.out.println(linkedList);
    linkedList.reverse();
    System.out.println(linkedList);

    System.out.println("Reversed Copy");
    System.out.println(linkedList);
    System.out.println(linkedList.makeReversedCopy());


    final SinglyLinkedList<Integer> longLinkedList = new SinglyLinkedList<>();
    longLinkedList.addLast(1);
    longLinkedList.addLast(2);
    longLinkedList.addLast(4);
    longLinkedList.addLast(5);
    longLinkedList.addLast(6);
    longLinkedList.addLast(7);
    longLinkedList.addLast(9);
    longLinkedList.addLast(10);

    System.out.println("Reverse in groups");
    System.out.println(longLinkedList);
    longLinkedList.reverseInGroupsOf(3);
    System.out.println(longLinkedList);

    System.out.println("7th from the end");
    System.out.println(longLinkedList.kthToLastIteratively(7));
    System.out.println(longLinkedList.kthToLastRecursively(7));

    System.out.println("Remove duplicates");
    SinglyLinkedList<Integer> duplicateLinkedList = new SinglyLinkedList<>();
    duplicateLinkedList.addLast(1);
    duplicateLinkedList.addLast(7);
    duplicateLinkedList.addLast(6);
    duplicateLinkedList.addLast(1);
    duplicateLinkedList.addLast(2);
    duplicateLinkedList.addLast(6);
    System.out.println(duplicateLinkedList);
    duplicateLinkedList.removeDuplicates();
    System.out.println(duplicateLinkedList);
    System.out.println("Last: " + duplicateLinkedList.getLast());
  }
}
