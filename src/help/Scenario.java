package help;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import Server.Game_Server_Ex2;
import api.*;
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
    public HashMap<Integer, Agent> agents;
    public ArrayList<Pokemon> pokemonsList = new ArrayList<Pokemon>();

//    public static final int ID =206226706;

    /**
     * Init from json info about the graph, fruit and robot.
     * places insert the robots and fruits to list.
     *
     * @param scenario_num
     */

    public Scenario(int scenario_num) throws JSONException, IOException {
//        Game_Server.login(ID);
        this.game = Game_Server_Ex2.getServer(scenario_num);
        String graphJson = game.getGraph();

        BufferedWriter writer = new BufferedWriter(new FileWriter("graphJson.txt"));
        writer.write(graphJson);
        writer.close();


        graph_algo = new DWGraph_Algo();
        graph_algo.load("graphJson.txt");
        graph = graph_algo.getGraph();
        String info = game.toString();

        try {
            JSONObject ttt = new JSONObject(game.getPokemons());
            JSONArray ags = ttt.getJSONArray("Pokemons");
            for (int i = 0; i < ags.length(); i++) {
                JSONObject pp = ags.getJSONObject(i);
                var f = new Pokemon(pp.toString(), graph);
                pokemonsList.add(f);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject gameServerObject = line.getJSONObject("GameServer");
            int agentsNum = gameServerObject.getInt("agents");
            Game_Algo.addAgentNearPokemon(agentsNum, pokemonsList, game, graph);
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        agents = new HashMap<>();
        JSONObject agentsInfo = new JSONObject(game.getAgents());
        JSONArray agentsArray = agentsInfo.getJSONArray("Agents");

        for (int i = 0; i < agentsArray.length(); i++) {
            JSONObject agentObject = agentsArray.getJSONObject(i);
            Agent agent = new Agent(agentObject.toString());
            this.agents.put(agent.getId(), agent);
        }

    }

    public void updatePokemonsAfterMove(String pokemonsJson) {
        this.pokemonsList.clear();
        try {
            JSONObject pokemonsObject = new JSONObject(pokemonsJson);
            JSONArray pokemonsJsonArray = pokemonsObject.getJSONArray("Pokemons");
            for (int i = 0; i < pokemonsJsonArray.length(); i++) {
                JSONObject pokemonObject = pokemonsJsonArray.getJSONObject(i);
                Pokemon p = new Pokemon(pokemonObject.toString(), graph);
                pokemonsList.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateAgentsAfterMove(String s) throws JSONException {
        JSONObject agentsInfo = new JSONObject(s);
        JSONArray agentsArray = agentsInfo.getJSONArray("Agents");

        for (int i = 0; i < agentsArray.length(); i++) {
            JSONObject agentObject = agentsArray.getJSONObject(i);
            JSONObject agentInfo = agentObject.getJSONObject("Agent");
            int id = agentInfo.getInt("id");
            String pos = agentInfo.getString("pos");
            int currentSrc = agentInfo.getInt("src");
            int currentDest = agentInfo.getInt("dest");

            this.agents.get(id).setPos(new Point3D(pos));
            this.agents.get(id).setCurrentSrc(currentSrc);
            this.agents.get(id).setCurrentDest(currentDest);


        }

    }


    public HashMap<Integer, Agent> getAgents() {
        return agents;
    }

    public directed_weighted_graph getGraph() {
        return graph;
    }

    public ArrayList<Pokemon> getPokemonsList() {
        return pokemonsList;
    }
}