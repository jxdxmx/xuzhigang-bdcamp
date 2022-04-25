#scala面试题
https://blog.csdn.net/weixin_45557389/article/details/108195843

#scala下划线的用法大全
https://baijiahao.baidu.com/s?id=1724716830760236383&wfr=spider&for=pc
> 用于变量初始化 .该语法只适用于成员变量，不适用于局部变量
> 用于导包引入导包引入时使用_导入该包下所有内容，类比Java中的*
> 用于模式匹配模式匹配中可以用下划线来作为Java中default的类比使用，也可以在匹配集合类型时，用于代表集合中元素
> 用于访问tuple元素
```scala
    val t = (1, 2, 3)
    println(t._1)
```
> 用于简写函数
```scala
  def main(args: Array[String]): Unit = {
    var nums = List(1, 2, 3, 4)
    println(nums.map(_ + 2)) //    List(3, 4, 5, 6)
    println(nums.sortWith(_ > _)) //    List(4, 3, 2, 1)
    println(nums.filter(_ % 2 == 0))//    List(2, 4)
  }
```
>定义偏函数.对某个多参数函数进行部分函数调用，没有传入的参数使用_代替，返回结果即为偏函数。
```scala
object HelloWorld {
  def main(args: Array[String]): Unit = {
    var b = sum(1, _, 3)
    println(b(20))
    // 24
  }

  def sum(a: Int, b: Int, c: Int) = a + b + c
}
```



```shell script
 重命名成员
import java.util.{HashMap => JavaHashMap}
import java.util.{HashMap => _} // 引入了util包的所有成员,但是HashMap被隐藏了
import java.awt._  // 引入包内所有成员

字符字面量
在 Scala 字符变量使用单引号 ' 来定义，如下：
'a' 
'\u0041'
'\n'
'\t'

其中 \ 表示转义字符，其后可以跟 u0041 数字或者 \r\n 等固定的转义字符。

字符串字面量
在 Scala 字符串字面量使用双引号 " 来定义，如下：

"Hello,\nWorld!"
"菜鸟教程官网：www.runoob.com"
多行字符串的表示方法
多行字符串用三个双引号来表示分隔符，格式为：""" ... """。

实例如下：

val foo = """菜鸟教程
www.runoob.com
www.w3cschool.cc
www.runnoob.com
以上三个地址都能访问"""
    //    var b = 5L
    //    print(b)
    //    Console.print("hello")
    //    println("Hello, world 222!")
    //    var m = JavaHashMap
    //    util.HashMap

    //    整型字面量
    //    整型字面量用于 Int 类型，如果表示 Long，可以在数字后面添加 L 或者小写 l 作为后缀。：
    //
    //    0
    //    035
    //    21
    //    0xFFFFFFFF
    //    0777L
    //    浮点型字面量
    //    如果浮点数后面有f或者F后缀时，表示这是一个Float类型，否则就是一个Double类型的。实例如下：
    //
    //    0.0
    //    1e30f
    //    3.14159f
    //    1.0e100
    //    .1
 ```

    
```scala
        val foo =
          """菜鸟教程
    www.runoob.com
    www.w3cschool.cc
    www.runnoob.com
    以上三个地址都能访问"""
    
        println(foo)
    var nullvar = null
    var nullvar2 = scala.Nil
    var nullvar = Null // 报错

    println(nullvar, 5, nullvar2)
var myVar : String = "Foo"
var myVar : String = "Too"
    println("Hello\tWorld\n\nxxxx");
    var myVar: String = "Foo"
    var myVar2 = "Foo"
    println(myVar)
    println(myVar, myVar2)
    
    val xmax, ymax = 100 // xmax, ymax都声明为100
    var pa = (1, 2)
    println(xmax, ymax, pa)
```

#Scala 访问修饰符
##私有(Private)成员
##保护(Protected)成员
##公共(Public)成员
##作用域保护

class Outer {

  class Inner {
    private def f() {
      println("f")
    }

