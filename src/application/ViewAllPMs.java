package application;

import java.sql.SQLException;
import java.text.SimpleDateFormat; 
import java.util.Date;
import java.util.LinkedList;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import java.util.ArrayList;
import javafx.scene.layout.Region;


/**
 * The ViewAllPMs class allows us to see a full conversation between two users 
 *
*
 * <h1>Private Message Page Class</h1>
 * @author CSE360 Monday 11
 * @version 1.0
 * @since 2025-03-30
 */


public class ViewAllPMs {
	
	private final DatabaseHelper databaseHelper;

    /** Constructor for the PM page 
     * @param databaseHelper
     */
    public ViewAllPMs(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    
    /** This will show a page, with a button for all non student users, and allow a staff member to click and message them
     * @param primaryStage
     * @param user
     */
    public void showPMBoard(Stage primaryStage, User user, String currentRole) {
    	VBox layout = new VBox(10);
    	LinkedList<User> allUsers = databaseHelper.getAllUsers();
    	
    	//Button to return to staff homepage
    	Button back = new Button("Back");
    	back.setOnAction(a -> {
    		WelcomeLoginPage welcomeLoginPage = new WelcomeLoginPage(databaseHelper); 
			welcomeLoginPage.show(primaryStage, user);
	    });
    	layout.getChildren().add(back);
    	if(currentRole.equals("Student")) {	layout.getChildren().add(new Label ("Click to initiate a conversation with a Staff Member"));}
    	else if(currentRole.equals("Reviewer")) {	layout.getChildren().add(new Label ("Click to initiate a conversation"));}

    	//Create a button for each conversation happening in the database
    	for (User currentUser : allUsers) {
    		if(currentRole.equals("Student")) {																//If the user acessing this page is a student
    			if(currentUser.getIsStaff() && (!currentUser.getUserName().equals(user.getUserName()))) {
    				
        			Button conversation = new Button(currentUser.getUserName());
            		conversation.setUserData(currentUser);
            		conversation.setOnAction(a->{
            			@SuppressWarnings("unchecked")
        				User newUser = (User) conversation.getUserData(); //remake the Answer Object on Click
            			
            			int check = databaseHelper.getConnectionID(newUser.getUserName(), user.getUserName());

    					if(check >= 0) {

    						System.out.println("Found Conversation with" + newUser.getUserName() + " and " + user.getUserName());
    						
    						//ConversationListPage conversationPage = new ConversationListPage(databaseHelper);
    						//conversationPage.show(user);
    						PrivateMessagePage messagePage = new PrivateMessagePage(databaseHelper);
    						messagePage.show( user, newUser.getUserName());
    					}
    					else {
    						Boolean LILpuppy = databaseHelper.newConversations(newUser.getUserName(), user.getUserName());

    						System.out.println("Creating a new conversation with" + newUser.getUserName() + " and " + user.getUserName());

    						if(LILpuppy) {
    							PrivateMessagePage messagePage = new PrivateMessagePage(databaseHelper); //This dawg
    							
    						}
    					}
            			
            		});
            		layout.getChildren().add(conversation);
        		}
    		} else if((!currentUser.getUserName().equals(user.getUserName()))) {							//IF the user accessing this page is not a student
    			Button conversation = new Button(currentUser.getUserName());
        		conversation.setUserData(currentUser);
        		conversation.setOnAction(a->{
        			@SuppressWarnings("unchecked")
    				User newUser = (User) conversation.getUserData(); //remake the Answer Object on Click
        			
        			int check = databaseHelper.getConnectionID(newUser.getUserName(), user.getUserName());

					if(check >= 0) {

						System.out.println("Found Conversation with" + newUser.getUserName() + " and " + user.getUserName());

						//ConversationListPage conversationPage = new ConversationListPage(databaseHelper);
						//conversationPage.show(user);
						PrivateMessagePage messagePage = new PrivateMessagePage(databaseHelper);
						messagePage.show( user, newUser.getUserName());
					}
					else {
						Boolean LILpuppy = databaseHelper.newConversations(newUser.getUserName(), user.getUserName());

						System.out.println("Creating a new conversation with" + newUser.getUserName() + " and " + user.getUserName());

						if(LILpuppy) {
							PrivateMessagePage messagePage = new PrivateMessagePage(databaseHelper); //This dawg
							
						}
					}
        			
        		});
        		layout.getChildren().add(conversation);
    		}
    		
    	}
    	ScrollPane allButtons = new ScrollPane(layout);
    	
    	// Configure and set window visible
    			Scene scene = new Scene(allButtons, 800, 400);
    	        scene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());

    	        primaryStage.setScene(scene);
    	        primaryStage.setTitle("Initiate Private Messages");
    	        primaryStage.show();
    		
    	
    }
    
