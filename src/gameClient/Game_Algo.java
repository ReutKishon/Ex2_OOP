package gameClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class represents the algorithmic part of the game.
 * all logical functions are implemented here.
 */
public class Game_Algo {
    public static final double EPS = 0.000001;
    public static final double EPS1 = 0.001, EPS2 = EPS1 * EPS1;

    /**
     * add agent near to the pokemons.
     *
     * @param pokemonList pokemons list
     * @param agentsNum   number of agents
     * @param game        game
     * @param graph       graph
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
     * @param pokemonInfo json string - info about the pokemon
     * @param graph       graph
     * @throws JSONException exception
     */
    public static edge_data getPokemonEdgeByJson(String pokemonInfo, directed_weighted_graph graph) throws JSONException {
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
     * The function returns the edge on which the pokemon is placed
     *
     * @param pokemon pokemon object
     * @param graph   graph
     * @throws JSONException
     */
    public static edge_data getPokemonEdge(Pokemon pokemon, directed_weighted_graph graph) throws JSONException {

        //traversal all edges and checks if the the fruit is on edge
        for (node_data node : graph.getV()) {
            for (edge_data edge : graph.getE(node.getKey())) {
                double disSP = node.getLocation().distance(pokemon.getPos());  // distance from src to pokemon
                double disPD = pokemon.getPos().distance(graph.getNode(edge.getDest()).getLocation());  // distance from pokemon to dest
                double disSD = node.getLocation().distance(graph.getNode(edge.getDest()).getLocation());    // distance from src to dest
                if (pokemon.getType() == -1) {
                    if (disSP + disPD >= disSD - EPS && disSP + disPD <= disSD + EPS && edge.getSrc() - edge.getDest() > 0) {
                        return edge;
                    }
                } else if (pokemon.getType() == 1) {
                    if (disSP + disPD >= disSD - EPS && disSP + disPD <= disSD + EPS && edge.getSrc() - edge.getDest() < 0) {
                        return edge;
                    }
                }
            }
        }
        return null;
    }


    /**
     * The function decides what node's key that the agent should move to it
     *
     * @param agent current agent
     * @return returns the node's key that the agent should move to it
     * @throws JSONException
     */
    public static int nextNode(Agent agent) throws JSONException {
        List<node_data> agentRouteToPokemon = agent.getRoute();

        for (int i = 0; i < agentRouteToPokemon.size(); i++) {
            if (agentRouteToPokemon.get(i).getKey() == agent.getCurrentSrc()) {
                if ((i + 1) < agentRouteToPokemon.size())
                    return agentRouteToPokemon.get(i + 1).getKey();
            }
        }

        if (agent.getCurr_pokemon() != null) {
            return agent.getCurr_pokemon().getEdge().getDest();
        }
        return -1;
    }


    /**
     * The function sets the pokemon and route of the agent that he should reach.
     *
     * @param src      The node that the agent is currently in
     * @param scenario scenario
     * @param agent    agent
     * @throws JSONException
     */
    public static void setFinalDestAndRoute(Scenario scenario, int src, Agent agent) throws JSONException {

        double minDistance = Integer.MAX_VALUE;
        double tempDistance;
        Pokemon pokemonResult = null;

        scenario.updatePokemonsAfterMove(scenario.game.getPokemons());


        for (Pokemon pokemon : scenario.getPokemonsList()) {
            edge_data pokemonEdge = getPokemonEdge(pokemon, scenario.graph);
            if (pokemonEdge != null && pokemonEdge.getTag() != -1) {       // if else robot not go to this fruit.

                //get min edge to src of the pokemon edge
                tempDistance = scenario.graph_algo.shortestPathDist(src, scenario.graph.getNode(pokemonEdge.getSrc()).getKey());
                if (tempDistance == -1) continue;//there is no path
                if (tempDistance < minDistance) {
                    minDistance = tempDistance;
                    pokemonResult = pokemon;

                }
            }
        }


        if (pokemonResult != null) {
            pokemonResult.setMin_dist(minDistance);
            pokemonResult.setMin_agent(agent.getId());
            agent.setCurr_pokemon(pokemonResult);
            List<node_data> shortestPath = scenario.graph_algo.shortestPath(src, pokemonResult.getEdge().getSrc());  // insert the path to list
            agent.setRoute(shortestPath);
            pokemonResult.getEdge().setTag(-1);
        }


    }



}







