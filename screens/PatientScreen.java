package screens;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sprint2.Appointment;
import sprint2.ManagementApp;
import sprint2.Person;
import sprint2.ManagementSystem;
import sprint2.Patient;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.geometry.*;

public class PatientScreen extends Pane{

	private ManagementApp mApp;
	private ManagementSystem manSys;
	private Patient pat;
	private Label leftInfoName, leftInfoDOB, leftInfoAddress, leftInfoTitle;
	private Label rightNextApp, rightNextAppID, rightDoctor, rightReason, rightDate, rightTime;
	private  double stageX, stageY;
	
	public PatientScreen(ManagementApp stage, ManagementSystem manSys)
	{
		mApp = stage;
		this.manSys = manSys;
		this.pat = (Patient)manSys.getCurrentUser();
		this.stageX = mApp.getStageX();
		this.stageY = mApp.getStageY();
		createNodes();				
	}
	private void createNodes() {			
		HBox info = buildInfoPanel();
		VBox apps = buildAppsPanel();
		
		VBox base = new VBox(info, apps);
		Pane view = new Pane(base); 
		getChildren().add(view);
	}
	private HBox buildInfoPanel() { 
		HBox info = new HBox(); 
		info.setPrefHeight(stageY/3);

		VBox left = buildLeftInfo();
		left.setPrefWidth(stageX/2);
		info.getChildren().add(left);

		VBox right = buildRightInfo();
		info.getChildren().add(right);
		return info; 
	}
	private VBox buildLeftInfo() { 
		Person p = manSys.getCurrentUser();

		leftInfoName = new Label("\t\t" + p.getName()); 
		leftInfoDOB = new Label("\t\t" + p.getBirthDate().toString()); 
		leftInfoAddress = new Label("\t\t" + p.getAddress()); 
		leftInfoTitle = new Label("Patient:");
		leftInfoTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

		VBox left = new VBox(1.5, leftInfoTitle, leftInfoName, leftInfoDOB, leftInfoAddress);
		left.setPadding(new Insets(10, 10, 10, 10));
		return left; 
	}
	private VBox buildRightInfo() { 
		int i = 0;
		Appointment app = pat.getAppointments().get(i);
		while(app.getDate().compareTo(LocalDate.now()) < 1) {
			app = pat.getAppointments().get(++i);
		}
		rightNextApp = new Label("Appointment:");
		rightNextApp.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		rightNextAppID = new Label("\t\t" + app.getID());
		rightDoctor = new Label("\t\t" + app.getDoctor().getName());
		rightReason = new Label("\t\t" + app.getReason());
		rightDate = new Label("\t\t" + app.getDate());
		rightTime = new Label("\t\t" + app.getTime());

		VBox right = new VBox(1.5, rightNextApp, rightNextAppID, rightDoctor, rightReason, rightDate, rightTime);
		right.setPadding(new Insets(10, 10, 10, 10));
		return right; 
	}
	private VBox buildAppsPanel() {
		ListView<Appointment> appointments = new ListView<Appointment>();
		appointments.setItems(FXCollections.observableArrayList(pat.getAppointments()));
		appointments.setOnMouseClicked(e -> {
			Appointment newApp = appointments.getSelectionModel().getSelectedItem();
			rightNextAppID.setText("\t\t" + newApp.getID());
			rightDoctor.setText("\t\t" + newApp.getDoctor().getName());
			rightReason.setText("\t\t" + newApp.getReason());
			rightDate.setText("\t\t" + newApp.getDate());
			rightTime.setText("\t\t" + newApp.getTime());
		})	;	
		VBox btnLogout = buildLogoutButton();
		
		VBox apps = new VBox(1.5, appointments, btnLogout);
		apps.setAlignment(Pos.CENTER);
		apps.setPrefWidth(stageX);
		apps.setPadding(new Insets(10, 100, 10, 100));
		return apps;
	}
	private VBox buildLogoutButton() {
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		Button btn = new Button("Log Out");
		btn.setOnAction(e -> {
			mApp.setPerson(null);
			mApp.loginPane();
		});
		vBox.getChildren().add(btn);
		return vBox;
	}	
}