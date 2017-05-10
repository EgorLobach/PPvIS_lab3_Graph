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
                MainFrame mainFrame = new MainFrame("Frame", new Dimension(1000, 500));
                mainFrame.initMainFrame();
            }
        });
    }
}
