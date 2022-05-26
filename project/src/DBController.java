import java.sql.*;

import javax.swing.JOptionPane;

public class DBController {
    Connection conn;
    ResultSet result;

    public DBController() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineselling", "root", "");
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public int loginUser(String uname,String pword) {
        String password ="";
        int id = 0;
        try {
            Statement st = conn.createStatement();
            result = st.executeQuery("select id,pword from customer where uname = '"+uname+"'");
            result.next();
            password = result.getString("pword");
            id = result.getInt("id");
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        if(pword.equals(password)) {
            return id;
        } else {
            return 0;
        }
    }

    public Boolean userExist(String uname) {
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from customer where uname = ?");
            stmt.setString(1, uname);
            result = stmt.executeQuery();
            result.next();
            if(result.wasNull()) {
                return true;
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false;
    }

    public void registerUser(String name, String age, String uname, String pword) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into customer(customername,customerage,uname,pword) values(?,?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, age);
            stmt.setString(3, uname);
            stmt.setString(4, pword);
            if(stmt.execute()) {
                JOptionPane.showMessageDialog(null, "Your account has been registered");
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void sellItem(int id, String name, String desc, String price, String due) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into items2(seller_id,item_name,item_description,item_price,item_duedate) values(?,?,?,?,?)");
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, desc);
            stmt.setString(4, price);
            stmt.setString(5, due);
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Item has been added");
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public ResultSet itemsOnSale(int id) {
        try {
            Statement st = conn.createStatement();
            result = st.executeQuery("select * from items2 where seller_id = '"+id+"'");
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return result;
    }

    public ResultSet getOrders(int id) {
        try {
            Statement st = conn.createStatement();
            result = st.executeQuery("select * from items2 inner join orders2 on items2.item_id = orders2.item_id and items2.seller_id = '"+id+"' left join delivery on delivery.orders_id = orders2.orders_id");
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return result;
    }

    public ResultSet getDelivery(int id) {
        try {
            Statement st = conn.createStatement();
            result = st.executeQuery("select * from items2 inner join orders2 on items2.item_id = orders2.item_id and orders2.customer_id = '"+id+"' left join delivery on delivery.orders_id = orders2.orders_id");
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return result;
    }

    public ResultSet getProducts() {
        try {
            Statement st = conn.createStatement();
            result = st.executeQuery("select * from items2");
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return result;
    }

    public void testRun(int id) {
        try {
            Statement st = conn.createStatement();
            result =st.executeQuery("select * from items2 inner join orders2 on items2.item_id = orders2.item_id and items2.seller_id = '"+id+"' left join delivery on delivery.orders_id = orders2.orders_id");
            while(result.next()) {
                System.out.println(result.getString("item_name"));
                System.out.println(result.getString("orders_date"));
                System.out.println(result.getString("delivery_details"));
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
