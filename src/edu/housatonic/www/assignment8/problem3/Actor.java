//****************************************************************************************
//	Author: Breno Silva		Last Modified: 05/04/14
//
//	CSC*E224				Programming Assignment VIII		Problem 3
//****************************************************************************************

package edu.housatonic.www.assignment8.problem3;

// Fig. 28.30: Person.java
// Person class that represents an entry in an address book.
public class Actor
{
   private int ActorID;
   private String firstName;
   private String lastName;

   // no-argument constructor
   public Actor()
   {
   } // end no-argument Person constructor

   // constructor
   public Actor(int id, String first, String last 
      )
   {
	  setActorID( id );
      setFirstName( first );
      setLastName( last );
      //setEmail( emailAddress );
    //  setPhoneNumber( phone );
   } // end five-argument Person constructor 

   // sets the addressID
   public void setActorID( int id )
   {
      ActorID = id;
   } // end method setAddressID

   // returns the addressID 
   public int getActorID()
   {
      return ActorID;
   } // end method getAddressID
   
   // sets the firstName
   public void setFirstName( String first )
   {
      firstName = first;
   } // end method setFirstName

   // returns the first name 
   public String getFirstName()
   {
      return firstName;
   } // end method getFirstName
   
   // sets the lastName
   public void setLastName( String last )
   {
      lastName = last;
   } // end method setLastName

   // returns the last name 
   public String getLastName()
   {
      return lastName;
   } // end method getLastName
   

} // end class Person


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

 