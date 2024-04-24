public class Lesson_3_ArrayMethods2 {

    // Question 1:
    // Write a method called genArr which takes an integer parameter, n
    // The method returns an integer array that ranges from 1 to n

    // genArr(4) -> {1, 2, 3, 4}
    // genArr(2) -> {1, 2}
    // genArr(0) -> {}
    // genArr(-3) -> {}

    public static int[] genArr(int n){
        // Variables
        int[] array;

        // genArr Body
        // Idea 1: n = Math.max(0, n);
        // Idea 2:
        if(n < 0) n = 0;
        array = new int[n];
        for(int i = 0; i < n; i++){
            array[i] = i + 1;
        }
        return array;
    }

    // Question 2:
    // Write a method called genArr which takes two integer parameters, start and end
    // The method returns an array that ranges from start to end

    // genArr(5, 8) -> {5, 6, 7, 8}
    // genArr(-2, 0) -> {-2, -1, 0}
    // genArr(4, 4) -> {4}
    // genArr(5, 2) -> {}

    public static int[] genArr(int start, int end){
        // Variables
        int[] array = new int[end - start + 1];
        // genArr Body
        if(start > end) start = end + 1;
        array = new int[end - start + 1];
        for(int i = 0; i < array.length; i++){
            array[i] = start+i;
        }
        return array;
    }

    public static void main(String[] args){
        int[] numbers = genArr(5, 8);

        for(int i = 0; i < numbers.length; i++){
            System.out.println(numbers[i]);
        }
    }
}
