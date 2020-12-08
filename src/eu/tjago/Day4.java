package eu.tjago;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day4 {
    public static void main(String[] args) throws IOException {
        new Day4().run();
    }

    private void run() throws IOException {
        Stream<String> texts = Files.lines(Paths.get("res/day4.txt"));

        LinkedList<Map<String, String>> passportDataList = new LinkedList<>();
        passportDataList.add(new HashMap<>());

        texts.forEach(
                s -> {
                    if (s.length() == 0 ) {//if empty line, create new map, for new passport
                        passportDataList.add(new HashMap<>());
                    } else {
                        String pairs[] = s.split(" ");
                        Arrays.stream(pairs).forEach(
                                text -> {
                                    String pair[] = text.split(":");
                                    passportDataList.getLast().put(pair[0], pair[1]);
                                }
                        );
                    }
                }
        );

        //Part 1
        System.out.println(passportDataList
                .stream()
                .filter(Day4::verifyPassport)
                .count());

        //Part 2
        System.out.println(countValidPassports(passportDataList));
    }

    private long countValidPassports(LinkedList<Map<String, String>> passportDataList) {
        return passportDataList
                .stream()
                .filter(Day4::verifyPassport)
                .filter(Day4::verifyPassData)
                .count();
    }

    private static boolean verifyPassport(Map<String, String> data) {
        return data.containsKey("byr")
                && data.containsKey("iyr")
                && data.containsKey("eyr")
                && data.containsKey("hgt")
                && data.containsKey("hcl")
                && data.containsKey("ecl")
                && data.containsKey("pid");
    }

    private static boolean verifyPassData(Map<String, String> data) {
        return Integer.parseInt(data.get("byr")) >= 1920
                && Integer.parseInt(data.get("byr")) <= 2002
                && Integer.parseInt(data.get("iyr")) >= 2010
                && Integer.parseInt(data.get("iyr")) <= 2020
                && Integer.parseInt(data.get("eyr")) >= 2020
                && Integer.parseInt(data.get("eyr")) <= 2030
                && checkHeight(data.get("hgt"))
                && checkHairColour(data.get("hcl"))
                && checkEyeColour(data.get("ecl"))
                && checkPassportID(data.get("pid"));

    }

    private static boolean checkPassportID(String pid) {
        return pid.length() == 9 && Integer.parseInt(pid) >= 0;
    }

    private static boolean checkEyeColour(String ecl) {
        return "amb".equals(ecl)
                || "blu".equals(ecl)
                || "brn".equals(ecl)
                || "gry".equals(ecl)
                || "grn".equals(ecl)
                || "hzl".equals(ecl)
                || "oth".equals(ecl);
    }

    private static boolean checkHairColour(String hcl) {
        return HexValidatorWebColor.isValid(hcl);
    }
    public static class HexValidatorWebColor {

        private static final String HEX_WEBCOLOR_PATTERN
//                = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$";
                = "^#([a-fA-F0-9]{6})$";

        private static final Pattern pattern = Pattern.compile(HEX_WEBCOLOR_PATTERN);

        public static boolean isValid(final String colorCode) {
            Matcher matcher = pattern.matcher(colorCode);
            return matcher.matches();
        }

        ;
    }

    private static boolean checkHeight(String hgt) {
        return (hgt.contains("cm")
                && Integer.parseInt(hgt.replaceAll("[^0-9]", "")) >= 150
                && Integer.parseInt(hgt.replaceAll("[^0-9]", "")) <= 193)
                || (hgt.contains("in")
                && Integer.parseInt(hgt.replaceAll("[^0-9]", "")) >= 59
                && Integer.parseInt(hgt.replaceAll("[^0-9]", "")) <= 76);
    }
}
