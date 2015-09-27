package com.rohankapur.dijkstra;

import com.sun.javafx.geom.Edge;
import sun.security.provider.certpath.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by rohankapur on 7/9/15.
 */
public class Dijkstra {
    public Graph graph;
    private VertexNode sourceNode;
    private VertexNode currentlyExpandedNode;
    private HashSet<VertexNode> expandedNodes = new HashSet<VertexNode>();
    private PriorityQueue<VertexNode> nodesToExpand = new PriorityQueue<VertexNode>();

    // Initialization and Configuration

    public Dijkstra(ArrayList<VertexNode> vertices, ArrayList<EdgeConnection> edges) {
        this.graph = new Graph(vertices, edges);
    }

    private void configureSourceNode(VertexNode presetNode) {
        HashSet<VertexNode> incomingNodes = new HashSet<VertexNode>();
        for (EdgeConnection edge : this.graph.edges) {
            incomingNodes.add(edge.incomingVertex);
        }
        for (VertexNode vertex : this.graph.vertices) {
            if (!incomingNodes.contains(vertex)) {
                this.sourceNode = vertex;
            }
        }
        if (this.sourceNode != null && presetNode == null) {
            this.sourceNode.setIsSourceNode(true);
            repropagateNodesToExpand();
        } else {
            this.sourceNode = presetNode;
            this.sourceNode.setIsSourceNode(true);
            repropagateNodesToExpand();
            // TODO: Error....
        }
    }

    // Path Finding

    public boolean propagateShortestPathFinding(VertexNode sourceNode) {
        if (this.graph.vertices.size() == 0 || this.graph.edges.size() == 0) {
            return false;
        }
        configureSourceNode(sourceNode);
        recursivePathFind();
        return this.graph.shortestPathsFound;
    }

    private void recursivePathFind() {
        if (this.graph.vertices.size() == this.expandedNodes.size()) {
            this.graph.shortestPathsFound = true;
            return;
        }
        if (this.sourceNode == null) {
            return;
        }
        else if (this.currentlyExpandedNode == null || this.expandedNodes.contains(this.currentlyExpandedNode)) {
            VertexNode nodeToExpand = nextNodeToExpand();
            if (nodeToExpand != null) {
                expandNode(nodeToExpand);
            }
            else { return; }
        }
        HashSet<EdgeConnection> outgoingEdges = getEdgesFromSource(this.currentlyExpandedNode);
        for (EdgeConnection edge : outgoingEdges) {
            VertexNode node = edge.incomingVertex;
            ArrayList<EdgeConnection> shorterPath = new ArrayList<EdgeConnection>(this.currentlyExpandedNode.getShortestPath());
            shorterPath.add(getEdgeFromSourceToSink(this.currentlyExpandedNode, node));
            if ((this.sourceNode == shorterPath.get(0).outgoingVertex) && node.offerShorterPath(shorterPath)) {
                reorderNodeInExpansionQueue(node);
            }
        }
        expandedCurrentNode();
        recursivePathFind();
    }

    private void expandNode(VertexNode node) {
        this.currentlyExpandedNode = node;
    }

    private void expandedCurrentNode() {
        this.expandedNodes.add(this.currentlyExpandedNode);
    }

    private VertexNode nextNodeToExpand() {
        return this.nodesToExpand.poll();
    }

    private void repropagateNodesToExpand() {
        this.nodesToExpand = new PriorityQueue<VertexNode>(this.graph.vertices.size(), new VertexNodeComparator());
        for (VertexNode vertex : this.graph.vertices) {
            if (!this.expandedNodes.contains(vertex)) {
                this.nodesToExpand.add(vertex);
            }
        }
    }

    private void reorderNodeInExpansionQueue(VertexNode node) {
        this.nodesToExpand.remove(node);
        this.nodesToExpand.add(node);
    }

    // Graph Data Search/Traversal

    private HashSet<EdgeConnection> getEdgesFromSource(VertexNode vertexNode) {
        HashSet<EdgeConnection> edges = new HashSet<EdgeConnection>();
        for (EdgeConnection edge : this.graph.edges) {
            if (edge.outgoingVertex.equals(vertexNode)) {
                edges.add(edge);
            }
        }
        return edges;
    }

    private HashSet<EdgeConnection> getEdgesToSink(VertexNode vertexNode) {
        HashSet<EdgeConnection> edges = new HashSet<EdgeConnection>();
        for (EdgeConnection edge : this.graph.edges) {
            if (edge.incomingVertex.equals(vertexNode)) {
                edges.add(edge);
            }
        }
        return edges;
    }

    private EdgeConnection getEdgeFromSourceToSink(VertexNode source, VertexNode sink) {
        EdgeConnection retValEdge = null;
        for (EdgeConnection edge : this.graph.edges) {
            if (edge.outgoingVertex.equals(source) && edge.incomingVertex.equals(sink)) {
                retValEdge = edge;
            }
        }
        return retValEdge;
    }
}
