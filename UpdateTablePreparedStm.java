package ca.myjava.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class UpdateTablePreparedStm {

    public static void updateLifeExpectancy(String countryID, float newLifeExpectancy) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // Validate the country ID
            if (!isValidCountryID(countryID)) {
                JOptionPane.showMessageDialog(null, "Invalid Country ID. Please enter a valid ID like 'CA', 'AB', 'OL'.");
                return;
            }

            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver loaded");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@calvin.humber.ca:1521:grok", "username", "password");

            // Create a prepared statement for update
            String sql = "UPDATE COUNTRIES SET LIFE_EXPECTANCY = ? WHERE COUNTRY_ID = ?";
            statement = conn.prepareStatement(sql);

            // Set parameters for the prepared statement
            statement.setFloat(1, newLifeExpectancy);
            statement.setString(2, countryID);

            // Execute the update
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("Failed to update record. Country ID not found.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    // Validate the country ID
    private static boolean isValidCountryID(String id) {
        // Check if the ID is exactly 2 characters and contains only alphabets
        return id.length() == 2 && id.matches("[A-Za-z]+");
    }
}
