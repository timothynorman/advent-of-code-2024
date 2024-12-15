package ca.timnorman.day04;

import ca.timnorman.utils.InputReader;

import java.util.ArrayList;

public class day04 {

    public static ArrayList<ArrayList<Character>> wordSearch;
    public static char[] searchChars;
    public static void main(String[] args) {
        InputReader inputReader = new InputReader("src/main/java/ca/timnorman/day04/input");
        wordSearch = inputReader.toCharGrid();
        String searchWord = "XMAS";
        searchChars = searchWord.toCharArray();

        System.out.println("Part 1: " + parseWordSearch());
        System.out.println("Part 2: " + searchForA());
    }

    private static int parseWordSearch() {
        int foundCount = 0;
        for (int j = 0; j < wordSearch.size(); j++) {    // iterate over lines
            ArrayList<Character> line = wordSearch.get(j);
            for (int i = 0; i < line.size(); i++) { // iterate over letters
                if (line.get(i).equals(searchChars[0])) {
                    foundCount += searchAroundLetter(i, j);
                }
            }
        }
        return foundCount;
    }

    private static int searchAroundLetter(int xCoord, int yCoord) {
        int foundCount = 0;
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                int[] direction = new int[] {i, j};
                int searchIndex = 1;    // 0th char must be found to start this method already.
                boolean doContinue = true;
                while (processChar(xCoord, yCoord, direction, searchIndex) && doContinue) {
                    foundCount++;
                    doContinue = false;
                }
            }
        }
        return foundCount;
    }

    private static boolean processChar(int xCoord, int yCoord, int[] direction, int index) {
        if (isAllowableNextPosition(xCoord, yCoord, direction)) {
            char nextChar = wordSearch.get(yCoord + direction[1]).get(xCoord + direction[0]);
            if (nextChar == searchChars[index] && searchChars.length -1 == index) {
                return true;
            } else if (!isAllowableNextPosition(xCoord, yCoord, direction) || nextChar != searchChars[index]) {
                return false;
            } else {
                xCoord += direction[0];
                yCoord += direction[1];
                index++;
                return processChar(xCoord, yCoord, direction, index);
            }
        } return false;
    }

    private static boolean isAllowableNextPosition(int xCoord, int yCoord, int[] direction) {
        if (xCoord + direction[0] > wordSearch.get(yCoord).size() -1 || xCoord + direction[0] < 0) return false;
        else if (yCoord + direction[1] > wordSearch.size() -1 || yCoord + direction[1] < 0) return false;
        return true;
    }

    private static int searchForA() {
        int foundCount = 0;
        for (int j = 1; j < wordSearch.size() -1; j++) {
            ArrayList<Character> line = wordSearch.get(j);
            for (int i = 1; i < line.size() -1; i++) {
                if (line.get(i).equals('A') && searchForXMas(i, j)) {
                    foundCount++;
                }
            }
        }
        return foundCount;
    }

    private static boolean searchForXMas(int xCoord, int yCoord) {
        for (int j = -1; j < 0; j += 2) {
            for (int i = -1; i < 0; i += 2) {
                if (isMas(xCoord, yCoord, new int[]{i, j})) {
                    return (isMas(xCoord, yCoord, new int[]{-i, j}) || isMas(xCoord, yCoord, new int[]{i, -j}));
                }
            }
        }
        return false;
    }

    // This is dirty and I don't like it.
    // It works, but there should be a more elegant way of checking all possibilities.
    private static boolean isMas(int xCoord, int yCoord, int[] direction) {
        return (wordSearch.get(yCoord + direction[1]).get(xCoord + direction[0]).equals('M'))
                && (wordSearch.get(yCoord - direction[1]).get(xCoord - direction[0]).equals('S'))
                || (wordSearch.get(yCoord + direction[1]).get(xCoord + direction[0]).equals('S'))
                && (wordSearch.get(yCoord - direction[1]).get(xCoord - direction[0]).equals('M'));
    }
}
