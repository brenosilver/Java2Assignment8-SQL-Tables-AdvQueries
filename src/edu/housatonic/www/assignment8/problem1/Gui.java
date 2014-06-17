//****************************************************************************************
//	Author: Breno Silva		Last Modified: 04/29/14
//
//	CSC*E224				Programming Assignment VIII		Problem 1
//****************************************************************************************

package edu.housatonic.www.assignment8.problem1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Gui extends JFrame implements ActionListener {
	private static DBConnectionPanel db;
	private JComboBox comboDriver;
	private JComboBox comboDbUrl;
	private JTextField usernameField;
	private JPasswordField passField;
	private JLabel labe5;
	private JButton connectButton;
	
	public Gui() {
		
		setLayout(new BorderLayout());
		
		// Create Panels
		JPanel centerPane = new JPanel(new GridLayout(4, 2, -270, 0));
		centerPane.setBorder(new TitledBorder("Enter database information"));
		
		JPanel southPane = new JPanel(new BorderLayout());
		
		// Create components
		JLabel labe1 = new JLabel("JDBC Driver");
		JLabel labe2 = new JLabel("Database URL");
		JLabel labe3 = new JLabel("Username");
		JLabel labe4 = new JLabel("Password");
		labe5 = new JLabel("No Connection");	
		
		comboDriver = new JComboBox();
		comboDriver.addItem("com.mysql.jdbc.Driver");
		comboDriver.addItem("sun.jdbc.odbc.JdbcOdbcDriver");
		
		comboDbUrl = new JComboBox();
		comboDbUrl.addItem("jdbc:mysql://98.130.0.70:3306/housato_Breno_Northwind");
		comboDbUrl.addItem("jdbc:mysql://98.130.0.70:3306/housato_Breno_Sakila");
		comboDbUrl.addItem("jdbc:mysql://98.130.0.70:3306/housato_Breno_World");
		
		usernameField = new JTextField(15);
		passField = new JPasswordField(15);
		connectButton = new JButton("Connect to DB");
		
		// Add to components
		centerPane.add(labe1);
		centerPane.add(comboDriver);
		
		centerPane.add(labe2);
		centerPane.add(comboDbUrl);
		
		centerPane.add(labe3);
		centerPane.add(usernameField);
		
		centerPane.add(labe4);
		centerPane.add(passField);
		
		southPane.add(labe5, BorderLayout.WEST);
		southPane.add(connectButton, BorderLayout.EAST);
		
		add(centerPane, BorderLayout.CENTER);
		add(southPane, BorderLayout.SOUTH);
		
		// Register button to listener
		connectButton.addActionListener(this);
		
		db = new DBConnectionPanel();
	}

	// Main method
	public static void main(String[] args) {
		JFrame frame = new Gui();
		
		frame.setTitle("Connection dialog");
		frame.setLocationRelativeTo(null);
		frame.setSize(450, 230);
		frame.setResizable(false);
		frame.setVisible(true);
		
		// Close connection when frame is closed
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				
				if(db != null && DBConnectionPanel.isConnected()){
					db.close();
					System.out.println("Connection closed");
				}
				
				System.exit(0);
			}
		});
		
	} // End of main

	// Action performed
	@Override
	public void actionPerformed(ActionEvent e) {
		String driver = (String) comboDriver.getSelectedItem();
		String url = (String) comboDbUrl.getSelectedItem();
		
		String userid = usernameField.getText();
		String password = String.valueOf(passField.getPassword());
		
		// Connect to DB
		if(!DBConnectionPanel.isConnected()){
			db.connect(driver, url, userid, password);
			
			if(DBConnectionPanel.isConnected()){
				labe5.setForeground(Color.GREEN.darker());
				labe5.setText("Connected");
				connectButton.setText("Disconnect");
			}
		}
		
		// Disconnect
		else{
			if(db != null){
				DBConnectionPanel.setConnected(false);
				db.close();
			}
			connectButton.setText("Connect to DB");
			labe5.setForeground(Color.BLACK);
			labe5.setText("No Connection");
			System.out.println("Connection closed");
		}
		
		
		System.out.println(driver);
		System.out.println(url);
		
	}
	

}
