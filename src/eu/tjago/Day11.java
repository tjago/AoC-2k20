package eu.tjago;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 {
    public Day11() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        new Day11().run();
    }

    int seatsPerRow = Files.lines(Paths.get("res/day11.txt")).findFirst().get().length();
    int seatRows = (int) Files.lines(Paths.get("res/day11.txt")).count();

    private void run() throws IOException {

        // array data structure will make it easy to compare seats to it's neighbours by passing array index
        int[][] seats = new int[seatRows][seatsPerRow];

        // fill initial data array
        List<String> seatsRowToParse = Files.lines(Paths.get("res/day11.txt")).collect(Collectors.toList());

        for(int i = 0; i < seatRows; i++) {
            for( int j = 0; j < seatsPerRow; j++) {
                seats[i][j] = seatsRowToParse.get(i).toCharArray()[j];
            }
        }

        int[][] newSeats;
        boolean notIdentical;

        do {
            newSeats = calcNewSeats(seats);
            System.out.println(calcOccupiedSeats(newSeats));
            notIdentical = !arraysAreIdentical(seats, newSeats);
            seats = newSeats.clone();
        } while (notIdentical);

        System.out.println(calcOccupiedSeats(newSeats));
    }

    private int[][] calcNewSeats(int[][] oldSeats) {
        int[][] newSeats = new int[seatRows][seatsPerRow];

        for(int i = 0; i < seatRows; i++) {
            for( int j = 0; j < seatsPerRow; j++) {
                newSeats[i][j] = calcNewSeatState(oldSeats, i, j);
                System.out.println("returning: " + (char) calcNewSeatState(oldSeats, i, j));
            }
        }
        return newSeats;
    }

    private int calcNewSeatState(int[][] seats, int i, int j) {
        switch (seats[i][j]) {
            case 'L':
                if (    i > 0 && seats[i-1][j] == '#' ||
                        i < seatRows -1  && seats[i+1][j] == '#' ||
                        j > 0 && seats[i][j -1] == '#' ||
                        j < seatsPerRow -1 && seats[i][j +1] == '#' ||

                        i > 0 && j > 0 && seats[i-1][j-1] == '#' ||
                        i < seatRows -1  && j > 0 && seats[i+1][j-1] == '#' ||
                        i > 0 && j < seatsPerRow -1 && seats[i-1][j +1] == '#' ||
                        i < seatRows -1 && j < seatsPerRow -1 && seats[i+1][j +1] == '#'
                ) {
                    return 'L';
                } else {
                    return '#';
                }
            case '.':
                return '.';
            case '#':
                int count = 0;

                if (i > 0 && seats[i-1][j] == '#') count++;
                if (i < seatRows -1  && seats[i+1][j] == '#') count++;
                if (j > 0 && seats[i][j -1] == '#') count++;
                if (j < seatsPerRow -1 && seats[i][j +1] == '#') count++;

                if (i > 0 && j > 0 && seats[i-1][j-1] == '#') count++;
                if (i < seatRows -1  && j > 0 && seats[i+1][j-1] == '#') count++;
                if (i > 0 && j < seatsPerRow -1 && seats[i-1][j +1] == '#') count++;
                if (i < seatRows -1 && j < seatsPerRow -1 && seats[i+1][j +1] == '#') count++;

                return count >= 4 ? 'L' : '#';
            default:
                return 0;
        }
    }

    private int calcOccupiedSeats(int[][] seats) {
        int count = 0;
        for(int i = 0; i < seatRows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                if (seats[i][j] == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean arraysAreIdentical(int[][] array1, int[][] array2) {
        for(int i = 0; i < seatRows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                if (array1[i][j] != array2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
