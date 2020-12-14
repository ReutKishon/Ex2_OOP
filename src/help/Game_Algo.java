package help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * This static class represents the algorithmic part of the game.
 * all logical functions are implemented here.
 */
public class Game_Algo {
    public static final double EPS = 0.000001;

    /**
     * add agent near to the pokemons.
     *
     * @param pokemonList
     * @param agentsNum
     * @param game
     */
    public static void addAgentNearPokemon(int agentsNum, ArrayList<Pokemon> pokemonList, game_service game, directed_weighted_graph graph) {
        HashMap<Integer, Boolean> visited = new HashMap<>();
        int i;
        for (i = 0; i < agentsNum; i++) {
            if (i < pokemonList.size()) {
                int src = pokemonList.get(i % pokemonList.size()).getEdge().getSrc();
                game.addAgent(src);
                visited.put(src, true);
            } else {
                for (node_data node : graph.getV()) {
                    if (visited.get(node.getKey()) == null) {
                        game.addAgent(node.getKey());
                        visited.put(node.getKey(), true);
                        return;
                    }
                }
            }
        }

    }

    /**
     * The function returns the edge on which the pokemon is placed
     *
     * @param pokemonInfo
     * @param graph
     */
    public static edge_data getPokemonEdge(String pokemonInfo, directed_weighted_graph graph) throws JSONException {
        JSONObject infoObject = new JSONObject(pokemonInfo); //deserialize
        JSONObject pokemonObject = infoObject.getJSONObject("Pokemon");
        String posString = pokemonObject.getString("pos"); //get location
        Point3D pos = new Point3D(posString);
        int type = pokemonObject.getInt("type");//get location int;
        //traversal all edges and checks if the the fruit is on edge
        for (node_data node : graph.getV()) {
            for (edge_data edge : graph.getE(node.getKey())) {
                double disSP = node.getLocation().distance(pos);  // distance from src to pokemon
                double disPD = pos.distance(graph.getNode(edge.getDest()).getLocation());  // distance from pokemon to dest
                double disSD = node.getLocation().distance(graph.getNode(edge.getDest()).getLocation());    // distance from src to dest
                if (type == -1) {
                    if (disSP + disPD >= disSD - EPS && disSP + disPD <= disSD + EPS && edge.getSrc() - edge.getDest() > 0) {
                        return edge;
                    }
                } else if (type == 1) {
                    if (disSP + disPD >= disSD - EPS && disSP + disPD <= disSD + EPS && edge.getSrc() - edge.getDest() < 0) {
                        return edge;
                    }
                }
            }
        }
        return null;
    }

    /**
     * The function returns the next node on the path to a pokemon.
     *
     * @param src
     * @param scenario
     */
    public static int nextNode(Scenario scenario, int src, int id) throws JSONException {
        int ans = -1;
        int dest = 0;
        int tempDest = 0;
        double minDist = -1;
        double tempDist;
        edge_data resEdge = null;

        JSONObject object = new JSONObject(scenario.game.getPokemons());
        JSONArray getArray = object.getJSONArray("Pokemons");

        for (int i = 0; i < getArray.length(); i++) {
            JSONObject pokemonObject = getArray.getJSONObject(i);

            try {
                edge_data pokemonEdge = getPokemonEdge(pokemonObject.toString(), scenario.graph);
                if (pokemonEdge != null && (pokemonEdge.getTag() == -1 || pokemonEdge.getTag() == id)) {       // if else robot not go to this fruit.
                    if (minDist == -1) {         //get to min edge
                        minDist = scenario.graph_algo.shortestPathDist(src, scenario.graph.getNode(pokemonEdge.getSrc()).getKey());
                        dest = scenario.graph.getNode(pokemonEdge.getSrc()).getKey();
                        resEdge = pokemonEdge;
                    } else {
                        tempDist = scenario.graph_algo.shortestPathDist(src, scenario.graph.getNode(pokemonEdge.getSrc()).getKey());
                        tempDest = scenario.graph.getNode(pokemonEdge.getSrc()).getKey();
                        if (tempDist < minDist) {
                            minDist = tempDist;
                            dest = tempDest;
                            resEdge = pokemonEdge;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (resEdge != null) {
            resEdge.setTag(id);
        }
        List<node_data> shortestPath = scenario.graph_algo.shortestPath(src, dest);  // insert the path to list
        if (shortestPath != null && shortestPath.size() > 1) {
            ans = shortestPath.get(1).getKey();       // the next step in path to fruit
            scenario.graph.getEdge(src, shortestPath.get(1).getKey()).setTag(-1);
        }
        if (shortestPath != null && shortestPath.size() == 1) {       //if the robot is in the  src edge fruit
            if (resEdge != null) {
                resEdge.setTag(-1);
                ans = resEdge.getDest();
            }
        }

        return ans;
    }


}