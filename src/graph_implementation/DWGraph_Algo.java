package graph_implementation;

import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.edge_data;
import api.node_data;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph graph;
    private int visitedTag;

    @Override
    public void init(directed_weighted_graph g) {
        this.graph = g;
        visitedTag = 1;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return graph;
    }

    @Override
    public directed_weighted_graph copy() {

        if (graph == null) {
            return null;
        }
        directed_weighted_graph copyGraph = new DWGraph_DS();

        for (node_data n : graph.getV()) {
            //copy the node if it's not created
            if (copyGraph.getNode(n.getKey()) == null) {
                copyGraph.addNode(new Node(n.getKey(), n.getTag(), n.getLocation(), n.getWeight(), n.getInfo()));

            }

            for (edge_data edge : graph.getE(n.getKey())) {
                node_data dest = graph.getNode(edge.getDest());
                //copy neighbor
                if (copyGraph.getNode(dest.getKey()) == null) {
                    copyGraph.addNode(new Node(dest.getKey(), dest.getTag(), dest.getLocation(), dest.getWeight(), dest.getInfo()));

                }

                //copy edge
                if (copyGraph.getEdge(n.getKey(), dest.getKey()) == null) {
                    double weight = graph.getEdge(n.getKey(), dest.getKey()).getWeight();
                    copyGraph.connect(n.getKey(), dest.getKey(), weight);
                }
            }


        }
        return copyGraph;


    }

    // A recursive function to print DFS starting from v
    void DFSUtil(directed_weighted_graph g, int v) {
        // Mark the current node as visited and print it
        g.getNode(v).setTag(visitedTag);
        System.out.print(v + " ");


        // Recur for all the vertices adjacent to this vertex
        for (edge_data edge : g.getE(v)) {
            node_data dest = g.getNode(edge.getDest());
            if (dest.getTag() != visitedTag)
                DFSUtil(g, dest.getKey());
        }
    }


    // Function that returns reverse (or transpose) of this graph
    directed_weighted_graph getTranspose() {
        directed_weighted_graph g = new DWGraph_DS();
        for (node_data v : graph.getV()) {
            // Recur for all the vertices adjacent to this vertex
            for (edge_data edge : graph.getE(v.getKey())) {
                int dest = edge.getDest();
                double weight = edge.getWeight();
                g.addNode(v);
                g.addNode(graph.getNode(dest));
                g.connect(dest, v.getKey(), weight);
            }
        }
        return g;

    }

    void fillOrder(int v, Stack<Integer> stack) {
        // Mark the current node as visited and print it
        graph.getNode(v).setTag(visitedTag);

        // Recur for all the vertices adjacent to this vertex
        Iterator<edge_data> i = graph.getE(v).iterator();

        // Recur for all the vertices adjacent to this vertex
        for (edge_data edge : graph.getE(v)) {
            node_data dest = graph.getNode(edge.getDest());
            if (dest.getTag() != visitedTag)
                fillOrder(dest.getKey(), stack);
        }

        // All vertices reachable from v are processed by now,
        // push v to Stack
        stack.push(v);
    }


    // The main function that finds and prints all strongly
    // connected components
    int sumSCCs() {
        Stack<Integer> stack = new Stack<>();
        int numberOfSCCs = 0;

        // Fill vertices in stack according to their finishing
        // times
        visitedTag++;
        for (node_data v : graph.getV()) {
            if (v.getTag() != visitedTag)
                fillOrder(v.getKey(), stack);
        }

        // Create a reversed graph
        directed_weighted_graph gt = getTranspose();

        visitedTag++;
        // Now process all vertices in order defined by Stack
        while (!stack.empty()) {
            // Pop a vertex from stack
            int v = stack.pop();

            // Print Strongly connected component of the popped vertex
            if (gt.getNode(v).getTag() != visitedTag) {
                DFSUtil(gt, v);
                System.out.println();
                numberOfSCCs++;
            }
        }
        return numberOfSCCs;
    }


    @Override
    public boolean isConnected() {
        return sumSCCs() == 1;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }

}
