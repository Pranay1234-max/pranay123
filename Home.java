package employeee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Home extends JFrame implements ActionListener {

    JButton view, add, update, remove;
    Connection connection;

    Home() {
        setLayout(null);
        // Load the SQLite JDBC driver
    try {
        Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "SQLite JDBC driver not found.");
        System.exit(1);
    }

        // SQLite database connection
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database/employee_databse.db");
            System.out.println("Connected to the database.");

            // Create employees table if not exists
            Statement statement = connection.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS employees (" +
                                     "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                     "NAME TEXT," +
                                     "DEPT TEXT," +
                                     "DESIGNATION TEXT)";
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1120, 630);
        add(image);

        JLabel heading = new JLabel("Employee Management System");
        heading.setBounds(620, 20, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        image.add(heading);

        add = new JButton("Add Employee");
        add.setBounds(650, 80, 150, 40);
        add.addActionListener(this);
        image.add(add);

        view = new JButton("View Employees");
        view.setBounds(820, 80, 150, 40);
        view.addActionListener(this);
        image.add(view);

        remove = new JButton("Remove Employee");
        remove.setBounds(820, 140, 150, 40);
        remove.addActionListener(this);
        image.add(remove);

        setSize(1120, 630);
        setLocation(250, 100);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            setVisible(false);
            new AddEmployee(connection);
        } else if (ae.getSource() == view) {
            setVisible(false);
            new ViewEmployee(connection);
        } else if (ae.getSource() == remove){
            setVisible(false);
            new ManageEmployee();
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}