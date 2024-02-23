import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton updateButton;
    private JButton deleteButton;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public LoginForm() {
        setTitle("Login Form");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        loginButton = new JButton("Login");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        // Layout
        setLayout(new GridLayout(4, 2));
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(updateButton);
        add(deleteButton);

        // Event handling
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRecord();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });

        // Database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/prime7th", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    private void loginUser() {
        try {
            String query = "SELECT * FROM sunil_tbl WHERE username = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usernameField.getText());
            preparedStatement.setString(2, String.valueOf(passwordField.getPassword()));

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                displayRecords();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while logging in.");
        }
    }

    private void displayRecords() {
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT * FROM sunil_tbl";
            resultSet = statement.executeQuery(query);

            // Displaying records
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Username: " + resultSet.getString("username") +
                        ", Password: " + resultSet.getString("password") +
                        ", Gender: " + resultSet.getString("gender") +
                        ", Course: " + resultSet.getString("course") +
                        ", Country: " + resultSet.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRecord() {
        try {
            String updateQuery = "UPDATE sunil_tbl SET course = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, "python");
            preparedStatement.setInt(2, 3);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record updated successfully.");
                displayRecords();
            } else {
                JOptionPane.showMessageDialog(this, "Record update failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while updating record.");
        }
    }

    private void deleteRecord() {
        try {
            String deleteQuery = "DELETE FROM sunil_tbl WHERE username = ?";
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, "sunil");

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record deleted successfully.");
                displayRecords();
            } else {
                JOptionPane.showMessageDialog(this, "Record deletion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while deleting record.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginForm();
            }
        });
    }
}
