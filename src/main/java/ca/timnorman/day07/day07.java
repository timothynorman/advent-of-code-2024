package ca.timnorman.day07;

import ca.timnorman.utils.InputReader;

import java.util.Arrays;
import java.util.List;

public class day07 {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader("src/main/java/ca/timnorman/day07/input");
        List<String> equations = inputReader.toLines();

        System.out.println("Part 1: " + sumPossibleEquations(equations));
    }

    private static long sumPossibleEquations(List<String> equations) {
        long sumOfEquations = 0;
        for (String equation: equations) {
            String[] splitString = equation.split(":");
            long targetValue = Long.parseLong(splitString[0]);
            long[] numbers = Arrays.stream(splitString[1].trim().split(" "))
                    .mapToLong(Long::parseLong)
                    .toArray();

            if (isEquationPossible(targetValue, numbers)) {
                sumOfEquations += targetValue;
            }
        }
        return sumOfEquations;
    }

    private static boolean isEquationPossible(long targetValue, long[] numbers) {
        return checkOperations(targetValue, numbers, 0, 0);
    }

    private static boolean checkOperations(long targetValue, long[] numbers, long currentValue, int index) {
        if (index == numbers.length) {
            return targetValue == currentValue;
        }
        return testAddition(targetValue, currentValue, numbers, index) ||
                testMultiplication(targetValue, currentValue, numbers, index);
    }

    private static boolean testAddition(long targetValue, long currentValue, long[] numbers, int index) {
        return checkOperations(targetValue, numbers, currentValue + numbers[index], index + 1);
    }

    private static boolean testMultiplication(long targetValue, long currentValue, long[] numbers, int index) {
        return checkOperations(targetValue, numbers, currentValue * numbers[index], index + 1);
    }
}
