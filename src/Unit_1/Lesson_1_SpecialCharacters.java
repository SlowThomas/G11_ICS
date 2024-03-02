package Unit_1;

public class Lesson_1_SpecialCharacters {
    public static void main(String[] args){
        // Q1: How do I display the double quotation symbol in a sentence?
        // A1:  Use the escape character - backslash \
        //      When using the escape character, the computer is expecting
        //      something to come after the escape character
        //      In this case, to display the double quotation marks
        //      we use \"
        System.out.println("I \"LOVE\" computer science");

        // Q2: If the computer treats backslash \ as a special character
        //     Then how would I display a backslash symbol in a sentence?
        // A2: To display a single backslash symbol, you use \\
        System.out.println("This symbol \\ is a backslash symbol");

        // Potential Test Question
        // Determine what will be displayed if we execute the following command
        System.out.println("\\\\\"\"\\\\\\\"");
        // stdout: \\""\\\"

        // Q3: \n
        // A3: Skips a line
        System.out.println("I\nLove\nComputer\nScience");

        // Q4: \t
        // A4: TAB Spacing
        //     TAB spacing places the cursor to the next cursor position
        //     that is a multiple of 4
        System.out.println("ab\tc\td");
        System.out.println("abcde\tfghi\tjk\tl");
        // stdout: abcde   fghi    jk  l

        // Exercise:
        System.out.println("\"\ttt\nnnn\ttt\n\\\\nntt\"");
        // stdout: "   tt
        //         nnn tt
        //         \\nntt"
    }
}
