public class Lesson_4_Series {
    public static void main(String[] args){
        // Question 1
        // Display the solution to the following series using while loop:
        // 5 + 9 + 13 + ... + 489
        System.out.println("--- Question 1 ---");
        int a = 5;
        int acc1 = 0;
        while(a <= 489){
            acc1 += a;
            a += 4;
        }
        System.out.println(acc1);


        // Question 2
        // Display the solution to the following series using for loop:
        // 97 + 84 + 71 + ... - 423
        System.out.println("\n\n--- Question 2 ---");
        int acc2 = 0;
        for(int b = 97; b >= -423; b -= 13){
            acc2 += b;
        }
        System.out.println(acc2);


        // Question 3
        // Display the solution to the following series using for loop:
        // 2 + 6 + 18 + ... + 28 697 814
        System.out.println("\n\n--- Question 3 ---");
        int acc3 = 0;
        for(int c = 2; c <= 28697814; acc3 += c, c *= 3){}
        System.out.println(acc3);


        // Question 4
        // Display the solution to the following series using while loop:
        // -5 + 10 - 20 + 40 - ... + 167 772 160
        System.out.println("\n\n--- Question 4 ---");
        int acc4 = 0, d = -5;
        while(d != 167772160 * -2){
            acc4 += d;
            d *= -2;
        }
        System.out.println(acc4);


        // Question 5
        // Display the solution to the following using for loop:
        // 3 x 5 x 7 x ... x 19
        int acc5 = 1;
        System.out.println("\n\n--- Question 5 ---");
        for(int e = 3; e <= 19; e += 2){
            acc5 *= e;
        }
        System.out.println(acc5);

        // Overflow:
        int x = 2147483647;
        x += 1;
        System.out.printf("%d\n", x);

        long y = 2147483647; // 64 bit integer
        y += 1;
        System.out.printf("%d\n", y);
    }
}
