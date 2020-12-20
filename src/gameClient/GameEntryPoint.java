package gameClient;

import api.game_service;
import gui.MyGui;
import org.json.JSONException;

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

    private static void setGamesNextAgentsDestination(game_service game) throws JSONException {
        int dest;
        for (Agent agent : scenario.getAgents().values()) {

            // agent doest not have pokemon destination
            if (agent.getCurr_pokemon() == null || agent.getCurrentSrc() == agent.getPokemonEdge().getDest()) {
                Game_Algo.setFinalDestAndRoute(scenario, agent.getCurrentSrc(), agent);

            }


            if (agent.getCurrentDest() == -1) {
                dest = Game_Algo.nextNode(scenario, agent);
                game.chooseNextEdge(agent.getId(), dest);
            }


        }

    }
//            if (agent.getCurrentSrc() == agent.getPokemonEdgeSrc()) {
//                dest = agent.getPokemonEdgeDest();
//                game.chooseNextEdge(agent.getId(), dest);
//            } else if (agent.getCurrentSrc() == agent.getPokemonEdgeDest() || agent.getPokemonEdgeDest() == -1) {
//                //set final dest and route to close pokemon
//                if (agent.getPokemonEdgeDest() != -1) {
//                    agent.getPokemonEdge().setTag(0);
//
//                }
//                Game_Algo.setFinalDestAndRoute(scenario, agent.getCurrentSrc(), agent);
//                dest = Game_Algo.nextNode(scenario, agent.getCurrentSrc(), agent.getId());
//                game.chooseNextEdge(agent.getId(), dest);
//
//            } else if (agent.getCurrentDest() == -1) {
//                dest = Game_Algo.nextNode(scenario, agent.getCurrentSrc(), agent.getId());
//                game.chooseNextEdge(agent.getId(), dest);
//            }

    private static void moveRobots(game_service game) throws JSONException {

        String moveJson = game.move();

        scenario.updateAgentsAfterMove(moveJson);
        scenario.updatePokemonsAfterMove(scenario.game.getPokemons());

        setGamesNextAgentsDestination(game);


    }


    @Override
    public void run() {

        _win.show();
        Thread startGame = new Thread(() -> {
            scenario.game.startGame();
            int count = 0;
            while (scenario.game.isRunning()) {

                try {
                    if (count % 2 == 0) {
                        moveRobots(scenario.game);
                    }
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


