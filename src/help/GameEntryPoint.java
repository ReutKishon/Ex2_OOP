package help;

import api.directed_weighted_graph;
import api.game_service;
import gameClient.MyGui;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


/**
 * This class Run the Game Which gets from scenario number game.
 */
public class GameEntryPoint implements Runnable {
    private static MyGui _win;
    public static Scenario scenario;
    public static final int DT = 52;

    /**
     * This function gets the scenario number.
     * The function uses the number scenario which places the Agents and Pokemons on the graph and builds it.
     * The function calculates the shortest path between the Agent and the pokemon.
     *
     * @param scenario_num
     */
    public GameEntryPoint(int scenario_num) {

        try {
            scenario = new Scenario(scenario_num);
            _win = new MyGui(scenario);
            _win.setSize(1000, 700);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }


    }

    private static void setGamesNextAgentsDestination(game_service game, String moveJson) throws JSONException {
        JSONObject object = new JSONObject(moveJson);
        JSONArray getArray = object.getJSONArray("Agents");

        for (int i = 0; i < getArray.length(); i++) {
            JSONObject jsonObject = getArray.getJSONObject(i);

            try {
                JSONObject agentObject = jsonObject.getJSONObject("Agent");
                int id = agentObject.getInt("id");
                int src = agentObject.getInt("src");
                int dest = agentObject.getInt("dest");


                if (dest == -1) {
                    dest = Game_Algo.nextNode(scenario, src, id);
                    game.chooseNextEdge(id, dest);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private static void moveRobots(game_service game, directed_weighted_graph gg) throws JSONException {

        var moveJson = game.move();
        var pokemondsJson = game.getPokemons();
        setGamesNextAgentsDestination(game, moveJson);

        var agents = scenario.updateAgentsAfterMove(moveJson);
        var pokemons = scenario.updatePokemonsAfterMove(pokemondsJson);

        _win.updateAgents(agents);
        _win.updatePokemons(pokemons);

    }


    @Override
    public void run() {

        _win.show();

        Thread startGame = new Thread(() -> {
            scenario.game.startGame();
            int count = 0;
            while (scenario.game.isRunning()) {
                try {
                    moveRobots(scenario.game, scenario.graph);
                    _win.repaint();
                    count++;
                    Thread.sleep(DT);

                } catch (JSONException | InterruptedException e) {
                    e.printStackTrace();
                }

                long t = scenario.game.timeToEnd();
                if (count % 10 == 0) {
                    System.out.println("time to end:" + (t / 1000));
                }

            }
            String results = scenario.game.toString();
            System.out.println("Game Over: " + results);
        });
        startGame.start();
    }
}


