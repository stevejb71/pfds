package pfds
package heaps

import collection.immutable._

class EmptyHeapException extends Exception

trait LeftistHeap[+E] {
  def isEmpty: Boolean
  def rank: Int
  def findMin: E
  def deleteMin: LeftistHeap[E]
}
case object Empty extends LeftistHeap[Nothing] {
  override def isEmpty = true
  override def rank = 0
  override def findMin = throw new EmptyHeapException()
  override def deleteMin = throw new EmptyHeapException()
}
case class Merge[E: Order](rank: Int, elem: E, left: LeftistHeap[E], right: LeftistHeap[E]) extends LeftistHeap[E] {
  override def isEmpty = false
  override def findMin: E = elem
  override def deleteMin = LeftistHeap.merge(left, right)
}

case object LeftistHeap {
  implicit class Syntax[E: Order](h: LeftistHeap[E]) {
    def insert(x: E): LeftistHeap[E] = LeftistHeap.insert(x, h)
    def merge(h2: LeftistHeap[E]): LeftistHeap[E] = LeftistHeap.merge(h, h2)
    def toStdList: List[E] = LeftistHeap.toStdList(h)
  }
  def emptyHeap[E]: LeftistHeap[E] = Empty
  def merge[E: Order](h1: LeftistHeap[E], h2: LeftistHeap[E]): LeftistHeap[E] =
    (h1, h2) match {
      case (h, Empty) => h
      case (Empty, h) => h
      case (Merge(_, x, a1, b1), Merge(_, y, a2, b2)) =>
        if(x <= y) {
          makeMerge(x, a1, merge(b1, h2))
        } else {
          makeMerge(y, a2, merge(h1, b2))
        }
    }
  def insert[E: Order](x: E, h: LeftistHeap[E]): LeftistHeap[E] = merge(Merge(1, x, Empty, Empty), h)

  def fromStdList[E: Order](xs: List[E]): LeftistHeap[E] = xs.foldLeft(emptyHeap[E])((h, x) => insert(x, h))
  def toStdList[E](h: LeftistHeap[E]): List[E] = {
    def go(h: LeftistHeap[E], acc: List[E]): List[E] = h match {
      case Empty => acc
      case m@Merge(_, _, _, _) => go(m.deleteMin, m.findMin :: acc)
    }
    go(h, Nil).reverse
  }

  private def makeMerge[E: Order](x: E, a: LeftistHeap[E], b: LeftistHeap[E]): LeftistHeap[E] =
    if(a.rank > b.rank) {
      Merge(b.rank + 1, x, a, b)
    } else {
      Merge(a.rank + 1, x, b, a)
    }
}
