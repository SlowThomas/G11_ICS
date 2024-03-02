package Unit_1;

// In this program we'll ask the user to continuously enter
// POSITIVE INTEGERs
// If the user enters a non-positive integer, the program
// will display the sum of all the positive integers that were entered

// e.g.
// Enter a positive integer: 9
// Enter a positive integer: 5
// Enter a positive integer: 3
// Enter a positive integer: -2
// Sum: 17

import java.util.Scanner;

public class Lesson_6_Scanner2 {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int number;
        int sum = 0;

        System.out.print("Enter a positive integer: ");
        number = s.nextInt();

        while(number > 0){
            sum += number;
            System.out.print("Enter a positive integer: ");
            number = s.nextInt();
        }

        System.out.printf("Sum: %d", sum);
    }
}

// Note:
// s.nextLine    <- Obtains a string input
// s.nextInt     <- Obtains an integer input
// s.nextDouble  <- Obtains a double input
// s.nextBoolean <- Obtains a Boolean input
// s.next()      <- ** Obtains a string input up to the first space