package com.rohankapur.dijkstra;

import com.sun.javafx.geom.Edge;

import java.util.ArrayList;

/**
 * Created by rohankapur on 8/9/15.
 */
public class VertexNode implements Comparable<VertexNode> {
    protected final int id;
    protected double x = 0; // Optional - only for visualization or heuristics
    protected double y = 0; // Optional - only for visualization or heuristics
    public int minimumPathCost;
    private ArrayList<EdgeConnection> shortestPath = new ArrayList<EdgeConnection>();
    private boolean isSourceNode = false;

    protected void setCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected void setIsSourceNode(boolean isSourceNode) {
        this.isSourceNode = isSourceNode;
        recalculateMinimumPathCost();
    }

    // Heuristic Information

    protected double displacementTo(VertexNode foe) { // Only works if x and y are non-zero (unless they are deliberately zero)
        double xDiff = x - foe.x;
        double yDiff = y - foe.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    // Minimum Path Cost

    protected void recalculateMinimumPathCost() {
        this.minimumPathCost = calculatePathCost(this.shortestPath);
    }

    protected int calculatePathCost(ArrayList<EdgeConnection> path) {
        int sum = 0;
        if (path.size() == 0) {
            sum = Integer.MAX_VALUE;
        }
        else {
            for (EdgeConnection edge : path) {
                sum += edge.stepCost;
            }
        }
        return this.isSourceNode ? 0 : sum;
    }

    // Better to call this if you're not sure it will be shorter than the current path (this abstracts away that excess logic)
    protected boolean offerShorterPath(ArrayList<EdgeConnection> shorterPath) {
        if (this.minimumPathCost > calculatePathCost(shorterPath)) {
            setShortestPath(shorterPath);
            return true;
        }
        return false;
    }

    private void setShortestPath(ArrayList<EdgeConnection> shortestPath) {
        this.shortestPath = shortestPath;
        recalculateMinimumPathCost();
    }

    protected ArrayList<EdgeConnection> getShortestPath() {
        return this.shortestPath;
    }

    // HashSet methods

    public boolean equals(VertexNode foe) {
        return (this.id == foe.id);
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public int compareTo(VertexNode o1) {
        return (this.minimumPathCost - o1.minimumPathCost);
    }

    public String toString() {
        return "Vertex " + this.id + " at position (" + x + ", " + y + ")";
    }

    VertexNode(int id) {
        this.id = id;
        recalculateMinimumPathCost();
    }
}
