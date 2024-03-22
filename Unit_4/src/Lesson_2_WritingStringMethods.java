public class Lesson_2_WritingStringMethods {
    // Write a method called everyN which takes a String s
    // and an integer n. The method returns a new String
    // consisting of every nth character from the String s

    // everyN("abcdefgh", 2) -> "bdfh"
    // everyN("abcdefgh", 3) -> "cf"
    // everyN("abcdefgh", 4) -> "dh"

    public static String everyN(String s, int n){
        // Variable
        String stringBuilder = "";

        // everyN Body
        for(int i = 0; i < s.length(); i++){
            if((i+1) % n == 0){
                stringBuilder += s.charAt(i);
            }
        }
        return stringBuilder;
    }

    public static void main(String[] args){
        System.out.println(everyN("Markville", 3));
        System.out.println(everyN("Computer Science", 5));
    }
}
