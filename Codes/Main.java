import java.util.Random;
import java.util.Arrays;

public class Main {

    // Method to generate a random Integer array
    public static Integer[] generateRandomArray(int size) {
        Random random = new Random();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100000);  // Random integers between 0 and 100000
        }
        return array;
    }

    // Generic method to measure sorting time
    public static <T extends Comparable<T>> long measureSortTime(Sorter<T> sorter, T[] array) {
        long startTime = System.nanoTime();
        sorter.sort(array);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        // Define array sizes
        int[] sizes = {1000, 10000, 100000};
        int repetitions = 20;

        // Create sorting algorithm instances
        Sorter<Integer>[] algorithms = new Sorter[]{
                new BubbleSortUntilNoChange<>(),
                new BubbleSortWhileNeeded<>(),
                new QuickSortGPT<>(),
                new SelectionSortGPT<>()
        };

        // Run the experiment
        for (int size : sizes) {
            System.out.println("Array size: " + size);
            for (Sorter<Integer> algorithm : algorithms) {
                long totalTime = 0;
                for (int i = 0; i < repetitions; i++) {
                    Integer[] array = generateRandomArray(size);
                    Integer[] arrayCopy = Arrays.copyOf(array, array.length);  // Ensure fresh unsorted copy each time
                    totalTime += measureSortTime(algorithm, arrayCopy);
                }
                long averageTime = totalTime / repetitions;
                System.out.println(algorithm.getClass().getSimpleName() + " average time: " + averageTime + " ns");
            }
            System.out.println();
        }
    }
}
