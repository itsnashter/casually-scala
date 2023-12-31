package leetcode

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.must.Matchers

class BSTIteratorWordSpec extends AnyWordSpec with Matchers {

  "A BST iterator" when {
    val node = new TreeNode(100)
    val it   = new BSTIterator(node)
    "tree with ONLY one node" should {
      "first hasNext() must be true" in { it.hasNext() mustBe true }
      "next() should return values set in node" in { it.next() mustBe node.value }
      "following hasNext() must be false" in { it.hasNext() mustBe false }

    }
  }
  "A BSTIterator" when {
    val nodes = (0 to 20).map(new TreeNode(_))
    nodes(15).left = nodes(9)
    nodes(15).right = nodes(20)
    nodes(7).left = nodes(3)
    nodes(7).right = nodes(15)
    val root  = nodes(7)

    "unbalance bst of level 3" should {
      "return sorted order on next() until hasNext()" in {

        @scala.annotation.tailrec
        def get(it: BSTIterator, acc: List[Int]): List[Int] =
          if (it.hasNext()) {
            val x = it.next()
            get(it, acc :+ x)
          } else acc

        val bit = new BSTIterator(root)
        get(bit, List.empty[Int]) mustBe List(3, 7, 9, 15, 20)

      }
    }
  }

}