    class InnerMost {
      f() // 正确
    }

  }

  (new Inner).f() //错误 访问不合法是因为 f 在 Inner 中被声明为 private，而访问不在类 Inner 之内。
}

```scala
package p {

  class Super {
    protected def f() {
      println("f")
    }
  }

  class Sub extends Super {
    f()
  }

  class Other {
    (new Super).f() //错误
  }
}
class Outer {
  class Inner {
    def f() { println("f") }
    class InnerMost {
      f() // 正确
    }
  }
  (new Inner).f() // 正确因为 f() 是 public
}


package bobsrockets{
    package navigation{
        private[bobsrockets] class Navigator{
         protected[navigation] def useStarChart(){}
         class LegOfJourney{
             private[Navigator] val distance = 100
             }
            private[this] var speed = 200
            }
        }
        package launch{
        import navigation._
        object Vehicle{
        private[launch] val guide = new Navigator
        }
    }
}
```

```shell script
上述例子中，类 Navigator 被标记为 private[bobsrockets] 就是说这个类对包含在 bobsrockets 包里的所有的类和对象可见。

比如说，从 Vehicle 对象里对 Navigator 的访问是被允许的，因为对象 Vehicle 包含在包 launch 中，而 launch 包在 bobsrockets 中，相反，所有在包 bobsrockets 之外的代码都不能访问类 Navigator。

作用域保护
Scala中，访问修饰符可以通过使用限定词强调。格式为:

private[x] 

或 

protected[x]
这里的x指代某个所属的包、类或单例对象。
如果写成private[x],读作"这个成员除了对[…]中的类或[…]中的包中的类及它们的伴生对像可见外，对其它所有类都是private。
```

```scala
package scala_learning

import scala_learning.bobsrockets.navigation._


object HelloWorld {
  def main(args: Array[String]): Unit = {
    //    var veh: HelloWorld = null // OK
    //    var veh: HelloWorld = new (HelloWorld) // OK
    var veh: HelloWorld = new HelloWorld // OK
    veh.a = 5
    println(veh, veh.a)
    //    var veh: Navigator = null # 无法访问

  }
}

package bobsrockets {
  package navigation {

    class HelloWorld {
      var a: Long = 0

      def main2(args: Array[String]): Unit = {
        println("Hello, world 111!")
      }
    }

    private[bobsrockets] class Navigator {
      protected[navigation] def useStarChart() {}

      class LegOfJourney {
        private[Navigator] val distance = 100
      }

      private[this] var speed = 200
    }

  }

  package launch {

    import scala_learning.bobsrockets.navigation._

    object Vehicle {
      private[launch] val l: Long = 5
      private[launch] val guide = new Navigator
    }

  }

}

```
#Scala 运算符

##关系运算符

```scala
package scala_learning

object HelloWorld {
  def main(args: Array[String]): Unit = {
    var a = 10;
    var b = 20;
    var c = 25;
    var d = 25;
    println("a + b = " + (a + b));
    println("a - b = " + (a - b));
    println("a * b = " + (a * b));
    println("b / a = " + (b / a));
    println("b % a = " + (b % a));
    println("c % a = " + (c % a));

  }
}

```
```scala
  def main(args: Array[String]) {
    var a = true;
    var b = false;

    println("a && b = " + (a && b));

    println("a || b = " + (a || b));

    println("!(a && b) = " + !(a && b));
  }
```

##赋值运算符
以下列出了 Scala 语言支持的赋值运算符:

