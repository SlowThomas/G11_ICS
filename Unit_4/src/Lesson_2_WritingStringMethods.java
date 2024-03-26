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
        System.out.println(addHyphens("Markville"));
        System.out.println(remove("Computer", 'r'));
        System.out.println(replace("School", 'o', '@'));
    }

    // Question 1
    // Write a method addHyphens that takes a String str and returns
    // a new String with hyphens (-) added in between each character
    // of the String
    // addHyphens("Markville") -> "M-a-r-k-v-i-l-l-e"
    // addHyphens("Comp Sci")  -> "C-o-m-p- -S-c-i"

    public static String addHyphens(String str){
        String sb = "";
        for(int i = 0; i < str.length() - 1; i++){
            sb += str.charAt(i) + "-";
        }
        return sb + str.charAt(str.length() - 1);
    }

    // Question 2
    // Write a method called remove which takes a String str and a character ch
    // The method returns the string with all instances of ch removed
    // remove("Computer", 'r') -> "Compute"
    // remove("Banana", 'a') -> "Bnn"
    public static String remove(String str, char ch){
        String sb = "";
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) != ch){
                sb += str.charAt(i);
            }
        }
        return sb;
    }

    // Question 3
    // Write a method replace which takes a String str and two characters c1 and c2
    // The method returns a copy of str where all instances of c1 replaced by c2
    // replace("Markville", 'l', 'L') -> "MarkviLLe"
    // replace("School", 'o', '@') -> "Sch@@l"
    public static String replace(String str, char c1, char c2){
        String sb = "";
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == c1){
                sb += c2;
            }
            else{
                sb += str.charAt(i);
            }
        }
        return sb;
    }
}
