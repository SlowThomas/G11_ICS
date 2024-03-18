package Unit_3;

public class Program_Tracing_1 {
    public static int m(int a, int b){
        int c = 0;
        for( ;a <= b; c+=a){
            a+=c;
            b+=4;
        }
        return b;
    }

    public static int n(int b, int a){
        int c = 0;
        while(b <= a){
            c += 1;
            b += c;
        }
        return c;
    }

    public static void main(String[] args){
        int a = 4;
        int b = 8;
        a += m(a, b);
        System.out.print(50 - n(b, a-15));
    }
}
