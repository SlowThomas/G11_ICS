public class Lesson_4_ArrayMethods3 {

    // Write a method called plusOne which takes an integer array, numbers
    // The method will return an integer array which shares the same size as numbers
    // with all the elements increased by one

    // plusOne({4, 5, 6, 2}) -> {5, 6, 7, 3}

    public static int[] plusOne(int[] numbers){
        // Variable
        int[] array = new int[numbers.length];

        // plusOne Body
        for(int i = 0; i < array.length; i++){
            array[i] = numbers[i] + 1;
        }

        return array;
    }

    // Write a method called extendArray which takes an integer
    // array and number, intArr and n
    // The method returns a new integer array with the element 'n'
    // added to the back of 'intArr'

    // extendArray({1, 4, 5, 9}, 2) -> {1, 4, 5, 9, 2}
    // extendArray({}, 4) -> {4}

    public static int[] extendArray(int[] intArr, int n){
        // Variables

        // Step 1) Declare a new array whose size is one more than
        //         the original array
        int[] newArr = new int[intArr.length + 1];

        // extendArray Body

        // Step 2) Copy the data from the original array to the new array
        for(int i = 0; i < intArr.length; i++){
            newArr[i] = intArr[i];
        }

        // Step 3) Replace the last element of the new array with the number 'n'
        newArr[newArr.length - 1] = n;

        // Step 4)
        return newArr;
    }

    public static void main(String[] args){
        int[] ia1 = {7, 6, 4, 3};
        ia1 = extendArray(ia1, 9);

        for(int i = 0; i < ia1.length; i++){
            System.out.println(ia1[i]);
        }
    }
}
