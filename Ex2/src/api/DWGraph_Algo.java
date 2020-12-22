package api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

import com.google.gson.*;

public class DWGraph_Algo implements dw_graph_algorithms{

    private directed_weighted_graph graphAlgo;

    public DWGraph_Algo(){
        this.graphAlgo = new DWGraph_DS();
    }

    public DWGraph_Algo (directed_weighted_graph g){
        this.graphAlgo = g;
    }

    /**
     * Init the graph on which this set of algorithms operates on
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.graphAlgo = g;
    }

    /**
     * Return the underlying graph of which this class works
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.graphAlgo;
    }

    /**
     * Compute a deep copy of this weighted graph to a new graph
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph g = new DWGraph_DS();
        if (this.graphAlgo == null) {
            return null;
        }
        for (node_data n : this.graphAlgo.getV()) {
            if (n != null) {
                // create new node for deep copy
                node_data n1 = new NodeData(n.getKey(),n.getLocation(), n.getWeight(),n.getInfo(), n.getTag());
                g.addNode(n1);
            }
        }
        // copy the edges
        for (node_data n : this.graphAlgo.getV()) {
            if (this.graphAlgo.getE(n.getKey()) != null) {
                for (edge_data e : this.graphAlgo.getE(n.getKey())) {
                    if (e != null) {
                        g.connect(e.getSrc(), e.getDest(), e.getWeight());
                    }
                }
            }
        }
        return g;
    }

    /**
     * This method check if the graph is connected.
     * In this method, we are using BFS algorithm. First, we are checking the graph from one side and
     * then we creat a new graph and checking the oppsite side of the graph.
     * NOTE: assume directional graph (all n*(n-1) ordered pairs)
     * @return true if and only if there is a valid path from each node to each
     * other node, else return false
     */
    @Override
    public boolean isConnected() {
        //the graph is already connected
        if (this.graphAlgo.nodeSize() <= 1) {
            return true;
        }
        //checking if the graph is connected from one side to another by BFS algorithm
        Queue<node_data> Tor = new LinkedList<node_data>();
        boolean flag = true;
        Iterator<node_data> itr = this.graphAlgo.getV().iterator();
        node_data h = itr.next();
        h.setInfo("Black");
        Tor.add(h);
        while (!Tor.isEmpty()) {
            node_data cur = Tor.poll();
            try {
                Iterator<edge_data> itr1 = this.graphAlgo.getE(cur.getKey()).iterator();
                while (itr1.hasNext()) {
                    node_data nexi = this.graphAlgo.getNode(itr1.next().getDest());
                    if (nexi.getInfo().equals("White")) {
                        Tor.add(nexi);
                        nexi.setInfo("Black");
                    }
                }
            }
            catch(Exception e){
            }
        }
        //if the graph isn't connected return false
        while (itr.hasNext()) {
            if (itr.next().getInfo().equals("White")) {
                flag = false;
                break;
            }
        }
        Iterator<node_data> itr3 = this.graphAlgo.getV().iterator();
        while (itr3.hasNext()) {
            itr3.next().setInfo("White");
        }
        if (flag == false) {
            return flag;
        }

        //creating a copied graph with the same nodes and edges but they are going backwards (The edges are turn to the opposite direction)
        directed_weighted_graph thenewgraph = new DWGraph_DS();
        Iterator<node_data> itr4 = this.graphAlgo.getV().iterator();
        while (itr4.hasNext()) {
            node_data w = itr4.next();
            thenewgraph.addNode(w);
        }
        Iterator<node_data> itr5 = this.graphAlgo.getV().iterator();
        while (itr5.hasNext()) {
            node_data thesrc = itr5.next();
            try {
                Iterator<edge_data> itr2 = this.graphAlgo.getE(thesrc.getKey()).iterator();
                while (itr2.hasNext()) {
                    edge_data thedest = itr2.next();
                    thenewgraph.connect(thedest.getDest(),thesrc.getKey(), this.graphAlgo.getEdge(thesrc.getKey(), thedest.getDest()).getWeight());
                }
            }
            catch(Exception e) {
            }
        }

        //checking if the backwards edges is connected by BFS algorithm
        Queue<node_data> Tor1 = new LinkedList<node_data>();
        boolean flag1 = true;
        Iterator<node_data> itr6 = thenewgraph.getV().iterator();
        node_data back = itr6.next();
        back.setInfo("Black");
        Tor1.add(back);
        while (!Tor1.isEmpty()) {
            node_data cur = Tor1.poll();
            try {
                Iterator<edge_data> itr7 = thenewgraph.getE(cur.getKey()).iterator();
                while (itr7.hasNext()) {
                    node_data nexi = thenewgraph.getNode(itr7.next().getDest());
                    if (nexi.getInfo().equals("White")) {
                        Tor1.add(nexi);
                        nexi.setInfo("Black");
                    }
                }
            }
            catch(Exception e) {
            }
        }

        //if the backwards graph isn't connected return false and if it is return true
        Iterator<node_data> itr8 = thenewgraph.getV().iterator();
        while (itr8.hasNext()){
            if(itr8.next().getInfo().equals("White")){
                flag1 = false;
                break;
            }
        }
        return (flag&flag1);
    }

