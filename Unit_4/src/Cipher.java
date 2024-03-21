
/*
* Cipher performs a simple encryption and decryption on a text file message.
*
* Example usage:
* --------------------------------------------------------------------------
* Encrypting or decrypting? ([encrypt]/decrypt)
* Input file name: poem.txt
* Output file name: poem.out
* Continue running the program? (y/[n])
* --------------------------------------------------------------------------
* */

// String methods used:
// charAt()
// equals()
// indexOf()
// isEmpty()
// length()
// operator+()
// substring()
// toLowerCase()
// toUpperCase()


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Cipher {
    public static void main(String[] args) throws IOException {
        // Substitution for step 2 of encryption
        final String original = "AEIJOPRSTVX ", substitution = "@=!?*#&$+^%_";

        // All variables used in the program:
        //
        // mode: determines whether to perform encryption or decryption
        //       0 - encrypt mode
        //       1 - decrypt mode
        //
        // input: general container for storing user input
        //
        // output: general container for storing file output
        int mode;
        String input, output;

        // All objects used in the program:
        //
        // inFile: the input file
        //
        // outFile: the output file
        //
        // fin: ifstream for the input file
        //
        // stdin: istream for the console
        //
        // fout: ofstream for the output file
        File inFile, outFile;
        Scanner fin, stdin;
        PrintWriter fout;

        // Initialize console istream
        stdin = new Scanner(System.in);

        // Main loop
        while(true){

            // Prompts ----------------------------------------------------------------------

            // Let user choose to either perform encryption or decryption
            // Default option is encryption
            // Repeats prompting until a valid input is received
            do {
                System.out.print("Encrypting or decrypting? ([encrypt]/decrypt) ");
                input = stdin.nextLine();
            }while(!(input.equals("encrypt") || input.equals("decrypt") || input.isEmpty()));
            if(input.equals("decrypt"))
                mode = 1; // decrypt mode
            else
                mode = 0; // encrypt mode

            // Let user choose the file as the input file
            // Repeats prompting until an existing file name is received
            do {
                do {
                    System.out.print("Input file name: ");
                    input = stdin.nextLine();
                } while (input.isEmpty());
                // Only attempt to create a File object if input is not empty
                inFile = new File(input);
            }while(!inFile.exists());

            // Let user choose the file as the output file
            // Repeats prompting until a name is given
            do {
                System.out.print("Output file name: ");
                input = stdin.nextLine();
            }while(input.isEmpty());
            // Only attempt to create a File object if input is not empty
            outFile = new File(input);

            // Initialize the fstreams
            fin = new Scanner(inFile);
            fout = new PrintWriter(outFile);




            // Processing -------------------------------------------------------------------

            // Encryption mode
            if(mode == 0){
                // Load each individual line of the input file
                while(fin.hasNextLine()){
                    // Use the current input line as template
                    output = fin.nextLine();

                    // 1. Change all letters to uppercase.
                    output = output.toUpperCase();

                    // 2. Perform the character substitutions.
                    for(int i = 0; i < output.length(); i++){
                        if(original.indexOf(output.charAt(i)) != -1){
                            output = output.substring(0, i)
                                    + substitution.charAt(original.indexOf(output.charAt(i)))
                                    + output.substring(i + 1);
                        }
                    }

                    // 3. Move the first half of the string to be the last half.
                    // (For lines of odd length, the line must be divided such that
                    // the first half being moved contains one more character than
                    // the last half.)
                    output = output.substring(output.length() / 2 + output.length() % 2)
                            + output.substring(0, output.length() / 2 + output.length() % 2);

                    // 4. Swap the first two characters with the last two.
                    output = output.substring(output.length() - 2)
                            + output.substring(2, output.length() - 2)
                            + output.substring(0, 2);

                    // 5. Swap the two characters immediately to the left of the middle
                    // of the string with the two characters that immediately follow them.
                    output = output.substring(0, output.length() / 2 + output.length() % 2 - 2)
                            + output.substring(output.length() / 2 + output.length() % 2, output.length() / 2 + output.length() % 2 + 2)
                            + output.substring(output.length() / 2 + output.length() % 2 - 2, output.length() / 2 + output.length() % 2)
                            + output.substring(output.length() / 2 + output.length() % 2 + 2);

                    // 6. Swap every two letters.
                    for(int i = 1; i < output.length(); i += 2){
                        output = output.substring(0, i - 1)
                                + output.charAt(i)
                                + output.charAt(i - 1)
                                + output.substring(i + 1);
                    }

                    // Write the encrypted line into the output file
                    fout.println(output);
                }
            }

            // Decryption mode
            else{
                while(fin.hasNextLine()){
                    // Use the current input line as template
                    output = fin.nextLine();

                    // 6. Swap every two letters.
                    for(int i = 1; i < output.length(); i += 2){
                        output = output.substring(0, i - 1)
                                + output.charAt(i)
                                + output.charAt(i - 1)
                                + output.substring(i + 1);
                    }

                    // 5. Swap the two characters immediately to the left of the middle
                    // of the string with the two characters that immediately follow them.
                    output = output.substring(0, output.length() / 2 + output.length() % 2 - 2)
                            + output.substring(output.length() / 2 + output.length() % 2, output.length() / 2 + output.length() % 2 + 2)
                            + output.substring(output.length() / 2 + output.length() % 2 - 2, output.length() / 2 + output.length() % 2)
                            + output.substring(output.length() / 2 + output.length() % 2 + 2);

                    // 4. Swap the first two characters with the last two.
                    output = output.substring(output.length() - 2)
                            + output.substring(2, output.length() - 2)
                            + output.substring(0, 2);

                    // 3. Move the first half of the string to be the last half.
                    // (For lines of odd length, the line must be divided such that
                    // the first half being moved contains one more character than the last half.)
                    output = output.substring(output.length() / 2)
                            + output.substring(0, output.length() / 2);

                    // 2. Perform the character substitutions.
                    for(int i = 0; i < output.length(); i++){
                        if(substitution.indexOf(output.charAt(i)) != -1){
                            output = output.substring(0, i)
                                    + original.charAt(substitution.indexOf(output.charAt(i)))
                                    + output.substring(i + 1);
                        }
                    }

                    // 1. Change all letters to lowercase.
                    output = output.toLowerCase();

                    // Write the decrypted line into the output file
                    fout.println(output);
                }
            }
            // Save the changes to the files
            fin.close();
            fout.close();




            // Iteration Control ------------------------------------------------------------

            // Let user choose whether to keep running
            // y for yes, n for no
            // Default is n
            // Repeats prompting until a valid input is received
            do{
                System.out.print("Continue running the program? (y/[n]) ");
                input = stdin.nextLine();
            } while(!(input.equals("y") || input.equals("n") || input.isEmpty()));
            if(!input.equals("y"))
                break;

            // add a line break if user chooses to continue running the program
            System.out.println();
        }
    }
}
