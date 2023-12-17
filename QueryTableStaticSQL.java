package ca.myjava.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import presentation.GUI;

public class QueryTableStaticSQL {

    public void viewCountry(String id) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            if (!isValidCountryID(id)) {
                JOptionPane.showMessageDialog(null, "Invalid Country ID. Please enter a valid ID like 'CA', 'AB', 'OL'.");
                return;
            }

            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@calvin.humber.ca:1521:grok", "username", "password");
                 Statement statement = connection.createStatement()) {

                String query = "SELECT * FROM COUNTRIES WHERE COUNTRY_ID = '" + id + "'";

                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    GUI.idField.setText(resultSet.getString("COUNTRY_ID"));
                    GUI.countryField.setText(resultSet.getString("COUNTRY_NAME"));
                    GUI.regionField.setText(resultSet.getString("REGION_ID"));
                    GUI.lifeField.setText(resultSet.getString("LIFE_EXPECTANCY"));
                } else {
                    JOptionPane.showMessageDialog(null, "No Country found with ID: " + id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error executing SQL query: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading JDBC driver: " + e.getMessage());
        }
    }

    // Validate the country ID
    private boolean isValidCountryID(String id) {
        // Check if the ID is exactly 2 characters and contains only alphabets
        return id.length() == 2 && id.matches("[A-Za-z]+");
    }
}
