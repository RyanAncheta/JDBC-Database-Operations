package ca.myjava.unknown;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextArea;
import javax.swing.JOptionPane;

public class AnySQL {

    public static void executeSQLFromTextArea(String sqlCommand, JTextArea resultArea) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@calvin.humber.ca:1521:grok", "username",
                    "password");

            try (Statement statement = connection.createStatement()) {
                boolean isResultSet;
                try {
                    isResultSet = statement.execute(sqlCommand);
                } catch (SQLException sqlEx) {
                    JOptionPane.showMessageDialog(null, "Error in SQL statement: " + sqlEx.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

                if (isResultSet) {
                    ResultSet resultSet = statement.getResultSet();
                    displayResultSet(resultSet, resultArea);
                } else {
                    int updateCount = statement.getUpdateCount();
                    resultArea.setText("Rows affected: " + updateCount);
                }
            }

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "SQL Execution Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void displayResultSet(ResultSet resultSet, JTextArea resultArea) {
        try {
            StringBuilder resultText = new StringBuilder();
            while (resultSet.next()) {
                resultText.append("COUNTRY NAME: ").append(resultSet.getString("COUNTRY_NAME")).append("\n");
                resultText.append("LIFE EXPECTANCY: ").append(resultSet.getString("LIFE_EXPECTANCY")).append("\n\n");
            }
            resultArea.setText(resultText.toString());

        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null, "Error processing result set: " + e.getMessage(), "Result Set Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