运算符	描述	实例
=	简单的赋值运算，指定右边操作数赋值给左边的操作数。	C = A + B 将 A + B 的运算结果赋值给 C
+=	相加后再赋值，将左右两边的操作数相加后再赋值给左边的操作数。	C += A 相当于 C = C + A
-=	相减后再赋值，将左右两边的操作数相减后再赋值给左边的操作数。	C -= A 相当于 C = C - A
*=	相乘后再赋值，将左右两边的操作数相乘后再赋值给左边的操作数。	C *= A 相当于 C = C * A
/=	相除后再赋值，将左右两边的操作数相除后再赋值给左边的操作数。	C /= A 相当于 C = C / A
%=	求余后再赋值，将左右两边的操作数求余后再赋值给左边的操作数。	C %= A is equivalent to C = C % A
<<=	按位左移后再赋值	C <<= 2 相当于 C = C << 2
>>=	按位右移后再赋值	C >>= 2 相当于 C = C >> 2
&=	按位与运算后赋值	C &= 2 相当于 C = C & 2
^=	按位异或运算符后再赋值	C ^= 2 相当于 C = C ^ 2
|=	按位或运算后再赋值	C |= 2 相当于 C = C | 2
```scala
  def main(args: Array[String]) {
    var a = 60;
    /* 60 = 0011 1100 */
    var b = 13;
    /* 13 = 0000 1101 */
    var c = 0;

    c = a & b; /* 12 = 0000 1100 */
    println("a & b = " + c);

    c = a | b; /* 61 = 0011 1101 */
    println("a | b = " + c);

    c = a ^ b; /* 49 = 0011 0001 */
    println("a ^ b = " + c);

    c = ~a; /* -61 = 1100 0011 */
    println("~a = " + c);

    c = a << 2; /* 240 = 1111 0000 */
    println("a << 2 = " + c);

    c = a >> 2; /* 15 = 1111 */
    println("a >> 2  = " + c);

    c = a >>> 2; /* 15 = 0000 1111 */
    println("a >>> 2 = " + c);
  }
```

#Scala IF...ELSE 语句
```scala
  def main(args: Array[String]) {
    var x = 10;

    if (x < 20) {
      println("x < 20");
    }
  }

```

```scala

object HelloWorld {

  def main(args: Array[String]) {
    var x = 30;

    if (x < 20) {
      println("x 小于 20");
    } else {
      println("x 大于 20");
    }
  }

}
```
```scala
  def main(args: Array[String]) {
    var x = 30;

    if (x == 10) {
      println("X 的值为 10");
    } else if (x == 20) {
      println("X 的值为 20");
    } else if (x == 30) {
      println("X 的值为 30");
    } else {
      println("无法判断 X 的值");
    }
  }

```

#Scala 循环
循环类型	描述
while 循环	运行一系列语句，如果条件为true，会重复运行，直到条件变为false。
do...while 循环	类似 while 语句区别在于判断循环条件之前，先执行一次循环的代码块。
for 循环	用来重复执行一系列语句直到达成特定条件达成，一般通过在每次循环完成后增加计数器的值来实现。
```scala
  def main(args: Array[String]) {
    var a = 10;
    // 无限循环
    while (true) {
      println("a 的值为 : " + a);
    }
  }

```
#Scala 方法与函数
```scala
object HelloWorld {

  class Test {
    def m(x: Int) = x + 3

    val f = (x: Int) => x + 3
  }

  def main(args: Array[String]): Unit = {
    println(new (Test).f(3))
  }

}
```

def functionName ([参数列表]) : [return type]

```scala
object add {
  def addInt(a: Int, b: Int): Int = {
    var sum: Int = 0
    sum = a + b

    return sum
  }
}

//如果方法没有返回值，可以返回为 Unit，这个类似于 Java 的 void, 实例如下：
object HelloWorld {
  def main(args: Array[String]): Unit = {
    println(add.addInt(1, 2))
  }
```


```scala
package scala_learning


object HelloWorld {
  def main(args: Array[String]) {
    println("Returned Value : " + addInt(5, 7));
  }

  def addInt(a: Int, b: Int): Int = {
    var sum: Int = 0
    sum = a + b

    return sum
  }

}
```
#Scala 闭包
```scala
object HelloWorld {
  def main(args: Array[String]) {
    println("muliplier(1) value = " + multiplier(1))
    println("muliplier(2) value = " + multiplier(2))
  }

  var factor = 3
  val multiplier = (i: Int) => i * factor

}
```
#Scala 字符串
####StringBuilder
```scala
object HelloWorld {
  val greeting: String = "Hello,World!"
  var greeting2 = "Hello World!";

  def main(args: Array[String]) {
    println(greeting, greeting2)
  }

  //我们前面提到过 String 对象是不可变的，如果你需要创建一个可以修改的字符串，可以使用 String Builder 类，如下实例:
  val buf = new StringBuilder;
  buf += 'a'
  buf += 'x'
  buf ++= "abe"
  buf ++= "bcdef"
  println("buf is : " + buf.toString);

}
```

