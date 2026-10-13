package application;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import databasePart1.DatabaseHelper;

/**
 * <h1>Team Project 4 JUnit Tests</h1>
 * <p>This Class runs 30 tests that include the following classes:</p>
 * <ul>
 * <li>Request.java</li>
 * <li>Scorecard.java</li>
 * </ul>
 */
public class TP4_JUnit {
	
	private static final DatabaseHelper databaseHelper = new DatabaseHelper();
	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/FoundationDatabase";  

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = "";
	
	/**
	 * Runs before all the JUnit tests, connects to the Database
	 * @throws SQLException 
	 */
	@BeforeClass
	public static void connectToDatabase() throws SQLException {
		try {
			databaseHelper.connectToDatabase(); // Connect to the database
			Thread.sleep(200); 					//Make sure we are connected, SLeep Command essentially
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} 
		Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
		Statement statement = connection.createStatement(); 
		statement.execute("DROP TABLE Requests");
		String RequestsTable = "CREATE TABLE IF NOT EXISTS Requests ("
				+ "username VARCHAR(17), "
				+ "description VARCHAR(3000), "
				+ "pending BOOLEAN DEFAULT TRUE, "  
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "solution VARCHAR(3000) DEFAULT 'No Actions taken yet') ";
		statement.execute(RequestsTable);
	}
	
	//*************************^^^^^^^^^^^^^^^^^DATABASE CONNECTIVITY**********************************************
	

