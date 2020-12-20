package gui;

import gameClient.Scenario;

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
        this.setResizable(true);
        this.setVisible(true);
    }


}

