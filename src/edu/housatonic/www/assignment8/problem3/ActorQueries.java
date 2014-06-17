//****************************************************************************************
//	Author: Breno Silva		Last Modified: 05/04/14
//
//	CSC*E224				Programming Assignment VIII		Problem 3
//****************************************************************************************

package edu.housatonic.www.assignment8.problem3;

// Fig. 28.31: PersonQueries.java
// PreparedStatements used by the Address Book application
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class ActorQueries 
{
   private static final String URL = "jdbc:mysql://98.130.0.70:3306/housato_Breno_Sakila";
   private static final String USERNAME = "housato_breno";
   private static final String PASSWORD = "Breno859";

   private Connection connection = null; // manages connection
   private PreparedStatement selectAllActors = null; 
   private PreparedStatement selectActorByLastName = null; 
   private PreparedStatement insertNewActor = null;
   private PreparedStatement insertFilmToActor = null; 
   private PreparedStatement selectActorByID = null;
   private PreparedStatement checkFilmByID = null;
   private PreparedStatement checkFilmActor = null; 
   private PreparedStatement insertNewFilm = null;
   private PreparedStatement checkLanguage = null; 
   private PreparedStatement checkLastFilmID = null; 
    
   // constructor
   public ActorQueries()
   {
      try 
      {
    	  
  		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			 System.err.println("Driver could not be loaded...");	
		}
         connection = 
            DriverManager.getConnection( URL, USERNAME, PASSWORD );

         // create query that selects all entries in the AddressBook
         selectAllActors = 
            connection.prepareStatement( "SELECT * FROM actor" );
         
         // create query that selects entries with a specific last name
         selectActorByLastName = connection.prepareStatement( 
            "SELECT * FROM actor WHERE last_name = ?" );
         
         // create insert that adds a new entry into the database
         insertNewActor = connection.prepareStatement( 
            "INSERT INTO actor " + 
            "( first_name, last_name) " + 
            "VALUES ( ?, ?)" );
         
         // Add Film to Actor
         insertFilmToActor = connection.prepareStatement( 
            "INSERT INTO film_actor " + 
            "( actor_id, film_id) " + 
            "VALUES ( ?, ?)" );
         
         // Select Actor by ID
         selectActorByID = connection.prepareStatement( 
            "SELECT * FROM actor " +
            "WHERE actor_id = ?" );
         
         // Select Film by ID
         checkFilmByID = connection.prepareStatement( 
            "SELECT film_id, title FROM film " +
            "WHERE film_id = ?" );
         
         // Check if actor has a film
         checkFilmActor = connection.prepareStatement( 
            "SELECT actor_id, film_id FROM film_actor " +
            "WHERE actor_id = ? AND film_id = ?" );
         
         // Insert new Film with an Actor
         insertNewFilm = connection.prepareStatement( 
            "INSERT INTO film " + 
            "( title, language_id) " + 
            "VALUES ( ?, ?)" );
         
         // Check if language exists
         checkLanguage = connection.prepareStatement( 
            "SELECT language_id FROM language " +
            "WHERE language_id = ? " );
         
         // Get the last film ID to be added
         checkLastFilmID = connection.prepareStatement( 
            "SELECT film_id FROM film " +
            "WHERE film_id = LAST_INSERT_ID() " );
         
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
         System.exit( 1 );
      } // end catch
   } // end ActorQueries constructor
   
   
   
   //******* Start Methods *******//
   
   // select all of the addresses in the database
   public List< Actor > getAllActors()
   {
      List< Actor > results = null;
      ResultSet resultSet = null;
      
      try 
      {
         // executeQuery returns ResultSet containing matching entries
         resultSet = selectAllActors.executeQuery(); 
         results = new ArrayList< Actor >();
         
         while ( resultSet.next() )
         {
            results.add( new Actor(
               resultSet.getInt( "actor_id" ),
               resultSet.getString( "first_name" ),
               resultSet.getString( "last_name" ) ) );
         } // end while
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();         
      } // end catch
      finally
      {
         try 
         {
            resultSet.close();
         } // end try
         catch ( SQLException sqlException )
         {
            sqlException.printStackTrace();         
            close();
         } // end catch
      } // end finally
      
      return results;
   } // end method getAllPeople
   
   // select person by last name   
   public List< Actor > getActorsByLastName( String name )
   {
      List< Actor > results = null;
      ResultSet resultSet = null;

      try 
      {
         selectActorByLastName.setString( 1, name ); // specify last name

         // executeQuery returns ResultSet containing matching entries
         resultSet = selectActorByLastName.executeQuery(); 

         results = new ArrayList< Actor >();

         while ( resultSet.next() )
         {
            results.add( new Actor( 
               resultSet.getInt( "actor_id" ),
               resultSet.getString( "first_name" ),
               resultSet.getString( "last_name" ) ) );
         } // end while
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
      } // end catch
      finally
      {
         try 
         {
            resultSet.close();
         } // end try
         catch ( SQLException sqlException )
         {
            sqlException.printStackTrace();         
            close();
         } // end catch
      } // end finally
      
      return results;
   } // end method getPeopleByName
   
   // add an entry
   public int addActor(
      String fname, String lname )
   {
      int result = 0;
      
      // set parameters, then execute insertNewPerson
      try 
      {
         insertNewActor.setString( 1, fname );
         insertNewActor.setString( 2, lname );

         // insert the new entry; returns # of rows updated
         result = insertNewActor.executeUpdate(); 
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
         close();
      } // end catch
      
      return result;
   } // end method addPerson
   
   // add an existing film to an actor
   public int addFilmToActor(int actorID, int filmID)
		   {
		      int result = 0;
		      
		      // set parameters, then execute insertFilmToActor
		      try 
		      {
		    	  insertFilmToActor.setInt( 1, actorID );
		    	  insertFilmToActor.setInt( 2, filmID );

		         // insert the new entry; returns # of rows updated
		         result = insertFilmToActor.executeUpdate(); 
		      } // end try
		      catch ( SQLException sqlException )
		      {
		         sqlException.printStackTrace();
		         close();
		      } // end catch
		      
		      return result;
   } // end method addFilmToActor
   
   // Select an actor by id
   public Actor getActorByID(int id){
	   	   
	   try {
		   selectActorByID.setInt( 1, id );
		   ResultSet resultSet = selectActorByID.executeQuery(); 
		   
		   if ( resultSet.next() )
		   {
			Actor actor = new Actor(
			resultSet.getInt( "actor_id" ),
			resultSet.getString( "first_name" ),
			resultSet.getString( "last_name" ) );
			
			return actor;
		   } // end if
	   }
	   catch (SQLException e) {
		   System.err.println("An error occurred while retrieving the actor");
			System.err.println(e.getMessage());
		}
	   return null;
   } // end method getActorByID
   
   // Check if a film is in the DB
   public boolean checkFilmByID(int id){
   	   
	   try {
		   checkFilmByID.setInt( 1, id );
		   ResultSet resultSet = checkFilmByID.executeQuery(); 
		   
		   if ( resultSet.next() )
		   {
			return true;
		   } // end if
	   }
	   catch (SQLException e) {
		   System.err.println("An error occurred while retrieving the film");
			System.err.println(e.getMessage());
		}
	   System.out.println("Film not found");
	   return false;
   } // end method checkFilmByID
   
   // Check if Actor already is in certain film
   public boolean checkFilmActor(int filmId, int actorId){
	   
	   try {
		   checkFilmActor.setInt( 1, filmId );
		   checkFilmActor.setInt( 2, actorId );
		   ResultSet resultSet = checkFilmActor.executeQuery(); 
		   
		   if ( resultSet.next() )
			   return true;

	   }
	   catch (SQLException e) {
		   System.err.println("An error occurred while retrieving film-actor");
		   System.err.println(e.getMessage());
		}

	   return false;
   } // end method checkFilmActor
   
   // Add a new film with an actor
   public int insertNewFilm(String title, int langId, int actorID){
      int result = 0;
      
      // set parameters, then execute insertFilmToActor
      try 
      {
    	  insertNewFilm.setString( 1, title );
    	  insertNewFilm.setInt( 2, langId );
    	 
          // if actor exists
    	  if(getActorByID(actorID) != null){
    		  result = insertNewFilm.executeUpdate(); // execute query
    		  
    		  ResultSet filmIDResult = checkLastFilmID.executeQuery(); 
   		      if ( filmIDResult.next() ){
   		    	int filmID = filmIDResult.getInt("film_id");
   		    	
   				// if film was created
   				if (checkFilmByID(filmID)){
   					// if film isn't already linked to the actor
   					if(!checkFilmActor(actorID, filmID)){
   						addFilmToActor(actorID, filmID); // Link film to actor
   						JOptionPane.showMessageDialog(null, "Film linked to actor successfully");
   					}
   					else{
   						JOptionPane.showMessageDialog(null, "This actor is already in this film");
   					}
   				}
   		      }
    	  }
    	  	
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
         close();
      } // end catch
      
      return result;
   } // end method insertNewFilm
   
   
   // Check if Actor already has certain film
   public boolean checkLanguage(int languageID){
	   
	   try {
		   checkLanguage.setInt( 1, languageID );
		   ResultSet resultSet = checkLanguage.executeQuery(); 
		   
		   if ( resultSet.next() )
		   {
			return true;
		   } // end if
	   }
	   catch (SQLException e) {
		   System.err.println("An error occurred while retrieving the language ID");
		   System.err.println(e.getMessage());
		}

	   return false;
   }
   
   
   // close the database connection
   public void close()
   {
      try 
      {
         connection.close();
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
      } // end catch
   } // end method close
} // end class PersonQueries


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

 