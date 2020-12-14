package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;
import help.RunGame;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ex2 {

    //    private final int scenario_num;
    private static Arena _ar;
    private static MyFrame _win;


    public static void main(String[] a) throws JSONException {
//
//        game_service game = Game_Server_Ex2.getServer(0);
//        System.out.println(game.toString());
//        game.getGraph().;
//        game.addAgent(0);
//        game.startGame();
//        int count = 0;
//        while (game.isRunning()) {
//            System.out.println(game.getAgents());
//
//            game.chooseNextEdge(0, 10);
//            String moveJson = game.move();
//            System.out.println(moveJson);
//            System.out.println(game.getAgents());
//
//
//
//        }

        RunGame runGame = new RunGame(0);



    }

}



