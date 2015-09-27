package com.rohankapur.dijkstra;

import java.util.Comparator;

/**
 * Created by rohankapur on 8/9/15.
 */
public class VertexNodeComparator implements Comparator<VertexNode> {
    @Override
    public int compare(VertexNode o1, VertexNode o2) {
        return o1.compareTo(o2);
    }
}
