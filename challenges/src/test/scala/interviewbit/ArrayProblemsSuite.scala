package interviewbit

class ArrayProblemsSuite extends munit.FunSuite {

  import ArrayProblems._

  val examples = Seq(
    ("eval array of all negatives", Array(-1, -2, -3), 1),
    ("eval sequential positive ints starting with 1 to return next element in sequence", Array(1, 2, 3, 4), 5),
    ("eval sequential positive ints shuffled to return missing positive int", Array(1, 4, 3, 2), 5),
    ("eval random positive ints to return missing int", Array(20, 1, 3, 34), 2),
    ("eval array with only 0 to return 1", Array(0), 1),
    ("eval array with duplicates", Array(1, 3, 2, 3), 4),
    (
      "eval array with negatives and positives to return first missing pos int.",
      Array(-7, -19, -7, -9, 2, 7, 4, 100, 6),
      1
    )
  )

  test("First missing positive integer") {
    examples.foreach { case (clue, ints, missingPosition) =>
      assertEquals(firstMissingPositive(ints), missingPosition, clue)
    }
  }

}
