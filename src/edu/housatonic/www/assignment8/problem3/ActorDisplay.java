//****************************************************************************************
//	Author: Breno Silva		Last Modified: 05/04/14
//
//	CSC*E224				Programming Assignment VIII		Problem 3
//****************************************************************************************

/*
 * 								*Sakila MYSQL DB*
 * > "Actors" TAB: Retrieves all actors. Filter by last name. Insert new Actor 
 * > "Link Film to Actor" TAB: Add a new entry in the film_actor table to link an actor to a film
 * > "New Film" TAB: Add a new film to the film table and link an existing actor to that film
 * 
 */

package edu.housatonic.www.assignment8.problem3;

// Fig. 28.32: AddressBookDisplay.java
// A simple address book
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List; 
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

public class ActorDisplay extends JFrame
{
   private Actor currentEntry;
   private ActorQueries actorQueries;
   private List< Actor > results;   
   private int numberOfEntries = 0;
   private int currentEntryIndex;

   private JButton browseButton;
   private JLabel firstNameLabel;
   private JTextField firstNameTextField;
   private JLabel idLabel;
   private JTextField idTextField;
   private JTextField indexTextField;
   private JLabel lastNameLabel;
   private JTextField lastNameTextField;
   private JTextField maxTextField;
   private JButton nextButton;
   private JLabel ofLabel;
   private JButton previousButton;
   private JButton queryButton;
   private JLabel queryLabel;
   private JPanel queryPanel;
   private JPanel navigatePanel;
   private JPanel displayPanel;
   private JTextField queryTextField;
   private JButton insertButton;
   
   private JPanel filmActor;
   private JLabel filmActorID_label;
   private JLabel filmActorFilmID_label;
   private JTextField filmActorID;
   private JTextField filmActorFilmID;
   private JButton filmActorSubmit;
   
   private JPanel newFilmPanel;
   private JLabel newFilmTitle_label;
   private JTextField newFilmTitle;
   private JLabel newFilmLangID_label;
   private JTextField newFilmLangID;
   private JLabel newFilmActorID_label;
   private JTextField newFilmActorID;
   private JButton newFilmSubmit;
   
