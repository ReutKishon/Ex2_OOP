package help;

import api.edge_data;
import api.node_data;
import gameClient.util.Point3D;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents Agent on a graph from the game
 */
public class Agent {
    private int id;
    private int currentSrc;
    private int currentDest;
    private int pokemonEdgeDest;
    private int pokemonEdgeSrc;
    List<node_data> route;
    private Point3D pos;
    private edge_data pokemonEdge;
    double value;
    double speed;

    /**
     * Init from json info about the Agent.
     *
     * @param s
     */
    public Agent(String s) {
        try {
            JSONObject info = new JSONObject(s);//deserialize
            JSONObject agentInfo = info.getJSONObject("Agent");
            String pos = agentInfo.getString("pos"); //get location
            this.pos = new Point3D(pos);
            this.id = agentInfo.getInt("id");//get id;
            this.currentSrc = agentInfo.getInt("src");// get src
            this.currentDest = agentInfo.getInt("dest");// get dest
            this.value = agentInfo.getDouble("value"); // get value
            this.speed = agentInfo.getDouble("speed"); // get speed
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.route = new ArrayList<>();
        this.pokemonEdgeDest = -1;

    }


    /**
     * @return the src of this Robot.
     */
    public int getCurrentSrc() {
        return currentSrc;
    }

    /**
     * Allow setting the src edge of this Robot.
     *
     * @param currentSrc
     */
    public void setCurrentSrc(int currentSrc) {
        this.currentSrc = currentSrc;
    }

    /**
     * @return the destination edge  of this Robot.
     */
    public int getCurrentDest() {
        return currentDest;
    }

    /**
     * Allow setting the  destination edge  of this Robot.
     *
     * @param currentDest
     */
    public void setCurrentDest(int currentDest) {
        this.currentDest = currentDest;
    }

    /**
     * @return the Id of this Robot.
     */
    public int getId() {
        return id;
    }

    /**
     * @return the location of this Robot.
     */
    public Point3D getPos() {
        return pos;
    }

    public void setPos(Point3D pos) {
        this.pos = pos;
    }

    public List<node_data> getRoute() {
        return this.route;
    }

    public void setRoute(List<node_data> route) {
        this.route = route;
    }


    public int getPokemonEdgeDest() {
        return pokemonEdgeDest;
    }

    public void setPokemonEdgeDest(int pokemonEdgeDest) {
        this.pokemonEdgeDest = pokemonEdgeDest;
    }

    public edge_data getPokemonEdge() {
        return pokemonEdge;
    }

    public void setPokemonEdge(edge_data pokemonEdge) {
        this.pokemonEdge = pokemonEdge;
    }

    public int getPokemonEdgeSrc() {
        return pokemonEdgeSrc;
    }

    public void setPokemonEdgeSrc(int pokemonEdgeSrc) {
        this.pokemonEdgeSrc = pokemonEdgeSrc;
    }
}