    /**
     * This method check the distance of the shortest path between the source node to the dest node.
     * In this method, we are using BFS algorithm to set each node it's own weight from the src node
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return the length of the shortest path between src to dest
     */
    @Override
    public double shortestPathDist (int src, int dest){
        double shortest = -1;
        if(graphAlgo.getNode(src) == null || graphAlgo.getNode(dest) == null){
            return shortest;
        }
        if(src == dest){
            return 0;
        }

        //set for each node its own weight from the src node by BFS algorithm
        Queue<node_data> q = new LinkedList<node_data>();
        q.add(graphAlgo.getNode(src));
        q.peek().setWeight(0);
        while (!q.isEmpty()) {
            node_data cur = q.poll();
            if (cur.getInfo()==null||!cur.getInfo().equals("Black")) {
                double x = cur.getWeight();
                try {
                    Iterator<edge_data> itr1 = this.graphAlgo.getE(cur.getKey()).iterator();
                    while (itr1.hasNext()) {
                        node_data nex = this.graphAlgo.getNode(itr1.next().getDest());
                        if (nex.getInfo().equals("White")) {
                            q.add(this.graphAlgo.getNode(nex.getKey()));
                            nex.setInfo("Grey");
                        }
                        if (nex.getWeight() > (x + this.graphAlgo.getEdge(cur.getKey(), nex.getKey()).getWeight()) || nex.getWeight() == -1) {
                            nex.setWeight((x + this.graphAlgo.getEdge(cur.getKey(), nex.getKey()).getWeight()));
                        }
                    }
                }
                catch (Exception e){
                }
                cur.setInfo("Black");
            }
        }

        //saves to int the smallest weight that found from src node to dest node
        if(graphAlgo.getNode(src).getWeight() != -1 && graphAlgo.getNode(dest).getWeight() != -1) {
            shortest = graphAlgo.getNode(dest).getWeight();
        }

        //sets all the nodes to the original initialize
        Iterator<node_data> itr3 = this.graphAlgo.getV().iterator();
        while (itr3.hasNext()) {
            node_data rem = itr3.next();
            rem.setInfo("White");
            rem.setWeight(-1);
        }
        return shortest;
    }

