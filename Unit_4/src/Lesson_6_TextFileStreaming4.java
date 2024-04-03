import java.util.Scanner;
import java.io.*;

public class Lesson_6_TextFileStreaming4 {
    public static void main(String[] args) throws IOException{
        // Variables
        Scanner inputFile = new Scanner(new File("marksWithNames.txt"));
        PrintWriter outputFile = new PrintWriter(new FileWriter("marksWithNames2.txt"));
        String firstName;
        String lastName;
        double term;
        double exam;

        // Print the header:
        outputFile.printf("%-15s%-15s%-15s%-15s%-15s%n", "First Name", "Last Name",
                          "Term Marks", "Final Exam", "Final Grade");

        while(inputFile.hasNextLine()){
            firstName = inputFile.next();
            lastName = inputFile.next();
            term = inputFile.nextDouble();
            exam = inputFile.nextDouble();
            outputFile.printf("%-15s%-15s%-15.1f%-15.1f%-15.1f%n", firstName, lastName,
                              term, exam, 0.7*term + 0.3*exam);
        }

        // Close Streamed Files
        inputFile.close();
        outputFile.close();
        System.out.println("Done! :D :D :D");
    }
}
