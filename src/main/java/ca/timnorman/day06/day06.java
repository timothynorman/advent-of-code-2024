package ca.timnorman.day06;

import ca.timnorman.utils.InputReader;

import java.util.ArrayList;

public class day06 {
    enum Direction {
        UP (0, -1),
        RIGHT (1, 0),
        DOWN (0, 1),
        LEFT (-1, 0);

        private final int[] vector;

        Direction(int x, int y) {
            this.vector = new int[]{x, y};
        }

        public int[] getVector() {
            return vector;
        }

        public Direction turnRight() {
            return values()[(this.ordinal() + 1) % values().length];
        }
    }

    public static void main(String[] args) {
        InputReader inputReader = new InputReader("src/main/java/ca/timnorman/day06/input");
        ArrayList<ArrayList<Character>> room = inputReader.toCharGrid();

        Location startingLocation = findStartingLocation(room);
        int xCoord = startingLocation.getX();
        int yCoord = startingLocation.getY();
        Direction currentDirection = startingLocation.getDirection();

        System.out.println("Part 1: " + patrolLength(room, xCoord, yCoord, currentDirection));
    }

    private static Location findStartingLocation(ArrayList<ArrayList<Character>> room) {
        for (int j = 0; j < room.size(); j++) {
            for (int i = 0; i < room.get(j).size(); i++) {
                Character testLocation = room.get(j).get(i);
                if (testLocation != '.' && testLocation != '#') {

                    Direction startDirection = switch (testLocation) {
                        case '^' -> Direction.UP;
                        case '>' -> Direction.RIGHT;
                        case 'v' -> Direction.DOWN;
                        case '<' -> Direction.LEFT;
                        default -> null;
                    };
                    return new Location(i, j, startDirection);
                }
            }
        }
        return null;
    }

    private static int patrolLength(ArrayList<ArrayList<Character>> room, int startingX, int startingY, Direction startingDirection) {
        int distinctPositions = 1;
        int xCoord = startingX;
        int yCoord = startingY;
        Direction direction = startingDirection;

        room.get(yCoord).set(xCoord, 'X');  // Mark off starting tile.

        while (true) {
            try {
                Character nextTile = room.get(yCoord + direction.getVector()[1]).get(xCoord + direction.getVector()[0]);

                if (nextTile == '#') {
                    direction = direction.turnRight();
                } else if (nextTile == '.' || nextTile == 'X') {
                    xCoord += direction.getVector()[0];
                    yCoord += direction.getVector()[1];
                    if (nextTile == '.') {
                        room.get(yCoord).set(xCoord, 'X');
                        distinctPositions++;
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
        return distinctPositions;
    }

    private static class Location {
        int x;
        int y;
        Direction direction;

        Location(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Direction getDirection() {
            return direction;
        }
    }
}
