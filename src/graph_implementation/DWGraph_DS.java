package graph_implementation;

import api.*;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {

    HashMap<Integer, node_data> nodes;
    HashMap<Integer, HashMap<Integer, edge_data>> outEdges; // all the edges that started from key
    HashMap<Integer, HashMap<Integer, edge_data>> inEdges;   // all the edges that ended with key

    private int modifyCount;
    private int edgesSize;

    public DWGraph_DS() {
        edgesSize = 0;
        modifyCount = 0;
        nodes = new HashMap<>();
        outEdges = new HashMap<>();
        inEdges = new HashMap<>();
        Node.count = 0;
    }


    @Override

    public node_data getNode(int key) {
        if (!nodes.containsKey(key)) return null;
        return nodes.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if (!outEdges.containsKey(src)) return null;
        if (!outEdges.get(src).containsKey(dest)) return null; // O(1)
        return outEdges.get(src).get(dest);
    }

    @Override
    public void addNode(node_data n) {
        if (nodes.containsKey(n.getKey())) return; //O(1)
        nodes.put(n.getKey(), n);
        HashMap<Integer, edge_data> destNodes = new HashMap<>();
        HashMap<Integer, edge_data> srcNodes = new HashMap<>();

        //update the edges maps with new empty map that represents their neighbors.
        outEdges.put(n.getKey(), destNodes); //O(1)
        inEdges.put(n.getKey(), srcNodes); //O(1)

        // adding a new node to the graph - changes the graph
        modifyCount++;

    }

    @Override
    public void connect(int src, int dest, double w) {
        //A node connected to itself is forbidden.
        if (src == dest) return;
        // if the edge {src,dest} doesn't exist , then connect them
        if (!outEdges.get(src).containsKey(dest)) { // O(1)
            edge_data edgeData = new Edge(src, dest, w);
            outEdges.get(src).put(dest, edgeData);
            inEdges.get(dest).put(src, edgeData);
            edgesSize++;
            //adding an edge changes the graph.
            modifyCount++;
        }


    }

    @Override
    public Collection<node_data> getV() {
        return nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return outEdges.get(node_id).values();
    }

    @Override
    public node_data removeNode(int key) {
        // try to remove node that doesn't exist
        if (!nodes.containsKey(key)) { // O(1)
            return null;
        }
        // removing node from the graph changes the graph
        modifyCount++;
        // run over all the edges that starts with key
        for (int dest : outEdges.get(key).keySet()) {
            inEdges.get(dest).remove(key);
            edgesSize--;
            // removing edge from the graph changes the graph
            modifyCount++;
        }

        // run over all the edges that ends with key
        for (int src : inEdges.get(key).keySet()) {
            outEdges.get(src).remove(key);
            edgesSize--;
            // removing edge from the graph changes the graph
            modifyCount++;
        }

        outEdges.remove(key);
        inEdges.remove(key);
        node_data deletedNode = getNode(key);
        nodes.remove(key);


        return deletedNode;

    }


    @Override
    public edge_data removeEdge(int src, int dest) {
        //  try to remove edge that doesn't exist O(1)
        if (!outEdges.get(src).containsKey(dest)) return null;

        edge_data deleteEdge = outEdges.get(src).get(dest);
        // remove  node1 and node2 from each other's neighbors list and decrease the edgeSize in 1
        outEdges.get(src).remove(dest);
        inEdges.get(dest).remove(src);
        //decrease edgeSize
        edgesSize--;
        // removing edge from the graph changes the graph
        modifyCount++;
        return deleteEdge;
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgesSize;
    }

    @Override
    public int getMC() {
        return modifyCount;
    }




    public boolean equals(Object obj) {

        if (getClass() != obj.getClass())
            return false;
        directed_weighted_graph anotherGraph = (directed_weighted_graph) obj;

        if (this.nodeSize() != anotherGraph.nodeSize() || this.edgesSize != anotherGraph.edgeSize()) {
            return false;
        }

        for (node_data n : this.getV()) {

            // if anotherGraph does not contain n
            if (anotherGraph.getNode(n.getKey()) == null) {

                return false;
            }

            for (edge_data edge : this.getE(n.getKey())) {

                if (anotherGraph.getNode(edge.getDest()) == null) return false;

                //if anotherGraph does not contain the edge {n,dest}
                if (anotherGraph.getEdge(n.getKey(), edge.getDest())== null) {
                    return false;
                }
            }


        }

        return true;
    }
}
