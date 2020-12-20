//package tests;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import api.Edge;
//import api.edge_data;
//import org.json.JSONException;
//import org.junit.jupiter.api.Test;
//
//import gameClient.Game_Algo;
//import gameClient.Scenario;
//
//import java.io.IOException;
//
//
///**
// * This class is a test for GameAlgo class
// */
//class Game_AlgoTest {
//    public static Scenario scenario;
//
//    /**
//     * This class is a test for GameAlgo class
//     */
//    @Test
//    void test() {
//        System.out.println("**********test getFruitEdge*************");
//        try {
//            scenario = new Scenario(2);
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
//        edge_data t1 = scenario.pokemonsList.get(0).getEdge();
//        edge_data t2 = scenario.pokemonsList.get(1).getEdge();
//        edge_data t3 = scenario.pokemonsList.get(2).getEdge();
//
//        if (t1.getSrc() != 9 || t1.getDest() != 8)
//            fail("shoulbe be equals");
//
//        if (t2.getSrc() != 4 || t2.getDest() != 3)
//            fail("shoulbe be equals");
//
//        if (t3.getSrc() != 3 || t3.getDest() != 2)
//            fail("shoulbe be equals");
//    }
//
//    /**
//     * test if a robot is near a fruit.
//     */
//    @Test
//    void test2() {
//        System.out.println("**********test addRobotNearFruit*************");
//        try {
//            scenario = new Scenario(2);
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
//        edge_data t1 = scenario.pokemonsList.get(0).getEdge();
//
//        if (t1.getSrc() != 9 || t1.getDest() != 8)
//            fail("shoulbe be equals");
//
//        if (scenario.agents.get(0).getCurrentSrc() != 9)
//            fail("shoulbe be placed in src of fruit");
//    }
//
//    /**
//     * test if next node is the best path to fruit.
//     */
//    @Test
//    void test3() {
//        System.out.println("**********test nextNode*************");
//        try {
//            scenario = new Scenario(2);
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
//        edge_data t1 = scenario.pokemonsList.get(0).getEdge();
//
//        if (t1.getSrc() != 9 || t1.getDest() != 8)
//            fail("shoulbe be equals");
//
//        if (scenario.agents.get(0).getCurrentSrc() != 9)
//            fail("shoulbe be placed in src of fruit");
//
//        try {
//            if (Game_Algo.nextNode(scenario, scenario.agents.get(0).getCurrentSrc(), 0) != 8)
//                fail("The next step is wrong");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
