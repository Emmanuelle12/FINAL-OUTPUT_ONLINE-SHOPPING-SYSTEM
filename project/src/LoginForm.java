import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame implements ActionListener {
    private JFrame loginFrame;
    private Container container=getContentPane();
    private JLabel userLabel=new JLabel("USERNAME");
    private JLabel passwordLabel=new JLabel("PASSWORD");
    private JTextField userTextField=new JTextField();
    private JPasswordField passwordField=new JPasswordField();
    private JButton loginButton=new JButton("LOGIN");
    private JButton registertButton=new JButton("REGISTER");
    private JCheckBox showPassword=new JCheckBox("Show Password");

    public LoginForm() {
        this.loginFrame = new JFrame();
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public JFrame getLoginFrame() {
        loginFrame.setTitle("Login");
        container.setBackground(Color.decode("#0f1338"));
        userLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        showPassword.setForeground(Color.WHITE);
        showPassword.setBackground(Color.decode("#0f1338"));
        loginButton.setForeground(Color.WHITE);
        registertButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.decode("#0f1338"));
        registertButton.setBackground(Color.decode("#0f1338"));
        loginFrame.setBounds(10,10,400,300);
        loginFrame.setResizable(false);
        loginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        loginFrame.add(container);
        return loginFrame;
    }

    public void addComponentsToContainer() {
       container.add(userLabel);
       container.add(passwordLabel);
       container.add(userTextField);
       container.add(passwordField);
       container.add(showPassword);
       container.add(loginButton);
       container.add(registertButton);
    }

    public void setLayoutManager() {
       container.setLayout(null);
    }

   public void setLocationAndSize() {
       userLabel.setBounds(50,50,100,30);
       passwordLabel.setBounds(50,120,100,30);
       userTextField.setBounds(150,50,150,30);
       passwordField.setBounds(150,120,150,30);
       showPassword.setBounds(150,150,150,30);
       loginButton.setBounds(50,200,100,30);
       registertButton.setBounds(200,200,100,30);
    }

    public void addActionEvent() {
       loginButton.addActionListener(this);
       registertButton.addActionListener(this);
       showPassword.addActionListener(this);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getUserTextField() {
        return userTextField;
    }

    public String getPasswordField() {
        return String.valueOf(passwordField.getPassword());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DBController db = new DBController();
        if(e.getSource() == loginButton) {
            String pword = String.copyValueOf(passwordField.getPassword());
            String uname = userTextField.getText();
            if(uname.isBlank() || pword.isBlank()) {
                JOptionPane.showMessageDialog(null, "Please enter your username and password");
            } else if(db.loginUser(uname, pword) == 0) {
                JOptionPane.showMessageDialog(null, "Incorrect username or password");
            } else if(db.loginUser(uname, pword) != 0) {
                int id = db.loginUser(uname, pword);
                MainForm main = new MainForm(id);
                JFrame mainFrame = main.getMainFrame();
                mainFrame.setVisible(true);
                loginFrame.dispose();
            }
        }

        if(e.getSource() == registertButton) {
            RegistrationForm register = new RegistrationForm();
            JFrame registerFrame = register.getRegisterFrame();
            registerFrame.setVisible(true);
            loginFrame.dispose();
        }

        if(e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }
}
