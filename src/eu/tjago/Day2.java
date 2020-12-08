package eu.tjago;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class Day2 {
    public static void main(String[] args) throws IOException {
        new Day2().run();
    }

    private void run() throws IOException {

        Optional<Integer> result =  Files.lines(Paths.get("res/day2.txt"))
                .map(Day2::checkPass)
                .reduce(Integer::sum);

        System.out.println(result.get());

        //Part 2
        Optional<Integer> result2 = Files.lines(Paths.get("res/day2.txt"))
                .map(Day2::checkOccurence)
                .reduce(Integer::sum);

        System.out.println(result2.get());
    }
    static int checkPass(String s) {
        String[] array = s.split(" ");

        String[] range = array[0].split("-");
        int min = Integer.parseInt(range[0]);
        int max = Integer.parseInt(range[1]);

        String letter = array[1].substring(0,1);
        String password = array[2];

        if (checkLettersMatch(letter, password, min, max)) {
            return 1;
        }
        return 0;
    }
    static int checkOccurence(String s) {
        String[] array = s.split(" ");

        String[] range = array[0].split("-");
        int min = Integer.parseInt(range[0]);
        int max = Integer.parseInt(range[1]);

        String letter = array[1].substring(0,1);
        String password = array[2];

        if (checkLetterOcurOnce(letter, password, min, max)) {
            return 1;
        }
        return 0;
    }

    private static boolean checkLetterOcurOnce(String letter, String password, int min, int max) {
        return password.toCharArray()[min - 1] == letter.toCharArray()[0]
                && password.toCharArray()[max - 1] != letter.toCharArray()[0] ||
                password.toCharArray()[min - 1] != letter.toCharArray()[0]
                        && password.toCharArray()[max - 1] == letter.toCharArray()[0];
    }

    private static boolean checkLettersMatch(String letter, String password, int min, int max) {
        int counter = 0;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) == letter.toCharArray()[0]) {
                counter++;
            }
        }
        if (counter >= min && counter <= max) {
            return true;
        }
        return false;
    }
}
