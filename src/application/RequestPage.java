package application;

import java.sql.SQLException;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * The Request Page is the UI class for the Requests, using the request class to display stuff combined with the database functions. 
 * 
 */
public class RequestPage{
	
	private final DatabaseHelper databaseHelper;
	
	
	 /** Constructor for class. 
	 * @param databaseHelper
	 */
	public RequestPage(DatabaseHelper databaseHelper) {
	        this.databaseHelper = databaseHelper;
	    }
	
	 
	 
	 /** The way to display the review page. 
	 * @param userName
	 * @param isAdmin
	 * @param isInstructor
	 * @param isStaff
	 */
	public void show(String userName, boolean isAdmin, boolean isInstructor, boolean isStaff) {
		 VBox pageBox = new VBox();

		 HBox toggleBox = new HBox();
		 
		 
		 Separator separator = new Separator();
		 separator.setMaxWidth(750);
		 separator.setStyle("-fx-padding: 5 0 5 0;");
		 
		 
		 Button pendingButton = new Button("View Pending");
		 pendingButton.setOnAction(a->{
			 pageBox.getChildren().retainAll(toggleBox, separator);
			 displayPending(pageBox, userName, isInstructor, isAdmin);
		 });
		 
		 
		 Button completedButton = new Button("View Completed");
		 completedButton.setOnAction(b->{
			 pageBox.getChildren().retainAll(toggleBox, separator);
			 displayCompleted(pageBox, isInstructor);
		 });
		 
		 
		 toggleBox.getChildren().addAll(pendingButton, completedButton);
		 pageBox.getChildren().addAll(toggleBox, separator);
		 
		 
		 displayPending(pageBox, userName, isInstructor, isAdmin); // Default page to Pending View

		 
		 Stage newStage = new Stage();
		 Scene scene = new Scene(pageBox, 750, 400);
		 newStage.setScene(scene);
		 newStage.setTitle("Request Page");
		 newStage.show();
	 }
	 
