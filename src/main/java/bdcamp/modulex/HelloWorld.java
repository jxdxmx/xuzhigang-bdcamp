package bdcamp.modulex;

import java.util.StringTokenizer;

public class HelloWorld {
    public static void main(String[] args) {

        StringTokenizer itr = new StringTokenizer("1363157985066 \t13726230503\t00-FD-07-A4-72-B8:CMCC\t120.196.100.82\ti02.c.aliimg.com\t\t24\t27\t2481\t24681\t200\n");
        while (itr.hasMoreTokens()) {
            System.out.println(itr.nextToken());
        }
        String str = "1363157985066 \t13726230503\t00-FD-07-A4-72-B8:CMCC\t120.196.100.82\ti02.c.aliimg.com\t\t24\t27\t2481\t24681\t200\n";
        str = "1363157995052 \t13826544101\t5C-0E-8B-C7-F1-E0:CMCC\t120.197.40.4\t\t\t4\t0\t264\t0\t200";
        String[] a = str.split("[ \t]");
        System.out.println(a);

//        System.out.println("Hello World!"); // hello world
//        StringTokenizer itr = new StringTokenizer("Hello,How are you");
//        while (itr.hasMoreTokens()) {
//            System.out.println(itr.nextToken());
//        }
//
//        // line = line.replaceAll(pattern, "");
//        System.out.println("test : " + "Hello ,world".replaceAll(".", ""));
    }
}
