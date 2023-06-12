package org.example.maze;

import org.example.SortedList;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

public class Maze extends SimpleGraph<MazeLocation2D, DefaultEdge> {

    private Maze() {
        super(DefaultEdge.class);
    }

    public static Maze constructMaze(MazeLocation2D[][] mazeLocations2D, boolean withDiagonalPaths) {
        Maze maze = new Maze();
        for (int rowI = 0; rowI < mazeLocations2D.length; rowI++) {
            MazeLocation2D[] row = mazeLocations2D[rowI];
            for (int colI = 0; colI < row.length; colI++) {
                MazeLocation2D location = row[colI];
                if (!isWall(location)) {
                    maze.addVertex(location);
                    boolean hasColumnBefore = colI > 0;
                    if (hasColumnBefore) {
                        MazeLocation2D previousLocationInRow = row[colI - 1];
                        if (!isWall(previousLocationInRow)) {
                            maze.addEdge(location, previousLocationInRow);
                        }
                    }
                    boolean hasRowBefore = rowI > 0;
                    if (hasRowBefore) {
                        MazeLocation2D upperLocation = mazeLocations2D[rowI - 1][colI];
                        if (!isWall(upperLocation)) {
                            maze.addEdge(location, upperLocation);
                        }
                        if (withDiagonalPaths) {
                            if (hasColumnBefore) {
                                MazeLocation2D leftUpperLocation = mazeLocations2D[rowI - 1][colI - 1];
                                if (!isWall(leftUpperLocation)) {
                                    maze.addEdge(location, leftUpperLocation);
                                }
                            }
                            boolean hasColumnAfter = colI + 1 < row.length;
                            if (hasColumnAfter) {
                                MazeLocation2D rightUpperLocation = mazeLocations2D[rowI - 1][colI + 1];
                                if (!isWall(rightUpperLocation)) {
                                    maze.addEdge(location, rightUpperLocation);
                                }
                            }
                        }
                    }
                }
            }
        }
        return maze;
    }

    private static boolean isWall(MazeLocation2D mazeLocation2D) {
        return mazeLocation2D.getLocationState().equals(MazeLocationState.NON_PASSABLE);
    }

    public List<MazeLocation2D> findPathUsingAStarAlgorithm(MazeLocation2D startLocation, MazeLocation2D targetLocation) {
        SortedList<Node> openList = new SortedList<>();
        Set<Node> closedList = new TreeSet<>();
        openList.offer(new Node(startLocation, null, 0, startLocation.distanceTo(targetLocation)));
        Node currentNode = null;
        while (!openList.isEmpty()) {
            currentNode = openList.poll();
            closedList.add(currentNode);
            if (currentNode.getLocation().equals(targetLocation)) {
                break;
            }
            for (var neighbourNode : getNeighbours(currentNode, targetLocation)) {
                if (closedList.contains(neighbourNode)) {
                    continue;
                }
                if (openList.contains(neighbourNode)) {
                    Node savedNode = openList.get(neighbourNode);
                    if (neighbourNode.getG() < savedNode.getG()) {
                        openList.remove(neighbourNode);
                        savedNode.setG(neighbourNode.getG());
                        savedNode.setParent(currentNode);
                        openList.offer(savedNode);
                    }
                } else {
                    openList.offer(neighbourNode);
                }
            }
        }
        if (currentNode != null && currentNode.getLocation().equals(targetLocation)) {
            return constructPath(currentNode);
        } else {
            return Collections.emptyList();
        }
    }

    private List<Node> getNeighbours(Node currentNode, MazeLocation2D targetLocation) {
        return edgesOf(currentNode.getLocation()).stream()
                .map(defaultEdge -> getNeighbourVertex(defaultEdge, currentNode.getLocation()))
                .map(locationToNodeMapper(currentNode, targetLocation))
                .toList();
    }

    private static Function<MazeLocation2D, Node> locationToNodeMapper(Node currentNode, MazeLocation2D targetLocation) {
        return neighbourLocation -> new Node(neighbourLocation,
                currentNode,
                currentNode.getG() + currentNode.getLocation().distanceTo(neighbourLocation),
                neighbourLocation.distanceTo(targetLocation));
    }

    private MazeLocation2D getNeighbourVertex(DefaultEdge edge, MazeLocation2D currentVertex) {
        MazeLocation2D edgeSource = getEdgeSource(edge);
        return edgeSource.equals(currentVertex) ? getEdgeTarget(edge) : edgeSource;
    }

    private List<MazeLocation2D> constructPath(Node nodeWithTargetLocation) {
        List<MazeLocation2D> path = new LinkedList<>();
        if (nodeWithTargetLocation == null) {
            return path;
        }
        Node current = nodeWithTargetLocation;
        while (current.getParent() != null) {
            path.add(current.getLocation());
            current = current.getParent();
        }
        path.add(current.getLocation());
        Collections.reverse(path);
        return path;
    }


}