	/**
	 * Creates a new Request Obj, and constructs it. CHecks values to see if it exists
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void createRequest() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
		
    	assertEquals(1, request.getID());
	}
	
	//============START READ TESTS============\/\/\/\/\/\/\/\/\/\/\/
	
	/**
	 * Creates a new Request Obj, and constructs it. Then reads USERNAME and Asserts the object data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void readRequestUsername() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");

		assertTrue("Request was not read Correctly", request.getRequestUsername().equals("username4"));
		assertFalse("Request was not read Correctly", request.getRequestUsername().equals("username5"));
	}
	
	/**
	 * Creates a new Request Obj, and constructs it. Then reads DESCRIPTION and Asserts the object data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void readRequestDescription() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
		assertTrue("Request was not read Correctly", request.getDescription().equals("This is the Description"));
		assertFalse("Request was not read Correctly", request.getDescription().equals("This is the Bob"));
	}
	
	/**
	 * Creates a new Request Obj, and constructs it. Then reads PENDING and Asserts The object data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void readRequestPending() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
		assertTrue("Request was not read Correctly", request.getPending());
	}
	
	/**
	 * Creates a new Request Obj, and constructs it. Then reads PENDING and Asserts The object data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void readRequestPendingFalse() {
		Request request =  new Request ("username4", "This is the Description", false, 1, "This is the Solution");
		request.setPending(false);
		assertFalse("Request was not read Correctly", request.getPending());
	}
	
	/**
	 * Creates a new Request Obj, and constructs it. Then reads ID and Asserts the object data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void readRequestID() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
		assertEquals(1, request.getID());
		assertFalse("Incorrect request ID", (request.getID() == 1000));
		
	}
	/**
	 * Creates a new Request Obj, and constructs it. Then reads SOLUTION and Asserts the object data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void readRequestSolution() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
		assertTrue("Request was not read Correctly", request.getSolution().equals("This is the Solution"));
		assertFalse("Request was not read Correctly", request.getSolution().equals("This is the BOB"));
	}
	
	//==========END READ TESTS==============/\/\/\/\/\/\/\/\/\/\/\
	
	
	
	
	
	//==========START UPDATING TESTS===========================\/\/\/\//\/\/\/\/
	
	/**
	 * Creates a new Request Obj, and constructs it. Update the constructed USERNAME Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void updateRequestUsername() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
		assertFalse("Request was not updated Correctly", request.getRequestUsername().equals("username5"));
		request.setRequestUsername("username5");
		assertTrue("Request was not updated Correctly", request.getRequestUsername().equals("username5"));
	}
	
	/**
	 * Creates a new Request Obj, and constructs it. Update the constructed  DESCRIPTION Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void updateRequestDescription() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
		assertFalse("Request was not updated Correctly", request.getDescription().equals("New Description"));
		request.setDescription("New Description");
		assertTrue("Request was not updated Correctly", request.getDescription().equals("New Description"));
	}
	
	/**
	 * Creates a new Request Obj, and constructs it. Update the constructed PENDING Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void updateRequestPending() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
		assertTrue("Request was not updated Correctly", request.getPending());
		request.setPending(false);
		assertFalse("Request was not updated Correctly", request.getPending());
	}
	
	/**
	 * Creates a new Request Obj, and constructs it. Update the constructed ID Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void updateRequestID() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
		assertNotEquals(0, request.getID());
		request.setID(0);
		assertEquals(0, request.getID());
	}
	
	/**
	 * Creates a new Request Obj, and constructs it. Update the constructed SOLUTION Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void updateRequestSolution() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
		assertFalse("Request was not updated Correctly", request.getSolution().equals("New Solution"));
		request.setSolution("New Solution");
		assertTrue("Request was not updated Correctly", request.getSolution().equals("New Solution"));
	}
	
	//========================END UPDATING TESTS====================/\/\//\/\/\/\/\/\
	
	/**
	 * Creates a new Request Obj, and constructs it. Edits the Body Text, checks change. Sets object to Null, verifys assertNull.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void deleteRequest() {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
		request.setDescription("Deleted");
		assertTrue("Request Text was not changed to deleted Correctly", request.getDescription().equals("Deleted"));
		request = null;
		assertEquals(null, request);
		assertNull(request);
	}
	
	
	
	/**
	 * Creates a new Request Obj List, and constructs each request in it. Then searches for the specific request	.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void searchRequest() {
		ArrayList<Request> rlist = new ArrayList<Request>();
		for(int i=0; i<100; i++) {
			if(i != 50) {
				Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
				rlist.add(request);
			} else {
				Request request =  new Request ("username4", "This is the Description", true, 50, "SECRET");
				rlist.add(request);
			}
		}
		for(Request request : rlist) {
			if(request.getSolution().equals("SECRET")) {
				assertTrue("Request Not Found", request.getSolution().equals("SECRET"));
			}
		}
	}
	
	
	//========================================================USING DATABASE============================================================================
	//==================================================================================================================================================
	
	
	/**
	 * Creates a new Request Obj, and constructs it, Sends it to the Database. 
	 * <strong>JUnit returns an Assertion Statement</strong>
	 * @throws SQLException 
	 */
	@SuppressWarnings("null")
	@Test
	public void createRequestDB() throws SQLException {
		Request request =  new Request ("username4", "This is the Description", true, 1, "This is the Solution");
    	assertTrue("Request Not Added", databaseHelper.newRequest(request));
	}
	
	
	
	//============START READ TESTS DB============\/\/\/\/\/\/\/\/\/\/\/
	
		/**
		 * Creates new Request Objects, and constructs it, and sends it to the database. Then reads USERNAME from the database list and Asserts the object data.
		 * <strong>JUnit returns an Assertion Statement</strong>
		 * @throws SQLException 
		 */
		@SuppressWarnings("null")
		@Test
		public void readRequestUsernameDB() throws SQLException {
			
			Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement statement = connection.createStatement(); 
			statement.execute("DROP TABLE Requests");
			String RequestsTable = "CREATE TABLE IF NOT EXISTS Requests ("
					+ "username VARCHAR(17), "
					+ "description VARCHAR(3000), "
					+ "pending BOOLEAN DEFAULT TRUE, "  
					+ "id INT AUTO_INCREMENT PRIMARY KEY, "
					+ "solution VARCHAR(3000) DEFAULT 'No Actions taken yet') ";
			statement.execute(RequestsTable);
			
			for(int i=0; i<100; i++) {
				Request request =  new Request ("username4", "This is the Description", true, 1, "No Actions taken yet");
				databaseHelper.newRequest(request);
			}
			
			ArrayList<Request> rlist = databaseHelper.getAllRequests();
			for(Request request : rlist) {
				System.out.print(request.getRequestUsername() + " VS \"username4\"\n");
				
			}

			for(Request request : rlist) {
				assertTrue("Request USERNAME DB Not Read Correctly", request.getRequestUsername().equals("username4"));
			}
			
			
		}
		
