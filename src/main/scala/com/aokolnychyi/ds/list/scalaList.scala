package com.aokolnychyi.ds.list

sealed trait List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
  def map[V](function: T => V): List[V]
}

case object Nil extends List[Nothing] {
  override def isEmpty: Boolean = true
  override def head: Nothing = {
    throw new NoSuchElementException("Trying to access the head of an empty list!")
  }
  override def tail: List[Nothing] = {
    throw new UnsupportedOperationException("Trying to access the tail of an empty list!")
  }
  override def map[V](function: (Nothing) => V): List[Nothing] = Nil
}

case class Cons[T](head: T, tail: List[T]) extends List[T] {
  override def isEmpty: Boolean = false
  override def map[V](function: (T) => V): List[V] = Cons(function(head), tail.map(function))
}
