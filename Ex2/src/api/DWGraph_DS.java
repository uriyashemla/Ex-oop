package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DWGraph_DS implements directed_weighted_graph{

    private HashMap<Integer, node_data> nodes;
    private HashMap<Integer, HashMap<Integer, edge_data>> edges;
    private int Mc;
    private int edgeSize;

    public DWGraph_DS() {
        nodes = new HashMap<Integer, node_data>();
        edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
        Mc = 0;
        edgeSize = 0;
    }

    /**
     * returns the node_data by the node_id
     * @param key - the node_id
     * @return the node_data by the node_id, null if none
     */
    @Override
    public node_data getNode(int key) {
        if (nodes.containsKey(key)) {
            return nodes.get(key);
        }
        return null;
    }

    /**
     * returns the data of the edge (src,dest), null if none
     * Note: this method should run in O(1) time
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        try {
            return edges.get(src).get(dest);
        }
        // if src or dest doesn't exist return null
        catch(NullPointerException e) {
            return null;
        }
    }

    /**
     * adds a new node to the graph with the given node_data
     * Note: this method should run in O(1) time
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if (n == null) {
            throw new RuntimeException("input is null");
        }
        if(nodes.containsKey(n.getKey())) {
            throw new RuntimeException("the graph already contains node with this key: " + n.getKey());
        }
        nodes.put(n.getKey(), n);
        Mc++;
    }

    /**
     * Connects an edge with weight w between node src to node dest
     * Note: this method should run in O(1) time
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        node_data srcN =  nodes.get(src);
        node_data destN =  nodes.get(dest);

        if (srcN == null || destN == null) {
            throw new RuntimeException("one or both of the nodes don't exist");
        }
        if(src == dest) {
            throw new RuntimeException("no edge between a node to himself");
        }
        if (w <= 0) {
            throw new RuntimeException("the weight should be a positive number");
        }

        edge_data e = new EdgeData(src, dest, w);
        // if the edge doesn't exist - create a new one
        if (edges.get(src) == null) {
            edges.put(src, new HashMap<Integer, edge_data>());
            edgeSize++;
            Mc++;
            edges.get(src).put(dest, e);
        }
        // if the edge exist - connect the nodes
        else {
            edges.get(src).put(dest, e);
            edgeSize++;
        }
        Mc++;
    }

    /**
     * This method returns a pointer (shallow copy) for the collection representing all the nodes in the graph
     * Note: this method should run in O(1) time
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        if (nodes.isEmpty()) {
            throw new RuntimeException("the graph is empty and doesn't consist any nodes");
        }
        return nodes.values();
    }

    /**
     *  This method returns a pointer (shallow copy) for the collection representing all the edges getting out of
     * 	the given node (all the edges starting (source) at the given node)
     * 	Note: this method should run in O(k) time, k being the collection size
     * @param node_id
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        node_data n =  nodes.get(node_id);
        if (n == null) {
            throw new RuntimeException("node doesn't exist");
        }

        try {
            if(this.edges.get(node_id) == null);
        }
        catch(Exception e) {
            System.out.println("no edge from this node");
        }


        try {
            return edges.get(node_id).values();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     *  Deletes the node (with the given ID) from the graph and removes all edges which starts or ends at this node
     * 	This method should run in O(k), V.degree=k, as all the edges should be removed
     * @param key
     * @return the data of the removed node (null if none)
     */
    @Override
    public node_data removeNode(int key) {
        if (getNode(key) == null) {
            //throw new RuntimeException("node doesn't exist");
            return null;
        }
        node_data removeN = (NodeData) getNode(key);
        if (removeN != null) {
            nodes.remove(key);
            Mc++;

            if (edges.get(key) != null) {
                edges.remove(key);
            }
        }

        // removes all edges which starts or ends at this node
        for(Iterator<Integer> it1 = nodes.keySet().iterator(); it1.hasNext();) {
            removeEdge(it1.next(), key);
        }
        for(Iterator<Integer> it2 = nodes.keySet().iterator(); it2.hasNext();) {
            removeEdge(key, it2.next());
        }
        return removeN;
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none)
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if(src == dest) {
            throw new RuntimeException("there is no edge with the same node");
        }

        edge_data removeE = this.getEdge(src, dest);
        try {
            if (removeE != null) { // check if the edge exist
                edges.get(src).remove(dest);
                Mc++;
                edgeSize--;
                return removeE;
            }
        }
        catch(Exception e) {
            throw new RuntimeException("error");
        }
        return null;
    }

    /**
     *
     * @return the number of nodes that exist in the graph
     */
    @Override
    public int nodeSize() {
        return nodes.size();
    }

    /**
     *
     * @return the number of edges that exist in the graph
     */
    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    /**
     *
     * @return the number of changes that made on the graph
     */
    @Override
    public int getMC() {
        return this.Mc;
    }

    /**
     * check if 2 graphs are equals
     * @param obj
     * @return true if the graphs are equals, else false
     */
    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof DWGraph_DS)) {
            return false;
        }

        DWGraph_DS g = (DWGraph_DS) obj;

        if (nodeSize() != g.nodeSize()) {
            return false;
        }

        if (edgeSize() != g.edgeSize()) {
            return false;
        }

        if (!nodes.equals(g.nodes)) {
            return false;
        }

        if (!edges.equals(g.edges)) {
            return false;
        }
        return true;
    }
}