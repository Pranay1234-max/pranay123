package employeee.management.system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewEmployee extends JFrame implements ActionListener {

    JTable table;
    JButton back;
    Connection connection;  // Database connection

    public ViewEmployee(Connection connection) {
        this.connection = connection;
        setTitle("Employee Details");
        setLayout(new BorderLayout());
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Table setup
        table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Department", "Designation"}, 0);
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Load data into the table
        loadData(model);

        // Back button setup
        back = new JButton("Back");
        back.addActionListener(this);
        add(back, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadData(DefaultTableModel model) {
        try {
            String query = "SELECT * FROM employees";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String dept = rs.getString("DEPT");
                String designation = rs.getString("DESIGNATION");
                model.addRow(new Object[]{id, name, dept, designation});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading employee data.");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            setVisible(false);  // Dispose current frame
            new Home();      // Open the Home screen
        }
    }

   public static void main(String[] args) {
        Connection connection = null;
        try {
            // Assuming SQLite for example purposes
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database/employee_databse.db");

            new ViewEmployee(connection);  // Pass the connection to the ViewEmployee
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }
}
