package ca.timnorman.day03;

import ca.timnorman.utils.InputReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day03 {
    public static void main(String[] args) {
        final String INSTRUCTIONS_ONLY = "mul\\(\\d+\\,\\d+\\)";
        final String INSTRUCTIONS_AND_MODIFIERS = "(mul\\(\\d+\\,\\d+\\))|(don't\\(\\))|(do\\(\\))|(\\n|\\r|\\r\\n)";

        InputReader inputReader = new InputReader("src/main/java/ca/timnorman/day03/input");
        List<String> input = inputReader.toLines();

        System.out.println("Part 1: " + sumInstructionsMultiples(findInstructions(input, INSTRUCTIONS_ONLY)));
        System.out.println("Part 2: " + sumInstructionsMultiples(processInstructions(findInstructions(input, INSTRUCTIONS_AND_MODIFIERS))));
    }

    private static List<String> findInstructions(List<String> input, String pattern) {
        Pattern p = Pattern.compile(pattern);
        ArrayList<String> output = new ArrayList<>();

        for (String line: input) {
            Matcher m = p.matcher(line);

            while (m.find()) {
                output.add(m.group());
            }
        }
        return output;
    }

    private static List<String> processInstructions(List<String> input) {
        List<String> output = new ArrayList<>();
        boolean enabled = true;

        for (String line: input) {
            if (line.equals("do()") || line.equals("\\n")) { enabled = true;}
            else if (line.equals("don't()")) { enabled = false;}
            else if (enabled) {
                output.add(line);
            }
        }
        return output;
    }

    private static int sumInstructionsMultiples(List<String> instructions) {
        int sum = 0;
        for (String instruction: instructions) {
            sum += getOneDigit(instruction, true) * getOneDigit(instruction, false);
        }
        return sum;
    }

    private static int getOneDigit(String instructions, boolean isFirst) {
        Pattern p;
        int instruction = 0;

        if (isFirst) {
            p = Pattern.compile("(?<=\\()(\\d+)");  // get digits after '('
        } else {
            p = Pattern.compile("(\\d+)(?=\\))");   // get digits before ')'
        }

        Matcher m = p.matcher(instructions);
        while (m.find()) {
            instruction = Integer.parseInt(m.group());
        }
        return instruction;
    }
}
