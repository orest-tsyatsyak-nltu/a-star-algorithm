package org.example.maze;

public class MazePuzzle {
    private final MazeLocation2D[][] mazeMap;
    private final MazeLocation2D startPoint;
    private final MazeLocation2D targetPoint;

    public MazePuzzle(MazeLocation2D[][] mazeMap, MazeLocation2D startPoint, MazeLocation2D targetPoint) {
        this.mazeMap = mazeMap;
        this.startPoint = startPoint;
        this.targetPoint = targetPoint;
    }

    public MazeLocation2D[][] getMazeMap() {
        return mazeMap;
    }

    public MazeLocation2D getStartPoint() {
        return startPoint;
    }

    public MazeLocation2D getTargetPoint() {
        return targetPoint;
    }
}
