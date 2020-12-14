package tests;

import api.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

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
    void getEdge() {
        directed_weighted_graph g = createGraph();
        edge_data edge = g.getEdge(4, 1);
        assertEquals(4, edge.getSrc());
        assertEquals(1, edge.getDest());
        assertEquals(1, edge.getWeight());
    }

    @Test
    void addNode() {

    }

    @Test
    void connect() {
        directed_weighted_graph g = createGraph();
        g.connect(3, 0, 2);
        g.connect(3, 4, 6);
        assertEquals(8, g.edgeSize());

    }


    @Test
    void getE() {
        directed_weighted_graph g = createGraph();
        Collection<edge_data> res = g.getE(0);
        for (edge_data e : res) {
            System.out.println("src: " + e.getSrc() + ", dest: " + e.getDest() + ", weight: " + e.getWeight());
        }
    }

    @Test
    void removeNode() {
        directed_weighted_graph graph = createGraph();
        graph.removeNode(2);
        assertEquals(3, graph.edgeSize());
        assertEquals(4, graph.nodeSize());

        assertNull(graph.getEdge(0, 2));
        assertNull(graph.getEdge(3, 2));
        assertNull(graph.getEdge(2, 1));

        graph.removeNode(4);
        assertEquals(0, graph.edgeSize());
        assertEquals(3, graph.nodeSize());

        assertNull(graph.getEdge(4, 1));
        assertNull(graph.getEdge(1, 4));
        assertNull(graph.getEdge(0, 4));


    }

    @Test
    void removeEdge() {
        directed_weighted_graph graph = createGraph();
        graph.removeEdge(2, 1);
        assertNull(graph.getEdge(2, 1));
        assertEquals(5, graph.edgeSize());
    }

    @Test
    void nodeSize() {
        directed_weighted_graph graph = createGraph();
        assertEquals(5, graph.nodeSize());
        graph.removeNode(3);
        graph.removeNode(2);

        assertEquals(3, graph.nodeSize());
        node_data n = new Node();
        graph.addNode(n);
        assertEquals(4, graph.nodeSize());

    }

    @Test
    void edgeSize() {
        directed_weighted_graph graph = createGraph();
        assertEquals(6, graph.edgeSize());
        graph.removeNode(3);
        graph.removeNode(2);

        assertEquals(3, graph.edgeSize());
        graph.connect(0, 1, 4);
        assertEquals(4, graph.edgeSize());
    }

    @Test
    void getMC() {
        directed_weighted_graph graph = createGraph();
        assertEquals(11, graph.getMC());
        graph.removeEdge(3, 2);
        assertEquals(12, graph.getMC());
        graph.removeNode(2);
        assertEquals(15, graph.getMC());


    }
}