	 /** A way to display "pending" for a request 
	 * @param pageBox
	 * @param userName
	 * @param isInstructor
	 * @param isAdmin
	 */
	private void displayPending(VBox pageBox, String userName, boolean isInstructor, boolean isAdmin) {
		 		 
		 if(isInstructor) {
			 Button createRequest = new Button("Create Request");
			 createRequest.setOnAction(a->{
				 createRequestView(userName);
			 });
			 pageBox.getChildren().add(createRequest);
		 }

		 
		 VBox allRequests = new VBox(10);
		 ArrayList<Request> requestList = databaseHelper.getAllPending();		 

		 
		 if (requestList.size() != 0) {
			 
			 for(Request currentRequest : requestList) {
				 
				 String requestContent = "";
				 
				 requestContent += "Request# " + currentRequest.getID() + "\tCreated By: " + currentRequest.getRequestUsername();
				 
				 requestContent += "\n\nDescription: \n" + currentRequest.getDescription();
				 
				 requestContent += "\n\nSolution: \n" + currentRequest.getSolution();
				 
				 
				 TextArea textArea = new TextArea(requestContent);
				 textArea.setEditable(false);
				 textArea.setWrapText(true);
				 
				 
				 VBox currentRequestBox = new VBox(textArea);
				 
				 
				 
				 if(isAdmin) {
					 Button closeRequestButton = new Button("Close Request");
					 
					 VBox solutionBox = new VBox();
					 solutionBox.setVisible(false);
					 
					 TextArea inputField = new TextArea();
					 inputField.setWrapText(true);
					 
					 Button submitButton = new Button("Submit");
					 
					 solutionBox.getChildren().addAll(inputField, submitButton);
					 
					 closeRequestButton.setOnAction(a->{
						 solutionBox.setVisible(true);
					 });
					 
					 
					 submitButton.setOnAction(b->{
						 String solutionText = inputField.getText();
						 
						 
						 try {
							databaseHelper.setSolution(currentRequest.getDescription(), solutionText);
						 } catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						 }
						 
						 try {
							databaseHelper.setPending(currentRequest.getDescription(), false);
						 } catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						 }
					 });
					 
					 
					 currentRequestBox.getChildren().addAll(closeRequestButton);
					 
					 
					 HBox allContent = new HBox();
					 allContent.getChildren().addAll(currentRequestBox, solutionBox);
					 
					 allRequests.getChildren().addAll(allContent);

				 } else {
					 allRequests.getChildren().add(currentRequestBox);	
				 }
				 
			 } 
		 } else {
			 Label emptyListLabel = new Label("There are no current Requests!");
			 
			 allRequests.getChildren().add(emptyListLabel);
		 }
		
		 
		 
		 
		 ScrollPane scrollPane = new ScrollPane();
		 scrollPane.setContent(allRequests);
		 
		 pageBox.getChildren().addAll(scrollPane);
		 
	 }
	 
	 /** Creates a way to view requests 
	 * @param userName
	 */
	private void createRequestView(String userName) {
		 Stage newStage = new Stage();

		 VBox createRequestBox = new VBox(10);
		 
		 Label headingLabel = new Label("Lets create a request:");
		 
		 TextArea inputField = new TextArea();
		 inputField.setWrapText(true);
		 
		 Button submitButton = new Button("Submit Request");
		 submitButton.setOnAction(a->{
			 String input = inputField.getText();
			 
			 Request newRequest = new Request(userName, input);
			 
			 Boolean created = false;
			 try {
				created = databaseHelper.newRequest(newRequest);
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
			 
			 if (created) {
				 newStage.close();
			 }
		 });
		 
		 createRequestBox.getChildren().addAll(headingLabel, inputField, submitButton);
		 
		 
		 Scene scene = new Scene(createRequestBox, 500, 400);
		 newStage.setScene(scene);
		 newStage.setTitle("Create Request Page");
		 newStage.show();
	 }
	 
	 
	 /** A way to display "completed" for this page. 
	 * @param pageBox
	 * @param isInstructor
	 */
	private void displayCompleted(VBox pageBox, boolean isInstructor) {
		 VBox allRequests = new VBox(10);


		 ArrayList<Request> completedList = databaseHelper.getAllCompleted();
		 
		 if(completedList.size() != 0) {
			 for(Request currentRequest : completedList) {
				 
				 String requestContent = "";
				 
				 requestContent += "Request# " + currentRequest.getID() + "\tCreated By: " + currentRequest.getRequestUsername();
				 
				 requestContent += "\n\nDescription: \n" + currentRequest.getDescription();
				 
				 requestContent += "\n\nSolution: \n" + currentRequest.getSolution();
				 
				 
				 TextArea textArea = new TextArea(requestContent);
				 textArea.setEditable(false);
				 textArea.setWrapText(true);
				 
				 
				 
				 if(isInstructor) {
					 Button reopenButton = new Button("Re-Open");
					 
					 Label instructionLabel = new Label("Create new description:");
					 
					 TextArea newDescriptionArea = new TextArea();
					 newDescriptionArea.setWrapText(true);
					 
					 Button submitButton = new Button("Submit");
					 
					 submitButton.setOnAction(b->{
						 String newDescriptionText = newDescriptionArea.getText();
						 
						 
						 
						 try {
								databaseHelper.setPending(currentRequest.getDescription(), true);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						 
						 
						 newDescriptionText = currentRequest.getDescription() + "\n" + newDescriptionText;
						 
						 try {
							databaseHelper.setDescription(currentRequest.getDescription(), newDescriptionText);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
 
					 });
					 
					 VBox reopenBox = new VBox(instructionLabel, newDescriptionArea, submitButton);
					 reopenBox.setVisible(false);
					 
					 reopenButton.setOnAction(a->{
						 reopenBox.setVisible(true);
					 });
					 
					 
					 
					 
					 VBox mainContent = new VBox(textArea, reopenButton);

					 HBox allContent = new HBox(mainContent, reopenBox);
					 
					 allRequests.getChildren().add(allContent);
					 
				 } else {
					 allRequests.getChildren().addAll(textArea);
				 }
				 
				 
				 
			 }
		 } else {
			 String errorText = "No requests have been completed yet.";
			 
			 TextArea textArea = new TextArea(errorText);
			 textArea.setWrapText(true);
			 textArea.setEditable(false);
			 allRequests.getChildren().addAll(textArea);
		 }
		 
		 ScrollPane scrollPane = new ScrollPane();
		 scrollPane.setContent(allRequests);
		 
		 pageBox.getChildren().addAll(scrollPane);
	 }
	 
}