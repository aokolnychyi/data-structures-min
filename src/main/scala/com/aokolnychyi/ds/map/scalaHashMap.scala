package com.aokolnychyi.ds.map

import scala.collection.immutable.ListMap

// the base class that provides common functionality and also acts as an empty HashMap
class ScalaHashMap[K, V] {

  def get(key: K): Option[V] = getValue(key, 0)

  def put(key: K, value: V): ScalaHashMap[K, V] = putKeyValue(key, value, 0)

  def +(element: (K, V)): ScalaHashMap[K, V] = put(element._1, element._2)

  private[map] def getValue(key: K, level: Int): Option[V] = None

  private[map] def putKeyValue(key: K, value: V, level: Int): ScalaHashMap[K, V] =
    SingleEntryHashMap(key, value)

  // a utility method to merge two leaf hash maps (SingleEntryHashMap or CollisionHashMap)
  // into an instance of HashTrieMap
  private[map] def makeHashTrieMap(
      firstHash: Int,
      firstElement: ScalaHashMap[K, V],
      secondHash: Int,
      secondElement: ScalaHashMap[K, V],
      level: Int): HashTrieMap[K, V] = {

    // 0x1f is a hex representation of 31, which is 11111 in binary
    // first, get rid of level * 5 lowest bits since they were considered before
    // perform bitwise & with 11111 to consider only 5 bits that are relevant for this level
    val firstIndex = (firstHash >>> (level * 5)) & 0x1f
    val secondIndex = (secondHash >>> (level * 5)) & 0x1f

    if (firstIndex != secondIndex) {
      // the hashes are already different at this level
      // the bitmap tells to which positions the array elements correspond
      // for instance, bitmap 10001 and Array(elem1, elem2) mean that
      // elem1 is located at index 0 and elem2 is located at index 4
      val bitmap = (1 << firstIndex) | (1 << secondIndex)
      val elements = if (firstIndex < secondIndex) {
        Array(firstElement, secondElement)
      } else {
        Array(secondElement, firstElement)
      }
      HashTrieMap(bitmap, elements)
    } else {
      // the hashes are the same at this level (i.e., current 5 bits are equal)
      // therefore, we need to recurse and handle this later on
      val bitmap = 1 << firstIndex
      val element = makeHashTrieMap(firstHash, firstElement, secondHash, secondElement, level + 1)
      HashTrieMap(bitmap, Array(element))
    }
  }

}

object ScalaHashMap {
  def apply[K, V](): ScalaHashMap[K, V] = new ScalaHashMap()
}

// a class that represents hash maps with one entry
case class SingleEntryHashMap[K, V](key: K, value: V) extends ScalaHashMap[K, V] {

  override private[map] def getValue(key: K, level: Int): Option[V] =
    if (this.key == key && this.key.## == key.##) Some(value) else None

  override private[map] def putKeyValue(key: K, value: V, level: Int): ScalaHashMap[K, V] = {
    if (this.key.## == key.## && this.key == key) {
      if (this.value == value) this else SingleEntryHashMap(key, value)
    } else if (this.key.## != key.##) {
      makeHashTrieMap(this.key.##, this, key.##, SingleEntryHashMap(key, value), level)
    } else {
      CollisionHashMap(key.##, ListMap(this.key -> this.value, key -> value))
    }
  }

}

// a class that represents hash maps with multiple elements, which have the same hash
case class CollisionHashMap[K, V](hash: Int, keyValuePairs: ListMap[K, V]) extends ScalaHashMap[K, V] {

  override private[map] def getValue(key: K, level: Int): Option[V] = keyValuePairs.get(key)

  override private[map] def putKeyValue(key: K, value: V, level: Int): ScalaHashMap[K, V] = {
    if (key.## == hash) {
      CollisionHashMap(hash, keyValuePairs + (key -> value))
    } else {
      makeHashTrieMap(hash, this, key.##, SingleEntryHashMap(key, value), level)
    }
  }

}

// a class that represents hash maps using the trie data structure
// the bitmap allows us to avoid storing all 32 array elements when it is not required
// the bitmap contains 32 bits, where 1s indicate that there is an entry at that index
// the entry can be of any type (CollisionHashMap, SingleEntryHashMap, HashTrieMap)
case class HashTrieMap[K, V](
    bitmap: Int,
    elements: Array[ScalaHashMap[K, V]]) extends ScalaHashMap[K, V] {

  override private[map] def getValue(key: K, level: Int): Option[V] = {
    // take 5 relevant bits of the key hash at this level and
    // determine the index in the children array
    val index = (key.## >>> (level * 5)) & 0x1f
    val mask = 1 << index
    if (bitmap == -1) {
      // the children array at this level is full and contains 32 elements
      // therefore, we are sure at what subtree to recurse
      elements(index).getValue(key, level + 1)
    } else if ((bitmap & mask) != 0) {
      // the children array has less than 32 entries and we need to determine where to recurse
      // bitmap: 1001, mask, 1000, array: [elem1, elem2]
      // count 1s in (1001 & 0111) to determine how many elements to skip (i.e., offset)
      val offset = Integer.bitCount(bitmap & (mask - 1))
      elements(offset).getValue(key, level + 1)
    } else {
      None
    }
  }

  override private[map] def putKeyValue(key: K, value: V, level: Int) = {
    val index = (key.## >>> (level * 5)) & 0x1f
    val mask = 1 << index
    val offset = Integer.bitCount(bitmap & (mask - 1))

    if ((bitmap & mask) != 0) {
      // there is already an entry at this level
      val currentElement = elements(offset)
      val updatedCurrentElement = currentElement.putKeyValue(key, value, level + 1)
      if (updatedCurrentElement eq currentElement) {
        this
      } else {
        val newElements = elements.clone()
        newElements(offset) = updatedCurrentElement
        HashTrieMap(bitmap, newElements)
      }
    } else {
      // there is no existing entry at this level
      val newElements = new Array[ScalaHashMap[K, V]](elements.length + 1)
      Array.copy(elements, 0, newElements, 0, offset)
      newElements(offset) = SingleEntryHashMap(key, value)
      Array.copy(elements, offset, newElements, offset + 1, elements.length - offset)
      HashTrieMap(bitmap | mask, newElements)
    }
  }

}