    /**
     * This little guy generates a button for all of the conversatoins that are happening in the system. and when you click on them it will display the current conversation.
     * @param primaryStage
     * @param user
     */
    public void show(Stage primaryStage, User user) {
    	VBox layout = new VBox(10);
    	ArrayList<ArrayList<PrivateMessage>> allPMs = databaseHelper.getAllConnections();
    	
    	//Button to return to staff homepage
    	Button staffButton = new Button("Back");
    	staffButton.setOnAction(a -> {
	    	String role = user.getRole();
	    	System.out.println(role);
	    	new StaffHomePage(databaseHelper).show(primaryStage, user);
	    });
    	layout.getChildren().add(staffButton);
    	
    	//Create a button for each conversation happening in the database
    	for (ArrayList<PrivateMessage> messageList : allPMs) {
    		if(!messageList.isEmpty()) {
    			Button conversation = new Button(messageList.getFirst().getStuUsername() + " " + messageList.getFirst().getRevUsername());
        		conversation.setUserData(messageList);
        		conversation.setOnAction(a->{
        			@SuppressWarnings("unchecked")
    				ArrayList<PrivateMessage> list = (ArrayList<PrivateMessage>) conversation.getUserData(); //remake the Answer Object on Click
        			showPMs(list.getFirst().getStuUsername(), list.getFirst().getRevUsername());
        		});
        		layout.getChildren().add(conversation);
    		}
    	}
    	
    	// Configure and set window visible
    			Scene scene = new Scene(layout, 800, 400);
    	        scene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());

    	        primaryStage.setScene(scene);
    	        primaryStage.setTitle("Review All Private Messages");
    	        primaryStage.show();
    		
    	
    }
    

	/** Displays the private message conversation given a user and a reviewer. 
	 * Calls multiple functions including getConnectionID and newMessage from the database in the function 
	 * @param userName
	 * @param reviewerName
	 */
	public void showPMs (String userName, String reviewerName) {
		Stage messageStage = new Stage();
		
		// Vbox to store components for the entire page
		VBox primaryVBox = new VBox(10);
		
		
		// Label for the page ***********************************************************************************
		Label headingLabel = new Label();
		headingLabel.setStyle("-fx-font-size: 24px;"); // USE THIS TO EDIT TEXT SIZE OF THE HEADING
		headingLabel.setText(reviewerName); 
		
		
		// Create ScrollPane ***********************************************************************************
		VBox boxForScrollPane = new VBox();
		int connectionID = databaseHelper.getConnectionID(userName, reviewerName);
		
		ArrayList<PrivateMessage> messagesList = databaseHelper.loadAllMessages(connectionID);
		
		boxForScrollPane = formatConversations(boxForScrollPane, messagesList);
		
		ScrollPane scrollPane = new ScrollPane();
		
		scrollPane.setContent(boxForScrollPane);
		
		
		
		// Sending Message Functionality ************************************************************************
		HBox newMessageBox = new HBox();
		
		
		
		// Add components to the VBox
		primaryVBox.getChildren().addAll(headingLabel, scrollPane, newMessageBox);
		
		// Set up the new page and display
		Scene messageScene = new Scene(primaryVBox, 600, 600);
		messageStage.setScene(messageScene);
		messageStage.setTitle("Private Message(s)");
		messageStage.show();
		
	}
	
	
	
	/** Formats the PMS using VBox, Hbox and the array list of messages in a conversation. 
	 * @param boxForScrollPane
	 * @param messagesList
	 * @return VBox  ; for every message in the messagesList
	 */
	public VBox formatConversations(VBox boxForScrollPane, ArrayList<PrivateMessage> messagesList) { // ADD CORRECT DATA TYPE
		for (PrivateMessage message : messagesList) {
			HBox contentHBox = new HBox(10);
			
			Label senderLabel = new Label();
			senderLabel.setText(message.getSender()); 
			
			
			Label messageText = new Label();
			messageText.setText(message.getMessage()); 
			
			
			Label timeLabel = new Label();
			String time = formatTime((message.getTime())); 
			timeLabel.setText(time); // Type cast int to STring
			
			
			Region spacer1 = new Region();
			Region spacer2 = new Region();
			
			
			// Add all to HBox
			contentHBox.setHgrow(spacer1,  Priority.ALWAYS);
			contentHBox.setHgrow(spacer2, Priority.ALWAYS);
			contentHBox.getChildren().addAll(senderLabel, spacer1, messageText, spacer2, timeLabel);
			
			
			// Add to VBox
			boxForScrollPane.getChildren().add(contentHBox);
		}
		return boxForScrollPane;
	}
	
	/** A way to format the time in the chats
	 * @param time 
	 * @return String in the format MM/dd/yyyy HH:mm
	 */
	public String formatTime(int time) {
		
		Date date = new Date((long) time *1000);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		
		return sdf.format(date);
	}
	
    
}
	
	
	
