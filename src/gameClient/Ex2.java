package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import graph_implementation.DWGraph_Algo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Ex2 implements Runnable {

    private final int scenario_num;
    private static Arena _ar;
    private static MyFrame _win;


    public Ex2(int scenario_num) {
        this.scenario_num = scenario_num;
    }

    public static void main(String[] a) {
        Thread client = new Thread(new Ex2(1));
        client.start();
    }


    public void action() {

        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        dw_graph_algorithms ga = new DWGraph_Algo();
        boolean isLoaded = ga.load(game.getGraph());
        if (!isLoaded) try {
            throw new Exception("can not load the graph");
        } catch (Exception e) {
            e.printStackTrace();
        }
        directed_weighted_graph graph = ga.getGraph();
        setAgents(graph, game);

    }


    public void setAgents(directed_weighted_graph graph, game_service game) {
        String jsonAgents = game.getAgents();
        String jsonPokemons = game.getPokemons();
        List<CL_Agent> agentList = Arena.getAgents(jsonAgents, graph);
        List<CL_Pokemon> pokemonList = Arena.json2Pokemons(jsonPokemons);
        int i;
        // update all Pokemon's edges
        for (i = 0; i < pokemonList.size(); i++) {
            CL_Pokemon pokemon = pokemonList.get(i);
            Arena.updateEdge(pokemon, graph);
            agentList.get(i).setCurrNode(pokemon.get_edge().getSrc());

        }
//        if (i<agentList.size()){

//            for (int j = i; j <agentList.size() ; j++) {
//                agentList.get(j).setCurrNode(0);
//            }


        }





    @Override
    public void run() {

        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        action();
        String jsonGraph = game.getGraph();
        String jsonPks = game.getPokemons();
        directed_weighted_graph graph = game.getJava_Graph_Not_to_be_used();
        init(game);

        game.startGame();
        int ind = 0;
        long dt = 100;

        while (game.isRunning()) {
            moveAgents(game, graph);
            try {
                if (ind % 1 == 0) {
                    _win.repaint();
                }
                Thread.sleep(dt);
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();
//
        System.out.println(res);
        System.exit(0);


    }

    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     *
     * @param game
     * @param graph
     * @param
     */
    private static void moveAgents(game_service game, directed_weighted_graph graph) {
        String agentsStatus = game.move();
        List<CL_Agent> agentList = Arena.getAgents(agentsStatus, graph);
        _ar.setAgents(agentList);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String jsonPokemons = game.getPokemons();
        List<CL_Pokemon> pokemonList = Arena.json2Pokemons(jsonPokemons);
        _ar.setPokemons(pokemonList);
        for (int i = 0; i < agentList.size(); i++) {
            CL_Agent agent = agentList.get(i);
            int id = agent.getID();
            int dest = agent.getNextNode();
            int src = agent.getSrcNode();
            double v = agent.getValue();
            if (dest == -1) {
//                dest = nextNode(graph, src, pokemonList, agentList);
//                game.chooseNextEdge(agent.getID(), dest);
                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
            }
        }
    }

    /**
     * a very simple random walk implementation!
     * <p>
     * //     * @param graph
     * //     * @param src
     *
     * @return
     */
//    private static int nextNode(directed_weighted_graph graph, int src, List<
//            CL_Pokemon> pokemonList, List<CL_Agent> agentList) {
//        dw_graph_algorithms ga = new DWGraph_Algo();
//        ga.init(graph);
//        Collection<edge_data> srcEdgesOut = graph.getE(src);
//        Iterator<edge_data> itr = srcEdgesOut.iterator();
//        double shortestPathDist = Integer.MAX_VALUE;
//        int dest = 0;
//        for (int i = 0; i < pokemonList.size(); i++) {
//            edge_data pokemonEdge = pokemonList.get(i).get_edge();
//            int nextDest = pokemonEdge.getDest();
//            double shortestPathToPokemon = ga.shortestPathDist(src, nextDest);
//            if (shortestPathToPokemon < shortestPathDist) {
//                shortestPathDist = shortestPathToPokemon;
//                dest = pokemonEdge.getDest();
//            }
//        }
//
//        List<node_data> path = ga.shortestPath(src, dest);
//        return path.get(1).getKey();
//
//    }
    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        //gg.init(g);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);


        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for (int a = 0; a < cl_fs.size(); a++) {
                Arena.updateEdge(cl_fs.get(a), gg);
            }
            for (int a = 0; a < rs; a++) {
                int ind = a % cl_fs.size();
                CL_Pokemon c = cl_fs.get(ind);
                int nn = c.get_edge().getDest();
                if (c.getType() < 0) {
                    nn = c.get_edge().getSrc();
                }

                game.addAgent(nn);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
