package eu.tjago;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args) throws IOException {
        new Day1().run();
    }

    private void run() throws IOException {
        List<Integer> numbers = Files.lines(Paths.get("res/day1.txt"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for (int num1 : numbers) {
            for (int num2 : numbers) {
                if (num1 + num2 == 2020) {
                    System.out.println("Result is: " + (num1 * num2));
                }
            }
        }

        for (int num1 : numbers) {
            for (int num2 : numbers) {
                for (int num3 : numbers) {
                    if (num1 + num2 + num3 == 2020) {
                        System.out.println("Result2 is: " + (num1 * num2 * num3));
                    }
                }
            }
        }
    }
}
