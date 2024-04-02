import java.util.Scanner;
import java.io.*;

public class Lesson_5_TextFileStreaming3 {

    // Goal:
    // Duplicate all the data from "grades.txt" to "grades2.txt"
    // with the addition of the following line attached at the
    // bottom of "grades2.txt":
    // Average: ______%

    public static void main(String[] args) throws IOException {
        // Variables
        Scanner inputFile = new Scanner(new File("grades.txt"));
        PrintWriter outputFile = new PrintWriter(new FileWriter("grades2.txt"));
        double currentEntry;
        double sumEntries = 0;
        int noOfEntries = 0;

        while(inputFile.hasNextLine()){
            currentEntry = inputFile.nextDouble();
            outputFile.println(currentEntry);
            sumEntries += currentEntry;
            noOfEntries++;
        }

        outputFile.printf("Average: %.1f%%", sumEntries / noOfEntries);

        inputFile.close();
        outputFile.close();
    }
}
