// In this program we will be:
// Streaming the 4 data from the text file myData.txt
// We will then print out a statement that relates to the 4 data

import java.util.Scanner;
import java.io.*; //***
public class Lesson_3_TextFileStreaming1 {
    public static void main(String[] args) throws IOException { // ***
        String name;
        int grade;
        double marks;
        String hobby;

        Scanner inputFile = new Scanner(new File("myData.txt"));

        name = inputFile.nextLine();
        grade = inputFile.nextInt();
        marks = inputFile.nextDouble();
        hobby = inputFile.nextLine();

        System.out.printf("Hello! I am %s! I am a grade %d " +
                "student with a %.2f%% at Markville", name, grade, marks);
    }
}
