package eu.tjago;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day10 {
    public static void main(String[] args) throws IOException {
        new Day10().run();
    }

    private void run() throws IOException {
        List<Integer> numbers = Stream.concat(Stream.of(0), Files
                .lines(Paths.get("res/day10.txt"))
                .map(Integer::parseInt))
                .sorted()
                .collect(Collectors.toList());

        long diff1 = IntStream.range(1, numbers.size())
                .filter(i -> numbers.get(i) - numbers.get(i -1) == 1)
                .count();

        long diff3 = IntStream.range(1, numbers.size())
                .filter(i -> numbers.get(i) - numbers.get(i -1) == 3)
                .count();

        System.out.println(diff1 * (diff3 + 1));

        //Part 2
    }
}
