package tests;

import api.edge_data;
import gameClient.Agent;
import gameClient.Game_Algo;
import gameClient.Pokemon;
import gameClient.Scenario;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Game_AlgoTest {

    @Test
    void addAgentNearPokemon1() {
        Scenario s = null;
        try {
            s = new Scenario(0,0);
            assertEquals(s.getPokemonsList().get(0).getEdge().getSrc(), s.getAgents().get(0).getCurrentSrc());

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void addAgentNearPokemon2() {

        Scenario s1 = null;
        try {
            s1 = new Scenario(1,0);
            assertEquals(9, s1.getAgents().get(0).getCurrentSrc());

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addAgentNearPokemon3() {

        Scenario s2 = null;
        try {
            s2 = new Scenario(8,0);
            assertEquals(26, s2.getAgents().get(0).getCurrentSrc());

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }



    @Test
    void getPokemonEdge() {
        try {
            Scenario scenario = new Scenario(0,0);
            Pokemon p = scenario.getPokemonsList().get(0);
            edge_data edge = Game_Algo.getPokemonEdge(p,scenario.getGraph());

            assert edge != null;
            assertEquals(9,edge.getSrc());
            assertEquals(8,edge.getDest());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void nextNode_SetFinalDestAndRoute1()  {
        try {
            Scenario scenario = new Scenario(0,0);
            Agent agent = scenario.getAgents().get(0);
            Game_Algo.setFinalDestAndRoute(scenario,agent.getCurrentSrc(),agent);
            assertEquals(8,Game_Algo.nextNode(agent));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void nextNode_SetFinalDestAndRoute2() {
        try {
            Scenario scenario = new Scenario(4,0);
            Agent agent = scenario.getAgents().get(0);
            Game_Algo.setFinalDestAndRoute(scenario,agent.getCurrentSrc(),agent);
            int nextNode = Game_Algo.nextNode(agent);
            assertEquals(13,nextNode);



        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }


}