常量 字符串长度
```scala
object HelloWorld {
  def main(args: Array[String]) {
    val palindrome = "www.runoob.com";
    val len = palindrome.length();
    println("String Length is : " + len);
  }

}
```

```scala
  def main(args: Array[String]) {
    var str1 = "菜鸟教程官网：";
    var str2 = "www.runoob.com";
    var str3 = "菜鸟教程的 Slogan 为：";
    var str4 = "学的不仅是技术，更是梦想！";
    println(str1 + str2);
    println(str3.concat(str4));
  }

```
```scala
  def main(args: Array[String]) {
    var floatVar = 12.456
    var intVar = 2000
    var stringVar = "菜鸟教程!"
    var fs = printf("浮点型变量为 " +
      "%f, 整型变量为 %d, 字符串为 " +
      " %s", floatVar, intVar, stringVar)
    println(fs)
  }


```
#Scala 数组
```scala
  def main(args: Array[String]) {
    var z: Array[String] = new Array[String](3)
    var z2 = new Array[String](3)
    z(0) = "Runoob";
    z(1) = "Baidu";
    z(4 / 2) = "Google"
    var z3 = Array("Runoob", "Baidu", "Google")
    println(z, z2, z3)
  }
```
```scala
object HelloWorld {
  def main(args: Array[String]) {
    var myList = Array(1.9, 2.9, 3.4, 3.5)
     
    // 输出所有数组元素
    for (x <- myList) {
      println(x)
    }

    // 计算数组所有元素的总和
    var total = 0.0;
    for (i <- 0 to (myList.length - 1)) {
      total += myList(i);
    }
    println("总和为 " + total);

    // 查找数组中的最大元素
    var max = myList(0);
    for (i <- 1 to (myList.length - 1)) {
      if (myList(i) > max) max = myList(i);
    }
    println("最大值为 " + max);
  }
}
```
多维数组
```scala
object HelloWorld {
  def main(args: Array[String]) {
    val myMatrix = Array.ofDim[Int](3, 3)

    // 创建矩阵
    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        myMatrix(i)(j) = j;
      }
    }

    // 打印二维阵列
    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        print(" " + myMatrix(i)(j));
      }
      println();
    }

  }
}
```
合并数组

```scala
import scala.Array._

object HelloWorld {
  def main(args: Array[String]) {
    var myList1 = Array(1.9, 2.9, 3.4, 3.5)
    var myList2 = Array(8.9, 7.9, 0.4, 1.5)

    var myList3 = concat(myList1, myList2)

    // 输出所有数组元素
    for (x <- myList3) {
      println(x)
    }
  }
}
```
创建区间数组

```scala
package scala_learning

import scala.Array._

object HelloWorld {
  def main(args: Array[String]) {
    var myList1 = range(10, 20, 2)
    var myList2 = range(10, 20)

    // 输出所有数组元素
    for (x <- myList1) {
      print(" " + x)
    }
    println()
    for (x <- myList2) {
      print(" " + x)
    }
  }
}
```

#Scala Collection
```scala
object HelloWorld {
  def main(args: Array[String]) {
    //    // 定义整型 List
    //    val x = List(1,2,3,4)
    //
    // 定义 Set
    var xx = Set(1, 3, 5, 7)
    //
    //    // 定义 Map
    //    val x = Map("one" -> 1, "two" -> 2, "three" -> 3)

    // 创建两个不同类型元素的元组
    val x = (10, "Runoob")

    //    // 定义 Option
    //    val x:Option[Int] = Some(5)

    println(x._1, x._2)

    xx = xx.+(55) // 添加一个元素
    xx.+(5)
    for (i <- xx) {
      println(i)
    }
  }
}
```

