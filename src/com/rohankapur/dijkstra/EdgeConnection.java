package com.rohankapur.dijkstra;

/**
 * Created by rohankapur on 8/9/15.
 */
public class EdgeConnection {
    protected final int stepCost;
    protected final VertexNode outgoingVertex;
    protected final VertexNode incomingVertex;

    public EdgeConnection(double bearing, int stepCost, VertexNode outgoingVertex, VertexNode incomingVertex) {
        this.stepCost = stepCost;
        this.outgoingVertex = outgoingVertex;
        this.incomingVertex = incomingVertex;
    }

    public String toString() {
        return "Traverse " + this.outgoingVertex.id + " -> " + this.incomingVertex.id + " (" + this.stepCost + " units cost)";
    }

    public boolean equals(EdgeConnection foe) {
        return (this.outgoingVertex.equals(foe.outgoingVertex) && this.incomingVertex.equals(foe.incomingVertex));
    }
}
