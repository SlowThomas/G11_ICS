// Goals for this program:
// 1) Read marksWithNames.txt and store all the data into
//    their respective arrays
// 2) Overwrite marksWithNames.txt so that it consists
//    the same data, but with formatting applied
//    as well as an extra "final grade" column

import java.io.*;
import java.util.Scanner;

public class Lesson_6_TextFileStreaming2 {

    public static void main(String[] args) throws IOException{
        // Variables
        String[] firstName = new String[24];
        String[] lastName = new String[24];
        double[] termMarks = new double[24];
        double[] examMarks = new double[24];
        Scanner inputFile = new Scanner(new File("marksWithNames.txt"));
        PrintWriter outputFile;

        // main Body

        // Part 1: Read the file and store all the data
        //         into the 4 arrays that we've created above

        // Skip over the first two lines
        // since they're useless
        inputFile.nextLine();
        inputFile.nextLine();

        for(int i = 0; i < 24; i++){
            firstName[i] = inputFile.next();
            lastName[i]  = inputFile.next();
            termMarks[i] = inputFile.nextDouble();
            examMarks[i] = inputFile.nextDouble();
        }

        inputFile.close();

        // Part 2: Overwrite the file marksWithNames.txt
        //         with proper formatting, as well as adding
        //         the 'final grade' column
        outputFile = new PrintWriter(new FileWriter("marksWithNames.txt"));

        outputFile.printf("%-15s%-15s%-15s%-15s%-15s%n", "First Name",
                                                  "Last Name",
                                                  "Term Marks",
                                                  "Exam Marks",
                                                  "Final Grade");
        for(int i = 0; i < 24; i++){
            outputFile.printf("%-15s%-15s%-15.1f%-15.1f%-15.1f%n", firstName[i],
                                                                   lastName[i],
                                                                   termMarks[i],
                                                                   examMarks[i],
                                                                   termMarks[i]*0.7 + examMarks[i]*0.3);
        }

        outputFile.close();
        System.out.println(":D");
    }
}
