package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;

import gameClient.util.Range;
import help.Agent;
import help.Pokemon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MyGui extends JPanel {

    directed_weighted_graph graph;
    List<Agent> agents;
    List<Pokemon> pokemons;
    private final Range ry;
    private final Range rx;
    private final Range rz;
    private final int kRADIUS = 5;


    public MyGui(directed_weighted_graph graph, List<Agent> agents, List<Pokemon> pokemons) {
        this.pokemons = pokemons;
        this.agents = agents;
        this.graph = graph;
        this.ry = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);
        this.rx = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);
        this.rz = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    //
//    /**
//     * coordinate to be scaled to x range
//     */
//
    public double rangeX(double d) {
        double outX = (d - rx.get_min()) / (rx.get_max() - rx.get_min());
        double x = 100 * (12 * outX + 1);
        return x;
    }

    //
//    /**
//     * coordinate to be scaled to y range
//     */
    public double rangeY(double d) {
        double outY = (d - ry.get_min()) / (ry.get_max() - ry.get_min());
        double y = 400 * (1 - outY) + 100;
        return y;
    }
//
//    /**
//     * Double buffer for the paint function
//     */
//    @Override
//    public void paint(Graphics g) {
//        Image img = createImage(1300, 700);
//        Graphics gImg = img.getGraphics();
//        paintComponents(gImg);
//        g.drawImage(img, 0, 0, this);
//
//    }


    public void paintComponent(Graphics g) {


        for (node_data node : graph.getV()) {
            geo_location nodeLocation = node.getLocation();

            g.setColor(Color.BLUE);

            g.fillOval((int) nodeLocation.x(), (int) nodeLocation.y(),
                    2 * kRADIUS, 2 * kRADIUS);
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(node.getKey()), (int) (rangeX(nodeLocation.x())), -10 + (int) (rangeY(nodeLocation.y())));
//            path.put((Point3D) nodeLocation, node.getKey());

            for (edge_data edge : this.graph.getE(node.getKey())) {
                geo_location dest_location = this.graph.getNode(edge.getDest()).getLocation();

                g.setColor(Color.blue);
                g.fillOval((int) dest_location.x(), (int) dest_location.y(),
                        2 * kRADIUS, 2 * kRADIUS);

                g.setColor(Color.RED);
                g.drawLine((int) nodeLocation.x(), (int) nodeLocation.y(), (int) dest_location.x(), (int) dest_location.y());

//                double edgeWeight = edge.getWeight();
//                g.drawString(String.format("%.2f", edgeWeight), (int) (nodeLocation.x() + dest_location.x()) / 2, (int) (nodeLocation.y() + dest_location.y()) / 2);


            }


        }

/**
 *
 *   print agents
 */
        for (Agent agent : agents) {

            int id = agent.getId();

            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("agent.png"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            Image newRob = image != null ? image.getScaledInstance(20, 20, Image.SCALE_DEFAULT) : null;
            g.drawImage(newRob, (int) agent.getPos().x(), (int) agent.getPos().y(), null);

        }
        /**
         //         *
         //         *   print pokemons
         //         */
        for (Pokemon pokemon : pokemons) {

            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("pokemon.png"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            Image newPokemon = pokemon != null ? image.getScaledInstance(20, 20, Image.SCALE_DEFAULT) : null;
            g.drawImage(newPokemon, (int) pokemon.getPos().x(), (int) pokemon.getPos().y(), null);

        }
//
//
///**
// * print seconds left till game ends
// */
//        try {
//            if (Auto) {
//                String time = "Time till game Over: " + RunGame.scenario.game.timeToEnd() / 1000 + "";
//                g.drawString(time, 650, 75);
//            }
//            if (Manu) {
//                String time = "Time till game Over: " + s.game.timeToEnd() / 1000 + "";
//                g.drawString(time, 650, 75);
//            }
//            /**
//             *   print game score
//             */
//            if (EndAuto) {
//                String end = "game Over: " + RunGame.scenario.game.toString();
//                g.drawString(end, 450, 50);
//            }
//            if (EndManu) {
//                String end = "game Over: " + s.game.toString();
//                g.drawString(end, 450, 50);
//            }
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    }

    public void paint(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);
        paintComponent(g);
        //	updateFrame();
//        drawPokemons(g);
//        drawGraph(g);
//        drawAgants(g);
//        drawInfo(g);
    }
}

