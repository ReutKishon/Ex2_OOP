package help;
//import Server.Game_Server;
//import Server.game_service;
//import algorithms.Graph_Algo;
//import dataStructure.*;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import utils.Point3D;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
//
//import dataStructure.DGraph;
//import dataStructure.graph;
//import dataStructure.node_data;
//import utils.Range;


public class MyGameGUI<Public> extends JFrame implements MouseListener {

    directed_weighted_graph graph = new DWGraph_DS();                   //GUI elements
    Scenario s;
    dw_graph_algorithms graph_algo = new DWGraph_Algo();
    JMenuBar menuFrame;
    JMenu fileMenu, robotsMenu, gameTable;
    JMenuItem openItem, saveKmlItem, saveItem, automaticItem, manualItem, savePngItem, BestTableItem, CompTableItem;

    LinkedList<geo_location> points = new LinkedList<>();         //Compilations elements
    private final int kRADIUS = 5;
    game_service game;
    ArrayList<Pokemon> pokemons;
    ArrayList<Agent> agents;
    int players;
    HashMap<Point3D, Integer> path = new HashMap<>();
    Integer id = null;
    Thread thread;
    public static final double EPS = 0.0001;
    private final Range rx = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);
    private final Range ry = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);
    private final Range rz = new Range(Integer.MAX_VALUE, Integer.MIN_VALUE);

    int scenario;
    RunGame rg;
    boolean Auto;
    boolean Manu;
    boolean EndAuto;
    boolean EndManu;
    node_data pick;


    /**
     * constructor for gui without any parameter
     */

    public MyGameGUI() {                                       //starting without a loaded graph
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setBounds(500, 500, 600, 600);
        this.setTitle("The maze of Waze 3");
        initComponents();
        actionsGui();
        this.setVisible(true);
        myGame();


    }

    /**
     * constructor for gui with graph parameter
     */

    public MyGameGUI(directed_weighted_graph g) {                              //starting with a loaded graph
        myGame();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        this.setLayout(new FlowLayout());
        this.setBounds(500, 500, 600, 600);
        this.setTitle("The maze of Waze 3");
        this.graph = g;
        this.graph_algo.init(g);
        Collection<node_data> nd = this.graph.getV();
        Iterator<node_data> it = nd.iterator();             //painting the loaded graph
        while (it.hasNext()) {
            points.add(it.next().getLocation());
        }
        actionsGui();


    }

    /**
     * initiate the GUI components:
     * File tab
     * Driving robots tab
     */

    public void initComponents() {                          //creating the tabs of the GUI


        menuFrame = new JMenuBar();               //set menu bar
        setJMenuBar(menuFrame);
        menuFrame.setVisible(true);

        //set file menu	with open, save
        fileMenu = new JMenu("File");

        //keyboard shortcuts
        openItem = new JMenuItem("Open");
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        fileMenu.add(openItem);

        saveItem = new JMenuItem("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileMenu.add(saveItem);

        savePngItem = new JMenuItem("Save as Png");
        savePngItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        fileMenu.add(savePngItem);

        saveKmlItem = new JMenuItem("Save as KML");
        saveKmlItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        fileMenu.add(saveKmlItem);

        menuFrame.add(fileMenu);

        // set algorithms menu
        robotsMenu = new JMenu("Robots");
        manualItem = new JMenuItem("Manual driving");
        manualItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
        robotsMenu.add(manualItem);

        automaticItem = new JMenuItem("Automatic driving");
        automaticItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
        robotsMenu.add(automaticItem);

        menuFrame.add(robotsMenu);

        gameTable = new JMenu("Game Table");

        BestTableItem = new JMenuItem("Option A");
        BestTableItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        gameTable.add(BestTableItem);

        CompTableItem = new JMenuItem("Option B");
        CompTableItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        gameTable.add(CompTableItem);

        menuFrame.add(gameTable);
    }

    /**
     * Activating the Functions in each tab
     * Open graph
     * save graph
     * save png of graph
     * save KMl to file
     */

    public void actionsGui() {                  //After user chose an option the GUI option will execute the option here

//        openItem.addActionListener(new ActionListener() {        //File menu actions
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                readFileDialog();
//            }
//        });
//        saveItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                writeFileDialog();
//                actionsGui();
//            }
//        });
//        savePngItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                savePng();
//                actionsGui();
//            }
//        });


        /**
         * Activating the funcions of driving robots
         *  drive robot manual
         *  drive robot automatic
         *
         */

        automaticItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                automaticDrive();
                actionsGui();
            }
        });
