package neetcode

object ArraysAndHashing extends App {

  containsDuplicate(Array(1, 2, 3, 4))
  def containsDuplicate(nums: Array[Int]): Boolean = {

    @scala.annotation.tailrec
    def aux(array: Array[Int], index: Int, acc: Set[Int]): Boolean = {
      if (index == array.size) false
      else if (acc.contains(array(index))) true
      else aux(array, index + 1, acc + array(index))
    }
    aux(nums, 0, Set.empty[Int])
  }

}
