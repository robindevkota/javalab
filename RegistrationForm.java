import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationForm extends JFrame {
    private JTextField idField, usernameField;
    private JPasswordField passwordField, repasswordField;
    private JRadioButton maleRadio, femaleRadio;
    private JCheckBox javaCheck, pythonCheck, cppCheck;
    private JComboBox<String> countryComboBox;
    private JButton submitButton, resetButton;

    private Connection connection;
    private PreparedStatement preparedStatement;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        idField = new JTextField(10);
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        repasswordField = new JPasswordField(10);
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        javaCheck = new JCheckBox("Java");
        pythonCheck = new JCheckBox("Python");
        cppCheck = new JCheckBox("C++");
        countryComboBox = new JComboBox<>(new String[]{"USA", "Canada", "UK", "Australia", "India"});
        submitButton = new JButton("Submit");
        resetButton = new JButton("Reset");

        // Layout
        setLayout(new GridLayout(9, 2));
        add(new JLabel("ID:"));
        add(idField);
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Re-enter Password:"));
        add(repasswordField);
        add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        add(genderPanel);
        add(new JLabel("Course:"));
        JPanel coursePanel = new JPanel();
        coursePanel.add(javaCheck);
        coursePanel.add(pythonCheck);
        coursePanel.add(cppCheck);
        add(coursePanel);
        add(new JLabel("Country:"));
        add(countryComboBox);
        add(submitButton);
        add(resetButton);

        // Event handling
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    insertData();
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
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

    private boolean validateForm() {
        // Check for emptiness
        if (idField.getText().isEmpty() || usernameField.getText().isEmpty() ||
                passwordField.getPassword().length == 0 || repasswordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return false;
        }

        // Check if password and re-entered password match
        if (!String.valueOf(passwordField.getPassword()).equals(String.valueOf(repasswordField.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return false;
        }

        return true;
    }

    private void insertData() {
        try {
            String query = "INSERT INTO sunil_tbl (id, username, password, gender, course, country) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idField.getText());
            preparedStatement.setString(2, usernameField.getText());
            preparedStatement.setString(3, String.valueOf(passwordField.getPassword()));
            preparedStatement.setString(4, maleRadio.isSelected() ? "Male" : "Female");
            StringBuilder course = new StringBuilder();
            if (javaCheck.isSelected()) course.append("Java ");
            if (pythonCheck.isSelected()) course.append("Python ");
            if (cppCheck.isSelected()) course.append("C++");
            preparedStatement.setString(5, course.toString());
            preparedStatement.setString(6, (String) countryComboBox.getSelectedItem());

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration successful!");
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while registering.");
        }
    }

    private void clearFields() {
        idField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        repasswordField.setText("");
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
        javaCheck.setSelected(false);
        pythonCheck.setSelected(false);
        cppCheck.setSelected(false);
        countryComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RegistrationForm();
            }
        });
    }
}
