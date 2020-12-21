package gui;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.Arena;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.Pokemon;
import gameClient.Scenario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class myPanel extends JPanel {

    directed_weighted_graph graph;

    private final gameClient.util.Range2Range _w2f;
    private final Scenario scenario;

    public myPanel(Scenario scenario) {
        super();
        this.setBackground(Color.GREEN);
        this.setBounds(600, 600, 1000, 600);
        this.scenario = scenario;
        this.graph = scenario.getGraph();
        Range rx = new Range(20, this.getWidth() - 20);
        Range ry = new Range(this.getHeight() - 10, 150);
        Range2D frame = new Range2D(rx, ry);
        this._w2f = Arena.w2f(graph, frame);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPokemons(g);
        drawGraph(g);
        drawAgants(g);
        Font font = new Font("Verdana", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.blue);
        String time = "Time till game Over: " + scenario.game.timeToEnd() / 1000 + "";
        g.drawString(time, 500, 50);

        String end = scenario.gameOverString(scenario.game.toString());
        g.drawString(end, 500, 75);


    }

    public void reset() {
        repaint();
    }


    private void drawGraph(Graphics g) {
        for (node_data n : graph.getV()) {
            g.setColor(Color.blue);
            drawNode(n, 5, g);
            for (edge_data e : graph.getE(n.getKey())) {
                g.setColor(Color.gray);
                drawEdge(e, g);
            }
        }
    }

    private void drawPokemons(Graphics g) {
        if (scenario.pokemonsList != null) {
            Iterator<Pokemon> iterator = scenario.getPokemonsList().iterator();
            while (iterator.hasNext()) {
                Pokemon pokemon = iterator.next();
                Point3D c = pokemon.getPos();
                int r = 10;

                if (c != null) {

                    BufferedImage image = null;
                    try {
                        image = ImageIO.read(new File("pokemon.png"));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    geo_location fp = this._w2f.world2frame(c);
                    Image newPokemon = image != null ? image.getScaledInstance(30, 30, Image.SCALE_DEFAULT) : null;
                    g.drawImage(newPokemon, (int) fp.x() - r, (int) fp.y() - r, null);

                }
            }
        }
    }

    private void drawAgants(Graphics g) {
        g.setColor(Color.red);
        int i = 0;
        while (scenario.getAgents() != null && i < scenario.getAgents().size()) {
            geo_location c = scenario.getAgents().get(i).getPos();
            int r = 8;
            i++;
            if (c != null) {
                BufferedImage image = null;
                try {
                    image = ImageIO.read(new File("agent.png"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                geo_location fp = this._w2f.world2frame(c);
                Image newRob = image != null ? image.getScaledInstance(30, 30, Image.SCALE_DEFAULT) : null;
                g.drawImage(newRob, (int) fp.x() - r, (int) fp.y() - r, null);

            }
        }
    }

    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);

        g.drawString("" + n.getKey(), (int) fp.x(), (int) fp.y() - 4 * r);
    }

    private void drawEdge(edge_data e, Graphics g) {
        geo_location s = graph.getNode(e.getSrc()).getLocation();
        geo_location d = graph.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
//        Font font = new Font("Verdana", Font.BOLD, 10);
//        g.setFont(font);
//        g.setColor(Color.black);
//        g.drawString("" + e.getWeight(), (int) s0.x(), (int) d0.y());
    }


}
