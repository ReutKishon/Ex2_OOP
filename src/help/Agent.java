package help;

import api.geo_location;
import gameClient.util.Point3D;
import org.json.JSONException;
import org.json.JSONObject;

//import utils.Point3D;

/**
 * This class represents Robot on a graph from the game
 */
public class Agent {
    private int id;
    private int src;
    private int dest;
    private Point3D pos;
    double value;
    double speed;

    /**
     * Init from json info about the Robot.
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
            this.src = agentInfo.getInt("src");// get src
            this.dest = agentInfo.getInt("dest");// get dest
            this.value = agentInfo.getDouble("value"); // get value
            this.speed = agentInfo.getDouble("speed"); // get speed
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public Agent(int id, Point3D pos) {
        this.pos = pos;
        this.id = id;
    }

    /**
     * @return the src of this Robot.
     */
    public int getSrc() {
        return src;
    }

    /**
     * Allow setting the src edge of this Robot.
     *
     * @param src
     */
    public void setSrc(int src) {
        this.src = src;
    }

    /**
     * @return the destination edge  of this Robot.
     */
    public int getDest() {
        return dest;
    }

    /**
     * Allow setting the  destination edge  of this Robot.
     *
     * @param dest
     */
    public void setDest(int dest) {
        this.dest = dest;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
