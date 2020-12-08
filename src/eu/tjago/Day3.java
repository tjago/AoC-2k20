package eu.tjago;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day3 {
    public static void main(String[] args) {
        new Day3().run();
    }

    static List<String> rows = getStringArraysOutOfFile("res/day3.txt");

    private void run() {
        long result1 = IntStream.rangeClosed(0, rows.size()-1)
                .filter(i -> isTreeAtPosition(i, i * 3)) // 0 -> (0,0) , 1 -> (1,3)
                .count();

        long result2 = IntStream.rangeClosed(0, rows.size()-1)
                .filter(i -> isTreeAtPosition(i, i))
                .count();

        long result3 = IntStream.rangeClosed(0, rows.size()-1)
                .filter(i -> isTreeAtPosition(i, i * 5))
                .count();

        long result4 = IntStream.rangeClosed(0, rows.size()-1)
                .filter(i -> isTreeAtPosition(i, i * 7))
                .count();

        long result5 = IntStream.rangeClosed(0, (rows.size()-1) / 2) //1 -> 2 , 2 -> 4, 3 -> 6, 4 -> 8, 5 -> 10
                .filter(i -> isTreeAtPosition(i * 2, i))
                .count();

        System.out.println(result1);
        System.out.println(result1 * result2 * result3  * result4 * result5);
    }

    static boolean isTreeAtPosition(Integer row, Integer col) {
        return rows.get(row).toCharArray()[col % 31] == '#';
    }

    static List<String> getStringArraysOutOfFile(String filename) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();

            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
