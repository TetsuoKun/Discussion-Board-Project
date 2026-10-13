package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import databasePart1.*;

/**
 * Responsible for allowing reviewer to carry out duties. 
 * Creates functionality and UI.
 */
public class ReviewerHomePage {

	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for the ReviewerHomePage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public ReviewerHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
    /**
     * Create functionality and UI for all Reviewer needs. 
     * This includes: logging out, accessing conversations,
     * and getting to list reviews page.
     * 
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object.
     */
	public void show(Stage primaryStage, User user) {
		DisplayUtilities displayUtilities = new DisplayUtilities();
		
		//Set up page Layout
		VBox layout = new VBox(10);
		layout.setId("standard-page-vbox-layout"); //Configure CSS
		layout.setStyle("-fx-alignment: center;");
		
		//Horizontal Consistency among the non admin pages
		HBox hLayout = new HBox(10);
		hLayout.setStyle("-fx-alignment: center;");

		// Create hello label
		Label helloLabel = displayUtilities.createLabel("Hello Reviewer", 200, 100);
		helloLabel.setId("welcome-text");
		
		
		// Create logout button
		Button logoutButton = new Button("Logout");
		
		logoutButton.setOnAction( a-> {
			UserLoginPage userLoginPage = new UserLoginPage(databaseHelper); 
			userLoginPage.show(primaryStage);
		});

		// Button to Switch Roles
		Button switchRoleButton = new Button ("Switch Roles");
		
		switchRoleButton.setOnAction( a-> {   
				WelcomeLoginPage welcomeLoginPage = new WelcomeLoginPage(databaseHelper); 
				welcomeLoginPage.show(primaryStage, user);
		});

		// Button to send student to ViewDiscussion page 
		Button viewDiscussionButton = new Button ("View Discussion");
		
		viewDiscussionButton.setOnAction( a-> {
			ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper);
			Question question = new Question(0, "null", "null", "null", false, false, 0, false, 0);//a blank question to load through the ViewDiscussion Page, used to save 424 lines of code
			viewDiscussions.show(primaryStage, user, question, false, "Reviewer");
		});



		Button conversationButton = new Button("Open Conversations:");
		conversationButton.setOnAction(d->{
			ConversationListPage conversationPage = new ConversationListPage(databaseHelper);
			conversationPage.show(user);
		});

		Button reviewListPage = new Button("View List of Reviews");
		reviewListPage.setOnAction(c->{
			ListReviewsPage reviewsPage = new ListReviewsPage(databaseHelper);
			reviewsPage.show(user.getUserName());
		});
		
		// Create button to request reviewer role
		Button reviewerProfileButton = new Button("Reviewer Profiles");
		reviewerProfileButton.setOnAction( b->{
			ReviewerProfilePage reviewerProfilePage = new ReviewerProfilePage(databaseHelper); 
			reviewerProfilePage.show(primaryStage, user);
			
		});	
		
		Button privateMessage = new Button("Initiate New Conversation");
		privateMessage.setOnAction( a->{
			ViewAllPMs viewAllPMs = new ViewAllPMs(databaseHelper);
			viewAllPMs.showPMBoard(primaryStage, user, "Reviewer");
		});
		
		// Add all elements to page
		hLayout.getChildren().addAll(viewDiscussionButton, switchRoleButton, logoutButton);
		layout.getChildren().addAll(helloLabel, hLayout, conversationButton, reviewListPage, reviewerProfileButton, privateMessage);
		
		
		// Configure and set window visible
		Scene scene = new Scene(layout, 800, 400);
		scene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.setTitle("Reviewer Home Page");
		primaryStage.show();
	}
}