   // no-argument constructor
   public ActorDisplay()
   {
      super( "Actor Film DB" ); 
      
      // establish database connection and set up PreparedStatements
      actorQueries = new ActorQueries(); 
      
      // create GUI
      JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

      JPanel tab1Pane = new JPanel();
      tab1Pane.setPreferredSize(new Dimension(300,200));
      JPanel tab2Pane = new JPanel();
      JPanel tab3Pane = new JPanel();
      
      navigatePanel = new JPanel();
      previousButton = new JButton();
      indexTextField = new JTextField( 2 );
      ofLabel = new JLabel();
      maxTextField = new JTextField( 2 );
      nextButton = new JButton();
      displayPanel = new JPanel();
      idLabel = new JLabel();
      idTextField = new JTextField( 10 );
      firstNameLabel = new JLabel();
      firstNameTextField = new JTextField( 10 );
      lastNameLabel = new JLabel();
      lastNameTextField = new JTextField( 10 );
      queryPanel = new JPanel();
      queryLabel = new JLabel();
      queryTextField = new JTextField( 10 );
      queryButton = new JButton();
      browseButton = new JButton();
      insertButton = new JButton();
      
      // Tab 2
      filmActor = new JPanel();
	  filmActorID_label = new JLabel("Actor ID: ");
      filmActorFilmID_label = new JLabel("Film ID: ");
      filmActorID = new JTextField(10);
      filmActorFilmID = new JTextField(10);
      filmActorSubmit = new JButton("Add Film to Actor");
      
      // Tab 3
      newFilmPanel = new JPanel();
      newFilmTitle_label = new JLabel("New film title: ");
      newFilmTitle = new JTextField(10);
      newFilmLangID_label = new JLabel("Language ID: ");
      newFilmLangID = new JTextField(10);
      newFilmActorID_label = new JLabel("Actor ID in the film: ");
      newFilmActorID = new JTextField(10);
      newFilmSubmit = new JButton("Add Film");

      setLayout( new FlowLayout( FlowLayout.CENTER, 10, 10 ) );
      setSize( 350, 280 );
      setResizable( false );

      navigatePanel.setLayout( new BoxLayout( navigatePanel, BoxLayout.X_AXIS ) );

      previousButton.setText( "Previous" );
      previousButton.setEnabled( false );
      previousButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent evt )
            {
               previousButtonActionPerformed( evt );
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

      navigatePanel.add( previousButton );
      navigatePanel.add( Box.createHorizontalStrut( 10 ) );

      indexTextField.setHorizontalAlignment(
         JTextField.CENTER );
      indexTextField.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent evt )
            {
               indexTextFieldActionPerformed( evt );
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

      navigatePanel.add( indexTextField );
      navigatePanel.add( Box.createHorizontalStrut( 10 ) );

      ofLabel.setText( "of" );
      navigatePanel.add( ofLabel );
      navigatePanel.add( Box.createHorizontalStrut( 10 ) );

      maxTextField.setHorizontalAlignment(
         JTextField.CENTER );
      maxTextField.setEditable( false );
      navigatePanel.add( maxTextField );
      navigatePanel.add( Box.createHorizontalStrut( 10 ) );

      nextButton.setText( "Next" );
      nextButton.setEnabled( false );
      nextButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent evt )
            {
               nextButtonActionPerformed( evt );
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

      navigatePanel.add( nextButton );
      tab1Pane.add(navigatePanel);

      displayPanel.setLayout( new GridLayout( 3, 2, 4, 4 ) );

      idLabel.setText( "Actor ID:" );
      displayPanel.add( idLabel );

      idTextField.setEditable( false );
      displayPanel.add( idTextField );

      firstNameLabel.setText( "First Name:" );
      displayPanel.add( firstNameLabel );
      displayPanel.add( firstNameTextField );

      lastNameLabel.setText( "Last Name:" );
      displayPanel.add( lastNameLabel );
      displayPanel.add( lastNameTextField );

      tab1Pane.add(displayPanel);

      queryPanel.setLayout( new BoxLayout( queryPanel, BoxLayout.X_AXIS) );

      queryPanel.setBorder( BorderFactory.createTitledBorder("Find an entry by last name" ));
      queryLabel.setText( "Last Name:" );
      queryPanel.add( Box.createHorizontalStrut( 5 ) );
      queryPanel.add( queryLabel );
      queryPanel.add( Box.createHorizontalStrut( 10 ) );
      queryPanel.add( queryTextField );
      queryPanel.add( Box.createHorizontalStrut( 10 ) );

      queryButton.setText( "Find" );
      queryButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent evt )
            {
               queryButtonActionPerformed( evt );
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

      queryPanel.add( queryButton );
      queryPanel.add( Box.createHorizontalStrut( 5 ) );

      tab1Pane.add(queryPanel);

      browseButton.setText( "Browse All Entries" );
      browseButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent evt )
            {
               browseButtonActionPerformed( evt );
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

      tab1Pane.add(browseButton);

      insertButton.setText( "Insert New Entry" );
      insertButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent evt )
            {
               insertButtonActionPerformed( evt );
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

      tab1Pane.add(insertButton);
      tabbedPane.addTab("Actors", tab1Pane);
      
	   
	   ////////////TAB 2////////////////
	   filmActor.setLayout( new GridLayout( 3, 2, 4, 4 ) );
	   filmActor.add(filmActorID_label);
	   filmActor.add(filmActorID);
	   filmActor.add(filmActorFilmID_label);
	   filmActor.add(filmActorFilmID);
	   filmActor.add( Box.createHorizontalStrut( 10 ) );
	   filmActor.add(filmActorSubmit);
	   
	   filmActorSubmit.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				Integer actId = Integer.valueOf(filmActorID.getText());
				Integer filmId = Integer.valueOf(filmActorFilmID.getText());
			
				// executeQuery returns ResultSet containing matching entries
				if (actorQueries.getActorByID(actId) != null || actorQueries.checkFilmByID(filmId) ){
					if(!actorQueries.checkFilmActor(actId, filmId)){
						actorQueries.addFilmToActor(actId, filmId);
						JOptionPane.showMessageDialog(null, "Film added to actor successfully");
					}
					else{
						JOptionPane.showMessageDialog(null, "This actor is already in this film");
					}
				}
			
			}catch(NumberFormatException except){
				JOptionPane.showMessageDialog(null, "Please enter a valid id");
			}
			
		}
	});   
	   /////////////////////////////
	   
	   tab2Pane.add(filmActor);
	   tabbedPane.addTab("Link Film to Actor", tab2Pane);
	     
	   
	   //////////////Tab 3/////////////
	   newFilmPanel.setLayout(new GridLayout( 4, 2, 37, 4 ));
	   newFilmPanel.add(newFilmTitle_label);
	   newFilmPanel.add(newFilmTitle);
	   newFilmPanel.add(newFilmLangID_label);
	   newFilmPanel.add(newFilmLangID);
	   newFilmPanel.add(newFilmActorID_label);
	   newFilmPanel.add(newFilmActorID);
	   newFilmPanel.add(Box.createHorizontalStrut( 10 ) );
	   newFilmPanel.add(newFilmSubmit);
	   
	   newFilmSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					String title = newFilmTitle.getText().toUpperCase();
					Integer langId = Integer.valueOf(newFilmLangID.getText());
					Integer actorID = Integer.valueOf(newFilmActorID.getText());
					
					//if actor exists, continue
					if(actorQueries.getActorByID(actorID) != null){
						
						// if language exists, continue
						if(actorQueries.checkLanguage(langId)){
							actorQueries.insertNewFilm(title, langId, actorID);
							JOptionPane.showMessageDialog(null, "Film added successfully!");
						}
						else{
							JOptionPane.showMessageDialog(null, "Language not found. Please enter a valid id!");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "Actor not found. Please enter a valid actor!");
					}
				}
				catch(NumberFormatException except){
					JOptionPane.showMessageDialog(null, "Please enter a valid ID!");
				}
			}
	   });
	   ////////////////////////////////////
	   
	   tab3Pane.add(newFilmPanel);
	   tabbedPane.addTab("New Film", tab3Pane);
	   
	   
	   add(tabbedPane);

      addWindowListener( 
         new WindowAdapter() 
         {  
            public void windowClosing( WindowEvent evt )
            {
               actorQueries.close(); // close database connection
               System.exit( 0 );
            } // end method windowClosing
         } // end anonymous inner class
      ); // end call to addWindowListener
	
      setVisible( true );
   } // end no-argument constructor

   // handles call when previousButton is clicked
   private void previousButtonActionPerformed( ActionEvent evt )
   {
      --currentEntryIndex;
      
      if ( currentEntryIndex < 0 )
         currentEntryIndex = numberOfEntries - 1;
      
      indexTextField.setText( "" + ( currentEntryIndex + 1 ) );
      indexTextFieldActionPerformed( evt );  
   } // end method previousButtonActionPerformed

   // handles call when nextButton is clicked
   private void nextButtonActionPerformed( ActionEvent evt ) 
   {
      ++currentEntryIndex;
      
      if ( currentEntryIndex >= numberOfEntries )
         currentEntryIndex = 0;
      
      indexTextField.setText( "" + ( currentEntryIndex + 1 ) );
      indexTextFieldActionPerformed( evt );
   } // end method nextButtonActionPerformed

   // handles call when queryButton is clicked
   private void queryButtonActionPerformed( ActionEvent evt )
   {
      results = 
         actorQueries.getActorsByLastName( queryTextField.getText() );
      numberOfEntries = results.size();
      
      if ( numberOfEntries != 0 )
      {
         currentEntryIndex = 0;
         currentEntry = results.get( currentEntryIndex );
         idTextField.setText("" + currentEntry.getActorID() );
         firstNameTextField.setText( currentEntry.getFirstName() );
         lastNameTextField.setText( currentEntry.getLastName() );
         maxTextField.setText( "" + numberOfEntries );
         indexTextField.setText( "" + ( currentEntryIndex + 1 ) );
         nextButton.setEnabled( true );
         previousButton.setEnabled( true );
      } // end if
      else
         browseButtonActionPerformed( evt );
   } // end method queryButtonActionPerformed

   // handles call when a new value is entered in indexTextField
   private void indexTextFieldActionPerformed( ActionEvent evt )
   {
      currentEntryIndex = 
         ( Integer.parseInt( indexTextField.getText() ) - 1 );
      
      if ( numberOfEntries != 0 && currentEntryIndex < numberOfEntries )
      {
         currentEntry = results.get( currentEntryIndex );
         idTextField.setText("" + currentEntry.getActorID() );
         firstNameTextField.setText( currentEntry.getFirstName() );
         lastNameTextField.setText( currentEntry.getLastName() );
         maxTextField.setText( "" + numberOfEntries );
         indexTextField.setText( "" + ( currentEntryIndex + 1 ) );
      } // end if
    } // end method indexTextFieldActionPerformed

   // handles call when browseButton is clicked
   private void browseButtonActionPerformed( ActionEvent evt )
   {
      try
      {
         results = actorQueries.getAllActors();
         numberOfEntries = results.size();
      
         if ( numberOfEntries != 0 )
         {
            currentEntryIndex = 0;
            currentEntry = results.get( currentEntryIndex );
            idTextField.setText("" + currentEntry.getActorID() );
            firstNameTextField.setText( currentEntry.getFirstName() );
            lastNameTextField.setText( currentEntry.getLastName() );
            maxTextField.setText( "" + numberOfEntries );
            indexTextField.setText( "" + ( currentEntryIndex + 1 ) );
            nextButton.setEnabled( true );
            previousButton.setEnabled( true );
         } // end if
      } // end try
      catch ( Exception e )
      {
         e.printStackTrace();
      } // end catch
   } // end method browseButtonActionPerformed

   // handles call when insertButton is clicked
   private void insertButtonActionPerformed( ActionEvent evt ) 
   {
      int result = actorQueries.addActor( firstNameTextField.getText(),
         lastNameTextField.getText() );
      
      if ( result == 1 )
         JOptionPane.showMessageDialog( this, "Actor added!",
            "Person added", JOptionPane.PLAIN_MESSAGE );
      else
         JOptionPane.showMessageDialog( this, "Person not added!",
            "Error", JOptionPane.PLAIN_MESSAGE );
          
      browseButtonActionPerformed( evt );
   } // end method insertButtonActionPerformed
   
   // main method
   public static void main( String args[] )
   {
      new ActorDisplay();
   } // end method main
} // end class AddressBookDisplay


/**************************************************************************
 * (C) Copyright 1992-2012 by Deitel & Associates, Inc. and               *
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

 