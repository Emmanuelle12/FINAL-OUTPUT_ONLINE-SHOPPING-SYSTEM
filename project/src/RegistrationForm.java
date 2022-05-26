import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationForm extends JFrame implements ActionListener{

    private JFrame registerFrame;
    private JPanel panel;
    private JLabel nameLabel = new JLabel("Name:");
    private JLabel ageLabel = new JLabel("Age:");
    private JLabel unameLabel = new JLabel("Username:");
    private JLabel pwordLabel = new JLabel("Password:");
    private JLabel pwordConfirmLabel = new JLabel("Confirm Password:");
    private JTextField nameField = new JTextField();
    private JTextField ageField = new JTextField();
    private JTextField unameField = new JTextField();
    private JPasswordField pwordField = new JPasswordField();
    private JPasswordField pwordConfirmField = new JPasswordField();
    private JButton registerButton = new JButton("REGISTER");
    private JButton logiButton = new JButton("LOGIN");
    
    public RegistrationForm() {
        this.registerFrame = new JFrame();
        this.panel = new JPanel();
        setLayoutManager();
        setComponents();
        addComponents();
        addActionEvent();
    }

    public JFrame getRegisterFrame() {
        registerFrame.setTitle("Register");
        registerFrame.setBounds(10,10,600,600);
        registerFrame.setResizable(false);
        registerFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        registerFrame.add(panel);
        return registerFrame;
    }

    public void setLayoutManager() {
        panel.setLayout(null);
    }

    private void addComponents() {
        panel.add(nameLabel);
        panel.add(ageLabel);
        panel.add(unameLabel);
        panel.add(pwordLabel);
        panel.add(pwordConfirmLabel);
        panel.add(nameField);
        panel.add(ageField);
        panel.add(unameField);
        panel.add(pwordField);
        panel.add(pwordConfirmField);
        panel.add(registerButton);
        panel.add(logiButton);
    }

    private void setComponents() {
        nameLabel.setBounds(50,50,200,30);
        nameField.setBounds(250,50,250,30);
        ageLabel.setBounds(50,100,200,30);
        ageField.setBounds(250,100,250,30);
        unameLabel.setBounds(50,150,200,30);
        unameField.setBounds(250,150,250,30);
        pwordLabel.setBounds(50,200,200,30);
        pwordField.setBounds(250,200,250,30);
        pwordConfirmLabel.setBounds(50,250,200,30);
        pwordConfirmField.setBounds(250,250,250,30);
        registerButton.setBounds(150,350,100,30);
        logiButton.setBounds(300,350,100,30);
    }

    public void addActionEvent() {
        registerButton.addActionListener(this);
        logiButton.addActionListener(this);
     }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==registerButton) {
            String uname = unameField.getText();
            String name = nameField.getText();
            String age = ageField.getText();
            String pword = String.copyValueOf(pwordField.getPassword());
            String cpword = String.copyValueOf(pwordConfirmField.getPassword());
            DBController db = new DBController();
            if(uname.isEmpty() || name.isEmpty() || age.isEmpty() || pword.isEmpty() || cpword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill up all the fields");
            }else if(db.userExist(uname)) {
                JOptionPane.showMessageDialog(null, "Account already exist");
            } else if(!pword.equals(cpword)) {
                JOptionPane.showMessageDialog(null, "Please check your password");
            } else {
                db.registerUser(name, age, uname, pword);
            }
        }

        if(e.getSource()==logiButton) {
            LoginForm login = new LoginForm();
            JFrame frame = login.getLoginFrame();
            frame.setVisible(true);
            registerFrame.dispose();
        }
    }
}
