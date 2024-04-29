import java.util.Scanner;
import java.io.*;

public class Lesson_5_TextFileStreaming1 {
    // Class Variable
    public static int[] highscore = new int[3];

    // overwriteHighscore method
    // Write a method called overwriteHighscore that takes an integer, score
    // The method will overwrite:
    // 1) the class variable 'highscore', if needed
    // 2) the textfile 'highscore.txt'
    // Note: This method DOES NOT return any data
    public static void overwriteHighscore(int score) throws IOException{

        // Updating the 'highscore' array
        if(score > highscore[0]) { // If your score beats all three highscores
            highscore[2] = highscore[1];
            highscore[1] = highscore[0];
            highscore[0] = score;
        }
        else if(score > highscore[1]){
            highscore[2] = highscore[1];
            highscore[1] = score;
        }
        else if(score > highscore[2]){
            highscore[2] = score;
        }

        // Updating the 'highscore.txt' text file
        PrintWriter outputFile = new PrintWriter(new FileWriter("highscore.txt"));
        outputFile.println(highscore[0]);
        outputFile.println(highscore[1]);
        outputFile.println(highscore[2]);

        outputFile.close();
    }

    public static void main(String[] args) throws IOException{
        // Data Importation
        Scanner inputFile = new Scanner(new File("highscore.txt"));

        // Variables
        Scanner s = new Scanner(System.in);
        String userinput;
        int score;

        // main Body
        highscore[0] = inputFile.nextInt();
        highscore[1] = inputFile.nextInt();
        highscore[2] = inputFile.nextInt();
        inputFile.close();

        System.out.print("Play? (y/n) ");
        userinput = s.next();
        while(!userinput.equals("n")){
            System.out.print("Score: ");
            score = s.nextInt();
            overwriteHighscore(score);
            System.out.printf("Highscores: %d %d %d%n%n", highscore[0],
                                                          highscore[1],
                                                          highscore[2]);

            System.out.print("Play? (y/n) ");
            userinput = s.next();
        }
        System.out.println("Thank you for playing! See you next time!");
    }
}
