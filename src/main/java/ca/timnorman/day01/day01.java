package ca.timnorman.day01;

import ca.timnorman.utils.InputReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day01 {
    public static void main(String[] args) {

        String inputLocation = "src/main/java/ca/timnorman/day01/input";
        InputReader inputReader = new InputReader(inputLocation);

        List<String> input = inputReader.toLines();
        ArrayList<Integer> leftList = new ArrayList<>();
        ArrayList<Integer> rightList = new ArrayList<>();
        int difference = 0;
        int similarity = 0;

        for (String line: input) {
            String[] results = line.split(" +");
            leftList.add(Integer.valueOf(results[0]));
            rightList.add(Integer.valueOf(results[1]));
        }
        Collections.sort(leftList);
        Collections.sort(rightList);

        // Part 1
        for (int i = 0; i < leftList.size(); i++) {
            difference += Math.abs(leftList.get(i) - rightList.get(i));
        }
        System.out.println("Part 1: " + difference);

        // Part 2
        for (int leftNumber: leftList) {
            similarity += leftNumber * Collections.frequency(rightList, leftNumber);
        }
        System.out.println("Part 2: " + similarity);
    }
}
