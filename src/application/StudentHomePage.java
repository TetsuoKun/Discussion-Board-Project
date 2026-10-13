package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import databasePart1.*;

/**
 * Responsible for allowing student to carry out all necessary functions. 
 * Creates UI for student. 
 */
public class StudentHomePage {

	private final DatabaseHelper databaseHelper;

	
	/**
	 * Constructor for the StudentHomePage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */

    public StudentHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
    /**
     * Creates functionality and UI to allow the student carry out their duties. 
     * Includes accessing discussion page, requesting Reviewer role, 
     * accessing conversations page, and creating reviewer ratings. 
     * 
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object, necessary for accessing information. 
     */
	public void show(Stage primaryStage, User user) {	
		//Create Page layout
		VBox vLayout = new VBox(10);
		vLayout.setId("standard-page-vbox-layout");
		
		vLayout.setStyle("-fx-alignment: center;");
		
		HBox hLayout = new HBox(10);
		DisplayUtilities displayUtilities = new DisplayUtilities();
		
		
		// Create hello label		
		Label helloLabel = displayUtilities.createLabel("Hello Student", 200, 100);
		helloLabel.setId("welcome-text");
		
		
		// Button to send student to ViewDiscussion page 
		Button viewDiscussionButton = new Button ("View Discussion");
		
		viewDiscussionButton.setOnAction( a-> {
			ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper);
			Question question = new Question(0, "null", "null", "null", false, false, 0, false, 0);//a blank question to load through the ViewDiscussion Page, used to save 424 lines of code
			viewDiscussions.show(primaryStage, user, question, false, "Student");
		});

		
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
		
		// Create the stuffs for request of Reviewer Role **********************************
		HBox requestBox = new HBox(10);
		Boolean exists = databaseHelper.checkUserExists(user.getUserName());
		
		// Create button to request reviewer role
		Button requestReviewerRole = new Button("Request Reviewer Role");
		requestReviewerRole.setDisable(false);
		requestReviewerRole.setOnAction( b->{
			databaseHelper.addRequest(user.getUserName());
			requestReviewerRole.setDisable(true);
		});
		
		// Create button to request reviewer role
		Button reviewerProfileButton = new Button("Reviewer Profiles");
		reviewerProfileButton.setOnAction( b->{
			ReviewerProfilePage reviewerProfilePage = new ReviewerProfilePage(databaseHelper); 
			reviewerProfilePage.show(primaryStage, user);
			
		});
		
		// Create Label for response to reviewer request
		Label requestResponseLabel = new Label("RESPONSE");
		requestResponseLabel.setVisible(true);
		
		int decision = databaseHelper.getDecision(user.getUserName());

		if (decision == 0){
			requestResponseLabel.setText("Request is pending ...");
			requestReviewerRole.setDisable(true);
		} else if (decision == 1){
			requestResponseLabel.setText("Request has been denied.");
			requestReviewerRole.setDisable(true);
		} else if (decision == 2){
			requestResponseLabel.setText("Request has been approved. Congratulations!");
			requestReviewerRole.setDisable(true);
		}
		/*
		if (exists){
			requestReviewerRole.setVisible(false);
			requestResponseLabel.setVisible(true);
		} else {
			requestReviewerRole.setVisible(true);
			requestResponseLabel.setVisible(false);
		}*/
		

		// Functionality for getting to Reviwer Rating Page
		Button reviewerRatingPageButton = new Button("Go to Reviewer Rating Page");
		reviewerRatingPageButton.setOnAction(c -> {
			ReviewerRatingPage ratingPage = new ReviewerRatingPage(databaseHelper);
			ratingPage.show(primaryStage, user);
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
			viewAllPMs.showPMBoard(primaryStage, user, "Student");
		});
		
		Button scorecardButton = new Button("Scorecard Page");
		scorecardButton.setOnAction(d->{
			Scorecard scorecard = new Scorecard(databaseHelper);
			scorecard.displayReviewerList(false, user.getUserName());
		});
		
		
		//Center Items
		hLayout.setStyle("-fx-alignment: center;");
		requestBox.setStyle("-fx-alignment: center;");
		// Add all elements to layout
		requestBox.getChildren().addAll(requestReviewerRole, requestResponseLabel);
		hLayout.getChildren().addAll(viewDiscussionButton, switchRoleButton, logoutButton);
		vLayout.getChildren().addAll(helloLabel, hLayout, requestBox, reviewerRatingPageButton, conversationPageButton, reviewerProfileButton, privateMessage, scorecardButton);
		
		// Configure and set window visible
		Scene scene = new Scene(vLayout, 800, 400);
        scene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.setTitle("Student Home Page");
		primaryStage.show();
	}
}