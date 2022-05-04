package com.bobobode.kukuruza.sorting;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class IntArraySorterTest {

    @Test
    void insertionSort() {
        int[] actual = randomIntArray();
        int[] expected = sortedClone(actual);

        IntArraySorter.insertionSort(actual);

        assertArrayEquals(expected, actual);
    }

    @Test
    void bubbleSort() {
        int[] actual = randomIntArray();
        int[] expected = sortedClone(actual);

        IntArraySorter.bubbleSort(actual);

        assertArrayEquals(expected, actual);
    }

    @Test
    void mergeSort() {
        int[] actual = randomIntArray();
        int[] expected = sortedClone(actual);

        IntArraySorter.mergeSort(actual);

        assertArrayEquals(expected, actual);
    }

    @Test
    @Disabled("optional test for comparing performance of sorting algorithms")
    void performanceComparing() {
        int[] intsForInsertionSort = randomIntArray(100_000, -100_000, 100_000);
        int[] intsForBubbleSort = intsForInsertionSort.clone();
        int[] intsForMergeSort = intsForInsertionSort.clone();
        int[] intsForJavaSort = intsForInsertionSort.clone();

        long insertionSortDuration = durationMillis(() -> IntArraySorter.insertionSort(intsForInsertionSort));
        long bubbleSortDuration = durationMillis(() -> IntArraySorter.bubbleSort(intsForBubbleSort));
        long mergeSortDuration = durationMillis(() -> IntArraySorter.mergeSort(intsForMergeSort));
        long javaSortDuration = durationMillis(() -> Arrays.sort(intsForJavaSort));

        System.out.printf("Java sort duration: %d millis%n" +
                        "Insertion sort duration: %d millis%n" +
                        "Bubble sort duration: %d  millis%n" +
                        "Merge sort duration: %d millis",
                javaSortDuration, insertionSortDuration, bubbleSortDuration, mergeSortDuration);

        assertArrayEquals(intsForJavaSort, intsForInsertionSort);
        assertArrayEquals(intsForJavaSort, intsForBubbleSort);
        assertArrayEquals(intsForJavaSort, intsForMergeSort);
    }

    private long durationMillis(Runnable runnable) {
        Instant start = Instant.now();
        runnable.run();
        Instant end = Instant.now();
        return Duration.between(start, end).toMillis();
    }

    private int[] sortedClone(int[] ints) {
        int[] clone = ints.clone();
        Arrays.sort(clone);
        return clone;
    }

    private int[] randomIntArray() {
        return randomIntArray(20);
    }

    private int[] randomIntArray(int size) {
        return randomIntArray(size, -10, 10);
    }

    private int[] randomIntArray(int size, int minVal, int maxVal) {
        return Stream.generate(() -> ThreadLocalRandom.current().nextInt(minVal, maxVal))
                .limit(size)
                .mapToInt(Integer::intValue)
                .toArray();
    }

}
