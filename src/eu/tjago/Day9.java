package eu.tjago;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 {
    public static void main(String[] args) throws IOException {
        new Day9().run();
    }

    private void run() throws IOException {
        List<String> texts = Files.lines(Paths.get("res/day9.txt")).collect(Collectors.toList());

        int result = IntStream
                .range(25, texts.size())
                .filter(i -> {
                    long currentNum = Long.parseLong(texts.get(i));
                    return IntStream
                            .range(i -25, i)
                            .filter(j -> {
                                return IntStream
                                        .range(j+1, i)
                                        .filter(k -> {
                                            if (Long.parseLong(texts.get(j))
                                                    + Long.parseLong(texts.get(k)) == currentNum
                                            && Long.parseLong(texts.get(j)) != Long.parseLong(texts.get(k))) {
                                                return true;
                                            };
                                            return false;
                                        }).count() > 0;
                            }).count() == 0;
                })
                .findFirst().getAsInt();

        System.out.println(texts.get(result));

        //Part 2

        List<Long> numList = texts
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        IntStream
                .range(1, result -1)
                .forEach(i -> {
                    IntStream
                            .range(i +1, result -1)
                            .forEach(j -> {
                                List<Long> list = numList.subList(i,j);
                                if ( list.stream().reduce(Long::sum).get() == Long.parseLong(texts.get(result))) {
                                    System.out.println("" + (list.stream().reduce(Long::min).get()
                                            + list.stream().reduce(Long::max).get()));
                                }
                            });
                });
    }
}
