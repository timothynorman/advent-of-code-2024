package ca.timnorman.day09;

import ca.timnorman.utils.InputReader;

import java.util.ArrayList;
import java.util.List;

public class day09 {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader("src/main/java/ca/timnorman/day09/input");
        List<String> startingDiskMap = inputReader.toLines();

        ArrayList<Block> expandedDisk = expandDisk(startingDiskMap.getFirst());

        System.out.println("Part A: " + calculateCheckSum(moveBlocks(expandedDisk)));
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
                expandedDisk.set(index, expandedDisk.get(findRightmostMemoryBlock(expandedDisk)));
                expandedDisk.set(findRightmostMemoryBlock(expandedDisk), new Block(true, -1));
            }
            index++;
        }
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
}