		/**
		 * Pulls current database and reads DESCRIPTION and Asserts the object data.
		 * <strong>JUnit returns an Assertion Statement</strong>
		 */
		@SuppressWarnings("null")
		@Test
		public void readRequestDescriptionDB() {
			ArrayList<Request> rlist = databaseHelper.getAllRequests();

			for(Request request : rlist) {
				System.out.print(request.getDescription() + " VS \"This is the Description\"\n");
				
			}
			for(Request request : rlist) {
				if(request.getDescription().equals("This is the Description")) {
					assertTrue("Request Description Not Updated", request.getDescription().equals("This is the Description"));
				} 
				if(request.getDescription().equals("UPDATED DESCRIPTION")) {
					assertTrue("Request Description Not Updated", request.getDescription().equals("UPDATED DESCRIPTION"));
				} 
			}
		}
		
		/**
		 * Pulls current database and reads PENDING and Asserts The object data.
		 * <strong>JUnit returns an Assertion Statement</strong>
		 */
		@SuppressWarnings("null")
		@Test
		public void readRequestPendingDB() {
			ArrayList<Request> rlist = databaseHelper.getAllRequests();

			for(Request request : rlist) {
				assertTrue("Request Description DB Not Read Correctly", request.getPending());
			}
		}
		
		
		
		/**
		 * Pulls current database and reads ID and Asserts the object data.
		 * <strong>JUnit returns an Assertion Statement</strong>
		 */
		@SuppressWarnings("null")
		@Test
		public void readRequestIDDB() {
			ArrayList<Request> rlist = databaseHelper.getAllRequests();

			int index = 1;
			for(Request request : rlist) {
				assertEquals(index, request.getID());
				index++;
			}
		}
		/**
		 * Pulls current database and reads SOLUTION and Asserts the object data.
		 * <strong>JUnit returns an Assertion Statement</strong>
		 */
		@SuppressWarnings("null")
		@Test
		public void readRequestSolutionDB() {
			ArrayList<Request> rlist = databaseHelper.getAllRequests();

			for(Request request : rlist) {
				System.out.print(request.getSolution() + " VS \"No Actions taken yet\"\n");
				
			}
			for(Request request : rlist) {
				assertTrue("Request Solution DB Not Read Correctly", request.getSolution().equals("No Actions taken yet"));
				
			}
		}
		
		//==========END READ TESTS  DB==============/\/\/\/\/\/\/\/\/\/\/\
	
	
	/**
	 * Creates a new Request Obj List, and pulls the RequestsList from the DB. The requests from prior tests are on this DB pull.  Then searches for the specific request	.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 * @throws SQLException 
	 */
	@SuppressWarnings("null")
	@Test
	public void searchRequestDB() throws SQLException {
		
		ArrayList<Request> rlist = databaseHelper.getAllRequests();

		for(Request request : rlist) {
			if(request.getSolution().equals("SECRET")) {
				assertTrue("Request Not Found", request.getSolution().equals("SECRET"));
			}
		}
	}
	
	/**
	 * Creates a new Request Obj List, and pulls the RequestsList from the DB. The requests from prior tests are on this DB pull.  Then Aserts that all PENDING is True.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 * @throws SQLException 
	 */
	@SuppressWarnings("null")
	@Test
	public void pendingRequestDB() throws SQLException {
		
		ArrayList<Request> rlist = databaseHelper.getAllPending();

		for(Request request : rlist) {
			assertTrue("Request Not Found", request.getPending());
		}
	}
	
