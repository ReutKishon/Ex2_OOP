package api;


public class Node implements node_data {
    public final int id;
    public geo_location pos;
    private transient double weight;
    private transient String info;
    private transient int tag;
    public static int count = 0;

    public Node() {
        id = count++;
        tag = 0;
    }

    public Node(int id, int tag, geo_location location, double weight, String info) {
        this.id = id;
        this.tag = tag;
        this.pos = location;
        this.weight = weight;
        this.info = info;
    }

    @Override
    public int getKey() {
        return id;
    }

    @Override
    public geo_location getLocation() {
        return pos;
    }

    @Override
    public void setLocation(geo_location p) {
        this.pos = p;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
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
