package com.rohankapur.dijkstra;

import java.util.ArrayList;

/**
 * Created by rohankapur on 8/9/15.
 */
public class Graph {
    protected ArrayList<VertexNode> vertices = new ArrayList<VertexNode>();
    protected ArrayList<EdgeConnection> edges = new ArrayList<EdgeConnection>();
    public boolean shortestPathsFound = false;

    protected VertexNode getVertexById(int id) {
        VertexNode retVal = null;
        for (VertexNode node : this.vertices) {
            if (node.id == id) {
                retVal = node;
            }
        }
        return retVal;
    }

    Graph(ArrayList<VertexNode> vertices, ArrayList<EdgeConnection> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    @Override
    public String toString() {
        return ("" + this.vertices.size() + " nodes and " + this.edges.size() + " edges");
    }
}