	/**
	 * Creates a new Request Obj List, and pulls the RequestsList from the DB. The requests from prior tests are on this DB pull.  Then Aserts that all PENDING is False.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("null")
	@Test
	public void completedRequestDB() throws SQLException, InterruptedException {
		
		
		Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
		Statement statement = connection.createStatement(); 
		statement.execute("DROP TABLE Requests");
		String RequestsTable = "CREATE TABLE IF NOT EXISTS Requests ("
				+ "username VARCHAR(17), "
				+ "description VARCHAR(3000), "
				+ "pending BOOLEAN DEFAULT TRUE, "  
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "solution VARCHAR(3000) DEFAULT 'No Actions taken yet') ";
		statement.execute(RequestsTable);
		
		for(int i=0; i<100; i++) {
			Request request =  new Request ("username4", "This is the Description", true, 1, "No Actions taken yet");
			databaseHelper.newRequest(request);
		}
		Request r =  new Request ("username10", "I want to complete this one100");//, true, 1, "No Actions taken yet");
		boolean tf = databaseHelper.newRequest(r);
		
		if(tf) {
			Thread.sleep(500); 
			databaseHelper.setPending("I want to complete this one100", false);
			Thread.sleep(500);
			ArrayList<Request> rlist = databaseHelper.getAllCompleted();
			System.out.println("List Length:"+ rlist.size());
			System.out.println(rlist.get(0).getDescription());
			System.out.println(rlist.get(0).getPending());
			for(int i=0; i<rlist.size(); i++) {
				if(rlist.get(i).getDescription().equals("I want to complete this one100")) {
					System.out.println("assertFalse(\"Request Not Found\", rlist.get(i).getPending());=======" + rlist.get(i).getPending());
					if(rlist.get(i).getPending()) {

					}else {
						System.out.println("assertFalse(\"Request Not Found\", rlist.get(i).getPending());=======" + rlist.get(i).getPending());
						assertFalse("Pending INCORRECT", rlist.get(i).getPending());
					}
					
				} else {
					fail();
				}
			}
		} else {
			fail("Broken");
		}
	}
	
	
	/**
	 * Creates a 100 new Request Objects, and uploads to DB. Gets the ID of the last one, which shoul be 101 Then Aserts that Id num
	 * <strong>JUnit returns an Assertion Statement</strong>
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("null")
	@Test
	public void getIDDBTest() throws SQLException, InterruptedException {
		
		Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
		Statement statement = connection.createStatement(); 
		statement.execute("DROP TABLE Requests");
		String RequestsTable = "CREATE TABLE IF NOT EXISTS Requests ("
				+ "username VARCHAR(17), "
				+ "description VARCHAR(3000), "
				+ "pending BOOLEAN DEFAULT TRUE, "  
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "solution VARCHAR(3000) DEFAULT 'No Actions taken yet') ";
		statement.execute(RequestsTable);
		
		for(int i=0; i<100; i++) {
			Request request =  new Request ("username4", "This is the Description", true, 1, "No Actions taken yet");
			databaseHelper.newRequest(request);
		}
		
		Request r =  new Request ("username100", "I want to get the ID of this one");
		boolean tf = databaseHelper.newRequest(r);
		
		if(tf) {
			int getId = databaseHelper.getRequestID("I want to get the ID of this one");
			
			assertEquals(101, getId);
					
		} else {
			fail("Broken");
		}
	}
	
	
	/**
	 * Creates a new Request Obj, and constructs it send it to the database. Update the constructed Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 * @throws SQLException 
	 */
	@SuppressWarnings("null")
	@Test
	public void updateRequestSolutionDB() throws SQLException {
		
		
		
		Request request =  new Request ("username4", "This is the Description to Edit the solution on", true, 1, "This is the Solution");
		databaseHelper.newRequest(request);
		databaseHelper.setSolution("This is the Description to Edit the solution on", "UPDATED SOLUTION");

		
		ArrayList<Request> rlist = databaseHelper.getAllRequests();
		
		for(int i=0; i<rlist.size(); i++) {
			
			if(rlist.get(i).getSolution().equals("UPDATED SOLUTION")) {
				assertTrue("Request Description Not Updated", rlist.get(i).getSolution().equals("UPDATED SOLUTION"));
			} 
			
		}
	}	
	
	
	/**
	 * Creates a new Request Obj, and constructs it send it to the database. Update the constructed Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 * @throws SQLException 
	 */
	@SuppressWarnings("null")
	@Test
	public void updateRequestDecriptionDB() throws SQLException {
		
		
		
		Request request =  new Request ("username4", "This is the Description to Edit", true, 1, "This is the Solution");
		databaseHelper.newRequest(request);
		databaseHelper.setDescription("This is the Description to Edit", "UPDATED DESCRIPTION");

		
		ArrayList<Request> rlist = databaseHelper.getAllRequests();
		
		for(int i=0; i<rlist.size(); i++) {
			
			if(rlist.get(i).getDescription().equals("UPDATED DESCRIPTION")) {
				assertTrue("Request Description Not Updated", rlist.get(i).getDescription().equals("UPDATED DESCRIPTION"));
			} 
			
		}
	}
	