//        manualItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                manualDrive();
//                actionsGui();
//            }
//        });
//        BestTableItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                bTable();
//                actionsGui();
//            }
//        });
//        CompTableItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                aTable();
//                actionsGui();
//            }
//        });
    }

    /**
     * Option A is for showing number of games id results,and current level and top score of id player
     */

//    private void aTable() {
//        JFrame f = new JFrame();
////        SimpleDB.main(null);
//
//        try {
//            JSONObject info = new JSONObject(game.toString());//deserialize
//            JSONObject serverInfo = info.getJSONObject("GameServer");
//            int id = serverInfo.getInt("id");
//        } catch (JSONException e) {
//            e.printStackTrace();
//
//        }
//
//        String[] col = {"Place", "Level number", "Score", "Moves", "Date"};
//
//        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
//        // The 0 argument is number rows.
//
//        JTable table = new JTable(tableModel);
//
//        JOptionPane.showMessageDialog(f, "Number of games: 24"
//                + "\nCurrent level:  23");
//
//        //our top score
//        int numberGames = 0;
//
//        for (int i = 0; i < 24; i++) {
//            int id0 = SimpleDB.id.get(i);
//            int sco = score.get(i);
//            int lev = levelId.get(i);
//            int moves0 = moves.get(i);
//            Date dat = dateTime.get(i);
//
//
//            Object[] data = {i, id0, lev, sco, moves0, dat};
//
//            tableModel.addRow(data);
//
//
//        }
//
//        table.setBounds(30, 40, 200, 300);
//        // adding it to JScrollPane
//        JScrollPane sp = new JScrollPane(table);
//        f.add(sp);
//
//        // Frame Size
//        f.setSize(500, 200);
//        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
//        table.setRowSorter(sorter);
//
//        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
//        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
//        //sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
//        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
//        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
//        sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
//        sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
//        sorter.setSortKeys(sortKeys);
//        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        // Frame Visible = true
//        f.setVisible(true);
//
//
//    }

    /**
     * Option B is for watching the location of player compared to all of the players
     */


//    private void bTable() {
//        JFrame f = new JFrame();
//        SimpleDB.main(null);
//
//
//        String col1[] = {"Level num", "Best grade", "Place"};                                                //second table of our top score
//
//        DefaultTableModel tableModel = new DefaultTableModel(col1, 0);
//
//        JTable table = new JTable(tableModel);
//        int i = 0;
//        while (i < 24) {
//
//            Object[] data = {i, maxScore[i], PosLevel[i]};
//
//            tableModel.addRow(data);
//            i++;
//        }
//
//        table.setBounds(30, 40, 200, 300);
//        // adding it to JScrollPane
//        JScrollPane sp = new JScrollPane(table);
//        f.add(sp);
//
//        // Frame Size
//        f.setSize(500, 200);
//        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
//        table.setRowSorter(sorter);
//
//        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
//        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
//        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
//        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
//        sorter.setSortKeys(sortKeys);
//        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        // Frame Visible = true
//        f.setVisible(true);
//    }


    /**
     * Activating 'drive robot manual'
     */
