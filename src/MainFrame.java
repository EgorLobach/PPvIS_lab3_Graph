import javax.swing.*;
import java.awt.*;

/**
 * Created by anonymous on 10.05.2017.
 */
public class MainFrame {
    private JFrame headFrame = new JFrame();

    public MainFrame(String title, Dimension d){
        headFrame.setTitle(title);
        headFrame.setSize(d);
        headFrame.setLayout(new BorderLayout());
        headFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public void initMainFrame() {
        headFrame.pack();
        headFrame.setVisible(true);
    }
}
