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
pointer in each object ("Introduction to Algorithms" by Thomas H. Cormen).
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
``com.aokolnychyi.ds.list.SinglyLinkedList`` class. The following algorithms/features are covered:

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

See examples in ``com.aokolnychyi.ds.list.SinglyLinkedListExamples``.

### Functional Lists in Scala

#### Notes

- Functional programming relies on immutability & data sharing. So, Scala Lists are immutable.
There is a special way to represent immutable lists that allows to prepend in O(1) time.
If you implement immutable lists using immutable arrays, the runtime will be quadratic because each
cons operation will need to copy the whole array, leading to a quadratic running time
([source](https://softwareengineering.stackexchange.com/questions/132309/why-are-cons-lists-associated-with-functional-programming)).
- Scala has an interface called List, which is implemented by a case object called Nil
(represents an empty List) and a case class "::".
- The ``List`` trait has to be covariant to be able to pass ``List[Nothing]`` as a tail to
``List[String]``.
- In Scala, ``Nothing`` is a type (final class). It is defined at the bottom of the Scala type System,
which means it is a subtype of anything in Scala. There are no instances of ``Nothing``.
``Nothing`` is used in the definitions of ``None``, ``Nil``, etc. In addition, you can use
``Nothing`` as a return type for methods that never return or return abnormally
(e.g., throw an exception).
- Scala ``List`` is a monad.

### Implementation

A simplified implementation of functional lists is available in the
``com.aokolnychyi.ds.list.scalaList`` file. Check out examples in
``com.aokolnychyi.ds.list.ScalaListExamples``.


## Other important notes

- node vs wrapper on top
- A recursive algorithm will add overhead since you store recursive calls in the execution stack.
The space complexity of a recursive algorithm is proportional to the max length of the stack that it
requires (if no additional space is used). However, tail recursion allows recursive algorithms to
avoid this space overhead. In general, each function call may allocate a separate stack frame.
However, there are recursive problems that do not need this overhead and they can reuse the same
stack frame. Such problems are called tail-recursive problems. They call another function as the
last step of their computations. Scala does tail recursion optimization at compile-time. A tail 
recursive function is transformed into a loop by the compiler
([source](https://stackoverflow.com/questions/1677419/does-scala-support-tail-recursion-optimization)). 
There is no tail recursion optimization in Java (at least the Java specification does not require this).
Originally, the permission model in the JVM was stack-sensitive and thus tail-calls had to handle
the security aspects. I believe it is not a problem any more. There are some articles saying that
the transformation from tail-recursive function to simple loop must be done dynamically by a
JIT compiler. Therefore, it is up to JIT compilers to do this optimization
(see [here](https://stackoverflow.com/questions/3616483/why-does-the-jvm-still-not-support-tail-call-optimization)).

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
