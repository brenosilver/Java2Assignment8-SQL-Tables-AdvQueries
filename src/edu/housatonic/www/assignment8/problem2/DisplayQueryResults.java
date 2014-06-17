//****************************************************************************************
//	Author: Breno Silva		Last Modified: 05/04/14
//
//	CSC*E224				Programming Assignment VIII		Problem 2
//****************************************************************************************

package edu.housatonic.www.assignment8.problem2;

// Fig. 28.28: DisplayQueryResults.java
// Display the contents of the Authors table in the books database.
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;

public class DisplayQueryResults extends JFrame 
{
   // database URL, username and password
   static final String DATABASE_URL = "jdbc:mysql://98.130.0.70:3306/housato_Breno_Sakila";
   static final String USERNAME = "housato_breno";
   static final String PASSWORD = "Breno859";
   private String value = "";
   
   JComboBox selectQuery;
   
   // default query retrieves all data from authors table
   static final String DEFAULT_QUERY = "SELECT * FROM actor";
   
   static String selectAllFilmsFromActor = "SELECT fi.film_id, fi.title, fi.release_year, "
					+ "ac.actor_id, ac.first_name, ac.Last_name "
					+ "FROM film AS fi, actor AS ac, film_actor AS fac "
					+ "WHERE ac.actor_id = fac.actor_id AND fi.film_id = fac.film_id AND ac.first_name = ?";
   
   static String selectAllActorsFromFilm = "SELECT fi.title, fi.release_year, fi.film_id, "
			+ " ac.actor_id, ac.first_name, ac.last_name "
			+ "FROM actor AS ac, film_actor AS fac, film AS fi "
			+ "WHERE ac.actor_id = fac.actor_id AND fac.film_id = fi.film_id AND fi.title = ? "
			+ "ORDER BY ac.last_name, ac.first_name";
   
   private ResultSetTableModel tableModel;
   private JTextArea queryArea;
   
   // create ResultSetTableModel and GUI
   public DisplayQueryResults() 
   {   
      super( "Displaying Query Results" );
        
      // create ResultSetTableModel and display database table
      try 
      {
         // create TableModel for results of query SELECT * FROM authors
         tableModel = new ResultSetTableModel( DATABASE_URL,
            USERNAME, PASSWORD, DEFAULT_QUERY );

         // set up JTextArea in which user types queries
         queryArea = new JTextArea( DEFAULT_QUERY, 3, 100 );
         queryArea.setWrapStyleWord( true );
         queryArea.setLineWrap( true );
         
         JScrollPane scrollPane = new JScrollPane( queryArea,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
         
         // set up JButton for submitting queries
         JButton submitButton = new JButton( "Submit Query" );
 
         // Set JComboBox
         selectQuery = new JComboBox();
         selectQuery.addItem("Select * From actors");
         selectQuery.addItem("Select all Films from actor x");
         selectQuery.addItem("Select all actors from film x");

         // create Box to manage placement of queryArea and 
         // submitButton in GUI
         Box boxNorth = Box.createHorizontalBox();
         JPanel btnPane = new JPanel(new GridLayout(1,1));
         btnPane.add(submitButton);
         
         boxNorth.add( scrollPane );
         boxNorth.add(selectQuery);
         boxNorth.add(btnPane);

         // create JTable based on the tableModel
         JTable resultTable = new JTable( tableModel );
         
         JLabel filterLabel = new JLabel( "Filter:" );
         final JTextField filterText = new JTextField();
         JButton filterButton = new JButton( "Apply Filter" );
         Box boxSouth = Box.createHorizontalBox();
         
         boxSouth.add( filterLabel );
         boxSouth.add( filterText );
         boxSouth.add( filterButton );
         
         // place GUI components on content pane
         add( boxNorth, BorderLayout.NORTH );
         add( new JScrollPane( resultTable ), BorderLayout.CENTER );
         add( boxSouth, BorderLayout.SOUTH );
         
         // Create event listener for JCombobox
         selectQuery.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = String.valueOf(selectQuery.getSelectedItem());
				
				if(selected.equals("Select * From actors")){
					queryArea.setText(DEFAULT_QUERY);
					value = "";
				}
				else if(selected.equals("Select all Films from actor x")){
					queryArea.setText(selectAllFilmsFromActor);
					try{
						value = JOptionPane.showInputDialog(" Please type the Actor's first name: ").toUpperCase();
					}catch(NullPointerException nullPointerException){
						value = "";
					}
				}
				else if(selected.equals("Select all actors from film x")){
					queryArea.setText(selectAllActorsFromFilm);
					try{
						value = JOptionPane.showInputDialog("Please type the Film name: ").toUpperCase();
					}catch(NullPointerException nullPointerException){
						value = "";
					}
				}
				
			}
		});

         // create event listener for submitButton       
         submitButton.addActionListener(
         
            new ActionListener()
            {
               // pass query to table model
               public void actionPerformed( ActionEvent event )
               {
                  // perform a new query
                  try 
                  {
                     tableModel.setQuery( queryArea.getText(), value );
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query 
                     // by executing default query
                     try 
                     {
                        tableModel.setQuery( DEFAULT_QUERY, value );
                        queryArea.setText( DEFAULT_QUERY );
                     } // end try
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE );
         
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();
         
                        System.exit( 1 ); // terminate application
                     } // end inner catch                   
                  } // end outer catch
               } // end actionPerformed
            }  // end ActionListener inner class          
         ); // end call to addActionListener
         
         final TableRowSorter< TableModel > sorter = 
            new TableRowSorter< TableModel >( tableModel );
         resultTable.setRowSorter( sorter );
         setSize( 700, 300 ); // set window size
         setVisible( true ); // display window  
         
         // create listener for filterButton
         filterButton.addActionListener(            
            new ActionListener() 
            {
               // pass filter text to listener
               public void actionPerformed( ActionEvent e ) 
               {
                  String text = filterText.getText().toUpperCase();

                  if ( text.length() == 0 )
                     sorter.setRowFilter( null );
                  else
                  {
                     try
                     {
                        sorter.setRowFilter( 
                           RowFilter.regexFilter( text ) );
                     } // end try
                     catch ( PatternSyntaxException pse ) 
                     {
                        JOptionPane.showMessageDialog( null,
                           "Bad regex pattern", "Bad regex pattern",
                           JOptionPane.ERROR_MESSAGE );
                     } // end catch
                  } // end else
               } // end method actionPerfomed
            } // end annonymous inner class
         ); // end call to addActionLister
      } // end try
      catch ( SQLException sqlException ) 
      {
         JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
            "Database error", JOptionPane.ERROR_MESSAGE );
               
         // ensure database connection is closed
         tableModel.disconnectFromDatabase();
         
         System.exit( 1 ); // terminate application
      } // end catch
      
      // dispose of window when user quits application (this overrides
      // the default of HIDE_ON_CLOSE)
      setDefaultCloseOperation( DISPOSE_ON_CLOSE );
      
      // ensure database connection is closed when user quits application
      addWindowListener(
      
         new WindowAdapter() 
         {
            // disconnect from database and exit when window has closed
            public void windowClosed( WindowEvent event )
            {
               tableModel.disconnectFromDatabase();
               System.exit( 0 );
            } // end method windowClosed
         } // end WindowAdapter inner class
      ); // end call to addWindowListener
   } // end DisplayQueryResults constructor
   
   // execute application
   public static void main( String args[] ) 
   {
      new DisplayQueryResults();     
   } // end main
} // end class DisplayQueryResults



/**************************************************************************
 * (C) Copyright 1992-2012  by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
