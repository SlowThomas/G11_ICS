import algebra.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.*;

public class Test{

    public static class A implements Runnable{
        public String str = "hello";
        public void f(String t){
            str = t;
        }

        public void run(){
            System.out.println(str);
        }
    }

    public static void main(String[] args) throws IOException{
        A a = new A();
        A b = new A();
        a.f("a"); b.f("b");
        Thread t = new Thread(a);
        t.start();
        t = new Thread(b);
        t.start();
    }
}
