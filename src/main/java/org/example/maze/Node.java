package org.example.maze;

import java.util.Objects;

public class Node implements Comparable<Node> {
    private final MazeLocation2D location;
    private Node parent;
    private double g; // distanceFromStartNode
    private double h; // distanceToTargetNode
    private double f; // g + h

    public Node(MazeLocation2D location, Node parent, double g, double h) {
        this.location = location;
        this.parent = parent;
        this.g = g;
        this.h = h;
        updateF();
    }

    public MazeLocation2D getLocation() {
        return location;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
        updateF();
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
        updateF();
    }

    public double getF() {
        return f;
    }

    private void updateF() {
        f = g + h;
    }

    @Override
    public int compareTo(Node o) {
        if(equals(o)){
            return 0;
        }
        if (f < o.f) {
            return -1;
        } else if (f > o.f) {
            return 1;
        } else {
            if(h < o.h){
                return -1;
            } else if (h >o.h) {
                return 1;
            } else {
                if(g < o.g){
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(location, node.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return "Node{" +
               "location=" + location +
               ", parent=" + parent +
               ", g=" + g +
               ", h=" + h +
               ", f=" + f +
               '}';
    }
}
