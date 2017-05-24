import controller.Controller;
import model.DataBase;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by anonymous on 10.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DataBase dataBase = new DataBase();
                Controller controller = new Controller(dataBase);
                MainFrame mainFrame = new MainFrame("Frame", new Dimension(1200, 800), controller);
                mainFrame.initMainFrame();
            }
        });
    }
}
