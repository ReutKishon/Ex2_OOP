# Ex2_OOP

The main topic is developing logic for a game where a group of robots needs to perform movement on directed graph. Our game represented by JFrame.

Game rules: The agents earn points while collecting a pokemons. Each pokemon hass different score.

This project is made up of several classes:
Pokemon - This class represents the Pokemon that the robot is supposed to collect during the game. 
Robot - This class represents the "robots" that supposed to collect the pokemons during the game. StdDraw - for representing the game graphically.

MyGui , myPanel - implements the interface "graph" and represents a directional weighted graph.

The interface has a road-system or communication network in mind - and should support a large number of nodes (over 100,000).
The implementation based on an efficient compact representation.
DWGraph_Algo – Implements the interface "graph_algorithms", represents bunch of algorithms based on the graph we've created in DGraph.
Node - Implements the interface node_data, represents a vertex in our graph
Edge - Implements the interface edge_data, represents a vertex in our graph
DWGraph_DS : represents our graph.
Our graph have few methods:

getV - returns a collection that contains all the vertecis.
getE - returns a collection of edges of a given vertex key.
removeNode - remove the node and all the linked edges.
removeEdge - remove the edge with a source and destination as input.
addNode - add a node to the graph.
getNode - returns the node with a given node key.
connect - initialize an edge from source node to destination with weight as input.
getEdge - initialize the edge of source and destination as input.
Graph_Algo class have few methods:

shortestPathDist - returns the shortest distance from source to destination using dijkstra algorithem.
shortestPath - returns the shortest path from the source to destination using dijkstra algorithem.
init - initialize a graph from an input file.
save - save a graph to a json file.
isConnected - checks whether the graph is strongly connected.
For more information you can see our Wiki pages!

