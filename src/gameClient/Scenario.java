package gameClient;

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
 * places the The pokemons and places the agents near to the pokemon
 */
public class Scenario {
    public directed_weighted_graph graph;
    public dw_graph_algorithms graph_algo;
    public game_service game;
    public HashMap<Integer, Agent> agents;
    public ArrayList<Pokemon> pokemonsList = new ArrayList<Pokemon>();


    public int ID;

    /**
     * Init from json info about the graph, pokemon and agent.
     * insert the agents and pokemons to list.
     *
     * @param scenario_num
     * @throws JSONException
     * @throws IOException
     */

    public Scenario(int scenario_num, int id) throws JSONException, IOException {
        this.ID = id;
        this.game = Game_Server_Ex2.getServer(scenario_num);
        game.login(ID);
        String graphJson = game.getGraph();

        BufferedWriter writer = new BufferedWriter(new FileWriter("graph.json"));
        writer.write(graphJson);
        writer.close();


        graph_algo = new DWGraph_Algo();
        graph_algo.load("graph.json");
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

    /**
     * update the pokemons after move
     *
     * @param pokemonsJson Json string
     */
    public void updatePokemonsAfterMove(String pokemonsJson) {
        ArrayList<Pokemon> ans = new ArrayList<>();
        try {
            JSONObject pokemonsObject = new JSONObject(pokemonsJson);
            JSONArray pokemonsJsonArray = pokemonsObject.getJSONArray("Pokemons");
            for (int i = 0; i < pokemonsJsonArray.length(); i++) {
                JSONObject pokemonObject = pokemonsJsonArray.getJSONObject(i);
                Pokemon p = new Pokemon(pokemonObject.toString(), graph);
                ans.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.pokemonsList = ans;
    }

    /**
     * update the agents after move
     *
     * @param s Json string
     * @throws JSONException
     */
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

    /**
     * return a string about the info of the game for gui
     *
     * @param s game Json string
     * @return res
     */
    public String gameOverString(String s) {
        String res = "";
        JSONObject line;
        try {
            line = new JSONObject(s);
            JSONObject gameServerObject = line.getJSONObject("GameServer");

            String line1 = " Grade: " + gameServerObject.getInt("grade");

            String line2 = " Moves: " + gameServerObject.getInt("moves");

            String line3 = " Level: " + gameServerObject.getInt("game_level");

            res = line1 + " ," + line2 + " ," + line3;
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        return res;
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