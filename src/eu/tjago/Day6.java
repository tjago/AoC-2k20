package eu.tjago;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day6 {
    public static void main(String[] args) throws IOException {
        new Day6().run();
    }
    Map<String, Integer> results = new HashMap<>();

    private void run() throws IOException {

        Stream<String> texts = Files.lines(Paths.get("res/day6.txt"));


        LinkedList<List<String>> answers = new LinkedList<>();
        answers.add(new ArrayList<>());

        texts.forEach(s -> {
                    if (s.isEmpty()) {
                        answers.add(new ArrayList<>());
                    } else {
                        answers.getLast().add(s);
                    }
                }
        );

        Optional<Integer> sum = answers.stream()
                .map(Day6::mapAnswers)
                .reduce(Integer::sum);

        System.out.println(sum.get());

        //Part 2
        Optional<Integer> sum2 = answers.stream()
                .map(Day6::mapCommonAnswers)
                .reduce(Integer::sum);

        System.out.println(sum2.get());
    }

    private static Integer mapCommonAnswers(List<String> strings) {
        return (int) IntStream
                .rangeClosed('a', 'z')
                .filter(i -> strings
                        .stream()
                        .allMatch(s -> s.indexOf(i) != -1) )
                .count();
    }

    private static Integer mapAnswers(List<String> answers) {
        Optional<String> string = answers.stream()
                .reduce(String::concat);

        long num = string.stream()
                .map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .count();
        return (int) num;
    }
}
