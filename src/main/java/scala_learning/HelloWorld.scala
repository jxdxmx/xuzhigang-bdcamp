package scala_learning

import java.io.{File, PrintWriter}

import scala.io.{Source, StdIn}

object HelloWorld {
  def main(args: Array[String]) {
    val writer = new PrintWriter(new File("src/main/java/scala_learning/test.txt"))
    writer.write("菜鸟教程")
    writer.close()

    print("请输入菜鸟教程官网 : ")
    val line = StdIn.readLine()
    println("谢谢，你输入的是: " + line)

    println("文件内容为:")

    val s = Source.fromFile("src/main/java/scala_learning/test.txt")
    s.foreach {
      print
    }
  }


}
