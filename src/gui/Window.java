package gui;

import gameClient.GameEntryPoint;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window implements ActionListener {

    JFrame frame = new JFrame();
    JButton button = new JButton("Accept");
    TextField textFieldId = new TextField(30);
    TextField textFieldScenario = new TextField(30);
    JLabel labelId = new JLabel("please enter id:", JLabel.LEFT);
    JLabel labelLevel = new JLabel("please enter level:", JLabel.LEFT);
    private int id;
    private int level;

    JPanel jp = new JPanel();


    public Window() {


        textFieldId.setEditable(true);
        textFieldScenario.setEditable(true);
        button.setBounds(100, 160, 200, 40);
        button.addActionListener(this);
        frame.add(button);
        textFieldId.setSize(100, 100);
        textFieldScenario.setSize(30, 50);
        jp.add(labelId);
        jp.add(textFieldId);
        textFieldId.addActionListener(this);

        jp.add(labelLevel);
        jp.add(textFieldScenario);

        textFieldScenario.addActionListener(this);


        frame.add(jp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            GameEntryPoint gameEntryPoint = new GameEntryPoint(level, id);
            gameEntryPoint.run();

        } else if (e.getSource() == textFieldId) {
            String inputId = textFieldId.getText();
            this.id = Integer.parseInt(inputId);
        } else if (e.getSource() == textFieldScenario) {
            String inputLevel = textFieldScenario.getText();
            level = Integer.parseInt(inputLevel);

        }
    }
}

