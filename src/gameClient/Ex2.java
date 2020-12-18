package gameClient;

import api.DWGraph_DS;
import api.Node;
import api.directed_weighted_graph;
import api.node_data;
import gameClient.util.Point3D;
import help.Agent;
import help.Pokemon;
import org.json.JSONException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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

//        RunGame runGame = new RunGame(0);


        directed_weighted_graph g = new DWGraph_DS();
        node_data n1 = new Node(0, new Point3D(95, 130, 52));
        node_data n2 = new Node(1, new Point3D(89, 50, 91));
        node_data n3 = new Node(2, new Point3D(210, 70, 162));
        node_data n4 = new Node(3, new Point3D(40, 100, 60));
        node_data n5 = new Node(4, new Point3D(100, 200, 130));
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);


        g.connect(0, 4, 4);
        g.connect(0, 2, 2);
        g.connect(1, 4, 12);
        g.connect(4, 1, 1);
        g.connect(2, 1, 10);
        g.connect(3, 2, 8);

        List<Agent> agents = new ArrayList<>();
        agents.add(new Agent(0,new Point3D(97,140,50)));

        List<Pokemon> pokemons = new ArrayList<>();
        pokemons.add(new Pokemon(new Point3D(120,69,50)));
        pokemons.add(new Pokemon(new Point3D(56,84,50)));


        JFrame jFrame = new JFrame("My gui");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyGui myGui = new MyGui(g,agents,pokemons);
        jFrame.add(myGui);
        jFrame.setSize(700, 500);
        jFrame.setVisible(true);
        int i = 0;
        int up = 5;
        while (i<3){
            agents.get(0).setPos(new Point3D(97,140+up,50));
            up +=5;
            i++;
            myGui.repaint();
        }


    }

}



