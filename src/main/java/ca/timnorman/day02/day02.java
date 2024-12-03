package ca.timnorman.day02;

import ca.timnorman.utils.InputReader;

import java.util.List;

public class day02 {
    public static void main(String[] args) {
        String inputLocation = "src/main/java/ca/timnorman/day02/testInput";
        InputReader inputReader = new InputReader(inputLocation);

        List<List<Integer>> input = inputReader.toLinesInteger();

        System.out.println("Part 1: " + evaluateReports(input));
        System.out.println("Part 2: " + evaluateReportsWithDampers(input));
    }

    private static int evaluateReports(List<List<Integer>> input) {
        int safeReports = 0;
        for (List<Integer> line : input) {
            if ((line.get(0)) > line.get(1)) {  // First digit > second therefore decreasing
                if (testSafety(line, true)) safeReports++;
            } else {
                if (testSafety(line, false)) safeReports++;
            }
        }
        return safeReports;
    }

    private static int evaluateReportsWithDampers(List<List<Integer>> input) {
        int safeReports = 0;
        for (List<Integer> line : input) {
            if ((line.get(0)) > line.get(1)) {  // First digit > second therefore decreasing
                if (testSafetyWithDampener(line, true)) safeReports++;
            } else {
                if (testSafetyWithDampener(line, false)) safeReports++;
            }
        }
        return safeReports;
    }

    private static boolean testSafety(List<Integer>line, boolean isDecreasing) {
        for (int i = 0; i < line.size() - 1; i++) {
            int diff = line.get(i) - line.get(i + 1);

            if (isDecreasing) {
                if (diff > 3 || diff <= 0) return false;
            } else {
                if (diff < -3 || diff >= 0) return false;
            }
        } return true;
    }

    private static boolean testSafetyWithDampener(List<Integer> line, boolean isDecreasing) {
        // TODO Logic to remove one bad number. (NOT one free failure per line.) just check i + 2 term? What about overflow?
        return true;
    }

}