#Scala Iterator（迭代器）
```text
Scala Iterator（迭代器）不是一个集合，它是一种用于访问集合的方法。
迭代器 it 的两个基本操作是 next 和 hasNext。
调用 it.next() 会返回迭代器的下一个元素，并且更新迭代器的状态。
调用 it.hasNext() 用于检测集合中是否还有元素。
让迭代器 it 逐个返回所有元素最简单的方法是使用 while 循环：

```
```scala
object HelloWorld {
  def main(args: Array[String]) {
    val it = Iterator("Baidu", "Google", "Runoob", "Taobao")

    while (it.hasNext) {
      println(it.next())
    }
  }
}
```

```scala
object HelloWorld {
  def main(args: Array[String]) {
    val ita = Iterator(20, 40, 2, 50, 69, 90)
    val itb = Iterator(20, 40, 2, 50, 69, 90)

    println("最大元素是：" + ita.max)
    println("最小元素是：" + itb.min)

  }
}
```
```scala
  def main(args: Array[String]) {
    val ita = Iterator(20, 40, 2, 50, 69, 90)
    val itb = Iterator(20, 40, 2, 50, 69, 90)

    println("ita.size 的值: " + ita.size)
    println("itb.length 的值: " + itb.length)

  }
```
#Scala 类和对象
```scala
package scala_learning

object HelloWorld {
  def main(args: Array[String]) {
    val pt = new Point(10, 20);
    // 移到一个新的位置
    pt.move(10, 10);
    println(pt.x)
  }
}

class Point(xc: Int, yc: Int) {
  var x: Int = xc
  var y: Int = yc

  def move(dx: Int, dy: Int) {
    x = x + dx
    y = y + dy
    println("x 的坐标点: " + x);
    println("y 的坐标点: " + y);
  }
}

```

Scala 继承

```text
Scala继承一个基类跟Java很相似, 但我们需要注意以下几点：

1、重写一个非抽象方法必须使用override修饰符。
2、只有主构造函数才可以往基类的构造函数里写参数。
3、在子类中重写超类的抽象方法时，你不需要使用override关键字。
继承会继承父类的所有属性和方法，Scala 只允许继承一个父类。


```

```scala
package scala_learning

object HelloWorld {
  def main(args: Array[String]) {
    val pt = new Location(10, 20, 30);
    // 移到一个新的位置
    pt.move(10, 10);
    pt.move(10, 10, 30);
    println(pt.x)
  }
}

class Point(xc: Int, yc: Int) {
  var x: Int = xc
  var y: Int = yc

  def move(dx: Int, dy: Int) {
    x = x + dx
    y = y + dy
    println("x 的坐标点: " + x);
    println("y 的坐标点: " + y);
  }
}

class Location(val xc: Int, val yc: Int,
               val zc: Int) extends Point(xc, yc) {
  var z: Int = zc

  def move(dx: Int, dy: Int, dz: Int) {
    x = x + dx
    y = y + dy
    z = z + dz
    println("x 的坐标点 : " + x);
    println("y 的坐标点 : " + y);
    println("z 的坐标点 : " + z);
  }
}

```
Scala重写一个非抽象方法，必须用override修饰符。
```scala
package scala_learning

object HelloWorld {
  def main(args: Array[String]) {

  }

  val fred = new Employee
  fred.name = "Fred"
  fred.salary = 50000
  println(fred)

}

class Person {
  var name = ""

  override def toString = getClass.getName + "[name=" + name + "]"
}

class Employee extends Person {
  var salary = 0.0

  override def toString = super.toString + "[salary=" + salary + "]"
}


```
Scala 单例对象

