// In CodeHS, user input is done through a file called ConsoleProgram.java
// This file can be downloaded online (if you wish)

// However, ConsoleProgram.java actually utilizes something more primitive, known
// as Scanner. In this lesson, we're going to look at how to create a Scanner
// to obtain user input

// To utilize the Scanner, first of all you'd need to import into your program
import java.util.Scanner;

public class Lesson_5_Scanner1 {

    // Make a program that asks the user for a name (String)
    // The program will then display a sentence, using that name within
    // the sentence somewhere

    public static void main(String[] args){
        // Importing the scanner (on line 10) is NOT the same
        // as creating the scanner. You'd still need to
        // create a scanner after you import it
        Scanner s = new Scanner(System.in);
        String name;

        System.out.print("What is your name? ");
        name = s.nextLine();
        System.out.printf("Hello %s! Today is a nice day!", name);
    }
}
