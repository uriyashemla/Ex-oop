package api;

public class EdgeData implements edge_data{
    private int src;
    private int dest;
    private double weight;
    private String info = "";
    private int tag;

    /**
     * @param src
     * @param dest
     */
    public EdgeData (int src, int dest) {
        this.src=src;
        this.dest=dest;
    }

    /**
     * @param src
     * @param dest
     * @param weight
     */
    public EdgeData (int src, int dest, double weight) {
        this(src, dest, weight, "", 0);
    }

    /**
     * @param src
     * @param weight
     * @param dest
     */
    public EdgeData (int src, double weight, int dest){
        this.src=src;
        this.weight=weight;
        this.dest=dest;
    }

    /**
     * @param src
     * @param dest
     * @param weight
     * @param info
     * @param tag
     */
    public EdgeData (int src, int dest, double weight, String info, int tag) {
        if(weight < 0) {
            throw new RuntimeException("weight should be a positive number");
        }
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    /**
     * @param e
     */
    public EdgeData(edge_data e) {
        this(e.getSrc(), e.getDest(), e.getWeight(), e.getInfo(), e.getTag());
    }

    /**
     * The id of the source node of this edge
     * @return
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * The id of the destination node of this edge
     * @return
     */
    @Override
    public int getDest() {
        return this.dest;
    }

    /**
     * Returns the weight of this edge (positive value)
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Returns the remark (meta data) associated with this edge
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * T method allows setting the "tag" value for temporal marking an edge
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

}