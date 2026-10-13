package application;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import databasePart1.DatabaseHelper;

/**
 * This Class is for the JUnit Testing of HW4, which includes the Private Messaging System. and the creation, reading, updating and deleting of Private Messages.
 * 
 * <h1>HW4_JUnit Class</h1>
 * @author Bracken Peterson
 * @version 1.0
 * @since 2025-03-30
 */
public class HW4_JUnit {

	private static final DatabaseHelper databaseHelper = new DatabaseHelper();
	
	/**
	 * Runs before all the JUnit tests, connects to the Database
	 */
	@BeforeClass
	public static void connectToDatabase() {
		try {
			databaseHelper.connectToDatabase(); // Connect to the database
			Thread.sleep(200); 					//Make sure we are connected, SLeep Command essentially
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} 
	}
	
	/**
	 * This JUnit test will create 1000 Students and 1000 Reviewers, and will see if we can create a connection and private message between them, we check the databse if we actually have messages between them. 
	 * This is a Creation Test
	 */
	@Test
	public void createPMs() {
		ArrayList<User> students = new ArrayList<User>();
		ArrayList<User> reviewers = new ArrayList<User>();
		for(int i=0;i<1000; i++) {
			User newStudent = new User("stuname"+String.valueOf(i), "stu"+String.valueOf(i), "email", false, false, false, false, false, true); //new User with student role, and 
			User newReviewer = new User("revname"+String.valueOf(i), "rev"+String.valueOf(i), "email", false, false, false, false, true, false); //new User with reviewer role, and
			students.add(newStudent);
			reviewers.add(newReviewer);
		}
		
		for(int i=0; i<1000; i++) {
			int connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
			
			if(connectionID >= 0) {

				System.out.println("Found Conversation with " + students.get(i).getUserName()+String.valueOf(i) + " and " + reviewers.get(i).getUserName()+String.valueOf(i));
				PrivateMessage pm = new PrivateMessage(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i), connectionID, "Another "+String.valueOf(i), students.get(i).getUserName()+String.valueOf(i));
				databaseHelper.newMessage(pm);
			}
			else {
				Boolean puppyDog = databaseHelper.newConversations(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
				connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
				System.out.println("Creating a new conversation with " + students.get(i).getUserName()+String.valueOf(i) + " and " + reviewers.get(i).getUserName()+String.valueOf(i));

				PrivateMessage pm = new PrivateMessage(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i), connectionID, "Another "+String.valueOf(i), students.get(i).getUserName()+String.valueOf(i));
				databaseHelper.newMessage(pm);
			}
			
		}
		for(int i=0; i<1000;i++) {
			int connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
			ArrayList<PrivateMessage> messagesList = databaseHelper.loadAllMessages(connectionID);
			assertTrue("No Private Messages were Found", messagesList.size()>0);
		}
	}
	
	/**
	 * This JUnit test will create 1000 Students and 1000 Reviewers, and will see if we can verify/read a private message between them, we check the databse if the messages between them are correct. 
	 * This is a Reading Test
	 */
	@Test
	public void readPMs() {
		ArrayList<User> students = new ArrayList<User>();
		ArrayList<User> reviewers = new ArrayList<User>();
		for(int i=0;i<1000; i++) {
			User newStudent = new User("stuname"+String.valueOf(i), "stu"+String.valueOf(i), "email", false, false, false, false, false, true); //new User with student role, and 
			User newReviewer = new User("revname"+String.valueOf(i), "rev"+String.valueOf(i), "email", false, false, false, false, true, false); //new User with reviewer role, and
			students.add(newStudent);
			reviewers.add(newReviewer);
		}
		
		for(int i=0;i<1000; i++) {
			int connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
			
			if(connectionID >= 0) {

				System.out.println("Found Conversation with " + students.get(i).getUserName()+String.valueOf(i) + " and " + reviewers.get(i).getUserName()+String.valueOf(i));
				PrivateMessage pm = new PrivateMessage(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i), connectionID, "Message "+String.valueOf(i), students.get(i).getUserName()+String.valueOf(i));
				databaseHelper.newMessage(pm);
			}
			else {
				Boolean puppyDog = databaseHelper.newConversations(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
				connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
				System.out.println("Creating a new conversation with " + students.get(i).getUserName()+String.valueOf(i) + " and " + reviewers.get(i).getUserName()+String.valueOf(i));

				PrivateMessage pm = new PrivateMessage(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i), connectionID, "Message "+String.valueOf(i), students.get(i).getUserName()+String.valueOf(i));
				databaseHelper.newMessage(pm);
			}
			
		}
		for(int i=0;i<1000; i++) {
			int connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
			ArrayList<PrivateMessage> messagesList = databaseHelper.loadAllMessages(connectionID);
			for(PrivateMessage message : messagesList) {
				assertTrue("Messages Not Equivalent", message.getMessage().equals("Message "+String.valueOf(i)) || message.getMessage().equals("Another "+String.valueOf(i)) || message.getMessage().equals(String.valueOf(i)+" Another"));
			}
			
		}
	}
	
	/**
	 * This JUnit test will create 1000 Students and 1000 Reviewers, and will see if we can update a private message between them, we check the databse if the messages between them are correct. 
	 * This is a Updating Test
	 */
	@Test
	public void updatePMs() {
		ArrayList<User> students = new ArrayList<User>();
		ArrayList<User> reviewers = new ArrayList<User>();
		for(int i=0;i<1000; i++) {
			User newStudent = new User("stuname"+String.valueOf(i), "stu"+String.valueOf(i), "email", false, false, false, false, false, true); //new User with student role, and 
			User newReviewer = new User("revname"+String.valueOf(i), "rev"+String.valueOf(i), "email", false, false, false, false, true, false); //new User with reviewer role, and
			students.add(newStudent);
			reviewers.add(newReviewer);
		}
		//Read Current Messages
		for(int i=0;i<1000; i++) {
			int connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
			ArrayList<PrivateMessage> messagesList = databaseHelper.loadAllMessages(connectionID);
			for(PrivateMessage message : messagesList) {
				assertTrue("Messages Not Equivalent", message.getMessage().equals("Message "+String.valueOf(i)) || message.getMessage().equals("Another "+String.valueOf(i)) || message.getMessage().equals(String.valueOf(i)+" Another"));
			}
			
		}
		//Update all Messages
		for(int i=0;i<1000; i++) {
			int connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
			ArrayList<PrivateMessage> messagesList = databaseHelper.loadAllMessages(connectionID);
			for(PrivateMessage message : messagesList) {
				PrivateMessage newMessage = new PrivateMessage(message.getStuUsername(), message.getRevUsername(), message.getConnectionID(), message.getMessage(), message.getTime(), message.getSender());
				newMessage.setMessage(String.valueOf(i)+" Another");
				databaseHelper.updateMessage(connectionID, message, newMessage);
			}
			
		}
		//Read New Updated Messages
		for(int i=0;i<1000; i++) {
			int connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
			ArrayList<PrivateMessage> messagesList = databaseHelper.loadAllMessages(connectionID);
			for(PrivateMessage message : messagesList) {
				Assert.assertTrue("Messages Not Equivalent", message.getMessage().equals(String.valueOf(i)+" Another"));
			}
					
		}
	}
	
	/**
	 * This JUnit test will create 1000 Students and 1000 Reviewers, and will see if we can delete a private message between them, we check the databse if 
	 * the messages are down to zero, since at this point in the test we have a few messages with each users.
	 * This is a Deleting Test
	 */
	@Test
	public void deletePMs() {
		ArrayList<User> students = new ArrayList<User>();
		ArrayList<User> reviewers = new ArrayList<User>();
		for(int i=0;i<1000; i++) {
			User newStudent = new User("stuname"+String.valueOf(i), "stu"+String.valueOf(i), "email", false, false, false, false, false, true); //new User with student role, and 
			User newReviewer = new User("revname"+String.valueOf(i), "rev"+String.valueOf(i), "email", false, false, false, false, true, false); //new User with reviewer role, and
			students.add(newStudent);
			reviewers.add(newReviewer);
		}
		//Read Current Messages
		for(int i=0;i<1000; i++) {
			int connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
			ArrayList<PrivateMessage> messagesList = databaseHelper.loadAllMessages(connectionID);
			for(PrivateMessage message : messagesList) {
				assertTrue("Messages Not Equivalent", message.getMessage().equals("Message "+String.valueOf(i)) || message.getMessage().equals("Another "+String.valueOf(i)) || message.getMessage().equals(String.valueOf(i)+" Another"));
			}
			
		}
		//Delete all Messages
		for(int i=0;i<1000; i++) {
			int connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
			ArrayList<PrivateMessage> messagesList = databaseHelper.loadAllMessages(connectionID);
			for(PrivateMessage message : messagesList) {
				databaseHelper.deleteMessage(connectionID, message);
			}
		}
		//Read New Updated Messages, should be an null return
		for(int i=0;i<1000; i++) {
			int connectionID = databaseHelper.getConnectionID(students.get(i).getUserName()+String.valueOf(i), reviewers.get(i).getUserName()+String.valueOf(i));
			ArrayList<PrivateMessage> messagesList = databaseHelper.loadAllMessages(connectionID);
			assertTrue("Messages we not all deleted", (messagesList.size() == 0));
		}
	}
	

}
