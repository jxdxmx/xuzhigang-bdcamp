package bdcamp.modulex;

import java.util.StringTokenizer;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!"); // hello world
        StringTokenizer itr = new StringTokenizer("hello,world");
        while (itr.hasMoreTokens()) {
            System.out.println(itr.nextToken());
        }

    }
}
