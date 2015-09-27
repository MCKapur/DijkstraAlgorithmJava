package com.rohankapur.dijkstra;

import sun.security.provider.certpath.Vertex;

import javax.swing.JFrame;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static VertexNode genVertex(int id) {
        return new VertexNode(id);
    }

    private static EdgeConnection genEdge(int stepCost, VertexNode source, VertexNode sink) {
        return new EdgeConnection(0, stepCost, source, sink);
    }

    public static void main(String[] args) {
        // Creating Graph
        ArrayList<VertexNode> vertices = new ArrayList<VertexNode>();
        ArrayList<EdgeConnection> edges = new ArrayList<EdgeConnection>();
        // Adding Data
        Random random = new Random();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter vertex population:");
        int nodeCount = in.nextInt() + 1;
        System.out.println("Enter edge population growth rate (0-1):");
        double minDistance = Math.sqrt((5/(1-in.nextDouble())) / nodeCount);
        for (int i = 0; i < nodeCount; i++) {
            VertexNode vertex = genVertex(i);
            double x = 0.045, y = 0.045;
            if (i > 0) {
                x = random.nextDouble();
                y = random.nextDouble();
            }
            vertex.setCoordinate(x, y);
            vertices.add(vertex);
        }
        for (int p1 = 0; p1 < nodeCount; p1++) {
            for (int p2 = p1 + 1; p2 < nodeCount; p2++) {
                VertexNode vertex1 = vertices.get(p1);
                VertexNode vertex2 = vertices.get(p2);
                double displacement = vertex1.displacementTo(vertex2);
                if (displacement < minDistance && (random.nextDouble() >= 0.5)) {
                    double d = 50.0 * (displacement + 1.0);
                    edges.add(genEdge((int)Math.round(d), vertex1, vertex2));
                    if (random.nextDouble() >= 0.5)  {
                        edges.add(genEdge((int)Math.round(d), vertex2, vertex1));
                    }
                }
            }
        }
        // Running Dijkstra algorithm
        Dijkstra dijkstra = new Dijkstra(vertices, edges);
        System.out.println(dijkstra.graph);
        // Entering source
        System.out.println("Enter a source node id:");
        VertexNode sourceNode = dijkstra.graph.getVertexById(in.nextInt());
        // Dijkstra!
        System.out.println("Propagating shortest path algorithm...");
        long startTime = System.nanoTime();
        boolean success = dijkstra.propagateShortestPathFinding(sourceNode);
        long endTime = System.nanoTime();
        System.out.println("Dijkstra search " + (success ? "completed" : "failed") + (success ? (" in " + (endTime - startTime) / 1000000000.000000000d + " s") : ""));
        while (success) {
            // Entering destination
            System.out.println("Enter a destination node id:");
            int nodeDestinationId = in.nextInt();
            // Outputting path
            System.out.println("Shortest path from source to node " + nodeDestinationId + " is:");
            VertexNode destinationNode = dijkstra.graph.getVertexById(nodeDestinationId);
            ArrayList<EdgeConnection> shortestPath = destinationNode.getShortestPath();
            if (shortestPath.size() == 0 || destinationNode.minimumPathCost == Integer.MAX_VALUE) {
                System.out.println("No path found.");
            }
            else {
                for (int i = 0; i < shortestPath.size(); i++) {
                    System.out.println(shortestPath.get(i));
                }
                System.out.println("Total travel cost of " + destinationNode.minimumPathCost);
                // Visualization of path
                JFrame frame = new JFrame();
                final int FRAME_WIDTH = 600;
                final int FRAME_HEIGHT = 650;
                frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                frame.setTitle("Dijkstra's Algorithm Demo");
                DijkstraVisual panel = new DijkstraVisual(dijkstra.graph.vertices, dijkstra.graph.edges, shortestPath);
                frame.add(panel);
                frame.setVisible(true);
            }
        }
        if (!success)
            System.out.println("Error in dijkstra's path finding algorithm execution.");
    }
}
