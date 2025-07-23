package employeee.management.system;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class ManageEmployee extends JFrame {

    JTable table;
    DefaultTableModel model;
    JButton btnRemove;
    Connection connection;

    public ManageEmployee() {
        setTitle("Manage Employees");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Set up the table model
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Department", "Designation"});
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set up the scroll pane
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 50, 760, 400);
        add(sp);

        // Button to remove a record
        btnRemove = new JButton("Remove Selected Employee");
        btnRemove.setBounds(20, 460, 200, 30);
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                    removeEmployee(id);
                    model.removeRow(row);
                } else {
                    JOptionPane.showMessageDialog(null, "No row selected.");
                }
            }
        });
        add(btnRemove);
        
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(400, 460, 200, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            setVisible(false);  // Dispose current frame
            new Home();      // Open the Home screen
        }
            
        });
        add(btnBack);
        setVisible(true);

        // Connect to the database and fetch records
        connectAndFetch();
    }

    private void connectAndFetch() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database/employee_databse.db");
            fetchEmployees();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed");
            e.printStackTrace();
        }
    }

    private void fetchEmployees() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("ID"),
                    rs.getString("NAME"),
                    rs.getString("DEPT"),
                    rs.getString("DESIGNATION")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch employee records");
            e.printStackTrace();
        }
    }

    private void removeEmployee(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement("DELETE FROM employees WHERE ID = ?");
            pst.setInt(1, id);
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Employee removed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to remove employee.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to execute remove operation.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ManageEmployee();
    }
}
