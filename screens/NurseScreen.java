package screens;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sprint2.ManagementSystem;
import sprint2.Nurse;
import sprint2.Patient;
import sprint2.Appointment;
import sprint2.AppointmentData;
import sprint2.DatabaseDriver;
import sprint2.ManagementApp;

public class NurseScreen extends Pane{
	private ManagementApp mApp;
	private ManagementSystem manSys;
	private Nurse nur;
	private VBox rightInfo;
	private  double stageX, stageY;
	private Label updateSuccess, rightAppID, rightDoctor, rightPatient, rightReason, rightDate, rightTime, rightTemp, rightHeight, rightWeight, rightBloodP;
	
	// Text fields for the Nurse screen. 
	private TextField txtTest, txtHeight, txtWeight, txtTemp, txtBloodP;
	
	public NurseScreen() {}
	
	
	public NurseScreen(ManagementApp stage, ManagementSystem manSys)
	{
		mApp = stage;
		this.manSys = manSys;
		this.nur = (Nurse)manSys.getCurrentUser();
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

		VBox left = buildNurseOps();
		left.setPrefWidth(stageX/2);
		info.getChildren().add(left);

		rightInfo = buildRightInfo();
		info.getChildren().add(rightInfo);
		return info; 
	}
	private VBox buildNurseOps() {
		VBox vb = new VBox(10);
		
		vb.getChildren().add(buildWelcomeLabel());
		vb.getChildren().add(buildTestLabel());
		vb.getChildren().add(buildTest());
		vb.getChildren().add(buildTestButton());
		vb.getChildren().add(buildVitalsLabel());
		vb.getChildren().add(buildHeight());
		vb.getChildren().add(buildWeight());
		vb.getChildren().add(buildTemp());
		vb.getChildren().add(buildBloodP());
		vb.getChildren().add(buildVitalsButton());
		vb.setAlignment(Pos.CENTER);
		return vb;
	}
	private VBox buildRightInfo() { 
		int i = 0;
		Appointment app = manSys.calendar.get(i);
		while(app.getDate().compareTo(LocalDate.now()) != 1) {
			app = manSys.calendar.get(++i);
		}
		nur.setAppt(app);
		//System.out.print(nur.toString());
		Label rightApp = new Label("Appointment:");
		rightApp.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		rightAppID = new Label("Appointment ID:\t" + app.getID());
		rightDoctor = new Label("Doctor:\t" + app.getDoctor().getName());
		rightPatient = new Label("Patient:\t" + app.getPatient().getName());
		rightReason = new Label("Reason:\t" + app.getReason());
		rightDate = new Label("Date:\t" + app.getDate());
		rightTime = new Label("Time:\t" + app.getTime());
		rightTemp = new Label("Temp:\t");
		rightBloodP = new Label("Blood Pressure:\t");
		rightHeight = new Label("Height:\t");
		rightWeight = new Label("Weight:\t");
		if(app.getAppData() != null) {
			AppointmentData appData = app.getAppData();
			rightTemp.setText("Temp:\t"+ String.valueOf(appData.getTemp()));
			rightBloodP.setText("Blood Pressure: "+ appData.getPBloodP());
			rightHeight.setText("Height:\t" + String.valueOf(appData.getHeight()));
			rightWeight.setText("Weight:\t" + String.valueOf(appData.getWeight()));
		}
		
		VBox right = new VBox(1.5, rightApp, rightAppID, rightDoctor, rightPatient, rightReason, rightDate, rightTime, rightTemp, rightBloodP, rightHeight, rightWeight);
		right.setPadding(new Insets(10, 10, 10, 10));
		return right; 
	}
	private VBox buildAppsPanel() {
		ListView<Appointment> appointments = new ListView<Appointment>();
		
		ArrayList<Appointment> futureApps = new ArrayList<>();
		for(Appointment a: manSys.calendar) {
			if(a.getDate().compareTo(LocalDate.now()) != -1)
				futureApps.add(a);
		}
		appointments.setItems(FXCollections.observableArrayList(futureApps));
		appointments.setOnMouseClicked(e -> {
			Appointment newApp = appointments.getSelectionModel().getSelectedItem();
			nur.setAppt(newApp);
			updateSuccess.setVisible(false);
			//System.out.print(nur.toString());
			rightAppID.setText("Appointment ID:\t" + newApp.getID());
			rightDoctor.setText("Doctor:\t" + newApp.getDoctor().getName());
			rightPatient.setText("Patient:\t" + newApp.getPatient().getName());
			rightReason.setText("Reason:\t" + newApp.getReason());
			rightDate.setText("Date:\t" + newApp.getDate());
			rightTime.setText("Time:\t" + newApp.getTime());
			if(newApp.getAppData() != null) {
				AppointmentData appData = newApp.getAppData();
				rightTemp.setText("Temp:\t" + String.valueOf(appData.getTemp()));
				rightBloodP.setText("Blood Pressure: " + appData.getPBloodP());
				rightHeight.setText("Height:\t" + String.valueOf(appData.getHeight()));
				rightWeight.setText("Weight:\t" + String.valueOf(appData.getWeight()));
			}
			else {
				rightTemp.setText("");
				rightBloodP.setText("");
				rightHeight.setText("");
				rightWeight.setText("");
			}
		})	;	
		VBox btnLogout = buildLogoutButton();
		
		VBox apps = new VBox(1.5, appointments, btnLogout);
		apps.setAlignment(Pos.CENTER);
		apps.setPrefWidth(stageX);
		apps.setPadding(new Insets(10, 100, 10, 100));
		return apps;
	}
	private Label buildWelcomeLabel() {
		Label lblWelc = new Label("Nurse: " + nur.getName());
		lblWelc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		return lblWelc;
	}
	
