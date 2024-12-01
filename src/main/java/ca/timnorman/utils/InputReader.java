package ca.timnorman.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to read an input file and convert each line to a string, and save those
 * strings as entries in an ArrayList. Input is the filename of the input, and output
 * is the ArrayList of Strings representing the file.
 */
public class InputReader {

    // Declarations
    /**
     * The path of the file to be read.
     */
    String inputFilePath;

    /**
     * Constructor which takes in a file path as a string.
     * @param input The path file of the input as a string.
     */
    public InputReader(String input) {
        this.inputFilePath = input;
    }

    /**
     * Method to read an input file and return each line as an entry in an ArrayList.
     * @return ArrayList of each line of the input file as a String.
     */
    public List<String> toLines() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
            List<String> inputAsLines = new ArrayList<>();
            while (br.ready()){
                inputAsLines.add(br.readLine());
            }
            br.close();
            return inputAsLines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to read an input file of integers and return each line as a list of integers.
     * @return 2D ArrayList of Integers.
     */
    public List<List<Integer>> toLinesInteger() {
        return toLines().stream()
                .map(line -> line.split(" "))
                .map(arr -> List.of(arr).stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    /**
     * Method to read an input file and return it as a 2D ArrayList of Characters.
     * @return 2D ArrayList of Characters.
     */
    public ArrayList<ArrayList<Character>> toCharGrid() {
        List<String> inputAsLines = toLines();
        ArrayList<ArrayList<Character>> inputAsCharGrid = new ArrayList<>();

        for (String line : inputAsLines) {
            char[] ch = line.toCharArray();
            ArrayList<Character> charLine = new ArrayList<>();

            for (char c : ch) {
                charLine.add(c);
            }
            inputAsCharGrid.add(charLine);
        }
        return inputAsCharGrid;
    }
}