package gameClient;


import Server.Game_Server_Ex2;
import api.Edge;
import api.edge_data;
import api.game_service;
import help.GameEntryPoint;
import org.json.JSONException;


public class Ex2 {


    public static void main(String[] a) throws JSONException {
        var gameEntryPoint = new GameEntryPoint(13);
        gameEntryPoint.run();



    }

}



