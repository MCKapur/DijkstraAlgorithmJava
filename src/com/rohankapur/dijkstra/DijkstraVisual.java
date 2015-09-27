package com.rohankapur.dijkstra;

/**
 * Created by rohankapur on 13/9/15.
 */

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DijkstraVisual extends JPanel {
    private ArrayList<VertexNode> vertices;
    private ArrayList<EdgeConnection> edges;
    private ArrayList<EdgeConnection> path;

    public DijkstraVisual(ArrayList<VertexNode> vertices, ArrayList<EdgeConnection> edges, ArrayList<EdgeConnection> path) {
        super();
        this.vertices = vertices;
        this.path = path;
        this.edges = edges;
        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        double panelWidth = getWidth();
        double panelHeight = getHeight();

        g2.setColor(Color.white);
        g2.fill(new Rectangle2D.Double(0, 0, panelWidth, panelHeight));
        g2.setColor(Color.black); // Edges
        for (EdgeConnection e : edges) {
            g2.draw(new Line2D.Double(e.outgoingVertex.x * panelWidth,
                    e.outgoingVertex.y * panelHeight,
                    e.incomingVertex.x * panelWidth,
                    e.incomingVertex.y * panelHeight));
        }
        g2.setColor(Color.blue); // Points
        for (VertexNode p : vertices) {
            Ellipse2D circle = new Ellipse2D.Double();
            circle.setFrameFromCenter(
                    p.x * panelWidth,
                    p.y * panelHeight,
                    p.x * panelWidth + panelWidth * 0.25 / this.vertices.size(),
                    p.y * panelHeight + panelHeight * 0.25 / this.vertices.size());
            g2.fill(circle);
            g2.draw(circle);
        }
        g2.setFont((new Font("HelveticaNeue-Bold", Font.BOLD, 15)));
        for (int i = 0; i < path.size(); i++) {
            g2.setColor(Color.yellow); // Single source shortest path edges
            EdgeConnection e = path.get(i);
            g2.setStroke(new BasicStroke(2));
            g2.draw(new Line2D.Double(e.outgoingVertex.x * panelWidth,
                    e.outgoingVertex.y * panelHeight,
                    e.incomingVertex.x * panelWidth,
                    e.incomingVertex.y * panelHeight));
            g2.setColor(Color.yellow);
            if (i == 0) {
                g2.drawString("start node " + e.outgoingVertex.id, Math.round(e.outgoingVertex.x * panelWidth), Math.round(e.outgoingVertex.y * panelHeight * 0.97));
                if (i == path.size() - 1) {
                    g2.drawString(((i == path.size() - 1) ? "end " : "") + "node " + e.incomingVertex.id, Math.round(e.incomingVertex.x * panelWidth), Math.round(e.incomingVertex.y * panelHeight * 1.03));
                }
            }
            else {
                g2.drawString(((i == path.size() - 1) ? "end " : "") + "node " + e.incomingVertex.id, Math.round(e.incomingVertex.x * panelWidth), Math.round(e.incomingVertex.y * panelHeight * 1.03));
            }
        }
    }
}

