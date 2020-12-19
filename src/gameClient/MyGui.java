package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;

import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import help.Agent;
import help.Pokemon;
import help.Scenario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyGui extends JFrame {

    directed_weighted_graph graph;
    private final Range ry;
    private final Range rx;
    private final Range rz;
    private gameClient.util.Range2Range _w2f;
    private final Scenario scenario;
    ArrayList<Pokemon> pokemons;


    private final int kRADIUS = 5;


    public MyGui(Scenario scenario) {
        this.scenario = scenario;
        this.pokemons = scenario.getPokemonsList();
        this.graph = scenario.getGraph();
        this.ry = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);
        this.rx = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);
        this.rz = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    public void updatePokemons(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }


    private void updateFrame() {
        Range rx = new Range(20, this.getWidth() - 20);
        Range ry = new Range(this.getHeight() - 10, 150);
        Range2D frame = new Range2D(rx, ry);
        directed_weighted_graph g = scenario.getGraph();
        _w2f = Arena.w2f(g, frame);
    }


    public double rangeX(double d) {
        double outX = (d - rx.get_min()) / (rx.get_max() - rx.get_min());
        double x = 100 * (12 * outX + 1);
        return x;
    }


    public double rangeY(double d) {
        double outY = (d - ry.get_min()) / (ry.get_max() - ry.get_min());
        double y = 400 * (1 - outY) + 100;
        return y;
    }


    public void paint(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);
        updateFrame();

        drawPokemons(g);
        drawGraph(g);
        drawAgants(g);

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

            for (Pokemon pokemon : scenario.pokemonsList) {

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
        //	Iterator<OOP_Point3D> itr = rs.iterator();
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
        //	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
    }


}

