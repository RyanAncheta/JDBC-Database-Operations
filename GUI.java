package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.TitledBorder;

//The package and java file to connect to the oracle database
import database.connection.OracleJDBC;
import ca.myjava.query.QueryTablePreparedStm2;
import ca.myjava.query.QueryTableStaticSQL;
import ca.myjava.unknown.AnySQL;
import ca.myjava.update.UpdateTablePreparedStm;
import ca.myjava.update.UpdateTableStaticSQL;
import ca.myjava.update.UpdateTableUpdateResultSet;

public class GUI extends JFrame {

    // Attributes:
    public static JTextField idField, countryField, regionField, lifeField, ageRange1Field, ageRange2Field;
    public static JTextArea anySQLArea, resultSetArea;
    
    private JButton viewRangeButton, executeButton, viewButton, insertButton, updateButton, clearButton;
    private JButton sortButton;
    // The bottom label with connection status:
    public static JLabel connectionLabel;
    //Layout attributes:
    private Font labelFont = new Font("Arial", Font.PLAIN, 16);
    private Font fieldFont = new Font("Arial", Font.PLAIN, 16);
    private Font creditsFont = new Font("Arial", Font.PLAIN, 10);

    private Color backColor = new Color(255, 255, 255);
    private Color btnColor = new Color(230, 230, 255);
    private Color titleColor = new Color(53, 140, 219);
    
    // Constructor
    public GUI() throws ClassNotFoundException, SQLException {
        setTitle("****** JDBC - COUNTRIES LIFE EXPECTANCY ******");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 660);
        setResizable(false);
        setLocationRelativeTo(null);
        
        UIManager.put("Label.font", labelFont);
        UIManager.put("TextField.font", fieldFont);
        UIManager.put("Button.font", labelFont);
        
     // Text Fields
        idField = new JTextField(10);
        countryField = new JTextField(10);
        regionField = new JTextField(10);
        lifeField = new JTextField(10);
     // The text Areas anySQLArea
        anySQLArea = new JTextArea(15, 23);
        resultSetArea = new JTextArea(24, 30);
        
        ageRange1Field = new JTextField(10);
        ageRange2Field = new JTextField(10);

      // Buttons:
        viewRangeButton = new JButton("View Countries");
        executeButton = new JButton("Execute SQL");
        viewButton = new JButton("View");
        insertButton = new JButton("Insert");
        updateButton = new JButton("Update");
        clearButton = new JButton("Clear");
        sortButton = new JButton("Sort");
        getButtonsLayout();
       
        

