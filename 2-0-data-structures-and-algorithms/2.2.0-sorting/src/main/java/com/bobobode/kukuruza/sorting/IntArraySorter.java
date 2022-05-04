package com.bobobode.kukuruza.sorting;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IntArraySorter {

    public static void insertionSort(int[] ints) {
        for (int i = 0; i < ints.length; i++) {
            for (int j = i; j > 0 && ints[j - 1] > ints[j]; j--) {
                swap(ints, j, j - 1);
            }
        }
    }

    public static void bubbleSort(int[] ints) {
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints.length - i - 1; j++) {
                if (ints[j] > ints[j + 1]) {
                    swap(ints, j, j + 1);
                }
            }
        }
    }

    public static void mergeSort(int[] ints) {
        mergeSort(ints, 0, ints.length == 0 ? 0 : ints.length - 1);
    }

    private static void mergeSort(int[] ints, int left, int right) {
        if (left != right) {
            int middle = left + ((right - left) / 2);
            mergeSort(ints, left, middle);
            mergeSort(ints, middle + 1, right);

            // merging left sorted part of the array with the right sorted part
            for (int i = left, j = middle + 1; i < j && j <= right; i++) {
                if (ints[i] > ints[j]) {
                    shiftRightCircular(ints, i, j);
                    j++;
                }
            }
        }
    }

    private static void shiftRightCircular(int[] ints, int from, int to) {
        int last = ints[to];
        for (int i = to - 1; i >= from; i--) {
            ints[i + 1] = ints[i];
        }
        ints[from] = last;
    }

    private static void swap(int[] integers, int i, int j) {
        int temp = integers[i];
        integers[i] = integers[j];
        integers[j] = temp;
    }

}
