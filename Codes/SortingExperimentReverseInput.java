import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class SortingExperimentReverseInput {

    public static void main(String[] args) {
        // Array sizes to test for sorting performance
        int[] sizes = {100, 1000, 5000, 10000, 20000};

        // Array of sorting algorithms to test, each implementing the Sorter interface
        Sorter<Integer>[] sorters = new Sorter[] {
                new QuickSortGPT<>(),
                new SelectionSortGPT<>(),
                new BubbleSortUntilNoChange<>(),
                new BubbleSortWhileNeeded<>()
        };

        // Names corresponding to each sorting algorithm, for output purposes
        String[] sorterNames = {"QuickSortGPT", "SelectionSortGPT", "BubbleSortUntilNoChange", "BubbleSortWhileNeeded"};

        // The run label, e.g., "First Run", "Second Run", etc.
        String runLabel = "Third Run (Reverse Sorted Input)";  // Change this label for each run

        try (PrintWriter csvWriter = new PrintWriter(new FileWriter("sorting_experiment_reverse_sorted_input_results.csv", true))) {  // Open in append mode

            // Write the run label to indicate the start of a new run
            csvWriter.println(runLabel);
            csvWriter.println("Array Size,Sorting Algorithm,Execution Time (ns)");

            // Loop through different array sizes
            for (int size : sizes) {

                // Print the current array size being tested
                System.out.println("Array size: " + size);

                // Create a reverse-sorted array of the specified size
                Integer[] array = new Integer[size];
                for (int i = 0; i < size; i++) {
                    array[i] = size - i;  // Reverse sorted integers
                }

                // Test each sorting algorithm on the current reverse sorted array
                for (int i = 0; i < sorters.length; i++) {

                    // Make a copy of the original array to ensure each algorithm sorts the same data
                    Integer[] arrayCopy = Arrays.copyOf(array, array.length);

                    // Measure the start time in nanoseconds before sorting
                    long startTime = System.nanoTime();

                    // Sort the array using the current algorithm
                    sorters[i].sort(arrayCopy);

                    // Measure the end time in nanoseconds after sorting
                    long endTime = System.nanoTime();

                    // Calculate the duration (execution time) in nanoseconds
                    long duration = endTime - startTime;

                    // Print the result to the console
                    System.out.println(sorterNames[i] + " took " + duration + " ns");

                    // Write the result to the CSV file
                    csvWriter.println(size + "," + sorterNames[i] + "," + duration);
                }

                System.out.println();  // Print an empty line between array sizes for readability
            }

            // Notify the user that the results have been written to the CSV file
            System.out.println("Results for " + runLabel + " written to sorting_experiment_reverse_sorted_input_results.csv");

        } catch (IOException e) {
            // Handle any errors that occur during file writing
            e.printStackTrace();
        }
    }
}
