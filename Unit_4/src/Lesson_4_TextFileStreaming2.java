import java.util.Scanner;
import java.io.*;

public class Lesson_4_TextFileStreaming2{
    public static void main(String[] args) throws IOException {
        Scanner inputFile = new Scanner(new File("names.txt"));
        PrintWriter outputFile = new PrintWriter(new FileWriter("employees.txt"));
        int index = 1;

        while(inputFile.hasNextLine()){
            outputFile.println(index + " " + inputFile.nextLine());
            index++;
        }

        inputFile.close();
        outputFile.close();
    }
}

