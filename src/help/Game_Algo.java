package help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import api.*;
import gameClient.CL_Pokemon;
import gameClient.util.Point3D;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * This static class represents the algorithmic part of the game.
 * all logical functions are implemented here.
 */
public class Game_Algo {
    public static final double EPS = 0.000001;
    public static final double EPS1 = 0.001, EPS2 = EPS1 * EPS1;

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


    public static int nextNode(Scenario scenario, int src, int id) throws JSONException {
        Agent agent = scenario.getAgents().get(id);
        List<node_data> agentRouteToPokemon = agent.getRoute();
        int edgeTag = agent.getPokemonEdge().getTag();
        directed_weighted_graph graph = scenario.getGraph();

        for (int i = 0; i < agentRouteToPokemon.size(); i++) {
            if (agentRouteToPokemon.get(i).getKey() == src) {
                if ((i + 1) < agentRouteToPokemon.size())
                    return agentRouteToPokemon.get(i + 1).getKey();
            }
        }


        return -1;
    }


    /**
     * The function returns the next node on the path to a pokemon.
     *
     * @param src
     * @param scenario
     */
    public static void setFinalDestAndRoute(Scenario scenario, int src, Agent agent) throws JSONException {

        double minDistance = Integer.MAX_VALUE;
        double tempDistance;
        edge_data resPokemonEdgeMin = null;



        for (Pokemon pokemon : scenario.getPokemonsList()) {
            edge_data pokemonEdge = getPokemonEdge(pokemon, scenario.graph);
            if (pokemonEdge != null && pokemonEdge.getTag()!=-1) {       // if else robot not go to this fruit.
                //get min edge
                tempDistance = scenario.graph_algo.shortestPathDist(src, scenario.graph.getNode(pokemonEdge.getSrc()).getKey());
                if (tempDistance < minDistance) {
                    minDistance = tempDistance;
                    resPokemonEdgeMin = pokemonEdge;

                }
            }
        }


        if (resPokemonEdgeMin != null) {

            List<node_data> shortestPath = scenario.graph_algo.shortestPath(src, resPokemonEdgeMin.getSrc());  // insert the path to list

            agent.setPokemonEdgeDest(resPokemonEdgeMin.getDest());
            agent.setPokemonEdgeSrc(resPokemonEdgeMin.getSrc());
            if (shortestPath != null) {
                agent.setRoute(shortestPath);
            }
            resPokemonEdgeMin.setTag(-1);
            agent.setPokemonEdge(resPokemonEdgeMin);


        }
    }


    public static edge_data updateEdge(Point3D pos, int type, directed_weighted_graph g) {
        //	oop_edge_data ans = null;
        Iterator<node_data> itr = g.getV().iterator();
        while (itr.hasNext()) {
            node_data v = itr.next();
            Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
            while (iter.hasNext()) {
                edge_data e = iter.next();
                boolean f = isOnEdge(pos, e, type, g);
                if (f) {
                    return e;
                }
            }
        }
        return null;
    }

    private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest) {

        boolean ans = false;
        double dist = src.distance(dest);
        double d1 = src.distance(p) + p.distance(dest);
        if (dist > d1 - EPS2) {
            ans = true;
        }
        return ans;
    }

    private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
        geo_location src = g.getNode(s).getLocation();
        geo_location dest = g.getNode(d).getLocation();
        return isOnEdge(p, src, dest);
    }

    private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
        int src = g.getNode(e.getSrc()).getKey();
        int dest = g.getNode(e.getDest()).getKey();
        if (type < 0 && dest > src) {
            return false;
        }
        if (type > 0 && src > dest) {
            return false;
        }
        return isOnEdge(p, src, dest, g);
    }






}


