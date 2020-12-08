package eu.tjago;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day8 {
    public static void main(String[] args) throws Exception {
        new Day8().run();
    }

    static long globalVar = 0;
    Set<Integer> linesVisited = new HashSet<>();

    private void run() throws Exception {
        List<String> instructions = Files.lines(Paths.get("res/day8.txt")).collect(Collectors.toList());


        //Part 1
        int stepNum = 0;
        while(linesVisited.add(stepNum)) {
            stepNum += execute(instructions.get(stepNum));
        }

        System.out.println(globalVar);

        //Part 2

        IntStream
                .range(0, instructions.size() -1)
                .forEach(i -> {
                    //replace instructions
                    List<String> moddedInstructions = new ArrayList<>(instructions);
                    if (moddedInstructions.get(i).contains("nop")) {
                        moddedInstructions.set(i, moddedInstructions.get(i).replace("nop", "jmp"));
                    } else if (moddedInstructions.get(i).contains("jmp")) {
                        moddedInstructions.set(i, moddedInstructions.get(i).replace("jmp", "nop"));
                    }

                    linesVisited = new HashSet<>();
                    globalVar = 0; //reset global
                    AtomicInteger stepNumAtomic = new AtomicInteger();
                    while(linesVisited.add(stepNumAtomic.get())
                            && stepNumAtomic.get() != (long) instructions.size() -1) {
                        stepNumAtomic.set(stepNumAtomic.get() + execute(moddedInstructions.get(stepNumAtomic.get())));
                    }
                    if (stepNumAtomic.get() == (long) moddedInstructions.size() -1) {
                        System.out.println("Result 2 = " + globalVar);
                    }
                });
    }

    static int execute(String code) {
        switch (code.substring(0,3)) {
            case "nop":
                return 1;
            case "acc":
                globalVar += Integer.parseInt(code.substring(4));
                return 1;
            case "jmp":
                return Integer.parseInt(code.substring(4));
            default:
                throw new RuntimeException("Unknown value: " + code);
        }
    }
}
