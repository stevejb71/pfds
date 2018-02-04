import scala.annotation.tailrec

package object pfds {
  trait Order[A] {
    def lteqv(a1: A, a2: A): Boolean
  }

  implicit val intOrder: Order[Int] = new Order[Int] {
    override def lteqv(a1: Int, a2: Int): Boolean = a1 <= a2
  }

  implicit class OrderOps[A](x: A)(implicit val order: Order[A]) {
    def <=(y: A): Boolean = order.lteqv(x, y)
  }

  @tailrec
  def repeat[A](n: Int, start: A)(f: A => A): A = n match {
    case 0 => start
    case _ if n > 0 => repeat(n - 1, f(start))(f)
    case _ => throw new IllegalArgumentException(s"repeat got $n")
  }
}