    /**
     * This method returns a list of nodes of the shortest path.
     * In this method, we are using BFS algorithm to set each node it's own weight from the src node
     * @param src - start node
     * @param dest - end (target) node
     * @return the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest, if no such path --> returns null
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        List<node_data> path = new LinkedList<node_data>();
        if(graphAlgo.getNode(src)==null || graphAlgo.getNode(dest)==null){
            return null;
        }
        if(src == dest){
            path.add(this.graphAlgo.getNode(src));
            return path;
        }
        //set for each node it's own weight from the src node by using BFS algorithm
        Queue<node_data> q = new LinkedList<node_data>();
        q.add(graphAlgo.getNode(src));
        q.peek().setWeight(0);
        while (!q.isEmpty()) {
            node_data cur = q.poll();
            if (!cur.getInfo().equals("Black")) {
                double x = cur.getWeight();
                try {
                    Iterator<edge_data> itr1 = this.graphAlgo.getE(cur.getKey()).iterator();
                    while (itr1.hasNext()) {
                        node_data nex = this.graphAlgo.getNode(itr1.next().getDest());
                        if (nex.getInfo().equals("White")) {
                            q.add(this.graphAlgo.getNode(nex.getKey()));
                            nex.setInfo("Grey");
                        }
                        if (nex.getWeight() > (x + this.graphAlgo.getEdge(cur.getKey(), nex.getKey()).getWeight()) || nex.getWeight() == -1) {
                            nex.setWeight((x + this.graphAlgo.getEdge(cur.getKey(), nex.getKey()).getWeight()));
                        }
                    }
                }
                catch (Exception e){
                }
                cur.setInfo("Black");
            }
        }

        //creating a copied graph with the same nodes and edges but they are going backwards (the edges are turn to the opposite direction)
        directed_weighted_graph thenewgraph = new DWGraph_DS();
        Iterator<node_data> itr4 = this.graphAlgo.getV().iterator();
        while (itr4.hasNext()) {
            node_data w = itr4.next();
            thenewgraph.addNode(w);
        }
        Iterator<node_data> itr5 = this.graphAlgo.getV().iterator();
        while (itr5.hasNext()) {
            node_data thesrc = itr5.next();
            Iterator<edge_data> itr2 = this.graphAlgo.getE(thesrc.getKey()).iterator();
            while (itr2.hasNext()) {
                edge_data thedest = itr2.next();
                thenewgraph.connect(thedest.getDest(),thesrc.getKey(),this.graphAlgo.getEdge(thesrc.getKey(),thedest.getDest()).getWeight());
            }
        }

        //creating a stack and adding it the nodes that are leading from the src node to the dest node
        Stack<node_data> M = new Stack<node_data>();
        if(thenewgraph.getNode(src).getWeight()!=-1&&thenewgraph.getNode(dest).getWeight()!=-1) {
            node_data des = thenewgraph.getNode(dest);
            while (true){
                if(des.getWeight()==0){
                    M.add(des);
                    break;
                }

                //checking if the current node is the correct one that lead from the src node
                Iterator<edge_data> nini = thenewgraph.getE(des.getKey()).iterator();
                while (nini.hasNext()) {
                    node_data sho = thenewgraph.getNode(nini.next().getDest());
                    double d = thenewgraph.getEdge(des.getKey(),sho.getKey()).getWeight();
                    double e = sho.getWeight();
                    double f = des.getWeight();
                    if (f==e+d){
                        M.add(des);
                        des = sho;
                        break;
                    }
                }
            }
        }
        else{
            return null;
        }

        //adding the nodes to the list
        while (!M.isEmpty()) {
            path.add(M.pop());
        }

        //sets all the nodes to the original initialize
        Iterator<node_data> itr3 = this.graphAlgo.getV().iterator();
        while (itr3.hasNext()) {
            node_data rem = itr3.next();
            rem.setInfo("White");
            rem.setWeight(-1);
        }
        return path;
    }

    /**
     * Saves this weighted (directed) graph to the given file name - in JSON format
     * @param file - the file name (may include a relative path)
     * @return true if the graph saves successfully, else return false
     */
    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(graphAlgo);

        try {
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(json);
            pw.close();
            return true;
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method load a graph to this graph algorithm.
     * If the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one).
     * In case the graph was not loaded the original graph should remain "as is"
     * @param file - file name of JSON file
     * @return true if the graph was loaded successfully, else return false
     */
    @Override
    public boolean load(String file) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(directed_weighted_graph.class, new graphJD());
            Gson gson = builder.create();
            FileReader reader = new FileReader(file);

            directed_weighted_graph graph = gson.fromJson(reader, directed_weighted_graph.class);
            init(graph);
            return true;
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}