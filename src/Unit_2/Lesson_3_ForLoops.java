package Unit_2;

public class Lesson_3_ForLoops {
    public static void main(String[] args){
        // for(______; ______; ______)
        // 1) LOCAL VARIABLE DECLARATION
        // 2) CONDITION
        // 3) ADJUSTMENT FACTOR

        // Question 1
        // Use a for loop to display the following sequence:
        // 9
        // 16
        // 23
        // 30
        // ...
        // 583
        System.out.println("--- Question 1 ---");
        for(int a = 9; a <= 583; a += 7){
            System.out.println(a);
        }

        // Note: The Global variable that is declared in the for loop
        //       will self erase from the computer memory once the for loop
        //       is broken
        //       Why do professionals prefer for loop over while loop?
        //       Since the local variable self erase from the memory
        //       This clears up any un-use variables, making the
        //       computer/program more memory efficient


        // Question 2
        System.out.println("\n\n--- Question 2 ---");

        // Display the following sequence:
        // -120
        // -107
        // -94
        // ...
        // 348
        for(int b = -120; b <= 348; b += 13){
            System.out.println(b);
        }


        // Question 3
        System.out.println("\n\n--- Question 3 ---");

        // Display the following sequence:
        // 94
        // 86
        // 78
        // ...
        // -298
        for(int c = 94; c >= -298; c -= 8){
            System.out.println(c);
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
        for(int d = 2; d <= 28697814; d *= 3){
            System.out.printf("%,d\n", d);
        }

        // Question 5
        System.out.println("\n\n--- Question 5 ---");

        // Display the following sequence:
        // -5
        // 20
        // -80
        // ...
        // -5242880
        for(int e = -5; e < 5242880; e *= -4){
            System.out.println(e);
        }


        // Question 6 (Challenge #2)
        System.out.println("\n\n--- Question 6 ---");

        // Display the following sequence:
        // 5 120
        // 8 116
        // 11 112
        // ...
        // 95 0
        for(int x = 5, y = 120; x <= 95; x += 3, y -= 4){
            System.out.println(x + " " + y);
        }

        // Note:
        // 1) You can declare more than one local variable (or none)
        // 2) You can have conditions that do NOT rely on local
        //    variables that were declared
        // 3) You can have more than one adjustment factor (or none)
        //    Moreover, the adjustment factor can be applied to
        //    global variables as well
    }
}
