package x.util

import java.util.concurrent.ConcurrentLinkedQueue

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

/**
  * Wrapper for Java ConcurrentLinkedQueue.
  */
class ConcurrentQueue[T](val underlying: ConcurrentLinkedQueue[T] = new ConcurrentLinkedQueue[T]) {
  /**
    * Access element number <code>n</code>.
    *
    *  @return  the element at index <code>n</code>.
    *  @throws UnsupportedOperationException
    */
  def apply(n: Int): T = throw new UnsupportedOperationException("Cannot peek inside this queue.")


  /**
    * Returns the number of elements in this queue.  If this queue
    * contains more than {@code Integer.MAX_VALUE} elements, returns
    * {@code Integer.MAX_VALUE}.
    *
    * <p>Beware that, unlike in most collections, this method is
    * <em>NOT</em> a constant-time operation. Because of the
    * asynchronous nature of these queues, determining the current
    * number of elements requires an O(n) traversal.
    * Additionally, if elements are added or removed during execution
    * of this method, the returned result may be inaccurate.  Thus,
    * this method is typically not very useful in concurrent
    * applications.
    *
    * @return the number of elements in this queue
    */
  def size(): Int = underlying.size()

  /**
    * @see size().
    */
  def length(): Int = size()

  /**
    * Returns {@code true} if this queue contains no elements.
    *
    * @return { @code true} if this queue contains no elements
    */
  def isEmpty: Boolean = underlying.isEmpty

  /**
    * Inserts the specified element at the tail of this queue.
    * As the queue is unbounded, this method will never throw
    * {@link IllegalStateException} or return {@code false}.
    * @param  elem        the element to insert
    * @return { @code true} (as specified by { @link Collection#add})
    * @throws NullPointerException if the specified element is null
    */
  def +=(elem: T): this.type = { underlying.add(elem); this }

  /**
    * Appends all of the elements in the specified iterator to the end of
    * this queue, in the order that they are returned by the specified
    * iterator.  Attempts to {@code addAll} of a queue to
    * itself result in {@code IllegalArgumentException}.
    *
    * @param  it        an iterator
    * @return { @code true} if this queue changed as a result of the call
    * @throws NullPointerException     if the specified collection or any
    *                                  of its elements are null
    * @throws IllegalArgumentException if the collection is this queue
    */
  def ++=(it: TraversableOnce[T]): this.type = {
    underlying.addAll(it.toIterable)
    this
  }

  /**
    * Appends all of the elements in the specified collection to the end of
    * this queue, in the order that they are returned by the specified
    * collection's iterator.  Attempts to {@code addAll} of a queue to
    * itself result in {@code IllegalArgumentException}.
    *
    * @param  elems       the elements to add.
    * @return { @code true} if this queue changed as a result of the call
    * @throws NullPointerException     if the specified collection or any
    *                                  of its elements are null
    * @throws IllegalArgumentException if the collection is this queue
    */
  def enqueue(elems: T*): Unit = underlying.addAll(elems)

  /**
    * Returns the first element in the queue, and removes this element from the queue.
    *
    *  @return the first element of the queue.
    */
  def dequeue(): T = underlying.poll()

  /**
    * Returns the first element in the queue, or throws an error.
    * @return the first element.
    * @throws NullPointerException   if there is no element contained in the queue.
    */
  def front: T = {
    val front = underlying.peek
    if (front == null)
      throw new NullPointerException
    else front
  }

  /**
    * Retrieves and removes all elements currently available in the queue
    * or returns {@code Seq.empty} if this queue is empty.
    *
    * @return all elements in this queue, or { @code Seq.empty} if this queue is empty
    */
  def dequeueAll(): Seq[T] = {
    if (underlying.isEmpty)
      Seq.empty
    else {
      val res = new ArrayBuffer[T]
      while (underlying.nonEmpty) res += underlying.poll()
      res
    }
  }

  /**
    * Removes all of the elements from this queue.
    * The queue will be empty after this call returns.
    *
    * <p>Internally this implementation repeatedly invokes { @code underlying.poll() } until it returns <tt>null</tt>.
    */
  def clear(): Unit = underlying.clear()

  /**
    * Returns an iterator over the elements in this queue in proper sequence.
    * The elements will be returned in order from first (head) to last (tail).
    *
    * <p>The returned iterator is weakly consistent.
    *
    * @return an iterator over the elements in this queue in proper sequence
    */
  def iterator: Iterator[T] = underlying.iterator

  override def hashCode: Int = underlying.##

  override def equals(that: Any): Boolean = if (that == null) false else that == underlying

  override def toString: String = underlying.toString
}

object ConcurrentQueue {
  def apply[A](xs: A*): ConcurrentQueue[A] = new ConcurrentQueue[A] ++= xs
}

