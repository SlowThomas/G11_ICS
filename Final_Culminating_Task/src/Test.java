import algebra.*;

import javax.swing.*;
import java.awt.*;


public class Test{

    public static class Comp extends JPanel implements Runnable{
        public Comp(){
            setPreferredSize(new Dimension(100, 100));
            Thread t = new Thread(this);
            t.start();
        }

        public void run(){
            while(true){
                try{ Thread.sleep(50); }
                catch(Exception e) {}

                repaint();

                System.out.println(num);
            }
        }

        public int num = 0;
        public void paintComponent(Graphics g){
            num++;
        }
    }

    public static void main(String[] args){

        // Matrix tests
        Matrix mat = new Matrix(new float[][]{
                {1, 0, 0, 1},
                {0, 1, 0, -1},
                {0, 0, 1, 1},
                {0, 0, 0, 1}
        });
        System.out.println(mat.det());
        mat.inverse().print();

        int[] a1 = {1, 2, 3}; int[] a2 = {3, 2, 1};

        Thread[] pool = new Thread[4];
        for(int i = 0; i < 3; i++){
            int finalI = i;
            pool[i] = new Thread(){
                @Override
                public void run(){
                    a1[finalI] += a2[finalI];
                }
            };
            pool[i].start();
        }

        try{for(Thread t : pool) t.join();}
        catch(Exception e){}

        System.out.println(a1[0] + " " + a1[1] + " " + a1[2]);

/*
        mat = new Matrix(new float[][]{
                {-1, 0, 0},
                {0, -1, 0},
                {0, 0, -1}
        });
        System.out.println(mat.det());
        mat.inverse().print();
         */

        // Parser tests
        // System.out.println(Integer.ParseInt("")); // err
        // System.out.println("           1                  3".split("\\h+")[0].length());

        // array tests
        // System.out.println((new int[2])[0]); // 0
        // System.out.println("".split(" ")[0].isEmpty()); // true

        // Color tests
        // System.out.println((new Color(255)).getRGB());
    }
}