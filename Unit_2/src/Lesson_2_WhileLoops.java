public class Lesson_2_WhileLoops {

    public static void main(String[] args){
        // Question 1
        System.out.println("--- Question 1 ---");

        // Display the following sequence:
        // 9
        // 16
        // 23
        // 30
        // ...
        // 583

        int a = 9;
        while(a <= 583){
            System.out.println(a);
            a += 7;
        }

        // What is the final value of 'a', after we break the loop?
        // 590

        // Question 2
        System.out.println("\n\n --- Question 2 ---");

        // Display the following sequence:
        // -120
        // -107
        // -94
        // ...
        // 348

        int b = -120;
        while(b <= 348){
            System.out.println(b);
            b += 13;
        }


        // Question 3
        System.out.println("\n\n --- Question 3 ---");

        // Display the following sequence:
        // 94
        // 86
        // 78
        // ...
        // -298

        int c = 94;
        while(c >= -298){
            System.out.println(c);
            c -= 8;
        }

        // Question 4
        System.out.println("\n\n--- Question 4 ---");

        // Display the following sequence:
        // 2
        // 6
        // 18
        // 54
        // ...
        // 28 697 814
        int d = 2;
        while(d <= 28697814){
            System.out.println(d);
            d *= 3;
        }


        // Question 5 (Challenge)
        System.out.println("\n\n--- Question 5 ---");

        // Display the following sequence:
        // -5
        // 20
        // -80
        // ...
        // -5242880
        int e = -5;
        // acceptable answers:
        // while(e != -5242880 * -4)
        // while(e < 5242880)
        while(e < 5242880){
            System.out.println(e);
            e *= -4;
        }
    }
}
