//package gui;
//
//
//
//import api.*;
//import gameClient.MyGui;
//import gameClient.util.Point3D;
//import help.Agent;
//import help.Pokemon;
//
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.util.*;
//import java.util.List;
//import javax.imageio.ImageIO;
//import javax.swing.*;
//
//public class graphGui extends JFrame implements Serializable, MouseListener, MouseMotionListener {
//    directed_weighted_graph gra = new DWGraph_DS();                   //GUI elements
//    dw_graph_algorithms ga = new DWGraph_Algo();
//    JMenuBar menuFrame;
//    JMenu fileMenu, AlgorithmsMenu;
//    JMenuItem openItem, createItem, saveItem, ShortestPathLengthItem, ShortestPathRouteItem, TravelSalesmanProblemItem, IsConnectedItem, savePngItem, generateItem;
//
//    LinkedList<Point3D> points = new LinkedList<Point3D>();         //Compilations elements
//    Point3D mPivot_point = null;
//    boolean mDraw_pivot = false;
//    boolean mMoving_point = false;
//    boolean mCreate = false;
//    private int kRADIUS = 5;
//    int lastKey = 0;
//    boolean generate = false;
//
//
//    public graphGui() {                                       //starting without a loaded graph
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLayout(new FlowLayout());
//        this.setBounds(500, 500, 600, 600);
//        this.setTitle("The maze of Waze");
//        initComponents();
//        actionsGui();
//        this.setVisible(true);
//    }
//
//    public graphGui(directed_weighted_graph g) {                              //starting with a loaded graph
//        this.setVisible(true);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLayout(new FlowLayout());
//        this.setBounds(500, 500, 600, 600);
//        this.setTitle("The maze of Waze");
//        this.gra = g;
//        this.ga.init(g);
//        //painting the loaded graph
//        for (node_data node_data : gra.getV()) {
//            points.add((Point3D) node_data.getLocation());
//        }
//
//        repaint();
//        initComponents();
//        actionsGui();
//
//
//    }
//
//
//    public void initComponents() {                          //creating the tabs of the GUI
//
//
//        menuFrame = new JMenuBar();               //set menu bar
//        setJMenuBar(menuFrame);
//        menuFrame.setVisible(true);
//
//        //set file menu	with open, save
//        fileMenu = new JMenu("File");
//
//        //keyboard shortcuts
//        openItem = new JMenuItem("Open");
//        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
//        fileMenu.add(openItem);
//
//        createItem = new JMenuItem("Create");
//        createItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
//        fileMenu.add(createItem);
//
//        generateItem = new JMenuItem("Generate");
//        generateItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
//        fileMenu.add(generateItem);
//
//        menuFrame.add(fileMenu);
//
//
//    }
//
//
//    public void actionsGui() {                  //After user chose an option the GUI option will execute the option here
//
//
//
//
//        createItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                mCreate = true;
//                initComponents();
//                actionsGui();
//            }
//
//        });
//        generateItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                buildGraph();
//                generate = true;
//                repaint();
//                generate = false;
//                actionsGui();
//            }
//        });
//
//    }
//
//
//
//
////    //all the mouse options are for the create option
////    @Override
////    public void mouseDragged(MouseEvent e) {
////        int x = e.getX();
////        int y = e.getY();
////        if (mDraw_pivot) {
////            mPivot_point.setX(x);
////            mPivot_point.setY(y);
////
////            repaint();
////        }
////
////    }
//
////    @Override
////    public void mouseMoved(MouseEvent e) {
////        int x = e.getX();
////        int y = e.getY();
////        if (mDraw_pivot) {
////            mPivot_point.setX(x);
////            mPivot_point.setY(y);
////
////            repaint();
////        }
////        actionsGui();
////    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//        // TODO Auto-generated method stub
//    }
//
////    @Override
////    public void mousePressed(MouseEvent e) {
////        int x = e.getX();
////        int y = e.getY();
////        Point3D tmp = new Point3D(x, y);
////        this.mPivot_point = tmp;
////        int key;
////        if (this.gra != null) {
////            key = this.gra.nodeSize() + 1;
////        } else {
////            key = 0;
////        }
////        nodeData nd = new nodeData(tmp, key, 0.0, "", 0);
////        this.gra.addNode(nd);
////        int min_dist = (int) (kRADIUS * 1.5);
////        double best_dist = 1000000;
////        for (Point3D p : points) {
////            double dist = tmp.distance3D(p);
////            if (dist < min_dist && dist < best_dist) {
////                mPivot_point = p;
////                best_dist = dist;
////                mMoving_point = true;
////            }
////        }
////
////        if (mPivot_point == null) {
////            mPivot_point = new Point3D(x, y);
////        } else {
////            if (isExist(tmp)) {
////                mPivot_point = tmp;
////                //      points.add(tmp);
////                int sr = this.gra.nodeSize() - 1;
////                int de = this.lastKey;
////                double dist = this.gra.getNode(sr).getLocation().distance3D(tmp);
////                gra.connect(sr, de, dist);
////            }
////        }
////
////        mDraw_pivot = true;
////        repaint();
////
////
////    }
////
////    @Override
////    public void mouseReleased(MouseEvent e) {
////        if (!mMoving_point && !isExist(mPivot_point)) {
////            points.add(new Point3D(mPivot_point));
////            repaint();
////        }
////        mMoving_point = false;
////        mPivot_point = null;
////        mDraw_pivot = false;
////    }
//
//
//
//
//
//
//    public void paint(Graphics g) {                                 //swing implementation of paint method
//
//        super.paint(g);
//        Collection<node_data> ver = this.gra.getV();
//        Iterator<node_data> it = ver.iterator();
//        Point3D prev = null;
//        int i = 0;
//        while (it.hasNext()) {
//            node_data nd = it.next();
//            Point3D p = (Point3D)nd.getLocation();
//
//
//            g.setColor(Color.BLUE);
//            g.fillOval((int) p.x() - kRADIUS, (int) p.y() - kRADIUS,
//                    2 * kRADIUS, 2 * kRADIUS);
//            g.setColor(Color.BLACK);
//            g.drawString(Integer.toString(nd.getKey()), (int) p.x(), (int) p.y());
//
//            Iterator<edge_data> it1 = this.gra.getE(nd.getKey()).iterator();
//            while (it1.hasNext()) {
//                edge_data ed = it1.next();
//                Point3D psrc =(Point3D) this.gra.getNode(ed.getSrc()).getLocation();
//                Point3D pdest = (Point3D) this.gra.getNode(ed.getDest()).getLocation();
//
//                g.setColor(Color.YELLOW);
//                g.fillOval((int) (Math.round(((psrc.x() * (0.1) + pdest.x() * (0.9)))) - kRADIUS),
//                        (int) (Math.round((psrc.y() * (0.1) + pdest.y() * (0.9))) - kRADIUS), 2 * kRADIUS, 2 * kRADIUS);
//
//                g.setColor(Color.RED);
//                g.drawLine((int) pdest.x(), (int) pdest.y(), (int) psrc.x(), (int) psrc.y());
//
//                double dist = this.gra.getEdge(ed.getSrc(), ed.getDest()).getWeight();
//                g.drawString(String.format("%.2f", dist),
//                        (int) ((pdest.x() + psrc.x()) / 2),
//                        (int) ((pdest.y() + psrc.y()) / 2));
//                i++;
//
//            }
//            if (mCreate) {
//                if (prev != null) {
//                    g.setColor(Color.YELLOW);
//                    g.fillOval((int) (Math.round(((prev.x() * (0.1) + p.x() * (0.9)))) - kRADIUS),
//                            (int) (Math.round((prev.y() * (0.1) + p.y() * (0.9))) - kRADIUS), 2 * kRADIUS, 2 * kRADIUS);
//
//                    g.setColor(Color.RED);
//                    g.drawLine((int) p.x(), (int) p.y(), (int) prev.x(), (int) prev.y());
//
//                    double dist = prev.distance(p);
//                    g.drawString(String.format("%.2f", dist),
//                            (int) ((p.x() + prev.x()) / 2),
//                            (int) ((p.y() + prev.y()) / 2));
//                }
//            }
//
//            if (mCreate) prev = p;
//
//        }
//
//        if (mDraw_pivot && !mMoving_point && mCreate) {                                            //create option when moving a point3d
//            g.setColor(Color.BLUE);
//            g.fillOval((int) mPivot_point.x() - kRADIUS, (int) mPivot_point.y() - kRADIUS,
//                    2 * kRADIUS, 2 * kRADIUS);
//            if (prev != null) {
//                g.setColor(Color.RED);
//
//                g.drawLine((int) mPivot_point.x(), (int) mPivot_point.y(),
//                        (int) prev.x(), (int) prev.y());
//                g.setColor(Color.GRAY);
//                double dist = prev.distance(mPivot_point);
//                g.drawString(String.format("%.2f", dist), (int) ((mPivot_point.x() + prev.x()) / 2), (int) ((mPivot_point.y() + prev.y()) / 2));
//            }
//
//        }
//        if (!generate && mCreate) {                                    //if creating a graph or loading one doesn't need to save the graph type
//            int i1 = 1;
//            double wei = 0;
//            Point3D pre = null;
//            for (Point3D p : points) {
//                if (pre != null && i1 != points.size()) {
//                    wei = pre.distance(p);
//                    int b = i1 - 1;
//                    gra.connect(b, i1, wei);
//                }
//
//                pre = p;
//                ++i1;
//            }
//        }
//    }
//
//    private boolean isExist(Point3D p) {                    //check if the point is already in graph
//        int count = 1;
//        for (Point3D p1 : points) {
//            if (p1.close2equals(p)) {
//                this.lastKey = count;
//                return true;
//            }
//            if (p1.equalsXY(p)) return true;
//            count++;
//        }
//        return false;
//    }
//
//    public void buildGraph() {                      //generate graph
//
//        generate = true;
//        for (int i = 0; i < 100; i++) {
//            Point3D pi = new Point3D(Math.random() * 700 , 0);
//            node_data n = new Node(i,0,pi,0.0,null);
//            this.gra.addNode(n);
//        }
//        for (int i = 0; i < 250; i++) {
//            this.gra.connect((int) (Math.random() * 100), (int) (Math.random() * 100), (int) (Math.random() * 20));
//        }
//
//    }
//
//    public void buildGraph(directed_weighted_graph G) {                             //generate option to create graph
//
//        generate = true;
//        Iterator<node_data> it = G.getV().iterator();
//        while (it.hasNext()) {
//            this.points.add((Point3D) it.next().getLocation());
//        }
//        this.gra = G;
//
//    }
//
//
//    public static void main(String[] args) {
//        directed_weighted_graph g = new DWGraph_DS();
//        node_data n1 = new Node(0, new Point3D(95, 130, 52));
//        node_data n2 = new Node(1, new Point3D(89, 50, 91));
//        node_data n3 = new Node(2, new Point3D(210, 70, 162));
//        node_data n4 = new Node(3, new Point3D(40, 100, 60));
//        node_data n5 = new Node(4, new Point3D(100, 200, 130));
//        g.addNode(n1);
//        g.addNode(n2);
//        g.addNode(n3);
//        g.addNode(n4);
//        g.addNode(n5);
//
//
//        g.connect(0, 4, 4);
//        g.connect(0, 2, 2);
//        g.connect(1, 4, 12);
//        g.connect(4, 1, 1);
//        g.connect(2, 1, 10);
//        g.connect(3, 2, 8);
//
//        List<Agent> agents = new ArrayList<>();
//        agents.add(new Agent(0,new Point3D(97,140,50)));
//
//        List<Pokemon> pokemons = new ArrayList<>();
//        pokemons.add(new Pokemon(new Point3D(120,69,50)));
//        pokemons.add(new Pokemon(new Point3D(56,84,50)));
//
//
//        JFrame jFrame = new JFrame("My gui");
//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        MyGui myGui = new MyGui();
//        jFrame.add(myGui);
//        jFrame.setSize(700, 500);
//        jFrame.setVisible(true);
//
//
//    }
//
//    @Override
//    public void mouseDragged(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseMoved(MouseEvent e) {
//
//    }
//}
//
//
