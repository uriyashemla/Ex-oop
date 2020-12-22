package api;

public class NodeData implements node_data{
    private int key;
    private int tag;
    private String info = "White";
    private double weight = -1;
    private geo_location location;

    public NodeData() {
    }

    public NodeData(int key) {
        this.key=key;
    }

    public NodeData (double x, double y, double z, int key, String info, int tag, double weight){
        this.key=key;
        this.weight=-1;
        this.info="";
        this.tag=-1;
        this.location=new geo_loc(x,y,z);
    }

    public NodeData (double x, double y, double z, int key){
        this.key=key;
        this.weight=-1;
        this.info="";
        this.tag=-1;
        this.location=new geo_loc(x,y,z);
    }

    public NodeData(int key, double weight, String info, int tag) {
        this.key = key;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    public NodeData(int key, geo_location location, double weight, String info, int tag) {
        this.key = key;
        this.location = new geo_loc(location);
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    public NodeData(node_data n) {
        this.key = n.getKey();
        this.location = new geo_loc(n.getLocation());
        this.weight = n.getWeight();
        this.info = n.getInfo();
        this.tag = n.getTag();
    }

    /**
     * Returns the key (id) associated with this node
     * @return
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * Returns the location of this node, if none - return null
     * @return
     */
    @Override
    public geo_location getLocation() {
        return this.location;
    }

    /**
     * Allows changing this node's location
     * @param p - new location (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        this.location=p;
    }

    /**
     * Allows changing this node's location
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Allows changing this node's weight
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.weight=w;
    }

    /**
     * Returns the remark (meta data) associated with this node
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info=s;
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
     * Allows setting the "tag" value for temporal marking an node+
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag=t;
    }
}