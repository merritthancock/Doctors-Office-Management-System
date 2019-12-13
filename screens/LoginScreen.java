package screens;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sprint2.Doctor;
import sprint2.ManagementSystem;
import sprint2.ManagementApp;
import sprint2.Nurse;
import sprint2.Patient;
import sprint2.Person;
import sprint2.Receptionist;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

public class LoginScreen extends Pane {

	private ManagementApp mApp;
	ManagementSystem manSys;	  

	// Text fields for the login screen. 
	private TextField txtUserName, txtPassword;

	// Fields, labels and boxes for Person view. 
	//private Label leftInfoName,leftInfoTitle, leftInfoDOB, leftInfoAddress;

	public LoginScreen(ManagementApp stage, ManagementSystem manSys)
	{
		mApp = stage;
		this.manSys = manSys;
		buildLogin();	
	}

	// The following methods will build the login screen and all components within
	private void buildLogin() {
		
		  if(getChildren() != null) 
			  getChildren().clear();
		 

		Pane base = new Pane();
		BorderPane bpLoginBorders = buildLoginBorders();
		base.getChildren().add(bpLoginBorders);
		VBox vbLoginCenter = buildLoginCenter();
		bpLoginBorders.setCenter(vbLoginCenter);

		getChildren().addAll(base);	
		//return base;
	}

	/*
	 * public void setPerson(Person p) { this.person = p; } public Person
	 * getPerson() { return this.person; }
	 */

	private BorderPane buildLoginBorders() {
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(300, 0, 0, 750));

		return bp;
	}

	private VBox buildLoginCenter() {
		VBox vb = new VBox(10);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().add(buildWelcomeLabel());
		vb.getChildren().add(buildUserNameRow());
		vb.getChildren().add(buildPasswordRow());
		vb.getChildren().add(buildLoginButton());
		return vb;
	}

	private Label buildWelcomeLabel() {
		Label lblWelc = new Label("Welcome!");
		lblWelc.setFont(Font.font("Verdana", FontWeight.BOLD, 70));

		return lblWelc;
	}

	private HBox buildUserNameRow() {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("\tUser Name:\t"));
		txtUserName = new TextField();
		txtUserName.setPromptText("Enter your username...");
		hb.getChildren().add(txtUserName);
		return hb;
	}

	private HBox buildPasswordRow() {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("\t Password:\t"));
		txtPassword = new PasswordField();
		txtPassword.setPromptText("Enter your password...");

		hb.getChildren().add(txtPassword);
		return hb;
	}

	private VBox buildLoginButton() {

		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		Button btn = new Button("Login");
		Label lbl = new Label("");
		lbl.setTextFill(Color.web("#FF0000"));

		vBox.getChildren().add(btn);
		vBox.getChildren().add(lbl);

		btn.setOnAction(e -> {	
			String user, password;
			Boolean valid = false;
			if(txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty()) {
				lbl.setText("Please enter a username and password."); 
			} 
			else {
				lbl.setText("");
				user = txtUserName.getText().trim();
				password = txtPassword.getText().trim();
				txtUserName.clear();
				txtPassword.clear();
				//check if credentials are valid here
				try {
					valid = manSys.login(user,password);
				} catch (Exception exc) {
					exc.printStackTrace();
				}
				if (valid == false){
					lbl.setText("Invalid username or password.");
				}
				//						  //used to test switching scenes
				//						  Boolean patient = false;
				//						  Boolean doctor = false;
				//						  Boolean receptionist = true;		
				//						  Boolean nurse = false;						  
				Person p = manSys.getCurrentUser();

				if(valid) {
					if(p instanceof Patient) {
						mApp.changeScene(new PatientScreen(mApp, manSys));
					}
					else if(p instanceof Doctor) {
						mApp.changeScene(new DoctorScreen(mApp, manSys));
					}
					else if(p instanceof Nurse) {
						mApp.changeScene(new NurseScreen(mApp, manSys));
					}
					else {
						mApp.changeScene(new ReceptionistScreen(mApp, manSys));
					}
				}
			}				  
		});

		return vBox;
	}	
}