package scala_learning


object HelloWorld {
  def main(args: Array[String]): Unit = {
    var b = sum(1, _, 3)
    println(b(20))
    // 24
  }

  def sum(a: Int, b: Int, c: Int) = a + b + c
}