      // Panels and labels:
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 8, 12));
        JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        
        JPanel inputPanel = createInputPanel("ID:", idField, "Country:", countryField, "Region ID:", regionField, "Life Expectancy", lifeField);
        JPanel anySQLPanel = createAnySQLPanel("Custom SQL:", anySQLArea, executeButton);
        
        JPanel rangePanel = createRangePanel("Search Countries Life Expectancy by Range", "Age range 1: ", ageRange1Field, "Age range 2: ", ageRange2Field, viewRangeButton, resultSetArea);
        
       

        // Adding a titled border to the main panel
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Edit Countries Table");
        editPanel.setBorder(titledBorder);
        titledBorder.setTitleColor(titleColor);
        titledBorder.setTitleFont(labelFont);
        JPanel buttonPanel = createButtonPanel(viewButton, insertButton, updateButton, clearButton);
        
        
        editPanel.add(inputPanel);
        editPanel.add(buttonPanel);
        editPanel.add(anySQLPanel);
        editPanel.add(executeButton);
        
        mainPanel.add(editPanel);
        mainPanel.add(rangePanel);

        
        JPanel lowerPanel = createLowerPanel();
        JPanel outerPanel = createOuterPanel(mainPanel, lowerPanel);
        
        add(outerPanel);
        
       connectDatabase();
        
        activateButtons();

    }
    
    private JPanel createInputPanel(String label1, JTextField field1, String label2, JTextField field2, String label3, JTextField field3, String label4, JTextField field4) {
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 8, 12));
        inputPanel.add(new JLabel(label1));
        inputPanel.add(field1);
        inputPanel.add(new JLabel(label2));
        inputPanel.add(field2);
        inputPanel.add(new JLabel(label3));
        inputPanel.add(field3);
        inputPanel.add(new JLabel(label4));
        inputPanel.add(field4);

        return inputPanel;
    }
    
    private JPanel createButtonPanel(JButton... buttons) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        for (JButton button : buttons) {
            buttonPanel.add(button);
            
        }

        return buttonPanel;
    }
    
    private JPanel createRangePanel(String label1, String label2, JTextField field1, String label3, JTextField field2, JButton button1, JTextArea area1) {
        JPanel rangePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 12));
        JLabel titleLabel = new JLabel(label1);
        titleLabel.setForeground(titleColor);
        rangePanel.add(titleLabel);
        rangePanel.add(new JLabel(label2));
        rangePanel.add(field1);
        rangePanel.add(new JLabel(label3));
        rangePanel.add(field2);
        rangePanel.add(button1);
        rangePanel.add(area1);
        rangePanel.add(sortButton);

        JScrollPane scrollPane = new JScrollPane(area1);
        rangePanel.add(scrollPane);
        return rangePanel;
    }
    
    private JPanel createAnySQLPanel(String label1, JTextArea area1, JButton button1) {
        JPanel anySQLPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        anySQLPanel.add(new JLabel(label1));
        anySQLPanel.add(area1);
        
        
        return anySQLPanel;
    }
    
    private JPanel createLowerPanel() {
        JPanel lowerPanel = new JPanel(new GridLayout(3, 1));
        connectionLabel = new JLabel("Database Not Connected????");
        JLabel developersLabel = new JLabel("//// Developed By Group 1 - ITC - 5201-0GB - NOV 2023");
        developersLabel.setLayout(new FlowLayout(FlowLayout.CENTER));
        developersLabel.setFont(creditsFont);
        developersLabel.setForeground(titleColor);
        
        lowerPanel.add(connectionLabel);
        lowerPanel.add(developersLabel);
        
        return lowerPanel;
    }
       
    private JPanel createOuterPanel(JPanel mainPanel, JPanel lowerPanel) {
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 2, 35));
        outerPanel.add(mainPanel);
        outerPanel.add(lowerPanel, BorderLayout.SOUTH);

        return outerPanel;
    }
    
    private void getButtonsLayout() {
    	viewRangeButton.setBackground(titleColor);
        executeButton.setBackground(titleColor);
        viewButton.setBackground(titleColor);
        insertButton.setBackground(titleColor);
        updateButton.setBackground(titleColor);
        clearButton.setBackground(titleColor);
        viewRangeButton.setForeground(backColor);
        executeButton.setForeground(backColor);
        viewButton.setForeground(backColor);
        insertButton.setForeground(backColor);
        updateButton.setForeground(backColor);
        clearButton.setForeground(backColor);
        viewRangeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        executeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        insertButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sortButton.setBackground(titleColor);
        sortButton.setForeground(backColor);
        sortButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
      
    private void connectDatabase() {
        try {
            OracleJDBC connection = new OracleJDBC();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
private void activateButtons() {
	
	viewRangeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	resultSetArea.setText("");
        	
        	QueryTablePreparedStm2 range1 = new QueryTablePreparedStm2();
        
        	
        	
        }
    });
	
	viewButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             String id = idField.getText().trim();
			 if (!id.isEmpty()) {
				 QueryTableStaticSQL staticSQL = new QueryTableStaticSQL();
			     staticSQL.viewCountry(id);
			 } else {
			     JOptionPane.showMessageDialog(GUI.this, "Enter ID to view country information.");
			 }
         }
     });
	executeButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        String sqlCommand = anySQLArea.getText().trim();
	        if (!sqlCommand.isEmpty()) {
	            AnySQL.executeSQLFromTextArea(sqlCommand, resultSetArea);
	        } else {
	            JOptionPane.showMessageDialog(GUI.this, "Enter an SQL command to execute.");
	        }
	    }
	});

	insertButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        
	    	  String countryID = idField.getText().trim();
	          String countryName = countryField.getText().trim();
	          String regionID = regionField.getText().trim();
	          String lifeExpectancy = lifeField.getText().trim();

	        
	        QueryTablePreparedStm2.insertRecord(countryID, countryName, regionID, lifeExpectancy);
	    }
	});

	updateButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        try {
	            String countryID = idField.getText().trim();
	            String newLifeExpectancyText = lifeField.getText().trim();

	            if (newLifeExpectancyText.isEmpty()) {
	                JOptionPane.showMessageDialog(GUI.this, "Enter a valid life expectancy value.");
	                return; // Exit the method if lifeField is empty
	            }

	            float newLifeExpectancy = Float.parseFloat(newLifeExpectancyText);

	            // Call the updateLifeExpectancy method in UpdateTablePreparedStm
	            UpdateTablePreparedStm.updateLifeExpectancy(countryID, newLifeExpectancy);

	            // Display the updated record
	            UpdateTableUpdateResultSet.displayUpdatedRecord(countryID, resultSetArea);
	            
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(GUI.this, "Enter a valid life expectancy value.");
	        }
	    }
	});

	sortButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Call the sortTableAlphabetically method in UpdateTableStaticSQL
            UpdateTableStaticSQL.sortTableAlphabetically(resultSetArea);
        }
    });


	

	clearButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearFields();
        }
    });
	
	
}
	//Clear Button does not clear the database, just the fields
	private void clearFields() {
		anySQLArea.setText("");
	    resultSetArea.setText("");
	    idField.setText("");
	    countryField.setText("");
	    regionField.setText("");
	    lifeField.setText("");
	} 
   
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		 GUI dataBaseApp = new GUI();
	        dataBaseApp.setVisible(true);
	}

}