//    private void manualDrive() {
//        try {
//            Manu = true;
//            s.game.startGame();
//            manuDrive();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

    /**
     * Activating 'drive robot automatic'
     */
    private void automaticDrive() {
        Auto = true;
        rg = new RunGame(scenario);
        game = rg.scenario.game;
        ThreadPaintAuto(RunGame.scenario.game);

    }

    /**
     * Saving the graph/status as png file
     */
//    private void savePng() {
//        try {
//            BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight() + 45, BufferedImage.TYPE_INT_RGB);
//            Graphics g = img.getGraphics();
//            paint(g);
//            ImageIO.write(img, "png", new File("Graph.png"));
//            JFrame j = new JFrame();
//            JOptionPane.showMessageDialog(j, "Graph is saved,now the GUI will close");
//            j.setDefaultCloseOperation(3);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * load graph using serializable number from txt file
     */
//    public void readFileDialog() {
//        FileDialog fd = new FileDialog(this, "Open text file", FileDialog.LOAD);
//        fd.setFile("*.txt");
//        fd.setDirectory("C:\\");
//        fd.setFilenameFilter((dir, name) -> name.endsWith(".txt"));
//        fd.setVisible(true);
//        String fileName = fd.getFile();
//        try {
//            FileInputStream file = new FileInputStream(fileName);
//            ObjectInputStream in = new ObjectInputStream(file);
//
//            this.graph = (directed_weighted_graph) in.readObject();
//            graph_algo.init(graph);
//            repaint();
//            in.close();
//            file.close();
//
//            System.out.println("Object has been deserialized");
//
//        } catch (IOException | ClassNotFoundException ex) {
//            System.out.print("Error reading file " + ex);
//            System.exit(2);
//        } catch (Exception ex) {
//            System.out.println("No file was loaded");
//        }
//    }

    /**
     * Save graph using serializable number to txt file
     */
//    public void writeFileDialog() {                                                   //write the graph into a file using serializable
//        FileDialog fd = new FileDialog(this, "Save the text file", FileDialog.SAVE);
//        fd.setFile("*.txt");
//        fd.setFilenameFilter((dir, name) -> name.endsWith(".txt"));
//        fd.setVisible(true);
//        String fileName = fd.getFile();
//        directed_weighted_graph g = this.graph;
//        try {
//            FileOutputStream file = new FileOutputStream(fileName);
//            ObjectOutputStream out = new ObjectOutputStream(file);
//            out.writeObject(g);
//
//            file.close();
//            out.close();
//
//        } catch (IOException ex) {
//            System.out.print("Error writing file  " + ex);
//            System.exit(2);
//        } catch (NullPointerException ex) {
//            JFrame f = new JFrame();
//            JOptionPane.showMessageDialog(f, "The graph was saved");
//            f.setDefaultCloseOperation(EXIT_ON_CLOSE);
//            System.exit(-1);
//        }
//
//    }

    /**
     * coordinate to be scaled to x range
     */

    public double rangeX(double d) {
        double outX = (d - rx.get_min()) / (rx.get_max() - rx.get_min());
        double x = 100 * (12 * outX + 1);
        return x;
    }

    /**
     * coordinate to be scaled to y range
     */
    public double rangeY(double d) {
        double outY = (d - ry.get_min()) / (ry.get_max() - ry.get_min());
        double y = 400 * (1 - outY) + 100;
        return y;
    }

    /**
     * Double buffer for the paint function
     */
    @Override
    public void paint(Graphics g) {
        Image img = createImage(1300, 700);
        Graphics gImg = img.getGraphics();
        paintComponents(gImg);
        g.drawImage(img, 0, 0, this);

    }

    /**
     * paint the graph using oval for nodes(vertices) and lines as edges,
     * fruits as targets(apple means uphill and banana means downhill)
     * the robots(androids) mission is to collect the fruits
     */
    @Override
    public void paintComponents(Graphics g) {                                 //swing implementation of paint method
        super.paint(g);
        Collection<node_data> nodes = this.graph.getV();
        for (node_data node : nodes) {
            geo_location p = node.getLocation();

            g.setColor(Color.BLUE);
            g.fillOval(((int) (rangeX(p.x()) - kRADIUS)), ((int) (rangeY(p.y()) - kRADIUS)),
                    2 * kRADIUS, 2 * kRADIUS);
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(node.getKey()), (int) (rangeX(p.x())), -10 + (int) (rangeY(p.y())));
            path.put((Point3D) p, node.getKey());

            for (edge_data edge : this.graph.getE(node.getKey())) {
                geo_location src_location = this.graph.getNode(edge.getSrc()).getLocation();
                geo_location dest_location = this.graph.getNode(edge.getDest()).getLocation();

                g.setColor(Color.YELLOW);
                g.fillOval((int) (Math.round(((rangeX(src_location.x()) * (0.1) + rangeX(dest_location.x()) * (0.9)))) - kRADIUS),
                        (int) (Math.round((rangeY(src_location.y()) * (0.1) + rangeY(dest_location.y()) * (0.9))) - kRADIUS), 2 * kRADIUS, 2 * kRADIUS);

                g.setColor(Color.RED);
                g.drawLine((int) rangeX(dest_location.x()), (int) rangeY(dest_location.y()), (int) rangeX(src_location.x()), (int) rangeY(src_location.y()));

                double dist = edge.getWeight();
                g.drawString(String.format("%.2f", dist),
                        (int) (Math.round(((rangeX(src_location.x()) * (0.3) + rangeX(dest_location.x()) * (0.7)))) - kRADIUS),
                        (int) (Math.round((rangeY(src_location.y()) * (0.3) + rangeY(dest_location.y()) * (0.7))) - kRADIUS));

            }


        }
