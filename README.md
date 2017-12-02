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

- Functional programming relies on immutability and data sharing. So, Scala Lists are immutable.
There is a special way to represent immutable lists that allows to prepend in O(1) time.
If you implement immutable lists using immutable arrays, the runtime will be quadratic because each
cons operation will need to copy the whole array, leading to a quadratic running time
([source](https://softwareengineering.stackexchange.com/questions/132309/why-are-cons-lists-associated-with-functional-programming)).
- Scala has an interface called ``List``, which is implemented by a case object called ``Nil``
(represents an empty list) and a case class ``::``.
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
``com.aokolnychyi.ds.list.scalaList`` file. The following features are supported:

- Check if empty (``List#isEmpty``)
- Get the first element (``List#head``)
- Get the tail (``List#tail``)
- Apply a function to all elements inside and return a new list (``List#map``)

Check out examples in ``com.aokolnychyi.ds.list.ScalaListExamples``.

## Stack

This chapters covers the Stack data structure that has the following general properties:

- Implements the LIFO (last in, first out) principle.
- Addition and removal in O(1) time.
- Frequently used to convert recursive algorithms into non-recursive.
- Used to reverse sequences.

### Stack in Java

#### Notes

- In Java, there is a class called ``Stack``, which extends ``Vector``. However, a more complete
and consistent set of LIFO stack operations is provided by the ``Deque`` interface and its
implementations, which should be used in preference to this class. Internally, the built-in
``Stack`` uses ``protected Object[] elementData`` from the ``Vector`` class and calls 
``Vector``’s methods. Most of the methods in ``Vector`` are synchronized.
- ``ArrayDeque`` is a resizable-array implementation of the ``Deque`` interface. Under the hood,
it has a circular buffer with head/tail pointers. Unlike ``LinkedList``, this class does
not implement the ``List`` interface, which means that you can not access anything except the
first and the last elements. This class is generally preferable to ``LinkedList`` for queues/stacks
due to a limited amount of garbage it generates (the old array will be discarded on the extensions).
Overall, ``ArrayDeque`` is the best queue and stack implementation. ``ArrayDeque`` doesn't have
the overhead of node allocations that ``LinkedList`` has nor the overhead of shifting the array
contents left on remove that ``ArrayList`` has ([source](http://java-performance.info/java-collections-overview/)).

#### Implementation

A sample stack implementation is available in ``com.aokolnychyi.ds.stack.Stack``. Some examples can
be found in ``com.aokolnychyi.ds.stack.StackExamples``. The following features are supported:

- Push an element (``Stack#push``)
- Pop the top element (``Stack#pop``)
- Access the optional top element (``Stack#top``)

### Stack in Scala

#### Notes

- Scala has both immutable and mutable versions of a stack, as well as an ``ArrayStack``. 
The immutable ``Stack`` is deprecated in favor of using ``List`` and ``::``.
There’s an ``ArrayStack`` class, which according to the Scala documentation, "provides fast indexing
and is generally slightly more efficient for most operations than a normal mutable stack." 
The ``immutable.Stack`` class is backed by a ``val elements: List`` as a field, while the ``mutable.ArrayStack``
is backed by an array and an index to the top element. In general, ``ArrayStack`` should be much
faster than regular ``Stack``. Many people recommend using a ``List`` instead of an
``immutable.Stack``. A ``List`` has at least one less layer of code, and you can push elements onto
the ``List`` with ``::`` and access the first element with the head method.

#### Implementation

A sample immutable Stack is contained in ``com.aokolnychyi.ds.stack.ScalaStack`` and its mutable
counterpart is represented by ``com.aokolnychyi.ds.stack.mutable.ScalaStack``. Both 
implementations support the following features:

- Push an element (``ScalaStack#push``)
- Pop the top element (``ScalaStack#pop``)
- Access the optional top element (``ScalaStack#peek``)

## Queue

This chapter covers the Queue data structure that has the following general properties:

- Implements the first-in, first-out (FIFO) principle.
- Fast addition and removal (see more below).
- Useful when you need to retrieve things in the order that you insert them.

### Queue In Java

#### Notes

- Addition and removal in O(1) time.
- There is a separate interface ``Queue`` in Java. It is implemented by ``LinkedList`` and other
classes. ``ArrayDeque`` is probably the best implementation.
- A quote: "I believe that the main performance bottleneck in ``LinkedList`` is the fact that whenever
you push to any end of the deque, behind the scene the implementation allocates a new linked list
node, which essentially involves JVM/OS, and that's expensive. Also, whenever you pop from
any end, the internal nodes of ``LinkedList`` become eligible for garbage collection and that's
more work behind the scene" ([source](https://stackoverflow.com/a/32625029/4108401))

#### Implementation

A sample queue implementation based on a circular array is available in
``com.aokolnychyi.ds.queue.Queue`` with examples in ``com.aokolnychyi.ds.queue.QueueExamples``. One
limitation compared to ``ArrayDeque`` is that you have to predefine the capacity in advance and
there is no auto resize. The following features are supported:

- Remove an element (``Queue#dequeue``)
- Add an element (``Queue#enqueue``)
- Get an optional element (``Queue#peek``)
- Get the current number of elements (``Queue#size``)

### Queue in Scala

#### Notes

- Scala has a functional queue implementation, which is based on two linked lists: one containing
the ''in'' elements and the other the ''out'' elements. Elements are added to the ''in'' list
and removed from the ''out'' list. When the ''out'' list runs dry, the queue is pivoted by
replacing the ''out'' list by ''in.reverse'', and ''in'' by ''Nil'' (source scaladoc).
- Removal from the built-in ``immutable.Queue`` runs in amortized constant time.
- There is even a better version of immutable Queues, called the Banker's Queue.
It is based on lazy ``Stream``s instead of ``List``s to actually distribute the work.
You can perform the reversal in truly O(1) time and you pay a penalty once you start to access
those elements. The laziness distributes the amortization. In addition to using ``Stream``s, you
need to store the sizes since ``Stream#reverse`` is not a lazy operation.
- There is another data structure that can be used as a functional queue. It is called 2-3 Finger
Tree. It is a double ended queue (aka, deck, deque), in which elements can be added to or removed
from either the front (head) or back (tail). Also, access to the middle elements is quite fast
(log n). On paper, it is very fast but on practice it is very slow.
The reason for this is data locality. The JVM tries to be smart and can put objects, which are
related, together (e.g., parts of an arrays are stored together, even objects that form a linked
list can be stored together). This is called Heap locality. However, it is not possible
with 2-3 Finger Tree and that's why this data structure is very slow on practice
([source](https://www.infoq.com/presentations/Functional-Data-Structures-in-Scala)).

#### Implementation

A sample immutable queue implementation is available in ``com.aokolnychyi.ds.queue.ScalaQueue``.
Its mutable counterpart is defined in ``com.aokolnychyi.ds.queue.mutable.ScalaQueue`` and has the
same underlying ideas as its Java version. Both implementations support the following features:

- Remove an element (``ScalaQueue#dequeue``)
- Add an element (``ScalaQueue#enqueue``)
- Get an optional element (``ScalaQueue#peek``)

See examples in the corresponding folders.

## Heap

This chapter covers the Heap data structure that has the following features:

- Heap is based on an array that represents a complete binary tree. A complete binary tree is a 
binary in which every level is fully filled (except possibly the last level, which must be filled
from left to right).
- Can be min (the smallest element is a root) or max (the biggest element is a root) binary heap.
A full binary tree is a binary tree in which no nodes have one child (i.e., one or two). A perfect
binary tree is both full and complete.
- Heaps are used in the Heap sort and Priority Queues.

### Heap in Java

#### Notes

- The Heap data structure is used in Java's ``PriorityQueue``.
- It takes O(n) time to build a binary heap from a set of elements.
See [here](https://stackoverflow.com/a/9755805/4108401).
- It takes O(log n) time to add an element and to remove the smallest/biggest (depending on the
heap type) element since you need to restore the heap properties. 
However, you can access the current min/max element in O(1) time.
- ``PriorityQueue`` in Java has several methods with the same functionality but with one
subtle difference: one method throws an exception and another one returns null if the queue is empty.
For instance, ``peek()`` and ``element()`` vs and ``poll()`` and ``remove()``.
- When you add a new element to a priority heap, you add it to the end. And then you call the
fix operation, whose goal is to propagate the inserted element up till the parent element
is less (max heap) or greater (min heap), or the index of the parent element is < 0 (already root).
- Elements of a priority queue are ordered according to their natural ordering (``Comparable``),
or by a ``Comparator`` provided at the queue construction time, depending on which constructor is
used. ``PriorityQueue`` does not permit null elements. A priority queue relying on natural ordering
also does not permit insertion of non-comparable objects (doing so may result in
 ``ClassCastException``). The ``Iterator`` provided in ``iterator()`` is not guaranteed to traverse
the elements of the priority queue in any particular order.
- The Java implementation provides O(log(n)) time to enqueue and dequeue (``offer()``,
``poll()``, ``remove()``, ``add()``); O(n) time for the ``remove(Object)`` and ``contains(Object)``
methods; O(1) time for retrieval methods (``peek()``, ``element()``).

#### Implementation

There is a generic abstract class ``com.aokolnychyi.ds.heap.Heap`` and two concrete implementations:
``com.aokolnychyi.ds.heap.MaxHeap`` and ``com.aokolnychyi.ds.heap.MinHeap``. The following
features are supported:

- Add an element ``Heap#add``
- Get the min/max element or Optional.empty ``Heap#peek``
- Get the min/max element or throw an exception ``Heap#element``
- Remove the current min/max element ``Heap#remove``

See examples in ``com.aokolnychyi.ds.heap.HeapExamples``.

### Heap in Scala

#### Heap

#### Notes

- The ``mutable.PriorityQueue`` class is based on a heap and it is very similar to what Java offers.
- There is no built-in immutable priority queue in Scala. You can use scalaz ``FingerTree`` instead.

#### Implementation

There is ``com.aokolnychyi.ds.heap.ScalaMutableMaxHeap``, which uses ``ArrayBuffer`` internally.
The following methods are supported:

- Add an element ``ScalaMutableMaxHeap#+=``
- Get the max element or None otherwise ``ScalaMutableMaxHeap#peek``
- Get the current max element or throw an exception ``ScalaMutableMaxHeap#element`` 

See examples in ``com.aokolnychyi.ds.heap.ScalaMutableMaxHeapExamples``.

## Vectors

- Bitmapped Vector Trie is a combination of associative and sequential data type since it preserves
the insertion order and provides fast access to head/tail/middle elements. It is a functional
analog to java.util.ArrayList. You append/prepend and update at nth
place in (almost) constant time. Actually, it is O(log_32 n). log_32 of 2.5 billion is less than 7.
- ``Vector`` has good cache-locality, where the 32-way branching of the tree means that
elements at the leaves are stored in relatively-compact 32-element arrays. This makes
traversing or manipulating them substantially faster than working with binary trees,
where any traversal has to follow a considerable number of pointers up-and-down in order to
advance a single element ([source](http://www.lihaoyi.com/post/ScalaVectoroperationsarentEffectivelyConstanttime.html)).
- Bit-mapped Vector Tries are faster than Java's ``ArrayList`` for huge data sets and truly
random access. This is explained by the architecture of modern computers and the fact that arrays
of size 32 can be handled very nicely. However, the  ``ArrayList`` will be better for linear
access since you can load a relatively big chunk of memory in one shot that represent a part
of the considered data set. It is not possible with ``Vector``. In addition, for smaller data sets
``ArrayList`` will be a better option since it has just to dereference once.
([source](https://www.infoq.com/presentations/Functional-Data-Structures-in-Scala)).

## Other important notes

- node vs wrapper on top
- Scala's Arrays are Java's Array.
- In computing, a persistent data structure is a data structure that always preserves the previous
version of itself when it is modified. Such data structures are effectively immutable, as their
operations do not (visibly) update the structure in-place, but instead always yield a new updated
structure ([source](https://en.wikipedia.org/wiki/Persistent_data_structure)).
- If you do an operation say a million times, you don't really care about the worst-case or the
best-case of that operation - what you care about is how much time is taken in total when you
repeat the operation a million times. So it doesn't matter if the operation is very slow once
in a while, as long as "once in a while" is rare enough for the slowness to be diluted away.
Essentially amortised time means "average time taken per operation, if you do many operations".
([source](https://stackoverflow.com/a/249695/4108401)) 
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
- If you implement an immutable data structure and want to remove an element, you can return
a tuple on removal, where the first element represents the removed element and the second elements
is the new collection.
- In functional programming, there are two types of data structures: sequential and associative.
The former preserves the insertion order and has fast access to head/tail while the latter
is orthogonal and has nice performance for accessing elements in the middle.
- Functional data structures are immutable and you need to copy them but you still want to achieve
comparable asymptotic performance. This is done by sharing data between the old and new data
structures. It is called structured sharing
([source](https://www.infoq.com/presentations/Functional-Data-Structures-in-Scala)).
- Scala's ``Ordered[T]`` extends Java's ``Comparable[T]``. ``Ordering[T]`` extends
``Comparator[T].`` There is an implicit ordering to ordered mapping. The ``Ordering`` companion
object provides an implicit ``Ordering[T]`` for any ``Comparable[T]``.
-  ``: _*`` in Scala is called type ascription. Type ascription is just telling the compiler
what type you expect out of an expression, from all possible valid types. ``: _*`` is a special
instance of type ascription which tells the compiler to treat a single argument of
a sequence type as a variable argument sequence, i.e. varargs. In other words, ``: _*`` is a
special notation that tells the compiler to pass each element as its own argument, 
rather than all of it as a single argument.

- Red-Black Trees can be also implemented in a functional manner. 


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
