package tests;

import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.node_data;
import graph_implementation.DWGraph_Algo;
import graph_implementation.DWGraph_DS;
import graph_implementation.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    directed_weighted_graph createGraph() {
        directed_weighted_graph g = new DWGraph_DS();
        for (int i = 0; i < 5; i++) {
            node_data n = new Node();
            g.addNode(n);
        }
        g.connect(0, 4, 4);
        g.connect(0, 2, 2);
        g.connect(1, 4, 12);
        g.connect(4, 1, 1);
        g.connect(2, 1, 10);
        g.connect(3, 2, 8);

        return g;
    }


    @Test
    void copy() {
        directed_weighted_graph g = createGraph();
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g);
        directed_weighted_graph copyGraph = ga.copy();
        assertEquals(g, copyGraph);
        g.removeNode(2);
        assertNotEquals(g, copyGraph);
        copyGraph.removeEdge(0, 2);
        copyGraph.removeEdge(3, 2);
        copyGraph.removeEdge(2, 1);
        assertNotEquals(g, copyGraph);
        copyGraph.removeNode(2);
        assertEquals(g,copyGraph);

    }

    @Test
    void isConnected() {
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}