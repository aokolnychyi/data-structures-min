package com.aokolnychyi.ds.stream

trait Stream[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: Stream[T]
}

// Case class parameters cannot be call-by-name
final class Cons[T](val head: T, tl: => Stream[T]) extends Stream[T] {
  override def isEmpty: Boolean = false
  override lazy val tail: Stream[T] = tl
}

object Cons {
  def apply[T](head: T, tail: => Stream[T]) = new Cons(head, tail)
}

object Nil extends Stream[Nothing] {
  override def isEmpty: Boolean = true
  override def head: Nothing = {
    throw new NoSuchElementException("Trying to access the head of an empty Stream!")
  }
  override def tail: Stream[Nothing] = {
    throw new NoSuchElementException("Trying to access the tail of an empty Stream!")
  }
}