```scala
package scala_learning

class Point(val xc: Int, val yc: Int) {
  var x: Int = xc
  var y: Int = yc

  def move(dx: Int, dy: Int) {
    x = x + dx
    y = y + dy
  }
}

object HelloWorld {
  def main(args: Array[String]) {

  }

  val point = new Point(10, 20)
  printPoint

  def printPoint {
    println("x 的坐标点 : " + point.x);
    println("y 的坐标点 : " + point.y);
  }
}

```
伴生对象实例

```scala
package scala_learning

// 私有构造方法
class Marker(val color: String) {
  println("创建" + this)

  override def toString(): String = "颜色标记：" + color
}

// 伴生对象，与类名字相同，可以访问类的私有属性和方法
object HelloWorld {

  private val markers: Map[String, Marker] = Map(
    "red" -> new Marker("red"),
    "blue" -> new Marker("blue"),
    "green" -> new Marker("green")
  )

  def apply(color: String) = {
    if (markers.contains(color)) markers(color) else null
  }


  def getMarker(color: String) = {
    if (markers.contains(color)) markers(color) else null
  }

  def main(args: Array[String]) {
    println(new Marker("red"))
    println(getMarker("blue"))
    // 单例函数调用，省略了.(点)符号      println(new Marker getMarker "blue")
  }
}

```

#Scala Trait(特征)
```text
特征构造顺序
特征也可以有构造器，由字段的初始化和其他特征体中的语句构成。这些语句在任何混入该特征的对象在构造时都会被执行。

构造器的执行顺序：

调用超类的构造器；
特征构造器在超类构造器之后、类构造器之前执行；
特征由左到右被构造；
每个特征当中，父特征先被构造；
如果多个特征共有一个父特征，父特征不会被重复构造
所有特征被构造完毕，子类被构造。
构造器的顺序是类的线性化的反向。线性化是描述某个类型的所有超类型的一种技术规格。
```
```scala
package scala_learning

trait Equal {
  def isEqual(x: Any): Boolean

  def isNotEqual(x: Any): Boolean = !isEqual(x)
}

class Point(xc: Int, yc: Int) extends Equal {
  var x: Int = xc
  var y: Int = yc

  def isEqual(obj: Any) =
    obj.isInstanceOf[Point] &&
      obj.asInstanceOf[Point].x == x
}

object HelloWorld {
  def main(args: Array[String]) {
    val p1 = new Point(2, 3)
    val p2 = new Point(2, 4)
    val p3 = new Point(3, 3)

    println(p1.isNotEqual(p2))
    println(p1.isNotEqual(p3))
    println(p1.isNotEqual(2))
  }
}

```

#Scala 模式匹配
```text
每个备选项都包含了一个模式及一到多个表达式
match 表达式通过以代码编写的先后次序尝试每个模式来完成计算，只要发现有一个匹配的case，剩下的case不会继续匹配。


```
```scala
object HelloWorld {
  def main(args: Array[String]) {
    println(matchTest(3))
  }

  def matchTest(x: Int): String = x match {
    case 1 => "one"
    case 2 => "two"
    case _ => {
      println("hello ,man")
       "many"
    }  
  }

}
```

不同数据类型的模式匹配：
```scala
object HelloWorld {
  def main(args: Array[String]) {
    println(matchTest("two"))
    println(matchTest("test"))
    println(matchTest(1))
    println(matchTest(6))
  }

  def matchTest(x: Any): Any = x match {
    case 1 => "one"
    case "two" => 2
    case y: Int => "scala.Int"
    case _ => "many"
  }

}

```
使用样例类  case class
```text
使用了case关键字的类定义就是样例类(case classes)，样例类是种特殊的类，经过优化以用于模式匹配。
构造器的每个参数都成为val，除非显式被声明为var，但是并不推荐这么做；
在伴生对象中提供了apply方法，所以可以不使用new关键字就可构建对象；
提供unapply方法使模式匹配可以工作；
生成toString、equals、hashCode和copy方法，除非显示给出这些方法的定义。

```
```scala
object HelloWorld {
  def main(args: Array[String]) {
    val alice = Person("Alice", 25)
    val bob = Person("Bob", 32)
    val charlie = Person("Charlie", 32)

    for (person <- List(alice, bob, charlie)) {
      person match {
        case Person("Alice", 25) => println("Hi Alice!")
        case Person("Bob", 32) => println("Hi Bob!")
        case Person(name, age) =>
          println("Age: " + age + " year, name: " + name + "?")
      }
    }
  }

  // 样例类
  case class Person(name: String, age: Int)
}

```
#Scala 正则表达式

