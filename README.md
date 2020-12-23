# **Object-Oriented Programming (OOP)**

In this first part of the assignment, we developed infrastructure of data structure, algorithms, and display system.
This assignment deals with the development of a data structure of positive weighted, directed graph.
A weighted graph is a graph in which each edge is given a numerical weight.
A directed graph is a set of objects (called vertices or nodes) that are connected together, where all the edges are directed from one vertex to another.

After implement the data structure, we implement several algorithms on the graph including bonding test (to check if the graph is connected), short path calculation (minimum distance), finding the shortest path (a collection of vertices between the source and the destination) and the ability to save and restore the graph from a file in JSON format.

## First part

At the beginning, we did NodeData class that implement node_data interface (which describe the features of vertex in the graph) and an EdgeData class that implement edge_data interface (which describe the features of edge in the graph).

Then, we did DWGraph_DS class which implements directed_weighted_graph interface. 
In this class, we make functions such as:
`getEdge` that returns the edge between the src to the dest nodes.

`connect` that connect an edge between two nodes, with an edge with weight >=0

Also, other function such as: `addNode`, `removeNode`, `removeEdge` and more.

Then/ after that, we did DWGraph_Algo class that implements dw _graph_algorithms interface (which represent a collection of algorithms on graphs).
In this class, we make functions such as:
`isConnected` that returns true or false whether the graph is connected. 
A graph is connected if every pair of vertices in the graph is connected. This means that there is a path between every pair of vertices.

`shortestPathDist` that returns the length of the shortest path between src to dest.

`save` that saves the weighted (directed) graph to the given file name in JSON format and more.

## Second part

In the second part of the assignments, we used our data structure and the algorithms to implements a game called "*Pokémon challenge*" which include processes, GUI and automatic system for playing against a server.
