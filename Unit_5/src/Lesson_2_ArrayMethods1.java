// Array Methods
// Array Parameter(s) -> Primitive Data

public class Lesson_2_ArrayMethods1 {
    // Question 1
    // Write a method called totalChars which takes a String array, words
    // The method returns the total number of characters in the array words

    // totalChars({"Hello", "World", "!"}) -> 11
    // totalChars({"ICS3U"}) -> 5
    // totalChars({}) -> 0

    public static int totalChars(String[] words){
        int sum = 0;
        for(int i = 0; i < words.length; i++){
            sum += words[i].length();
        }
        return sum;
    }

    // Question 2
    // Write a method called average which takes a NON-EMPTY integer array, marks
    // The method returns the average of all the elements in marks

    // average({3, 5, 8, 9}) -> 6.25
    // average({4, 5, 9, 3, 2}) -> 4.6

    public static double average(int[] marks){
        double sum = 0;
        for(int i = 0; i < marks.length; i++){
            sum += marks[i];
        }
        return sum / marks.length;
    }

    public static void main(String args[]){
        String[] names = {"Andrew", "Alec", "Saathana", "Victor"};
        String[] emptyStringArray = {};
        int[] numbers = {5, 2, 9, 3, 4, 6, 7};
        System.out.println(totalChars(names));
        System.out.println(totalChars(emptyStringArray));
        System.out.println(average(numbers));

    }
}
