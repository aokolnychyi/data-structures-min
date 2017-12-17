package com.aokolnychyi.ds.stream

trait Stream[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: Stream[T]
  def map[A](f: T => A): Stream[A]
}

// case class parameters cannot be call-by-name
final class Cons[T](val head: T, tl: => Stream[T]) extends Stream[T] {
  override def isEmpty: Boolean = false
  override lazy val tail: Stream[T] = tl
  override def map[A](f: T => A): Stream[A] = Cons(f(head), tl.map(f))
}

object Cons {
  def apply[T](head: T, tail: => Stream[T]) = new Cons(head, tail)
}

object Empty extends Stream[Nothing] {
  override def isEmpty: Boolean = true
  override def head: Nothing = {
    throw new NoSuchElementException("Trying to access the head of an empty Stream!")
  }
  override def tail: Stream[Nothing] = {
    throw new NoSuchElementException("Trying to access the tail of an empty Stream!")
  }
  override def map[A](f: Nothing => A): Stream[A] = this
}

object ScalaStreamExamples extends App {
  val stream = Cons(1, Cons(2, Empty))
  println(stream.head)
  println(stream.tail.head)
  println(stream.tail.tail)
  val result = stream.map(_ + 1)
  println(result.head)
  println(result.tail.head)
  println(result.tail.tail)
}
