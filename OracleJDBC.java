package database.connection;
import java.sql.*;

import javax.swing.SwingUtilities;

import presentation.GUI;



public class OracleJDBC {

	public OracleJDBC() throws SQLException, ClassNotFoundException {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("Driver loaded");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:hostname:1521:grok", "username",
				"password");

		System.out.println("Life Expectancy Database is connected :)!");
		
		
		// This lengthy function to Update GUI component on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            GUI.connectionLabel.setText("Countries Life Expectancy Database is connected !!!");
        });
        
        
        // To test the connection in the console
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM COUNTRIES");

		 // Process the result set
		
        while (resultSet.next()) {
            // Assuming "firstName" is a column in the "STAFF" table
            System.out.println("COUNTRY NAME: " + resultSet.getString("COUNTRY_NAME"));
            System.out.println("LIFE EXPECTANCY: " + resultSet.getString("LIFE_EXPECTANCY"));
        }
        
        ResultSetMetaData rsm = resultSet.getMetaData();

		System.out.println(rsm.getColumnCount());

		System.out.println();
    
    }
		
}
