package pfds.heaps

import org.scalameter.api._
import org.scalameter.picklers.Implicits._

import scala.util.Random

object LeftistHeapBenchmark extends Bench.Quickbenchmark {
  measure method "insert" in {
    val insertions = Gen.enumeration("insertions")(1000, 10000, 100000, 200000, 300000)
    using(insertions map randomArray(888)) in arrayToHeap
  }

  measure method "merge two identically sized heaps" in {
    val heapSizes = Gen.enumeration("heapSizes")(1000, 10000, 100000, 1000000)
    def randomHeap(seed: Int)(n: Int) = arrayToHeap(randomArray(seed)(n))
    val initialHeaps = heapSizes.map(n => (randomHeap(800)(n), randomHeap(900)(n)))
    using(initialHeaps) in mergeHeaps
  }

  private def mergeHeaps(hs: (LeftistHeap[Int], LeftistHeap[Int])) = hs._1 merge hs._2

  private def arrayToHeap(xs: Array[Int]): LeftistHeap[Int] = {
    var h = LeftistHeap.emptyHeap[Int]
    var n = 0
    while(n < xs.length) {
      h = h insert xs(n)
      n += 1
    }
    h
  }

  private def randomArray(seed: Int)(n: Int): Array[Int] = {
    val rnd = new Random(seed)
    Array.tabulate(n)(_ => rnd.nextInt)
  }
}
