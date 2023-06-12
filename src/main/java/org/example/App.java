package org.example;

import org.example.maze.Maze;
import org.example.maze.MazeLocation2D;
import org.example.maze.MazeLocationState;
import org.example.maze.MazePuzzle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {

    public static final String RESET = "\033[0m";
    public static final String GREEN = "\033[0;32m";
    public static final String CYAN = "\033[0;36m";
    public static final String RED = "\033[0;31m";

    private static final char WALL_CHARACTER = 'X';
    private static final char PATH_CHARACTER = '0';

    private static final char START_POINT_CHARACTER = 'A';
    private static final char TARGET_POINT_CHARACTER = 'B';

    private static final int ROWS = 6;
    private static final int COLS = 6;

    public static void main(String[] args) {
        MazePuzzle mazePuzzle = getMazePuzzleFromFile("mazeForVariant9.txt");
        Maze maze = Maze.constructMaze(mazePuzzle.getMazeMap(), true);
        List<MazeLocation2D> path = maze.findPathUsingAStarAlgorithm(mazePuzzle.getStartPoint(), mazePuzzle.getTargetPoint());
        printSolvedMaze(mazePuzzle, path);
        if (path.isEmpty()) {
            System.out.println("There is no path from point A to point B");
        }
    }

    private static MazePuzzle getMazePuzzleFromFile(String pathToFile) {
        try (Scanner scanner = new Scanner(new FileInputStream(pathToFile))) {
            MazeLocation2D[][] mazeMap = new MazeLocation2D[ROWS][COLS];
            MazeLocation2D startLocation = null;
            MazeLocation2D targetLocation = null;
            for (int i = 0; i < ROWS; i++) {
                String row = scanner.nextLine();
                for (int j = 0; j < COLS; j++) {
                    MazeLocationState mazeLocationState = MazeLocationState.PASSABLE;
                    if (row.charAt(j) == WALL_CHARACTER) {
                        mazeLocationState = MazeLocationState.NON_PASSABLE;
                    } else if (row.charAt(j) == START_POINT_CHARACTER) {
                        startLocation = new MazeLocation2D(i, j);
                    } else if (row.charAt(j) == TARGET_POINT_CHARACTER) {
                        targetLocation = new MazeLocation2D(i, j);
                    }
                    mazeMap[i][j] = new MazeLocation2D(i, j, mazeLocationState);
                }
            }
            return new MazePuzzle(mazeMap, startLocation, targetLocation);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printSolvedMaze(MazePuzzle mazePuzzle, List<MazeLocation2D> path) {
        MazeLocation2D[][] mazeMap = mazePuzzle.getMazeMap();
        for (int i = 0; i < mazeMap.length; i++) {
            for (int j = 0; j < mazeMap[i].length; j++) {
                if (mazePuzzle.getStartPoint().hasSameLocation(i, j)) {
                    printColoredCharacter('A', CYAN);
                } else if (mazePuzzle.getTargetPoint().hasSameLocation(i, j)) {
                    printColoredCharacter('B', CYAN);
                } else if (isPartOfPath(path, i, j)) {
                    printColoredCharacter('*', GREEN);
                } else {
                    if (mazeMap[i][j].getLocationState().equals(MazeLocationState.PASSABLE)) {
                        printColoredCharacter(' ', RESET);
                    } else {
                        printColoredCharacter(WALL_CHARACTER, RED);
                    }
                }
            }
            System.out.println();
        }
    }

    private static void printColoredCharacter(char character, String ansiColor) {
        System.out.print(ansiColor + character + RESET);
    }

    private static boolean isPartOfPath(List<MazeLocation2D> path, int x, int y) {
        return path.stream().anyMatch(location -> location.getX() == x && location.getY() == y);
    }

}
