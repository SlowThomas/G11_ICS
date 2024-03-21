public class Lesson_2_MethodOverloading2 {
    public static int random(int x, int y){
        if(x > y) return x+y;
        return x-y;
    }

    public static double random(double x, double y){
        if(x > y) return x*y;
        return 2*x;
    }

    public static double random(int x, double y){
        if(x > y) return 2*y;
        return x;
    }

    // You cannot have two methods that share the same
    // 1) number of parameters &&
    // 2) same parameter types
    // as this will create a conflict when the method is being called
    /*
    public static double random(int a, int b){
        if(a == b) return 0.0;
        return b/a;
    }
    */

    public static void main(String[] args){
        System.out.println(random(5, 2));
        System.out.println(random(2, 4));
        System.out.println(random(4, 5.5));
    }
}
