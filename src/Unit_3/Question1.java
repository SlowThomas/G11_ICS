package Unit_3;

public class Question1 {

    // header
    // public - access modifier/visibility
    // static - static/non-static
    // rest - signature
    //      1) return data type
    //      2) name of method
    //      3) parameter(s)
    public static int m(int a, int b){
        int c = a; // local variable
        b -= a;
        a += c;
        return a + b + c;
    }

    public static void main(String[] args){
        int d = 5;
        int e = 6;
        e = m(d, e);
        System.out.println(4+m(e, d));
    }
}
