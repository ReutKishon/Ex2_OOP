package graph_implementation;

import api.*;

import java.io.*;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import gameClient.util.Point3D;
import json.DWGraphJsonAdapter;

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
                Point3D p = new Point3D(n.getLocation().x(), n.getLocation().y(), n.getLocation().z());
                copyGraph.addNode(new Node(n.getKey(), n.getTag(), p, n.getWeight(), n.getInfo()));

            }

            for (edge_data edge : graph.getE(n.getKey())) {
                node_data dest = graph.getNode(edge.getDest());
                //copy neighbor
                if (copyGraph.getNode(dest.getKey()) == null) {
                    Point3D p = new Point3D(dest.getLocation().x(), dest.getLocation().y(), dest.getLocation().z());

                    copyGraph.addNode(new Node(dest.getKey(), dest.getTag(), p, dest.getWeight(), dest.getInfo()));

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


    public void Dijkstra(int src, HashMap<Integer, Double> distances, HashMap<Integer, Integer> parents) {
        PriorityQueue<Pair> priorityQueue = new PriorityQueue<Pair>(Comparator.comparing(Pair::getWeight));
        visitedTag++;
        // Add source node to the priority queue. A distance from src to itself is zero.
        priorityQueue.add(new Pair(src, 0.0));

        // Distance to the source is 0
        distances.put(src, 0.0);
        // checking for all the possible shortest path to every node from src , hench src does not have parent.
        parents.put(src, -1);

        int i = 0;
        while (i != graph.nodeSize() && !priorityQueue.isEmpty()) {

            // remove the minimum distance node
            // from the priority queue
            int u = priorityQueue.remove().getNode();

            // adding the node whose distance is
            // finalized
//            settled.add(u);
            graph.getNode(u).setTag(visitedTag);
            i++;

            NeighboursProcess(u, distances, priorityQueue, parents);
        }
    }

    // Function to process all the neighbours
    // of the passed node
    private void NeighboursProcess(int u, HashMap<Integer, Double> dist, PriorityQueue<Pair> pq, HashMap<Integer, Integer> parents) {


        // All the neighbors of v
        for (edge_data edge : graph.getE(u)) {
            node_data dest = graph.getNode(edge.getDest());
            // If current node hasn't already been processed
            if (dest.getTag() != visitedTag) {
                double edgeDistance = edge.getWeight();
                double newDistance = dist.get(u) + edgeDistance;

                // If new distance is cheaper in cost
                if (!dist.containsKey(dest.getKey()) || newDistance < dist.get(dest.getKey())) {
                    dist.put(dest.getKey(), newDistance);
                    //updating the parent of neighbor to u in order to restore the path later
                    parents.put(dest.getKey(), u);

                }

                // Add the current node to the queue
                pq.add(new Pair(dest.getKey(), dist.get(dest.getKey())));
            }
        }
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        if (graph == null) return -1;
        HashMap<Integer, Double> distances = new HashMap<>();
        HashMap<Integer, Integer> parents = new HashMap<>();
        // start dijkstra
        Dijkstra(src, distances, parents);
        // if there is no path between src to dest
        if (!distances.containsKey(dest)) {
            return -1;
        }

        return distances.get(dest);
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {

        if (graph == null) return null;

        HashMap<Integer, Double> distances = new HashMap<>();
        HashMap<Integer, Integer> parents = new HashMap<>();
        Dijkstra(src, distances, parents);
        if (distances.size() == 1 || !distances.containsKey(dest)) {
            return null;
        }
        List<node_data> path = new LinkedList<>();
        int tail = dest;
        path.add(graph.getNode(tail));
        while (parents.get(tail) != -1) {
            path.add(graph.getNode(parents.get(tail)));
            tail = parents.get(tail);
        }
        Collections.reverse(path);
        return path;
    }

    //
//    @Override
//    public boolean save(String file) {
//
//        try (Writer writer = new FileWriter(file)) {
//            Gson gson = new Gson();
//
//            gson.toJson(graph, writer);
//            writer.close();
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//
//
//    }
    @Override
    public boolean save(String file) {

        try (Writer writer = new FileWriter(file)) {
            var gsonBuilder = new GsonBuilder().setPrettyPrinting();

            gsonBuilder.registerTypeAdapter(DWGraph_DS.class, new DWGraphJsonAdapter());

            var gson = gsonBuilder.create();
            gson.toJson(graph, writer);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;


    }

    @Override
    public boolean load(String file) {

        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream((file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DWGraph_DS.class, new DWGraphJsonAdapter());
        var gson = gsonBuilder.create();

        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        graph = gson.fromJson(reader, DWGraph_DS.class);

        return true;
    }


    private static class Pair {
        private int node;
        private double weight;

        public Pair(int key, double weight) {
            this.node = key;
            this.weight = weight;
        }

        public double getWeight() {
            return weight;
        }

        public int getNode() {
            return node;
        }

    }

    public static class ABCAdapterFactory implements TypeAdapterFactory {
        private final Class<? extends geo_location> implementationClass;

        public ABCAdapterFactory(Class<? extends geo_location> implementationClass) {
            this.implementationClass = implementationClass;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (!geo_location.class.equals(type.getRawType())) return null;

            return (TypeAdapter<T>) gson.getAdapter(implementationClass);
        }
    }

}