```scala
object HelloWorld {
  def main(args: Array[String]) {
    val pattern = "Scala".r
    val str = "aa Scala is Scalable and cool"
    val rst = pattern findFirstIn str
    println(pattern findFirstIn str)
    println(rst)
  }
}
```

```scala
import scala.util.matching.Regex

object HelloWorld {
  def main(args: Array[String]) {
    val pattern = new Regex("(S|s)cala") // 首字母可以是大写 S 或小写 s
    val str = "Scala is scalable and cool"
    println((pattern findAllIn str).mkString(",")) // 使用逗号 , 连接返回结果
  }
}
```

```scala
object HelloWorld {
  // 如果你需要将匹配的文本替换为指定的关键词，可以使用 replaceFirstIn( ) 方法来替换第一个匹配项，使用 replaceAllIn( ) 方法替换所有匹配项，实例如下:
  def main(args: Array[String]) {
    val pattern = "(S|s)cala".r
    val str = "Scala is scalable and cool"

    println(pattern replaceFirstIn(str, "Java"))
  }
}

```

```scala
import scala.util.matching.Regex

object HelloWorld {
  def main(args: Array[String]) {
    // 注意上表中的每个字符使用了两个反斜线。
    // 这是因为在 Java 和 Scala 中字符串中的反斜线是转义字符。
    // 所以如果你要输出 \，你需要在字符串中写成 \\ 来获取一个反斜线。查看以下实例：
    val pattern = new Regex("abl[ae]\\d+")
    val str = "ablaw is able1 and cool"

    println((pattern findAllIn str).mkString(","))
  }
}

```

#Scala 异常处理
```scala
import java.io.{FileNotFoundException, FileReader, IOException}
object HelloWorld {
  def main(args: Array[String]): Unit = {
    try {
      val f = new FileReader("input.txt")
    } catch {
      case ex: FileNotFoundException => {
        println("Missing file exception")
      }
      case ex: IOException => {
        println("IO Exception")
      }
    }
  }
}
```
finally 语句

```scala
import java.io.{FileNotFoundException, FileReader, IOException}
object HelloWorld {
  def main(args: Array[String]) {
    try {
      val f = new FileReader("input.txt")
    } catch {
      case ex: FileNotFoundException => {
        println("Missing file exception")
      }
      case ex: IOException => {
        println("IO Exception")
      }
    } finally {
      println("Exiting finally...")
    }
  }
}

```
#Scala 提取器(Extractor)

```scala
object HelloWorld {
  def main(args: Array[String]) {

    println("Apply 方法 : " + apply("Zara", "gmail.com"));
    println("Unapply 方法 : " + unapply("Zara@gmail.com"));
    println("Unapply 方法 : " + unapply("Zara Ali"));

  }

  // 注入方法 (可选)
  def apply(user: String, domain: String) = {
    user + "@" + domain
  }

  // 提取方法（必选）
  def unapply(str: String): Option[(String, String)] = {
    val parts = str split "@"
    if (parts.length == 2) {
      Some(parts(0), parts(1))
    } else {
      None
    }
  }

}

```

```scala
object HelloWorld {
  def main(args: Array[String]) {

    val x = HelloWorld(5)
    println(x)

    x match {
      case HelloWorld(num) => println(x + " 是 " + num + " 的两倍！")
      //unapply 被调用
      case _ => println("无法计算")
    }

  }

  def apply(x: Int) = x * 2

  def unapply(z: Int): Option[Int] = if (z % 2 == 0) Some(z / 2) else None

}
```

