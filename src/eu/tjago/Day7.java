package eu.tjago;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {
    public static void main(String[] args) throws IOException {
        new Day7().run();
    }

    static Map<String, List<String>> bagsInfo = new HashMap<>();

    private void run() throws IOException {
        Stream<String> texts = Files.lines(Paths.get("res/day7.txt"));

        texts.forEach(Day7::createBagsMap);

        //Part 1
        long result = bagsInfo.keySet()
                .stream()
                .filter(Day7::containsShinyGoldBag)
                .count();

        System.out.println("\n count: " + bagsInfo.size() + " result:" + result);

        //Part 2
        System.out.println("bags inside shiny gold: " + getNumberOfBags("1 shiny gold"));
    }

    private static long getNumberOfBags(String bagEntry) {
        return bagsInfo.get(bagEntry.substring(2))
                .stream()
                .mapToLong(s -> (Integer.parseInt(s.substring(0,1)) + getNumberOfBags(s) * Integer.parseInt(s.substring(0,1))))
                .sum();
    }

    private static boolean containsShinyGoldBag(String s) {
        try {//remove number prefix from bags, so we can call get on map
            Integer.parseInt(s.substring(0,1));
                s = s.substring(2);
        } catch (NumberFormatException ignored) {};
        return bagsInfo.get(s).stream().anyMatch(s1 -> s1.contains("shiny gold"))
                || bagsInfo.get(s).stream().anyMatch(Day7::containsShinyGoldBag);
    }

    private static void createBagsMap(String text) {
        int index = text.indexOf("contain");

        List<String> list = Arrays
                .stream(text.substring(index + 8).split(", "))
                .map(s -> s.substring(0, s.indexOf("bag") -1)) //remove bags number suffix
                .collect(Collectors.toList());

        if (text.substring(index +8).contains("no other bags")) {
            bagsInfo.put(text.substring(0, index -6), new ArrayList<>()); // list of bags empty
        } else {
            bagsInfo.put(text.substring(0, index -6), list); // -6 remove [SPACE]bags[SPACE] from string
        }
    }
}
