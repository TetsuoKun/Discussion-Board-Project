package application;

import java.util.ArrayList;

import databasePart1.DatabaseHelper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This Page holds the view of all reviewers profile. all users can come and view the profiles.
 * 
 * @author CSE Monday 11
 * @version 1.0
 * @since 2025-04-13
 */
public class ReviewerProfilePage {

	private final DatabaseHelper databaseHelper;
	public Label bioText = new Label("");
	public Label reviewerNameText = new Label("");
	
	/**Constructor for the Review Answer Page 
	 * @param databaseHelper
	 */
	public ReviewerProfilePage(DatabaseHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
	}
	
	/**Main method to visually build the stage for the user.
	 * @param primaryStage
	 * @param user
	 */
	public void show(Stage primaryStage, User user) {
		//Set up stage
		BorderPane layout = new BorderPane();
		Insets padding = new Insets(10, 20, 10, 20);								//Define Padding object basically
		
	//****NAV******
		HBox navigation = new HBox(5);												//Store Navigation Buttons at the top
		Label errorLabel = new Label("");
			errorLabel.setWrapText(true);
			errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		
	//*****Reviewers List*****
    	VBox listOfAllReviewers = new VBox(5); 										//to store the List of Reviewers, LEFT SIDE 
    	
    	
	//*****Profile Box*****
    	VBox profileBox = new VBox(5);												//Store the Profile Information 
		VBox storeBio = new VBox(5);
    	
    	TextField bioEdit = new TextField();
    	
    	Label title = new Label("Reviewer Profile");
		Label bioLabel = new Label("Bio:");			
		Label ScorecardRankingTitle = new Label("Staff Scorecard:");
		Label scorecardRanking = new Label("");
		Label reviewerRankingTitle = new Label("Reviewer Ranking:");
		Label reviewerRanking = new Label("");

		HBox editControls = new HBox(5);
		
		bioEdit.setVisible(false);
		bioEdit.setPromptText("");
		bioEdit.setMinWidth(700);
		bioText.setStyle("-fx-font-weight: bold;");
		reviewerNameText.setId("reviewer-profile-name");		
		
		VBox bottom = new VBox (5);

			HBox bottomBoxes = new HBox(5);
			
				HBox bottomLabels = new HBox(330);
				bottomLabels.setStyle("-fx-alignment: center; -fx-font-weight: bold;");

				Label titleAllReviews = new Label("All Reviews");
				Label titleTrusts = new Label("Users that Trust This Reviewer");

				VBox allReviews  = new VBox(5);
				VBox allTrustedStudents = new VBox(5);	
		
		
	//****BUTTONS******
		
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
		
		// Button to Save Page
		Button saveButton = new Button ("Save");
		saveButton.setVisible(false);
		saveButton.setOnAction( a-> {   
			if(bioEdit.getLength()<=200) {
				databaseHelper.updateBio(user.getUserName() , bioEdit.getText());
				bioText.setText(bioEdit.getText());
				bioEdit.setVisible(false);
				saveButton.setVisible(false);
			} else { errorLabel.setText("Please Shorten Bio to below 201 characters");}
		});	
		

		// Button to Edit Page
		Button editButton = new Button ("Edit Profile");
		editButton.setVisible(false);
		editButton.setOnAction( a-> {   
			bioEdit.setPromptText(bioText.getText());
			bioEdit.setVisible(true);
			saveButton.setVisible(true);
		});		
		
		
		
		ArrayList<String> reviewersList = databaseHelper.loadReviewersList();		//Get List of Reviewers
		for(String reviewerName : reviewersList) {
			Button loadReviewerProfile = new Button(reviewerName);
			loadReviewerProfile.setUserData(reviewerName);							//Store ReviewerName in the button
			loadReviewerProfile.setId("discussion-control-vertical");//CSS
			
			loadReviewerProfile.setOnAction( a-> {
				allReviews.getChildren().clear();
				allTrustedStudents.getChildren().clear();
				String reviewerNameUnload = (String) loadReviewerProfile.getUserData();
				if(user.getUserName().equals(reviewerNameUnload)) {editButton.setVisible(true);}
				else {editButton.setVisible(false);}	
				//Text Set up
				String biotext = databaseHelper.loadBio(reviewerNameUnload);
				if(biotext.equals("No Bio")) {bioText.setText("Bio Has Not Been Created Yet");} 
				else {bioText.setText(biotext);}
				bioText.setWrapText(true);
				reviewerNameText.setText(reviewerNameUnload);
				
				reviewerRanking.setText(String.valueOf(databaseHelper.getRatingRank(reviewerName, databaseHelper).get(0) + 1) + " out of " + String.valueOf(databaseHelper.getRatingRank(reviewerName, databaseHelper).get(1)));
				scorecardRanking.setText("Total Score: " + databaseHelper.scorecardTotalPoints(reviewerName)+"\n"+"Average Score: " + databaseHelper.averageScore(reviewerName));
				//LoadReviews and Trusted Students VBOXes
				AnswersList reviewList = databaseHelper.loadReviewsByUsername(reviewerNameUnload);
				for(int i=0; i<reviewList.getSize(); i++) {
					if(reviewList.getAnswerFromIndex(i).getIsReview() && reviewList.getAnswerFromIndex(i).getUserName().equals(reviewerNameUnload)) {
						Label review = new Label(reviewList.getAnswerFromIndex(i).getAnswerText());
						review.setWrapText(true);
						review.setMinWidth(profileBox.getWidth()/2);
						review.setMaxWidth(profileBox.getWidth()/2);
						allReviews.getChildren().add(review);
					}
				}
				ArrayList<String> trustList = databaseHelper.loadTrustsList(reviewerNameUnload, databaseHelper);
				for(String userName: trustList) {
					Label name = new Label(userName);
					name.setWrapText(true);
					name.setMinWidth(profileBox.getWidth()/2);
					name.setMaxWidth(profileBox.getWidth()/2);
					allTrustedStudents.getChildren().add(name);
				}
				/*
				 * NOW ADD TRUSTED STUDENT LIST Generation in the ScrollPane
				 * AND ADD SPACING WIDTH FOR HBOX HOLDING THE SCROLLPANES
				 * AND ADD SET THE SCOLLPANE WIDTH IN PLACE AS WELL
				 * 
				 * 
				 * 
				 * 
				 */
				
			});
			listOfAllReviewers.getChildren().add(loadReviewerProfile);
		}
		
		editControls.getChildren().addAll(bioEdit, saveButton);
		
		
		
		ScrollPane allReviewsScroll = new ScrollPane(allReviews);
		ScrollPane allTrustedStudentsScroll = new ScrollPane(allTrustedStudents);
		allReviewsScroll.setFitToWidth(true);
		storeBio.setPrefWidth(800);
		
		//allTrustedStudentsScroll.setFitToWidth(true);
		bottomBoxes.setHgrow(allReviewsScroll, Priority.ALWAYS);
		bottomBoxes.setHgrow(allTrustedStudentsScroll, Priority.ALWAYS);
		bottom.setVgrow(bottom, Priority.ALWAYS);
		bottomBoxes.setMinWidth(950);
		bottomBoxes.setMinHeight(200);
		
		storeBio.setMaxWidth(500);
		bioText.prefWidthProperty().bind(storeBio.widthProperty());
		bioText.setWrapText(true);

		storeBio.getChildren().add(bioText);
		bottomBoxes.getChildren().addAll(allReviewsScroll, allTrustedStudentsScroll);
		bottomLabels.getChildren().addAll(titleAllReviews, titleTrusts);
		bottom.getChildren().addAll(bottomLabels, bottomBoxes);
		allReviews.setMaxWidth(bottomBoxes.getWidth()/2);
		allTrustedStudents.setMaxWidth(bottomBoxes.getWidth()/2);

		
    	ScrollPane scrollPaneLeft = new ScrollPane(listOfAllReviewers);				//Scrollable view of all reviewers in the system, LEFT SIDE 
    	scrollPaneLeft.setPadding(padding);
		layout.setPadding(padding);
    	layout.setLeft(scrollPaneLeft);
    	layout.setCenter(profileBox);
    	layout.setTop(navigation);
    	//seting some widths of the page
    	scrollPaneLeft.setPrefWidth(200);
    	
    	profileBox.getChildren().addAll(title, reviewerNameText , bioLabel, storeBio, editControls, ScorecardRankingTitle, scorecardRanking, reviewerRankingTitle, reviewerRanking, bottom);
		navigation.getChildren().addAll(switchRoleButton, logoutButton, editButton, errorLabel);
    	
    	Scene userScene = new Scene(layout, 1200, 500);
	    userScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
	    
	    // Set the scene to primary stage
	    primaryStage.setScene(userScene);
	    primaryStage.setTitle("Reviewer Profile Page");
	    primaryStage.centerOnScreen();
	    primaryStage.show();

	}
}
