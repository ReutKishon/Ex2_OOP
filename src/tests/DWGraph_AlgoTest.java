package tests;

import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.node_data;
import graph_implementation.DWGraph_Algo;
import graph_implementation.DWGraph_DS;
import graph_implementation.Node;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        assertEquals(g, copyGraph);

    }

    @Test
    void isConnected() {
        directed_weighted_graph g = createGraph();
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g);
        assertFalse(ga.isConnected());

        directed_weighted_graph g1 = new DWGraph_DS();
        for (int i = 0; i < 5; i++) {
            node_data n = new Node();
            g1.addNode(n);
        }
        System.out.println();
        g1.connect(1, 0, 1);
        g1.connect(0, 2, 3);
        g1.connect(2, 1, 5);
        g1.connect(0, 3, 11);
        g1.connect(3, 4, 2);
        g1.connect(4, 0, 5);

        ga.init(g1);
        assertTrue(ga.isConnected());


    }


    @Test
    void shortestPathDist() {

        directed_weighted_graph g1 = new DWGraph_DS();
        for (int i = 0; i < 5; i++) {
            node_data n = new Node();
            g1.addNode(n);
        }
        System.out.println();
        g1.connect(1, 0, 1);
        g1.connect(0, 2, 3);
        g1.connect(2, 1, 5);
        g1.connect(0, 3, 11);
        g1.connect(3, 4, 5);
        g1.connect(4, 0, 8);
        g1.connect(4, 3, 20);
        g1.connect(4, 2, 1);


        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g1);
        double res1 = ga.shortestPathDist(4, 1);
        assertEquals(6, res1);
        double res2 = ga.shortestPathDist(4, 3);
        assertEquals(18, res2);
        double res3 = ga.shortestPathDist(4, 0);
        assertEquals(7, res3);

    }

    @Test
    void shortestPath() {

        directed_weighted_graph g1 = new DWGraph_DS();
        for (int i = 0; i < 5; i++) {
            node_data n = new Node();
            g1.addNode(n);
        }
        System.out.println();
        g1.connect(1, 0, 1);
        g1.connect(0, 2, 3);
        g1.connect(2, 1, 5);
        g1.connect(0, 3, 11);
        g1.connect(3, 4, 5);
        g1.connect(4, 0, 8);
        g1.connect(4, 3, 20);
        g1.connect(4, 2, 1);

        dw_graph_algorithms ag0 = new DWGraph_Algo();
        ag0.init(g1);
        List<node_data> sp = ag0.shortestPath(4, 3);
        int[] checkKey = {4, 2, 1, 0, 3};
        int i = 0;
        for (node_data n : sp) {
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }
        List<node_data> sp2 = ag0.shortestPath(4, 0);
        int[] checkKey2 = {4, 2, 1, 0};
        i = 0;
        for (node_data n : sp2) {
            assertEquals(n.getKey(), checkKey2[i]);
            i++;
        }
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}