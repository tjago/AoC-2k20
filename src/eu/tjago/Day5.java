package eu.tjago;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {
    public static void main(String[] args) throws IOException {
        new Day5().run();
    }

    private void run() throws IOException {
        Stream<String> boardingPasses = Files.lines(Paths.get("res/day5.txt"));

        Optional<Integer> result = boardingPasses
                .map(Day5::calculateID)
                .max(Integer::compareTo);

        System.out.println(result.get());

        //Part 2
        Map<Integer, List<Seat>> rows = Files.lines(Paths.get("res/day5.txt"))
                .map(Day5::getSeat)
                .collect(Collectors.groupingBy(Seat::getRow));

        rows.forEach(
                (rowNumber, seats) -> {
                    if (seats.size() == 7 ) {// each row has 8 seats, if we have 7 that means remaining is ours
                        System.out.println("row not full: " + rowNumber);
                        seats.stream().forEach(System.out::println);
                    }
                }
        );
    }

    static class Seat {
        Integer row;
        Integer col;

        Seat(int row, int col) {
            this.row = row;
            this.col = col;
        }
        Integer getRow() { return this.row; }
        Integer getCol() { return this.col;}

        @Override
        public String toString() {
            return "Seat{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }

    private static Seat getSeat(String code) {
        return new Seat(Integer.parseInt(toBinaryCode(code).substring(0, 7), 2),
                Integer.parseInt(toBinaryCode(code).substring(7, 10), 2));
    }

    private static int calculateID(String code) {
        return 8 * Integer.parseInt(toBinaryCode(code).substring(0, 7), 2)   // row
                 + Integer.parseInt(toBinaryCode(code).substring(7, 10), 2); // column;
    }

    private static String toBinaryCode(String code) {
        return code.replace("F", "0")
                .replace("B", "1")
                .replace("R", "1")
                .replace("L", "0");
    }
}
