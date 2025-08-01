package ca.timnorman.day09;

import ca.timnorman.utils.InputReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day09 {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader("src/main/java/ca/timnorman/day09/testInput");
        List<String> startingDiskMap = inputReader.toLines();

        ArrayList<Block> expandedDisk = expandDisk(startingDiskMap.getFirst());

//        System.out.println("Part A: " + calculateCheckSum(moveBlocks(expandedDisk)));
        printDisk(expandedDisk);

        System.out.println(findNextEmptyZone(expandedDisk, 0).getStartIndex());
        System.out.println(findNextEmptyZone(expandedDisk, 0).getEndIndex());
        System.out.println(findNextEmptyZone(expandedDisk, 0).getLength());

        System.out.println(findNextFile(expandedDisk, 35).getStartIndex());
        System.out.println(findNextFile(expandedDisk, 35).getEndIndex());
        System.out.println(findNextFile(expandedDisk, 35).getLength());

        System.out.println(swapFile(expandedDisk, findNextEmptyZone(expandedDisk,0), findNextFile(expandedDisk,expandedDisk.size()-1)));
        printDisk(expandedDisk);

    }

    private static ArrayList<Block> expandDisk(String diskMap) {
        ArrayList<Block> expandedDisk = new ArrayList<>();
        int fileNumber = 0;
        for (int i = 0; i < diskMap.length(); i++) {
            int d = diskMap.charAt(i) - '0';

            // Every odd-numbered digit is memory; even-numbered are blank.
            if (i % 2 == 0) {
                for (int j = 0; j < d; j++) {
                    expandedDisk.add(new Block(false, fileNumber));
                }
                fileNumber++;
            } else {
                for (int j = 0; j < d; j++) {
                    expandedDisk.add(new Block(true, -1));
                }
            }
        }
        return expandedDisk;
    }

    private static ArrayList<Block> moveBlocks(ArrayList<Block> expandedDisk) {
        int index = 0;
        while (index < findRightmostMemoryBlock(expandedDisk)) {
            if (expandedDisk.get(index).isEmpty) {
                Collections.swap(expandedDisk, index, findRightmostMemoryBlock(expandedDisk));
            }
            index++;
        }
        return expandedDisk;
    }

    /**
     * Move contiguous files from right to left-most available empty space that can hold it.
     * @param expandedDisk
     * @return The expanded file with files moved to empty space.
     */
    private static ArrayList<Block> moveFiles(ArrayList<Block> expandedDisk) {
        return expandedDisk;
    }

    private static int findRightmostMemoryBlock(ArrayList<Block> disk) {
        for (int i = disk.size() - 1; i > 0; i--) {
            if (!disk.get(i).isEmpty) return i;
        }
        return -1;
    }

    private static void printDisk(ArrayList<Block> disk) {
        for (Block b : disk) {
            if (b.isEmpty) System.out.print(".");
            else System.out.print(b.getFileNumber());
        }
        System.out.println();
    }

    private static long calculateCheckSum(ArrayList<Block> disk) {
        long checksum = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (!disk.get(i).isEmpty) checksum += (long) i * disk.get(i).getFileNumber();
        }
        return checksum;
    }

    // todo find start, end, length of empty zones
    private static File findNextEmptyZone(ArrayList<Block> disk, int startingIndex) {
        int index = startingIndex;
        while (index < disk.size()-1) {
            if (disk.get(index).isEmpty) {
                int endIndex = index + 1;
                while (endIndex < disk.size() && disk.get(endIndex).isEmpty) endIndex++;
                return new File(index, endIndex-1);
            }
            index++;
        }
        return null;
    }

    // todo find rightmost file, with start, end, and length.
    /**
     * Searches disk from right -> left and returns File of data.
     * @param disk An ArrayList representing contiguous memory blocks.
     * @param endIndex The index of the right-most block of a file containing data (i.e. not empty)
     * @return A 'File' with the details of contiguous blocks of non-empty data.
     */
    private static File findNextFile(ArrayList<Block> disk, int endIndex) {
        int fileName = disk.get(endIndex).getFileNumber();
        int index = endIndex;
        while (disk.get(index).getFileNumber() == fileName) {
            index--;
        }
        return new File(index+1, endIndex);
    }

    // todo swap whole file. Iterate leftward from 'start' of file's location.

    /**
     * Swap an empty zone with a file. Empty zone must be at least as long as the file.
     * @param disk
     * @param emptyZone
     * @param file
     * @return The disk with file and empty zone swapped.
     */
    private static ArrayList<Block> swapFile(ArrayList<Block> disk, File emptyZone, File file) {
        int emptyIndex = emptyZone.getStartIndex();
        int fileIndex = file.getStartIndex();
        while (emptyIndex <= emptyZone.getLength()) {   //todo If empty is longer than file it could pull in other elements.
            Collections.swap(disk, emptyIndex, fileIndex);
            emptyIndex++;
            fileIndex++;
        }
        return disk;
    }

    private static class Block {
        private boolean isEmpty;
        private int fileNumber;

        public Block(){}

        public Block(boolean isEmpty, int fileNumber) {
            this.isEmpty = isEmpty;
            this.fileNumber = fileNumber;
        }

        public boolean isEmpty() {
            return isEmpty;
        }

        public void setIsEmpty(boolean isEmpty) {
            this.isEmpty = isEmpty;
        }

        public int getFileNumber() {
            return fileNumber;
        }

        public void setFileNumber(int fileNumber) {
            this.fileNumber = fileNumber;
        }

        @Override
        public String toString() {
            return "Block{" +
                    "isEmpty=" + isEmpty +
                    ", fileNumber=" + fileNumber +
                    '}';
        }
    }

    /**
     * A 'File' is a contiguous set of 'Blocks' that may represent data OR empty space.
     */
    private static class File {
        private int startIndex;
        private int endIndex;
        private int length;

        public File (int startIndex, int endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.length = endIndex - startIndex + 1;    // length is inclusive of start and end indices.
        }

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(int endIndex) {
            this.endIndex = endIndex;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }
}
