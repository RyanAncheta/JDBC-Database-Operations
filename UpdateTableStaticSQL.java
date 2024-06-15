package ca.myjava.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTextArea;

public class UpdateTableStaticSQL {

    public static void sortTableAlphabetically(JTextArea resultSetArea) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:hostname:1521:grok", "username", "password");
            Statement statement = connection.createStatement();

            // SQL query to retrieve all data from COUNTRIES
            String sqlQuery = "SELECT * FROM COUNTRIES";

            // Execute the query
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // Process and display the result set in the specified JTextArea
            List<String> rows = new ArrayList<>();

            while (resultSet.next()) {
                // Assuming you have columns like ID, COUNTRY_NAME, REGION_ID, LIFE_EXPECTANCY
                String countryName = resultSet.getString("COUNTRY_NAME");
                float lifeExpectancy = resultSet.getFloat("LIFE_EXPECTANCY");

                // Format the data and add it to the list
                String rowData = String.format("Country Name: %s, Life Expectancy: %.2f",
                        countryName,lifeExpectancy);
                rows.add(rowData);
            }

            // Sort the list alphabetically
            Collections.sort(rows);

            // Build the result text
            StringBuilder resultText = new StringBuilder();
            for (String row : rows) {
                resultText.append(row).append("\n");
            }

            // Update the text in the resultTextArea
            resultSetArea.setText(resultText.toString());

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