	//Below methods are for inputTest method
	private Label buildTestLabel() {
		Label lblWelc = new Label("Test");
		lblWelc.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		return lblWelc;
	}
	
	private HBox buildTest() {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("\tEnter Test:\t\t\t"));
		txtTest = new TextField();
		txtTest.setPromptText("Enter a test");
		hb.getChildren().add(txtTest);
		//hb.setAlignment(Pos.CENTER);
		return hb;
	}
	private VBox buildTestButton() {
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		Button btn = new Button("Submit Test");
		Label lbl = new Label("");
		lbl.setTextFill(Color.web("#FF0000"));
		btn.setOnAction(e->{
			String test;
			String id = nur.appt.getID().toString();
			System.out.println(id);

			AppointmentData appData = null;
			for(AppointmentData ad: manSys.appointmentData) {
				if(ad.getID().contentEquals(id)) {
					appData = ad;
					//System.out.println(appData.getID());
				}
			}
			if(appData == null) {
				appData = new AppointmentData(id);
				manSys.addAppData(id);
				
				manSys.appointmentData.add(appData);
			}

			test = txtTest.getText().toString();
			try {
				nur.inputTests(test, manSys);
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
			txtTest.clear();
			updateSuccess.setVisible(true);
		});
		updateSuccess = new Label("Operation Successful!");
		updateSuccess.setTextFill(Color.web("#FF0000"));
		vBox.getChildren().add(btn);
		vBox.getChildren().add(lbl);
		updateSuccess.setVisible(false);
		return vBox;
	}
	
	//Below methods are for inputVitals method
	private Label buildVitalsLabel() {
		Label lblWelc = new Label("Vitals");
		lblWelc.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		return lblWelc;
	}
	
	private HBox buildHeight() {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("\tEnter Height:\t\t\t"));
		txtHeight = new TextField();
		txtHeight.setPromptText("Enter Height");
		hb.getChildren().add(txtHeight);
		//hb.setAlignment(Pos.CENTER);
		return hb;
	}
	
	private HBox buildWeight() {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("\tEnter Weight:\t\t\t"));
		txtWeight = new TextField();
		txtWeight.setPromptText("Enter Weight");
		hb.getChildren().add(txtWeight);
		//hb.setAlignment(Pos.CENTER);
		return hb;
	}
	
	private HBox buildTemp() {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("\tEnter Temp:\t\t\t"));
		txtTemp = new TextField();
		txtTemp.setPromptText("Enter temperature");
		hb.getChildren().add(txtTemp);
		//hb.setAlignment(Pos.CENTER);
		return hb;
	}
	
	private HBox buildBloodP() {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("\tEnter Blood Pressure:\t"));
		txtBloodP = new TextField();
		txtBloodP.setPromptText("Enter Blood Pressure");
		hb.getChildren().add(txtBloodP);
		//hb.setAlignment(Pos.CENTER);
		return hb;
		
	}
	
	private VBox buildVitalsButton() {
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		Button btn = new Button("Submit Vitals");
		
		btn.setOnAction(e -> {
			String bloodP;
			double weight, temp;
			int height;
			String id = nur.appt.getID().toString();
			AppointmentData appData = null;
			for(AppointmentData ad: manSys.appointmentData) {
				if(ad.getID().contentEquals(id)) {
					appData = ad;
					//System.out.println(appData.getID());
				}
			}
			if(appData == null) {
				appData = new AppointmentData(id);
				manSys.addAppData(id);
				
				manSys.appointmentData.add(appData);
			}
			
			height = Integer.parseInt(txtHeight.getText().trim());
			weight = Double.parseDouble(txtWeight.getText().trim());
			temp = Double.parseDouble(txtTemp.getText().trim());
			bloodP = txtBloodP.getText().trim();
			try {
				nur.inputVitals(height, weight, temp, bloodP, manSys);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			rightTemp.setText("Temp:\t" + String.valueOf(temp));
			rightBloodP.setText("Blood Pressure: " + bloodP);
			rightHeight.setText("Height:\t" + String.valueOf(height));
			rightWeight.setText("Weight:\t" + String.valueOf(weight));
			txtHeight.clear();
			txtWeight.clear();
			txtTemp.clear();
			txtBloodP.clear();
			updateSuccess.setVisible(true);
		});
		updateSuccess = new Label("Operation Successful!");
		updateSuccess.setTextFill(Color.web("#FF0000"));
		vBox.getChildren().add(btn);
		vBox.getChildren().add(updateSuccess);
		updateSuccess.setVisible(false);
		return vBox;
	}
	
	private VBox buildLogoutButton() {
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		Button btn = new Button("Log Out");
		btn.setOnAction(e -> {
			mApp.setPerson(null);
			mApp.loginPane();
		});
		vBox.getChildren().add(btn);
		return vBox;
	}			
}