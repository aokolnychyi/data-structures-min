# Basic Data Structures in Java and Scala

This repo contains sample implementations of basic data structures together with some
algorithms around them. In addition, there are notes that I use to refresh my knowledge from time to
time. Most of the notes are borrowed from the Internet or books and included here with references.
The purpose of this repo is to have a single entry point to quickly go through all major points in
data structures.

## Lists

This chapter covers linked lists in Java and immutable functional lists in Scala.

### Linked Lists in Java

#### Notes

- A linked list arranges objects in a linear manner. Unlike an array, however, in which the
linear order is determined by the array indices, the order in a linked list is determined by a
pointer in each object (source: "Introduction to Algorithms" by Thomas H. Cormen).
- Can be singly linked (only next pointer) or doubly linked list (with previous and next pointers).
- Might be circular (the last element points to the first).
- Might contain references to the head and tail.
- O(n) time to find an element.
- O(n) time to delete an object by key (since you might have to iterate through the whole list). But
 the actual deletion is O(1) since you only modify pointers.
- There are two main implementations of the ``List`` interface in Java (apart from
``CopyOnWriteArrayList``): ``LinkedList`` and ``ArrayList``.
- Notes from a very good [comparison](https://stackoverflow.com/a/322742/4108401) of ``LinkedList``
and ``ArrayList``:

LinkedList<E> allows for constant-time insertions or removals using iterators, but only sequential
access of elements. In other words, you can walk the list forwards or backwards, but finding a
position in the list takes time proportional to the size of the list.

ArrayList<E>, on the other hand, allows fast random read access, so you can grab any element in
constant time. But adding or removing from anywhere but the end requires shifting all the latter
elements over, either to make an opening or fill the gap. Also, if you add more elements than the
capacity of the underlying array, a new array is allocated, and the old array is copied to the new
one, so adding to an ArrayList is O(n) in the worst case but constant on average (amortized time).

So depending on the operations you intend to do, you should choose the implementations accordingly.
Iterating over either kind of List is practically equally cheap. (Iterating over an ArrayList is
technically faster, but unless you're doing something really performance-sensitive, you shouldn't
worry about this -- they're both constants.)

The main benefits of using a LinkedList arise when you re-use existing iterators to insert and
remove elements. These operations can then be done in O(1) by changing the list locally only.
In an array list, the remainder of the array needs to be moved (i.e. copied).
On the other side, seeking in a LinkedList means following the links in O(n), whereas in an
ArrayList the desired position can be computed mathematically and accessed in O(1).

Also, if you have large lists, keep in mind that memory usage is also different. Each element of a
LinkedList has more overhead since pointers to the next and previous elements are also stored.
ArrayLists don't have this overhead. However, ArrayLists take up as much memory as is allocated for
the capacity, regardless of whether elements have actually been added.

#### Implementation

This repo contains a sample implementation of singly linked lists in the 
com.aokolnychyi.ds.list.SinglyLinkedList class. The following algorithms/features are covered:

- Addition to the front (``SinglyLinkedList#addFirst``)
- Addition to the end (``SinglyLinkedList#addLast``)
- Remove duplicates using the runner technique (``SinglyLinkedList#removeDuplicates``)
- Swap elements pairwise (``SinglyLinkedList#swapPairwiseIteratively``)
- Swap elements pairwise using recursion (``SinglyLinkedList#swapPairwiseRecursively``)
- Reverse a list (``SinglyLinkedList#reverse``)
- Produce a reversed copy of a list (``SinglyLinkedList#makeReversedCopy``)
- Reverse in groups (``SinglyLinkedList#reverseInGroupsOf``)
- Return kth last node (``SinglyLinkedList#kthToLastIteratively``)
- Return kth last node using recursion (``SinglyLinkedList#kthToLastRecursively``)

## Other important notes

- node vs wrapper on top
- tail recursion

Hash Maps

https://alvinalexander.com/scala/how-to-choose-map-implementation-class-sorted-scala-cookbook
http://docs.scala-lang.org/overviews/collections/concrete-immutable-collection-classes.html#hash_tries
https://stackoverflow.com/questions/31685236/scala-map-vs-hashmap
https://alvinalexander.com/scala/how-to-choose-map-implementation-class-sorted-scala-cookbook
http://javarevisited.blogspot.de/2016/01/how-does-java-hashmap-or-linkedhahsmap-handles.html
https://stackoverflow.com/questions/4980757/how-do-hashtables-deal-with-collisions

Trie

https://www.google.de/search?q=radix+tree+vs+trie&oq=radix+tree&aqs=chrome.1.69i57j0j69i60l3j0.6646j0j7&sourceid=chrome&ie=UTF-8
https://en.wikipedia.org/wiki/Hash_array_mapped_trie
https://blog.acolyer.org/2015/11/27/hamt/
https://stackoverflow.com/questions/14708134/what-is-the-difference-between-trie-and-radix-trie-data-structures
