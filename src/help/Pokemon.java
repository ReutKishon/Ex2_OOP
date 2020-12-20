package help;

import api.directed_weighted_graph;
import api.edge_data;
import gameClient.util.Point3D;
import api.DWGraph_DS;
import api.Edge;
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


    public Pokemon(Point3D pos) {
        this.pos = pos;
    }

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
            this.edge = Game_Algo.updateEdge(this.pos, this.type, graph);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

}
