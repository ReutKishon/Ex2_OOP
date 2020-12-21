package api;

public class Edge implements edge_data {
    private final int src;
    private final int dest;
    private final double w;
    private transient String info;
    private transient int tag;

    public Edge(int src, int dest, double weight) {
        this.src = src;
        this.dest = dest;
        this.w = weight;
    }

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDest() {
        return dest;
    }

    @Override
    public double getWeight() {
        return w;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
