package org.example.JavaCore.Array;

public class Array {
    public static void main(String[] args) {

        //region Array
        int[] numbers = new int[5];
        show(numbers);
        numbers[3] = 1;
        System.out.println("After add:");
        show(numbers);
        delete(3, numbers);
        System.out.println("After deletion:");
        show(numbers);
        //endregion

    }

    static void delete(int idx, int[] arr) {
        if (idx < 0 || idx >= arr.length) {
            System.out.println("Index out of bounds");
            return;
        }
        for (int i = idx; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[arr.length - 1] = 0;
    }

    static int[] show(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println("Element at index " + i + ": " + arr[i]);
        }
        return arr;
    }



//    ArrayList<>
}
