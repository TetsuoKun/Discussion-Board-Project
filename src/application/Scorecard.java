package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

import databasePart1.*;

// 
/**
 * Displays all stuff necessary for viewing the Scorecard of a user and for an Instructor to create a new scorecard. 
 */
public class Scorecard{
	
	private final DatabaseHelper databaseHelper;

	
	/** The constructor that creates this class. 
	 * @param databaseHelper
	 */
	public Scorecard(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
	

	// Display all the possible reviewers 
	// Provides a button to display that reviewers Score card
	/** Display all the possible reviewers 
	 * Provides a button to display that reviewers Score card
	 * @param isInstructor
	 * @param userName
	 */
	public void displayReviewerList(boolean isInstructor, String userName) {
		ArrayList<String> reviewerList = databaseHelper.loadReviewersList();
		
		VBox allContentBox = new VBox(10);
		
		for(String name : reviewerList) {
			Button nameButton = new Button(name);
			
			nameButton.setOnAction(a->{
				displayScorecard(isInstructor, name, userName);
			});
			
			allContentBox.getChildren().add(nameButton);
		}
		
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(allContentBox);
		
		
		Stage newStage = new Stage();
		Scene scene = new Scene(scrollPane, 750, 400);
	 	newStage.setScene(scene);
	 	newStage.setTitle("Scorecard Page");
		newStage.show();
		
	}
	
	
	
	/**Display the score card for the chosen reviewer 
	 * Displays info for both stuents and instructors 
	 * Only allows instructors to create a scorecard
	 * @param isInstructor
	 * @param reviewerName
	 * @param userName
	 */
	public void displayScorecard(boolean isInstructor, String reviewerName, String userName) {
		
		VBox allContent = new VBox();

		
		Label nameLabel = new Label("Reviewer Name: " + reviewerName);
		allContent.getChildren().add(nameLabel);
		
		
		
		if(databaseHelper.scorecardForReviewer(reviewerName)) {
			
			Label totalScoreLabel = new Label("Total Score: " + databaseHelper.scorecardTotalPoints(reviewerName));
			
			Label averageScoreLabel = new Label("Average Score: " + databaseHelper.averageScore(reviewerName));
			
			allContent.getChildren().add(totalScoreLabel);
			allContent.getChildren().add(averageScoreLabel);
		} else {
			Label errorLabel = new Label("No reviews found.");
			
			allContent.getChildren().add(errorLabel);
		}
		
		
		if(isInstructor) {
			boolean scorecardExist = databaseHelper.scorecardExistBetween(reviewerName, userName);
			
			int scoreBetween = 0;
			
			if(scorecardExist) {
				scoreBetween = databaseHelper.getScoreBetween(reviewerName, userName);
				Label currentScoreLabel = new Label("Current given score: " + scoreBetween);
				allContent.getChildren().add(currentScoreLabel);
			} else {
				Label currentScoreLabel = new Label("No current score given");
				allContent.getChildren().add(currentScoreLabel);
			}
			
		
			TextField inputField = new TextField();
			inputField.setPromptText("Enter value: 0-10");
			
			
			Label invalidScoreLabel = new Label("Invalid score. Must be 1-10");
			invalidScoreLabel.setVisible(false);
			
			
			
			Button submitButton = new Button("Submit Score");
			submitButton.setOnAction(a->{
				invalidScoreLabel.setVisible(false);
			
				String userInput = inputField.getText();
					
					
				int newScore = 0;
					
				try {
					newScore = Integer.parseInt(userInput);
						
						
					if((newScore >= 0) && (newScore <= 10)){
							
						if(scorecardExist) {
							databaseHelper.updateScorecard(reviewerName, userName, newScore);
						} else {
							databaseHelper.addScorecard(reviewerName, userName, newScore);
						}
							
							
					} else {
						invalidScoreLabel.setVisible(true);
					}
						
						
				} catch (NumberFormatException e) {
					invalidScoreLabel.setVisible(true);
				}	
			});
			
			allContent.getChildren().addAll(inputField, submitButton, invalidScoreLabel);
			
		}
		
		
		
		
		Stage newStage = new Stage();
		Scene scene = new Scene(allContent, 750, 400);
	 	newStage.setScene(scene);
	 	newStage.setTitle("Scorecard Page");
		newStage.show();
		
	}
	
}