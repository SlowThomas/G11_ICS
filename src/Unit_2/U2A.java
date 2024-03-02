package Unit_2;
/*
* Name: Thomas Xu
* Date: Feb 13, 2024
*
* Description:
* A simple number guessing game.
* The user has 10 chances to guess a secret number that is between 1 and 999.
* The secret number is hard-coded in one of the variables declared.
*
* */
import java.util.Scanner;


public class U2A {
    static Scanner stdin = new Scanner(System.in);
    public static void main(String[] args){
        int secret = 42, inputNum;
        System.out.println("You have 10 chances to guess a secret number that is between 1 and 999.\nPlease make a guess");
        for(int i = 9; i >= 0; i--){
            inputNum = stdin.nextInt();
            if(inputNum < 1 || inputNum > 999) {
                System.out.println("Invalid Input! Ensure your number is between 1 and 999!");
                i++;
            }
            else{
                if(i > 0) {
                    if (inputNum > secret)
                        System.out.printf("Choose a smaller number! You have %d guesses left.\n", i);
                    else if (inputNum < secret)
                        System.out.printf("Choose a larger number! You have %d guesses left.\n", i);
                }
                if(inputNum == secret){
                    System.out.println("Congratulations! That is the secret number!\n");
                    return;
                }
            }
        }
        System.out.println("Too bad! Try harder next time!");
    }
}
