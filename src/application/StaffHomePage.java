package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import databasePart1.*;

/**
 * Responsible for allowing staff member to carry out all duties. 
 */
public class StaffHomePage {

	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for the StaffHomePage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public StaffHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
    /**
     * Creates functionality and UI for the staff user.
     * Allows them to carry out all duties, or directs 
     * them to appropriate pages. 
     * 
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object.
     */
	public void show(Stage primaryStage, User user) {
		DisplayUtilities displayUtilities = new DisplayUtilities();
		
		//Set Up the Layout
		VBox layout = new VBox(10);
		layout.setId("standard-page-vbox-layout"); //Configure CSS
		layout.setStyle("-fx-alignment: center;");
		
		//Horizontal Consistency among the non admin pages
		HBox hLayout = new HBox(10);
		hLayout.setStyle("-fx-alignment: center;");
		
		// Create hello label
		Label helloLabel = displayUtilities.createLabel("Hello Staff", 300, 100);
		helloLabel.setId("welcome-text");
		
		
		// Create logout button
		Button logoutButton = new Button("Logout"); 
		
		logoutButton.setOnAction( a-> {
				UserLoginPage userLoginPage = new UserLoginPage(databaseHelper);
				userLoginPage.show(primaryStage);
		});
		
		// Button to send to ViewDiscussion page 
		Button viewDiscussionButton = new Button ("View Discussion");

		viewDiscussionButton.setOnAction( a-> {
			ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper);
			Question question = new Question(0, "null", "null", "null", false, false, 0, false, 0);//a blank question to load through the ViewDiscussion Page, used to save 424 lines of code
			viewDiscussions.show(primaryStage, user, question, false , "Staff");
		});
		
		Button viewPMs = new Button("View Private Messages in System");
		viewPMs.setOnAction( a->{
			ViewAllPMs viewAllPMs = new ViewAllPMs(databaseHelper);
			viewAllPMs.show(primaryStage, user);
		});
		
		// Get to Conversation List Page
		Button conversationPageButton = new Button("Go to Conversation Page");
		conversationPageButton.setOnAction(z->{
			ConversationListPage conversationPage = new ConversationListPage(databaseHelper);
			conversationPage.show(user);
		});
		
		Button privateMessage = new Button("Initiate New Conversation");
		privateMessage.setOnAction( a->{
			ViewAllPMs viewAllPMs = new ViewAllPMs(databaseHelper);
			viewAllPMs.showPMBoard(primaryStage, user, "Staff");
		});
		
		// Button to Switch Roles
		Button switchRoleButton = new Button ("Switch Roles");

		switchRoleButton.setOnAction( a-> {   
			WelcomeLoginPage welcomeLoginPage = new WelcomeLoginPage(databaseHelper); 
			welcomeLoginPage.show(primaryStage, user);
		});

		
		Button requestPageButton = new Button("Request Page");
		requestPageButton.setOnAction(e->{
			RequestPage viewRequests = new RequestPage(databaseHelper);
			viewRequests.show(user.getUserName(), false, false, true);
		});
		
		// Add all elements to page		
		hLayout.getChildren().addAll(viewDiscussionButton, switchRoleButton, logoutButton);
		layout.getChildren().addAll(helloLabel, hLayout, viewPMs, conversationPageButton, privateMessage, requestPageButton);
		
		
		// Configure and set window visible
		Scene scene = new Scene(layout, 800, 400);
        scene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Staff Home Page");
        primaryStage.show();
	}
}