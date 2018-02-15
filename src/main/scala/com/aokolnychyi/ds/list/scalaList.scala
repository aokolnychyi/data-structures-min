package com.aokolnychyi.ds.list

import scala.annotation.tailrec

sealed trait List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
  def contains[E >: T](element: E): Boolean
  def map[V](function: T => V): List[V]
  def reversed: List[T]
  def distinct: List[T]
  def swapPairwise(): List[T]
}

case object Nil extends List[Nothing] {
  override def isEmpty: Boolean = true
  override def head: Nothing = {
    throw new NoSuchElementException("Trying to access the head of an empty list!")
  }
  override def tail: List[Nothing] = {
    throw new UnsupportedOperationException("Trying to access the tail of an empty list!")
  }
  override def contains[E >: Nothing](element: E): Boolean = false
  override def map[V](function: (Nothing) => V): List[Nothing] = Nil
  override def reversed: List[Nothing] = Nil
  override def distinct: List[Nothing] = Nil
  override def swapPairwise(): List[Nothing] = Nil
}

case class Cons[T](head: T, tail: List[T]) extends List[T] {
  override def isEmpty: Boolean = false
  override def map[V](function: (T) => V): List[V] = Cons(function(head), tail.map(function))
  override def contains[E >: T](element: E): Boolean =
    if (element == head) true else tail.contains(element)
  override def reversed: List[T] = {
    @tailrec
    def inner(list: List[T], reversedList: List[T]): List[T] = list match {
      case Nil => reversedList
      case Cons(listHead, listTail) => inner(listTail, Cons(listHead, reversedList))
    }

    inner(this, Nil)
  }
  override def distinct: List[T] = {
    @tailrec
    def inner(list: List[T], output: List[T]): List[T] = list match {
      case Nil => output.reversed
      case Cons(listHead, listTail) if listTail.contains(listHead) => inner(listTail, output)
      case Cons(listHead, listTail) => inner(listTail, Cons(listHead, output))
    }

    inner(this, Nil)
  }
  override def swapPairwise(): List[T] = this match {
    case Cons(_, Nil) => this
    case Cons(head1, Cons(head2, _)) => Cons(head2, Cons(head1, tail.swapPairwise()))
  }
}
