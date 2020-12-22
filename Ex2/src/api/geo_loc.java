package api;

public class geo_loc implements geo_location{
    private double x;
    private double y;
    private double z;
    private double d; //distance

    /**
     * @param x
     * @param y
     * @param z
     */
    public geo_loc(double x, double y, double z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    /**
     * @param g
     */
    public geo_loc(geo_location g) {
        this(g.x(), g.y(), g.z());
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    /**
     * Calculating the distance
     * @param g
     * @return
     */
    @Override
    public double distance(geo_location g) {
        d=Math.pow(this.x()-g.x(), 2)+Math.pow(this.y()-g.y(), 2)+Math.pow(this.z()-g.z(), 2);
        return Math.sqrt(d);
    }

}