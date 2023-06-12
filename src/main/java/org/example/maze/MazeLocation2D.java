package org.example.maze;

import java.util.Objects;

public final class MazeLocation2D {
    private final int x;
    private final int y;
    private final MazeLocationState locationState;

    public MazeLocation2D(int x, int y, MazeLocationState locationState) {
        this.x = x;
        this.y = y;
        this.locationState = locationState;
    }

    public MazeLocation2D(int x, int y) {
        this.x = x;
        this.y = y;
        this.locationState = MazeLocationState.PASSABLE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MazeLocation2D) obj;
        return this.x == that.x &&
               this.y == that.y &&
               Objects.equals(this.locationState, that.locationState);
    }

    @Override
    public String toString() {
        return "MazeLocation2D{" +
               "x=" + x +
               ", y=" + y +
               '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MazeLocationState getLocationState() {
        return locationState;
    }

    public double distanceTo(MazeLocation2D other) {
        return Math.sqrt(Math.pow((other.x - x), 2) + Math.pow((other.y - y), 2));
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, locationState);
    }


}
