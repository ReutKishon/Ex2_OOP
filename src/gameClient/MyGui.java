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

import javax.swing.*;
import java.awt.*;


public class MyGui extends JFrame {
    myPanel panel;
    Scenario scenario;


    public MyGui(Scenario scenario) {
        super();
        this.scenario = scenario;
        panel = new myPanel(scenario);
        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // EDIT
        this.setVisible(true);
    }


}

