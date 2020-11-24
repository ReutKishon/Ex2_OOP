package graph_implementation;

import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.edge_data;
import api.node_data;

import java.util.List;

public class DWGraph_Algo implements dw_graph_algorithms {
    directed_weighted_graph graph;

    @Override
    public void init(directed_weighted_graph g) {
        this.graph = g;
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

    @Override
    public boolean isConnected() {
        return false;
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
