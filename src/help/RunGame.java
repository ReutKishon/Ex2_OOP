package help;


import java.io.IOException;
import java.util.List;

import api.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *This class Run the Game Which gets from scenario number game.
 */
public class RunGame   {
	public static Scenario scenario;
	public static final int DT = 52;
	/**
	 * This function gets the scenario number.
	 * The function uses the number scenario which places the robots and fruits on the graph and builds it.
	 * The function calculates the shortest path between the robot and the fruit.
	 * @param scenario_num
	 */
	public RunGame(int scenario_num)   {
		try {
			scenario = new Scenario(scenario_num);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}

		Thread startGame = new Thread( new Runnable() {
			@Override
			public void run() {
				scenario.game.startGame();
				int count = 0;
				while(scenario.game.isRunning()) {
					long timeToEnd = scenario.game.timeToEnd();
					if(count%10==0) {
						System.out.println( "time to end:"+(timeToEnd/1000));
					}
                    try {
                        moveRobots(scenario.game, scenario.graph);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {

						Thread.sleep(DT);
						count++;


					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				String results = scenario.game.toString();
				System.out.println("Game Over: "+results);
			}
		});
		startGame.start();

	}


	private static void moveRobots(game_service game, directed_weighted_graph gg) throws JSONException {
		String moveJson = game.move();

		if(moveJson!=null) {

            JSONObject object = new JSONObject(moveJson);
            JSONArray getArray = object.getJSONArray("Agents");

            for (int i = 0; i < getArray.length(); i++) {
                JSONObject jsonObject = getArray.getJSONObject(i);

                try {
					JSONObject agentObject = jsonObject.getJSONObject("Agent");
					int id = agentObject.getInt("id");
					int src = agentObject.getInt("src");
					int dest = agentObject.getInt("dest");


					if(dest==-1) {
						dest = Game_Algo.nextNode(scenario, src, id);
						System.out.println(agentObject);
						game.chooseNextEdge(id, dest);
						//System.out.println("Turn to node: "+dest);
					}
				}
				catch (JSONException e) {e.printStackTrace();}
			}
		}
	}




}


