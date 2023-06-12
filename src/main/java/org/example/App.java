package org.example;

import org.example.maze.Maze;
import org.example.maze.MazeLocation2D;
import org.example.maze.MazeLocationState;

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

    private static final char WALL_CHARACTER = 'X';
    private static final char PATH_CHARACTER = '0';

    private static final int ROWS = 6;

    private static final int COLS = 6;

    public static void main(String[] args) {
        MazeLocation2D[][] mazeMap = getMazeMapFromFile("mazeForVariant9.txt");
        MazeLocation2D A = new MazeLocation2D(5, 0);
        MazeLocation2D B = new MazeLocation2D(0, 5);
        Maze maze = Maze.constructMaze(mazeMap, true);
        List<MazeLocation2D> path = maze.findPathUsingAStarAlgorithm(A, B);
        printSolvedMaze(mazeMap, path, A, B);
        if(path.isEmpty()){
            System.out.println("There is no path from point A to point B");
        }
    }

    private static MazeLocation2D[][] getMazeMapFromFile(String pathToFile) {
        try (Scanner scanner = new Scanner(new FileInputStream(pathToFile))) {
            MazeLocation2D mazeLocations2D[][] = new MazeLocation2D[ROWS][COLS];
            for (int i = 0; i < ROWS; i++) {
                String row = scanner.nextLine();
                for (int j = 0; j < COLS; j++) {
                    MazeLocationState mazeLocationState;
                    if (row.charAt(j) == PATH_CHARACTER) {
                        mazeLocationState = MazeLocationState.PASSABLE;
                    } else {
                        mazeLocationState = MazeLocationState.NON_PASSABLE;
                    }
                    mazeLocations2D[i][j] = new MazeLocation2D(i, j, mazeLocationState);
                }
            }
            return mazeLocations2D;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printSolvedMaze(MazeLocation2D[][] mazeMap, List<MazeLocation2D> path,
                                        MazeLocation2D startLocation, MazeLocation2D targetLocation) {
        for (int i = 0; i < mazeMap.length; i++) {
            for (int j = 0; j < mazeMap[i].length; j++) {
                if (startLocation.getX() == i && startLocation.getY() == j) {
                    System.out.print(CYAN + "A" + RESET);
                } else if (targetLocation.getX() == i && targetLocation.getY() == j) {
                    System.out.print(CYAN + "B" + RESET);
                } else if (isPartOfPath(path, i, j)) {
                    System.out.print(GREEN + "*" + RESET);
                } else {
                    if (mazeMap[i][j].getLocationState().equals(MazeLocationState.PASSABLE)) {
                        System.out.print(' ');
                    } else {
                        System.out.print(WALL_CHARACTER);
                    }
                }
            }
            System.out.println();
        }
    }

    private static boolean isPartOfPath(List<MazeLocation2D> path, int x, int y) {
        return path.stream().anyMatch(location -> location.getX() == x && location.getY() == y);
    }

}
