package help;

import api.directed_weighted_graph;
import api.edge_data;
import gameClient.util.Point3D;
import api.DWGraph_DS;
import api.Edge;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *This class represents fruit on a graph from the game 
 */
public class Pokemon{
	private int type;
	private Point3D pos;
	private double value;
	private edge_data edge;
	/**
	 * Init from json info about the Fruit.
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
			this.edge =  Game_Algo.getPokemonEdge(s, graph);
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}  
	}
	/**
	 * Init from json info about the Fruit.
	 * @param s
	 */
	public Pokemon(String s) {
		try {
			JSONObject info = new JSONObject(s);//deserialize
			JSONObject pokemonInfo = info.getJSONObject("Pokemon");
			String pos = pokemonInfo.getString("pos"); //get location
			this.pos = new Point3D(pos);
			this.type = pokemonInfo.getInt("type");//get type int;
			this.value = pokemonInfo.getDouble("value");// get value
		} 
		catch (JSONException e) {
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

}
