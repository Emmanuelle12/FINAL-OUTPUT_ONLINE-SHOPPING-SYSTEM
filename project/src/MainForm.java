import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

import java.awt.event.*;

public class MainForm extends JFrame implements ActionListener{
    private JFrame mainFrame;
    private JTabbedPane mainPanel;
    private JPanel Sale;
    private JTable orderTable,deliveryTable,storeTable;
    private JButton sell = new JButton("SELL");
    private int userId;
    private JScrollPane orderScrollPane,productScrollPane,deliveryScrollPane,storeScrollPane;


    
    JLabel itemNameLabel = new JLabel("Name:");
    JLabel itemPriceLabel = new JLabel("Price:");
    JLabel dueDateLabel = new JLabel("Due Date:");
    JLabel itemDescLabel = new JLabel("Description:");
    JTextField itemNameTxt = new JTextField();
    JTextField itemPriceTxt = new JTextField();
    JTextField dueDateTxt = new JTextField();
    JTextField itemDescTxt = new JTextField();




    public MainForm(int id) {
        this.mainFrame = new JFrame();
        this.mainPanel = new JTabbedPane();
        this.userId = id;
        addActionEvent();
    }

    private void addActionEvent() {
        sell.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sell) {
            int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to sell this item?", "Confirmation Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
            if(input == 0) {
                DBController db = new DBController();
                db.sellItem(userId, itemNameTxt.getText(), itemDescTxt.getText(), itemPriceTxt.getText(), dueDateTxt.getText());
                itemDescTxt.setText("");
                itemNameTxt.setText("");
                itemPriceTxt.setText("");
                dueDateTxt.setText("");
            }
        }
    }

    public JFrame getMainFrame() {
        setOrderTable();
        setDeliveryTable();
        setStoreTable();
        setProducts();
        JPanel sellPanel = new JPanel();
        sellPanel = sellItem();
        sellPanel.setLayout(null);
        mainPanel.addTab("Products", productScrollPane);
        mainPanel.addTab("Orders", orderScrollPane);
        mainPanel.addTab("Delivery", deliveryScrollPane);
        mainPanel.addTab("Sell", sellPanel);
        mainPanel.addTab("Store", storeScrollPane);
        mainPanel.setBackground(Color.decode("#0f1338"));
        mainPanel.setForeground(Color.WHITE);
        mainFrame.add(mainPanel);
        mainFrame.setTitle("Online Selling System");
        mainFrame.setBounds(50,50,700,500);
        return mainFrame;
    }

    private void setStoreTable() {
        try {
            String cols[] = {"Name","Description","Price","Due Date"};
            String rows[][] = new String[100][4]; 
            DBController db = new DBController();
            ResultSet res = db.itemsOnSale(userId);
            int i = 0;
            while(res.next()) {
                String itemName = res.getString("item_name");
                String itemDesc = res.getString("item_description");
                String itemPrice = res.getString("item_price");
                String dueDate = res.getString("item_duedate");
                rows[i][0] = itemName;
                rows[i][1] = itemDesc;
                rows[i][2] = itemPrice;
                rows[i][3] = dueDate;
                i++;
            }
            DefaultTableModel model = new DefaultTableModel(rows,cols);
            storeTable = new JTable(model);
            storeTable.setRowHeight(50);
            storeTable.setBackground(Color.decode("#0f1338"));
            storeTable.setForeground(Color.WHITE);
            storeTable.setShowGrid(false);
            storeScrollPane = new JScrollPane(storeTable);
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setOrderTable() {
        try {
            String cols[] = {"Name","Date Ordered","Details"};
            String rows[][] = new String[100][3]; 
            DBController db = new DBController();
            ResultSet res = db.getOrders(userId);
            int i = 0;
            while(res.next()) {
                String itemName = res.getString("item_name");
                String orderDate = res.getString("orders_date");
                String details = res.getString("delivery_details");
                rows[i][0] = itemName;
                rows[i][1] = orderDate;
                if(details == null){
                    rows[i][2] = "unreceived";
                } else {
                    rows[i][2] = details;
                }
                i++;
            }
            DefaultTableModel model = new DefaultTableModel(rows,cols);
            orderTable = new JTable(model);
            orderTable.setRowHeight(50);
            orderTable.setBackground(Color.decode("#0f1338"));
            orderTable.setForeground(Color.WHITE);
            orderTable.setShowGrid(false);
            orderScrollPane = new JScrollPane(orderTable);
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setDeliveryTable() {
        try {
            String cols[] = {"Name","Date Ordered","Details"};
            String rows[][] = new String[100][3]; 
            DBController db = new DBController();
            ResultSet res = db.getDelivery(userId);
            int i = 0;
            while(res.next()) {
                String itemName = res.getString("item_name");
                String orderDate = res.getString("orders_date");
                String details = res.getString("delivery_details");
                rows[i][0] = itemName;
                rows[i][1] = orderDate;
                // rows[i][2] = details;
                if(details == null){
                    rows[i][2] = "unreceived";
                } else {
                    rows[i][2] = details;
                }
                i++;
            }
            DefaultTableModel model = new DefaultTableModel(rows,cols);
            deliveryTable = new JTable(model);
            // for(int x=0;x<=i;x++){
            //     if(deliveryTable.getModel().getValueAt(x+1, 3)==null) {
            //         JButton btnDelivery = new JButton("receive");
            //         deliveryTable.getModel().setValueAt(btnDelivery, x, 3);
            //     }
            // }
            deliveryTable.setRowHeight(50);
            deliveryTable.setBackground(Color.decode("#0f1338"));
            deliveryTable.setForeground(Color.WHITE);
            deliveryTable.setShowGrid(false);
            deliveryScrollPane = new JScrollPane(deliveryTable);
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setProducts() {
        FlowLayout layout = new FlowLayout();
        JPanel products = new JPanel(layout);
        DBController db = new DBController();
        try {
            ResultSet res = db.getProducts();
            while(res.next()) {
                String name = res.getString("item_name");
                String price = res.getString("item_price");
                String due = res.getString("item_duedate");
                JPanel card = createCard(name, due, price);
                products.add(card);
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        productScrollPane = new JScrollPane(products);
    }

    private JPanel createCard(String name, String due, String price) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(300,200));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        JLabel itemName = new JLabel(name);
        JLabel itemPrice = new JLabel(price);
        JLabel itemDue = new JLabel(due);
        itemName.setBounds(10,10,300,80);
        itemPrice.setBounds(10,90,300,40);
        itemDue.setBounds(10,120,300,20);
        card.add(itemName);
        card.add(itemPrice);
        card.add(itemDue);
        card.setBackground(Color.decode("#1A0420"));
        itemName.setForeground(Color.WHITE);
        itemDue.setForeground(Color.WHITE);
        itemPrice.setForeground(Color.WHITE);
        return card;
    }

    private JPanel sellItem() {
        Sale = new JPanel();
        itemNameLabel.setBounds(180, 10, 150, 60);
        itemNameTxt.setBounds(330, 25, 150, 30);
        itemDescLabel.setBounds(180, 70, 150, 60);
        itemDescTxt.setBounds(330, 85, 150, 30);
        itemPriceLabel.setBounds(180, 130, 150, 60);
        itemPriceTxt.setBounds(330, 145, 150, 30);
        dueDateLabel.setBounds(180, 190, 150, 60);
        dueDateTxt.setBounds(330, 205, 150, 30);
        sell.setBounds(255, 265, 150, 30);
        Sale.add(itemNameLabel);
        Sale.add(itemNameTxt);
        Sale.add(itemDescLabel);
        Sale.add(itemDescTxt);
        Sale.add(itemPriceLabel);
        Sale.add(itemPriceTxt);
        Sale.add(dueDateLabel);
        Sale.add(dueDateTxt);
        Sale.add(sell);
        return Sale;
    }
}
