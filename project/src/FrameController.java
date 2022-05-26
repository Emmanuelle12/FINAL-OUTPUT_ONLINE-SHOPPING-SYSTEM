import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FrameController extends JFrame implements ActionListener {

    final JFrame loginFrame;

    public FrameController() {
        LoginForm login = new LoginForm();
        this.loginFrame = login.getLoginFrame();
        this.loginFrame.setVisible(true);
        // MainForm main = new MainForm(1);
        // this.mainFrame = main.getMainFrame();
        // this.mainFrame.setVisible(true);
    }

    public void addActionEvent() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
