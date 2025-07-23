package employeee.management.system;

import java.awt.*;
import javax.swing.*;
//import com.toedter.calendar.JDateChooser;
import java.util.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class AddEmployee extends JFrame implements ActionListener {

    Random ran = new Random();
    int number = ran.nextInt(999999);
    Connection connection;

    JTextField tfname, tfdept, tfdesignation;
//    JDateChooser dcdob;
    JComboBox cbeducation;
    JLabel lblempId;
    JButton add, back;

    AddEmployee(Connection connection) {
        // Initialize the connection object
        this.connection = connection;
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
       
        JLabel heading = new JLabel("Add Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);

        tfname = new JTextField();
        tfname.setBounds(200, 150, 150, 30);
        add(tfname);

        JLabel labeldept = new JLabel("Department");
        labeldept.setBounds(50, 250, 150, 30);
        labeldept.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldept);

        tfdept = new JTextField();
        tfdept.setBounds(200, 250, 150, 30);
        add(tfdept);

        JLabel labeldesignation = new JLabel("Designation");
        labeldesignation.setBounds(50, 350, 150, 30);
        labeldesignation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldesignation);

        tfdesignation = new JTextField();
        tfdesignation.setBounds(200, 350, 150, 30);
        add(tfdesignation);

        add = new JButton("Add Details");
        add.setBounds(250, 550, 150, 40);
        add.addActionListener(this);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add(add);

        back = new JButton("Back");
        back.setBounds(450, 550, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        setSize(900, 700);
        setLocation(300, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            // Get the values from the text fields
            String name = tfname.getText();
            String dept = tfdept.getText();
            String designation = tfdesignation.getText();

            // Establish connection to the database
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database/employee_databse.db")) {
                // Create a PreparedStatement for the INSERT query
                String insertQuery = "INSERT INTO employees (NAME, DEPT, DESIGNATION) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    // Set the parameters for the PreparedStatement
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, dept);
                    preparedStatement.setString(3, designation);

                    // Execute the INSERT query
                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(this, "Employee added successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add employee.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to add employee.");
            }
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new AddEmployee();
    }
}
