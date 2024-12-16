package ca.timnorman.day05;

import ca.timnorman.utils.InputReader;

import java.util.*;

public class day05 {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader("src/main/java/ca/timnorman/day05/input");
        List<String> input = inputReader.toLines();

        Map<String, List<String>> rules = processRules(input);
        List<String[]> printInstructions = processPages(input);

        System.out.println("Part 1: " + sumMiddlePages(rules, printInstructions));
    }

    private static int sumMiddlePages(Map<String, List<String>> rules, List<String[]> printInstructions) {
        int sumMiddlePage = 0;
        for (String[] instruction: printInstructions) {
            if (isInOrder(instruction, rules)) {
                int middlePage = instruction.length/2;
                sumMiddlePage += Integer.parseInt(instruction[middlePage]);
            }
        }
        return sumMiddlePage;
    }

    private static boolean isInOrder(String[] instruction, Map<String, List<String>> rules) {
        for (int i = 0; i < instruction.length; i++) {
            for (int j = i + 1; j < instruction.length; j++) {
                if (rules.get(instruction[i]) == null || !rules.get(instruction[i]).contains(instruction[j])) return false;
            }
        }
        return true;
    }

    private static List<String[]> processPages(List<String> input) {
        List<String[]> instructions = new ArrayList<>();
        for (String line: input) {
            if (line.contains(",")) {
                instructions.add(line.split(","));
            }
        }
        return instructions;
    }

    private static Map<String, List<String>> processRules(List<String> input) {
        Map<String, List<String>> rules = new HashMap<>();
        for (String line: input) {
            if (line.contains("|")) {
                String[] splitRule = line.split("\\|");
                if (!rules.containsKey(splitRule[0])) {
                    ArrayList<String> list = new ArrayList<>(Collections.singletonList(splitRule[1]));
                    rules.put(splitRule[0], list);
                } else {
                    rules.get(splitRule[0]).add(splitRule[1]);
                }
            }
        }
        return rules;
    }
}

class Day05Input {
}
