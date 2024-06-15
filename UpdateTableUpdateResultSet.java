package ca.myjava.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextArea;

public class UpdateTableUpdateResultSet {

    public static void displayUpdatedRecord(String countryID, JTextArea resultSetArea) {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:hostname:1521:grok", "username", "password");
          
            statement = conn.createStatement();

            String sql = "SELECT * FROM COUNTRIES WHERE COUNTRY_ID = '" + countryID + "'";
            resultSet = statement.executeQuery(sql);

            resultSetArea.append("Updated Record:\n");
            while (resultSet.next()) {
                resultSetArea.append("Country ID: " + resultSet.getString("COUNTRY_ID") + "\n");
                resultSetArea.append("Country Name: " + resultSet.getString("COUNTRY_NAME") + "\n");
                resultSetArea.append("Region ID: " + resultSet.getInt("REGION_ID") + "\n");
                resultSetArea.append("Life Expectancy: " + resultSet.getFloat("LIFE_EXPECTANCY") + "\n");
            }
            resultSetArea.append("\n");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
