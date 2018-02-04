package pfds
package heaps

import org.specs2.mutable.Specification
import LeftistHeap._
import org.specs2.ScalaCheck

class LeftistHeapSpec extends Specification with ScalaCheck {
  "after inserting an element x into an empty heap, the min element is x" >> prop {(x: Int) =>
    Empty.insert(x).findMin should_=== x
  }

  "after inserting an element x - 1 into an heap with min elt x, the min element is x - 1" >>
    prop {(xs: List[Int]) => xs.nonEmpty ==> {
      val h = LeftistHeap.fromStdList(xs.map(x => if(x == Int.MinValue) 0 else x))
      val x = h.findMin
      h.insert(x - 1).findMin should_=== x - 1
    }}

  "after inserting an element x + 1 into an heap with min elt x, the min element is x" >>
    prop {(xs: List[Int]) => xs.nonEmpty ==> {
      val h = LeftistHeap.fromStdList(xs.map(x => if(x == Int.MaxValue) 0 else x))
      val x = h.findMin
      h.insert(x + 1).findMin should_=== x
  }}

  "inserting n elements then deleting them from Empty returns Empty" >> prop {(n: Int, x: Int) =>
    val repeats = Math.abs(n % 20)
    val inserted = repeat(repeats, emptyHeap[Int])(_ insert x)
    val deleted = repeat(repeats, inserted)(_.deleteMin)

    deleted.isEmpty should_=== true
  }

  "merge of two heaps produces list in sorted order" >> prop {(a: Int, b: Int, c: Int, d: Int) =>
    val h1 = Empty.insert(a).insert(b)
    val h2 = Empty.insert(c).insert(d)

    val h3 = h1 merge h2

    h3.toStdList should_=== List(a, b, c, d).sorted
  }

  "from list to heap and back gives a sorted list" >> prop {(xs: List[Int]) => {
    val limitedSize = xs.take(20)
    LeftistHeap.fromStdList(limitedSize).toStdList should_=== limitedSize.sorted
  }}
}