/**
 *
 *   print agents
 */
        for (Agent agent : agents) {

            this.id = agent.getId();

            Point3D pBefore = agent.getPos();
            Point3D pAfter = new Point3D(rangeX(pBefore.x()), rangeY(pBefore.y()));

            BufferedImage rob = null;
            try {
                rob = ImageIO.read(new File("agent.png"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            Image newRob = rob != null ? rob.getScaledInstance(20, 20, Image.SCALE_DEFAULT) : null;
            g.drawImage(newRob, (int) pAfter.x() - kRADIUS, (int) pAfter.y() - kRADIUS, null);


        }
        /**
         *
         *   print pokemons
         */
        for (Pokemon value : pokemons) {
            Point3D pBefore = value.getPos();
            Point3D pAfter = new Point3D(rangeX(pBefore.x()), rangeY(pBefore.y()));

            BufferedImage pokemon = null;
            try {
                pokemon = ImageIO.read(new File("pokemon.png"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            Image newPokemon = pokemon != null ? pokemon.getScaledInstance(20, 20, Image.SCALE_DEFAULT) : null;

            g.drawImage(newPokemon, (int) pAfter.x() - kRADIUS, (int) pAfter.y() - kRADIUS, null);
        }


/**
 * print seconds left till game ends
 */
        try {
            if (Auto) {
                String time = "Time till game Over: " + RunGame.scenario.game.timeToEnd() / 1000 + "";
                g.drawString(time, 650, 75);
            }
            if (Manu) {
                String time = "Time till game Over: " + s.game.timeToEnd() / 1000 + "";
                g.drawString(time, 650, 75);
            }
            /**
             *   print game score
             */
            if (EndAuto) {
                String end = "game Over: " + RunGame.scenario.game.toString();
                g.drawString(end, 450, 50);
            }
            if (EndManu) {
                String end = "game Over: " + s.game.toString();
                g.drawString(end, 450, 50);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Thread to print the movement of the agent every few milliseconds 'Auto thread'
     * different threads because auto run with a special scenario background support.
     */
    public void ThreadPaintAuto(game_service game) {

        thread = new Thread(() -> {
            while (RunGame.scenario.game.isRunning()) {
                try {
                    rg.scenario.game.move();
                    agents = rg.scenario.getAgentsList();
                    pokemons = RunGame.scenario.getPokemonsList();

                    Thread.sleep(100);
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            EndAuto = true;
            repaint();
            thread.interrupt();
        });

        thread.start();
    }

    /**
     * Thread to print the movement of the robot every few milliseconds 'Manual thread'
     */
    public void ThreadPaintManu(game_service game) {

        thread = new Thread(() -> {
            while (s.game.isRunning()) {
                try {
//                    s.game.move();
//                    agents = s.game.getAgents();
//                    pokemons = s.game.getPokemons();
                    Thread.sleep(1000);
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            EndManu = true;
            repaint();
            thread.interrupt();
        });

        thread.start();
    }

    /**
     * Initiate the scenario of the game
     */
    public void myGame() {

        this.scenario = Integer.parseInt(JOptionPane.showInputDialog("Enter scenario number between 0-23"));
        try {
            s = new Scenario(scenario);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        this.agents = s.getAgentsList();
        this.pokemons = s.getPokemonsList();
        this.graph = s.graph;
        this.graph_algo.init(this.graph);
        this.game = s.game;
        this.players = s.agentsList.size();

/**
 *   Update nodes to gui window
 */
        Collection<node_data> c = this.graph.getV();
        for (node_data n : c) {
            geo_location p = n.getLocation();
            double x = p.x();
            double y = p.y();
            double z = p.z();
            if (x < rx.get_min())
                rx.set_min(x);
            else if (x > rx.get_max())
                rx.set_max(x);
            if (y < ry.get_min())
                ry.set_min(y);
            else if (y > ry.get_max())
                ry.set_max(y);
            if (z < rz.get_min())
                rz.set_min(z);
            else if (z > rz.get_max())
                rz.set_max(z);
        }

    }

    /**
     * Manual drive of the robots using the input window
     */
//    public void manuDrive() {
//        s.game.startGame();
//        ThreadPaintManu(s.game);
//        this.addMouseListener(this);
//        while (s.game.isRunning()) {
//           String log = s.game.move();
//            if (log != null) {
//                for (int i = 0; i < log.size(); i++) {
//                    String rid1 = JOptionPane.showInputDialog(rootPane, "Enter robot id");
//                    String des = JOptionPane.showInputDialog(rootPane, "Enter destination");
//                    if (des != null) {
//                        int dest = Integer.parseInt(des);
//                        int robotId = Integer.parseInt(rid1);
//                        game.chooseNextEdge(robotId, dest);
//                    }
//
//                }
//            }
//        }
//
//        if (!s.game.isRunning()) Manu = false;
//        if (!s.game.isRunning()) this.removeMouseListener(this);
//    }
    public static void main(String[] args) {
        MyGameGUI mg = new MyGameGUI();

    }

    /**
     * Manual drive of the robots using the mouse clicks +++needs more work
     */
    @Override
    public void mouseClicked(MouseEvent e) {


        try {
            int x = e.getX();
            int y = e.getY();
            Point3D p3 = new Point3D(x, y, 0);
            double min_dist = (kRADIUS * 3);
            double best_dist = 100000;

            Collection<node_data> ver = graph.getV();
            Iterator<node_data> itr = ver.iterator();
            while (itr.hasNext()) {
                node_data n = itr.next();
                geo_location p = n.getLocation();
                double x1 = (rangeX(p.x()));
                double y1 = (rangeY(p.y()));
                Point3D pTemp = new Point3D(x1, y1, 0);
                double dist = pTemp.distance(p3);
                if (dist < min_dist && dist < best_dist) {
                    best_dist = dist;
                    pick = n;
                }
            }


        } catch (Exception ex) {
            System.out.println("There is no route between the two");
            ex.printStackTrace();

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

}

