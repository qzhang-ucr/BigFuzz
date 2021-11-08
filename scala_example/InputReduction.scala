package examples.benchmarks.input_reduction_benchmarks

import scala.reflect.runtime.universe._
import provenance.data.Provenance
import symbolicprimitives.{ SymInt, SymString }
object InputReduction {
  def main(args: Array[String]): Unit = {
    println("Testing Quasiquotes...")
    val x: SymInt = new SymInt(8, Provenance.create(4, 5, 6))
    val z: SymInt = new SymInt(10, Provenance.create(9, 8, 7))
    val str1: SymString = new SymString("This is a SymString 1", Provenance.create(41))
    val str2: SymString = new SymString("This is a SymString 2", Provenance.create(42))
    val y = x + z
    if (_root_.refactor.BranchTracker.provWrapper(q"$x > 5", List[Any](x))) {
      if (_root_.refactor.BranchTracker.provWrapper(q"$y > $x", List[Any](x, y, x))) {
        if (_root_.refactor.BranchTracker.provWrapper(q"$str1 != $str2", List[Any](x, y, x, str1, str2))) print("str1 and str2 are not equal")
      }
    }
  }
  def convert_to_mm(s: String): Float = {
    val unit = s.substring(s.length - 2)
    val v = s.substring(0, s.length - 2).toFloat
    if (_root_.refactor.BranchTracker.provWrapper(q"${unit.equals("mm")}", List[Any]())) return v else v * 304.8f
  }
  def zipToState(str: String): String = {
    (str.toInt % 50).toString
  }
}