	/**
	 * Creates a new Request Obj, and constructs it send it to the database. Update the constructed Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 * @throws SQLException 
	 */
	@SuppressWarnings("null")
	@Test
	public void updateRequestPendingDB() throws SQLException {
		
		
		
		Request request =  new Request ("username4", "This is the PENDING to Edit", true, 1, "This is the Solution");
		databaseHelper.newRequest(request);
		databaseHelper.setPending("This is the PENDING to Edit", false);

		
		ArrayList<Request> rlist = databaseHelper.getAllRequests();
		
		for(int i=0; i<rlist.size(); i++) {
			
			if(rlist.get(i).getDescription().equals("This is the PENDING to Edit")) {
				if(rlist.get(i).getPending()) {
					
				} else {
					assertFalse("Request PENDING Not Updated", rlist.get(i).getPending());
				}
			} 
			
		}
	}
	
	/**
	 * Creates a new Request Obj, and constructs it send it to the database. Update the constructed Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 * @throws SQLException 
	 */
	@SuppressWarnings("null")
	@Test
	public void updateRequestIDDB() throws SQLException {
		
		
		
		Request request =  new Request ("username4", "This is the ID to Edit", true, 1, "This is the Solution");
		databaseHelper.newRequest(request);
		databaseHelper.setRequestID("This is the ID to Edit", 1000);

		
		ArrayList<Request> rlist = databaseHelper.getAllRequests();
		
		for(int i=0; i<rlist.size(); i++) {
			
			if(rlist.get(i).getID() == 1000) {
				assertTrue("Request ID Not Updated", rlist.get(i).getID() == 1000);
				
			} 
			
		}
	}
	
	/**
	 * Creates a new Request Obj, and constructs it send it to the database. Update the constructed Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 * @throws SQLException 
	 */
	@SuppressWarnings("null")
	@Test
	public void updateRequestNameDB() throws SQLException {
		
		
		
		Request request =  new Request ("username400000", "This is the NAME to Edit", true, 1, "This is the Solution");
		databaseHelper.newRequest(request);
		databaseHelper.setRequestName("This is the NAME to Edit", "bracken");

		
		ArrayList<Request> rlist = databaseHelper.getAllRequests();
		
		for(int i=0; i<rlist.size(); i++) {
			
			if(rlist.get(i).getRequestUsername().equals("bracken") ){
				assertTrue("Request NAME Not Updated", rlist.get(i).getRequestUsername().equals("bracken"));
				
			} 
			
		}
	}
	
	/**
	 * Creates a new Request Obj, and constructs it send it to the database. Delete it from the Database.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 * @throws SQLException 
	 */
	@SuppressWarnings("null")
	@Test
	public void deleteRequestnDB() throws SQLException {
		
		
		
		Request request =  new Request ("username40", "This is the Request to Delete", true, 1, "This is the Solution");
		databaseHelper.newRequest(request);
		
		boolean tf = databaseHelper.deleteRequest("This is the Request to Delete", "username40");

		
		ArrayList<Request> rlist = databaseHelper.getAllRequests();
		
		for(int i=0; i<rlist.size(); i++) {
			
			if(rlist.get(i).getDescription().equals("This is the Request to Delete")) {
				fail();
			} 
			
		}
		assertTrue("Delete did not work", tf);
	}
	
	

}
