package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class represents fruit on a graph from the game
 */
public class Pokemon {
    private int type;
    private Point3D pos;
    private double value;
    private edge_data edge;
    private int min_agent;
    private double min_dist;


    /**
     * Init from json info about the Fruit.
     *
     * @param graph
     * @param s
     */
    public Pokemon(String s, directed_weighted_graph graph) {
        try {
            JSONObject info = new JSONObject(s);//deserialize
            JSONObject pokenomInfo = info.getJSONObject("Pokemon");
            String pos = pokenomInfo.getString("pos"); //get location
            this.pos = new Point3D(pos);
            this.type = pokenomInfo.getInt("type");//get type;
            this.value = pokenomInfo.getDouble("value");// get value
            this.edge = Game_Algo.getPokemonEdgeByJson(s, graph);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.setMin_dist(Double.MAX_VALUE);
        this.setMin_agent(-1);
    }


    /**
     * @return the Type of this Fruit.
     */
    public int getType() {
        return type;
    }

    /**
     * @return the location of this Fruit.
     */
    public Point3D getPos() {
        return pos;
    }

    public void getPos(Point3D pos) {
        this.pos = pos;
    }

    public void setType(int type) {
        this.type = type;
    }


    /**
     * @return the Value of this Fruit.
     */
    public double getValue() {
        return value;
    }

    /**
     * @return the Edge of this Fruit.
     */
    public edge_data getEdge() {
        return edge;
    }

    public void setEdge(edge_data edge) {
        this.edge = edge;
    }

    public double getMin_dist() {
        return min_dist;
    }

    public void setMin_dist(double mid_dist) {
        this.min_dist = mid_dist;
    }

    public int getMin_agent() {
        return min_agent;
    }

    public void setMin_agent(int min_agent) {
        this.min_agent = min_agent;
    }
}
