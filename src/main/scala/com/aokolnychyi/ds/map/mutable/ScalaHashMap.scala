package com.aokolnychyi.ds.map.mutable

class ScalaHashMap[K, V](val capacity: Int = 4) {

  private val elements = Array.fill[Option[Entry]](capacity)(None)

  def +=(key: K, value: V): Unit = {

    def insert(bucket: Option[Entry]): Option[Entry] = bucket match {
      case None => Some(Entry(key, value, None, None))
      case Some(entry) if entry.key == key && entry.key.## == key.## =>
        Some(entry.copy(value = value))
      case Some(entry) if key.## < entry.key.## =>
        Some(entry.copy(leftChild = insert(entry.leftChild)))
      case Some(entry) =>
        Some(entry.copy(rightChild = insert(entry.rightChild)))
    }

    elements(bucketIndex(key)) = insert(elements(bucketIndex(key)))
  }

  def get(key: K): Option[V] = {

    def get(bucket: Option[Entry]): Option[V] = bucket match {
      case None => None
      case Some(entry) if entry.key.## == key.## && entry.key == entry.key => Some(entry.value)
      case Some(entry) if key.## < entry.key.## => get(entry.leftChild)
      case Some(entry) => get(entry.rightChild)
    }

    val bucket = elements(bucketIndex(key))
    get(bucket)
  }

  private def bucketIndex(key: K): Int = {
    key.## % capacity
  }

  private case class Entry(key: K, value: V, leftChild: Option[Entry], rightChild: Option[Entry])
}

