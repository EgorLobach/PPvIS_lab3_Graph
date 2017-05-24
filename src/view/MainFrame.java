package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by anonymous on 10.05.2017.
 */
public class MainFrame {
    private JFrame headFrame = new JFrame();
    private FunctionTable functionTable;
    private Controller controller;
    private JScrollPane graphScrollPane;
    private GraphPanel graphPanel;
    private JPanel headGraphPanel = new JPanel();
    private JPanel tableOfPointPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel scalePanel = new JPanel();
    private Dimension dimensionOfGraphPanel;
    private Dimension dimension;
    private JLabel numberLabel = new JLabel("Введите n:");
    private JTextField numberTextField = new JTextField();
    private JButton openFileButton = new JButton("Открыть файл");
    private JButton generateButton = new JButton("Сгенерировать строки");
    private JButton startButton = new JButton("Построить график");
    private String text = new String();
    private ArrayList<String> generatedRows = new ArrayList<>();
    private int number = 0;
    private JTextField scaleTextField = new JTextField("100%", 5);
    private Calculations calculations;

    public MainFrame(String title, Dimension d, Controller controller) {
        this.controller = controller;
        headFrame.setTitle(title);
        headFrame.setSize(d);
        headFrame.setLayout(new BorderLayout());
        tableOfPointPanel.setLayout(new BorderLayout());
        headGraphPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        headFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        functionTable = new FunctionTable(controller);
        graphPanel = new GraphPanel(controller);
        dimensionOfGraphPanel = new Dimension((int) (d.getWidth() - 300), (int) (d.getHeight() - 100));
        graphPanel.setPreferredSize(dimensionOfGraphPanel);
        dimension = new Dimension((int) (d.getWidth() - 300), (int) (d.getHeight() - 100));
        graphScrollPane = new JScrollPane(graphPanel);
        graphScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        graphScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        graphScrollPane.setPreferredSize(dimension);
        graphScrollPane.setAutoscrolls(true);
    }

    public void initMainFrame() {
        openFileButton.addActionListener(new OpenFileActionListener());
        generateButton.addActionListener(new GenerateStringActionListener());
        startButton.addActionListener(new StartActionListener());
        tableOfPointPanel.add(functionTable.getTablePanel(), BorderLayout.CENTER);
        buttonPanel.add(numberLabel);
        buttonPanel.add(numberTextField);
        buttonPanel.add(openFileButton);
        buttonPanel.add(generateButton);
        buttonPanel.add(startButton);
        tableOfPointPanel.add(buttonPanel, BorderLayout.SOUTH);
        headFrame.add(tableOfPointPanel, BorderLayout.WEST);
        graphPanel.addMouseWheelListener(new ZoomListener());
        MoveListener ml = new MoveListener();
        graphPanel.addMouseListener(ml);
        graphPanel.addMouseMotionListener(ml);
        scaleTextField.addKeyListener(new ScaleKeyListener());
        headGraphPanel.add(graphScrollPane, BorderLayout.CENTER);
        scalePanel.add(scaleTextField);
        headGraphPanel.add(scalePanel, BorderLayout.SOUTH);
        headFrame.add(headGraphPanel, BorderLayout.CENTER);
        headFrame.setVisible(true);
    }

    private class ZoomListener implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getPreciseWheelRotation() < 0 && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                dimensionOfGraphPanel.setSize((int) (dimensionOfGraphPanel.getWidth() + 100), (int) (dimensionOfGraphPanel.getHeight() + 100));
                graphPanel.setSize(dimensionOfGraphPanel);
                graphPanel.setPreferredSize(dimensionOfGraphPanel);
                scaleTextField.setText(String.valueOf((int) ((dimensionOfGraphPanel.getWidth() / dimension.getWidth()) * 100) + "%"));
                graphPanel.repaint();
            }
            if (e.getPreciseWheelRotation() > 0 && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                if (dimensionOfGraphPanel.getWidth() >= 1000) {
                    dimensionOfGraphPanel.setSize((int) (dimensionOfGraphPanel.getWidth() - 100), (int) (dimensionOfGraphPanel.getHeight() - 100));
                    graphPanel.setSize(dimensionOfGraphPanel);
                    graphPanel.setPreferredSize(dimensionOfGraphPanel);
                    scaleTextField.setText(String.valueOf((int) ((dimensionOfGraphPanel.getWidth() / dimension.getWidth()) * 100) + "%"));
                    graphPanel.repaint();
                }
            }
        }
    }

    class MoveListener extends MouseAdapter {

        private Point origin;

        @Override
        public void mousePressed(MouseEvent e) {
            origin = e.getPoint();
        }


        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (origin != null) {
                JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, graphPanel);
                if (viewPort != null) {
                    int deltaX = origin.x - e.getX();
                    int deltaY = origin.y - e.getY();

                    Rectangle view = viewPort.getViewRect();
                    view.x += deltaX * 0.3;
                    view.y += deltaY * 0.2;
                    graphPanel.scrollRectToVisible(view);
                }
            }
        }
    }

    private class OpenFileActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser("D:\\work\\java\\PPvIS_sem2\\PPvIS_lab3_Graph\\file");
            int ret = fileOpen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
                    String temp;
                    while ((temp = reader.readLine()) != null)
                        text += " " + temp;
                    text.trim();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    private class GenerateStringActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            number = Integer.parseInt(numberTextField.getText());
            for (int i = 1; i <= number; i++) {
                String randString = new String();
                int index = (int) (Math.random() * (text.length() - i));
                randString = text.substring(index, index + i);
                generatedRows.add(randString);
            }
        }
    }

    private class StartActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            calculations = new Calculations();
            calculations.start();
        }
    }

    private class ScaleKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                double temp =  (Integer.parseInt(scaleTextField.getText())/100.0);
                dimensionOfGraphPanel.setSize((int) (dimension.getWidth() * temp), (int) (dimension.getHeight() * temp));
                graphPanel.setSize(dimensionOfGraphPanel);
                graphPanel.setPreferredSize(dimensionOfGraphPanel);
                scaleTextField.setText(scaleTextField.getText() + "%");
                graphPanel.repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class Calculations extends Thread{

        @Override
        public void run() {
            super.run();
            synchronized (this) {
                for (int index = 1; index <= number; index++) {
                    double startTime = System.nanoTime();
                    int currentElem = 0;
                    for (int i = 0; i < text.length(); i++) {
                        if (text.charAt(i) == generatedRows.get(index - 1).charAt(currentElem))
                            currentElem++;
                        else currentElem = 0;
                        if (currentElem == index)
                            break;
                    }
                    double finishTime = System.nanoTime();
                    controller.add(finishTime - startTime);
                    graphPanel.reloadGraphPanel(controller);
                    functionTable.reloadTable(controller);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
