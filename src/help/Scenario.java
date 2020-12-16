package help;

import java.io.*;
import java.util.ArrayList;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * The class builds the graph to start the game
 * places the The fruits and places the robot near to the fruit
 */
public class Scenario {
    public directed_weighted_graph graph;
    public dw_graph_algorithms graph_algo;
    public game_service game;
    public ArrayList<Agent> agentsList = new ArrayList<Agent>();
    public ArrayList<Pokemon> pokemonsList = new ArrayList<Pokemon>();
    public int num;
//    public static final int ID =206226706;

    /**
     * Init from json info about the graph, fruit and robot.
     * places insert the robots and fruits to list.
     *
     * @param scenario_num
     */

    public Scenario(int scenario_num) throws JSONException, IOException {
//        Game_Server.login(ID);
        this.num = scenario_num;
        this.game = Game_Server_Ex2.getServer(scenario_num);
        String graphJson = game.getGraph();

        BufferedWriter writer = new BufferedWriter(new FileWriter("graphJson.txt"));
        writer.write(graphJson);
        writer.close();


        graph_algo = new DWGraph_Algo();
        graph_algo.load("graphJson.txt");
        graph = graph_algo.getGraph();
        String info = game.toString();

        JSONObject object = new JSONObject(game.getPokemons());
        JSONArray getArray = object.getJSONArray("Pokemons");

        for (int i = 0; i < getArray.length(); i++) {
            JSONObject pokemonObject = getArray.getJSONObject(i);
            Pokemon pokemon = new Pokemon(pokemonObject.toString(), this.graph);
            pokemonsList.add(pokemon);
        }


        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject gameServerObject = line.getJSONObject("GameServer");
            int agentsNum = gameServerObject.getInt("agents");
            Game_Algo.addAgentNearPokemon(agentsNum, pokemonsList, game, graph);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject agentsInfo = new JSONObject(game.getAgents());
        JSONArray agentsArray = agentsInfo.getJSONArray("Agents");

        for (int i = 0; i < agentsArray.length(); i++) {
            JSONObject agentObject = agentsArray.getJSONObject(i);
            Agent agent = new Agent(agentObject.toString());
            agentsList.add(agent);
        }


    }


}