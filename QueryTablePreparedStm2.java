package ca.myjava.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.connection.OracleJDBC;
import presentation.GUI;

public class QueryTablePreparedStm2 {

    public QueryTablePreparedStm2() {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rset = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver loaded");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@calvin.humber.ca:1521:grok", "username", "password");
            
            // Create a prepared statement
            String sql = "SELECT COUNTRY_NAME, REGION_ID, LIFE_EXPECTANCY FROM COUNTRIES"
                    + " WHERE LIFE_EXPECTANCY BETWEEN ? and ? order by 3 desc";
            statement = conn.prepareStatement(sql);

            // Get user input
            try {
                float age1 = Float.parseFloat(GUI.ageRange1Field.getText());
                float age2 = Float.parseFloat(GUI.ageRange2Field.getText());

                statement.setFloat(1, age1);
                statement.setFloat(2, age2);
            } catch (NumberFormatException e) {
                // Handle the case where user input is not a valid float
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numeric values for life expectancy.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if there's an error in user input
            }

            rset = statement.executeQuery();

            GUI.resultSetArea.append("  Country Name - Region ID - Life Expectancy -");
            GUI.resultSetArea.append("\n");
            GUI.resultSetArea.append("\n");
            System.out.println("Country Name, Region ID, Life Expectancy");
            while (rset.next()) {
                for (int i = 1; i <= 3; i++) {
                    GUI.resultSetArea.append("  " + rset.getString(i) + " -");
                }
                GUI.resultSetArea.append("\n"); // Add a newline after each row
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset != null)
                    rset.close();
                if (statement != null)
                    statement.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    public static void insertRecord(String countryID, String countryName, String regionID, String lifeExpectancy) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver loaded");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@calvin.humber.ca:1521:grok", "username", "password");

            
            if (!isValidCountryID(countryID)) {
                JOptionPane.showMessageDialog(null, "Invalid Country ID. Please enter a valid ID like 'CA', 'AB', 'OL'.");
                return;
            }

            int regionId;
            try {
                regionId = Integer.parseInt(regionID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Region ID. Please enter a valid numeric value.");
                return;
            }

            float lifeExpectancyValue;
            try {
                lifeExpectancyValue = Float.parseFloat(lifeExpectancy);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Life Expectancy. Please enter a valid numeric value.");
                return;
            }

            
            if (isNumeric(countryName)) {
                JOptionPane.showMessageDialog(null, "Invalid Country Name. Please enter a valid non-numeric value.");
                return;
            }

            
            String sql = "INSERT INTO COUNTRIES "
            		+ "(COUNTRY_ID, COUNTRY_NAME, REGION_ID, LIFE_EXPECTANCY) "
            		+ "VALUES (?, ?, ?, ?)";
            statement = conn.prepareStatement(sql);

            
            statement.setString(1, countryID);
            statement.setString(2, countryName);
            statement.setInt(3, regionId);
            statement.setFloat(4, lifeExpectancyValue);

            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
            	 JOptionPane.showMessageDialog(null, "Record inserted successfully.");
            } else {
            	JOptionPane.showMessageDialog(null, "Failed to inser record");
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

    
    private static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    
    private static boolean isValidCountryID(String id) {
        
        return id.length() == 2 && id.matches("[A-Za-z]+");
    }
}