#Scala 文件 I/O

```scala
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
```

```scala
object HelloWorld {
  def main(args: Array[String]): Unit = {
    println(sum2)
    println(sum2(1, 3, 5))
    var f = sum2
  }

  def sum(a: Int, b: Int, c: Int) = a + b + c

  def sum3(a: Int, b: Int, c: Int): Int = {
    a + b + c
  }

  var sum2 = (a: Int, b: Int, c: Int) => {
    a + b + c
  }
}
```

```scala
package scala_learning

/**
 * 单词计数：将集合中出现的相同的单词，进行计数，取计数排名前二的结果
 */
object HelloWorld {
  def main(args: Array[String]): Unit = {
    val stringList = List("hello scala hbase", "hello scala", "hello")

    // 1.将每一个字符串转换成一个一个的单词
    val wordList: List[String] = stringList.flatMap(str => str.split(" "))
    println(wordList) // List(hello, scala, hbase, hello, scala, hello)

    // 2.将相同的单词放置在一起
    // 在map中，如果传进来什么就返回什么，不要用_省略
    val wordsMap: Map[String, List[String]] = wordList.groupBy(word => word)
    println(wordsMap) // Map(hello -> List(hello, hello, hello), scala -> List(scala, scala), hbase -> List(hbase))

    // 3.对相同的单词进行计数
    // (word, list) => (word, count)
    val wordCount: Map[String, Int] = wordsMap.map(tuple => (tuple._1, tuple._2.size))
    println(wordCount) // Map(hello -> 3, scala -> 2, hbase -> 1)

    // 4.对计数完成后的结果进行排序（降序）
    val sortList: List[(String, Int)] = wordCount.toList.sortWith((left, right) => left._2 > right._2)
    println(sortList) //List((hello,3), (scala,2), (hbase,1))

    // 5.对排名后的结果取前2名
    val resultList: List[(String, Int)] = sortList.take(2)
    println(resultList) // List((hello,3), (scala,2))

    // 以上所有代码可以简化成
    stringList.flatMap(_.split(" ")).groupBy(word => word).map(tuple => (tuple._1, tuple._2.size)).toList.sortBy(_._2).reverse.take(2).foreach(println(_))

    println("-----------------------------")

    // 复杂类型
    val tupleList = List(("Hello Scala World ", 3), ("Hello Scala", 2), ("Hello", 1))
    val stringList1: List[String] = tupleList.map(t => t._1 * t._2)
    println(stringList1) // List(Hello Scala World Hello Scala World Hello Scala World , Hello ScalaHello Scala, Hello)

    val words: List[String] = stringList1.flatMap(_.split(" "))
    println(words) //List(Hello, Scala, World, Hello, Scala, World, Hello, Scala, World, Hello, ScalaHello, Scala, Hello)

  }
}

```

```scala
object HelloWorld {
  def main(args: Array[String]): Unit = {
    val name = "Tom"
    val age = 36
    //System.currentTimeMillis()是一个方法需要{}
    println(s"${System.currentTimeMillis()} $name ’s age is $age")
  }
}
```
#惰性函数 lazy
```scala
object HelloWorld {
  def init(): String = {
    println("huangbo 666")
    "huangbo"
  }

  def main(args: Array[String]): Unit = {
    //  huangbo 666
    //  666
    //  huangbo
    val name = init() // 将执行init()函数，并打印huangbo 666
    println("666")
    println(name)

    //  666
    //  huangbo 666
    //  huangbo
    lazy val name2 = init() // 不会立即执行init()函数
    println("666")
    println(name2) // 将执行init()函数，并打印huangbo 666，然后继续打印huangbo
  }
}
```

#偏函数
```scala
object HelloWorld {
  def main(args: Array[String]): Unit = {
    var b = sum(1, _, 3)
    println(b(20))
    // 24
  }

  def sum(a: Int, b: Int, c: Int) = a + b + c
}
```






