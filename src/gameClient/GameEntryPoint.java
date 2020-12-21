package gameClient;

import api.edge_data;
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
     *
     * @param scenario_num scenario number
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

    /**
     * This function set the next destination of each agent
     * Also try to set a "pokemon target" to each agent
     *
     * @param game game
     * @throws JSONException exception
     */
    private static void setGamesNextAgentsDestination(game_service game) throws JSONException {
        int dest;
        for (Agent agent : scenario.getAgents().values()) {

            // agent doest not have pokemon destination or it reached to his destination
            if (agent.getCurr_pokemon() == null || agent.getCurrentSrc() == agent.getCurr_pokemon().getEdge().getDest()) {

                if (agent.getCurr_pokemon() != null && agent.getCurrentSrc() == agent.getCurr_pokemon().getEdge().getDest()) {
                    agent.getCurr_pokemon().getEdge().setTag(0);
                }

                Game_Algo.setFinalDestAndRoute(scenario, agent.getCurrentSrc(), agent);

            }


            if (agent.getCurrentDest() == -1) {
                dest = Game_Algo.nextNode(agent);
                game.chooseNextEdge(agent.getId(), dest);


            }


        }

    }



    /**
     * This function moves the agents and update the next destination of each agent
     * Also updates the agents and pokemons position
     *
     * @param game game
     * @throws JSONException exception
     */
    private static void moveRobots(game_service game) throws JSONException {

        String moveJson = game.move();
        if (moveJson != null) {
            scenario.updateAgentsAfterMove(moveJson);
        }
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


