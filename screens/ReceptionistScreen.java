package screens;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import sprint2.Appointment;
import sprint2.AppointmentData;
import sprint2.ManagementApp;
import sprint2.Person;
import sprint2.Receptionist;

public class ReceptionistScreen extends Pane{

	private ManagementApp mApp;
	private ManagementSystem manSys;
	private Receptionist recep;
	private Label leftInfoName, leftInfoDOB, leftInfoAddress, leftInfoTitle;

	private Label rightInfoLabel, checkin, checkout, outLabel, roomNum;
	private Label rightDoctor, rightReason, rightDate, rightTime, rightTemp, rightBloodP, rightHeight, rightWeight, rightPatient, rightAppID;
	private TextField appID, txtRoomNum;

	private  double stageX, stageY;
	
	public ReceptionistScreen(ManagementApp stage, ManagementSystem manSys)
	{
		mApp = stage;
		this.manSys = manSys;
		this.recep = (Receptionist)manSys.getCurrentUser();
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
		left.setPrefWidth(stageX/3);
		info.getChildren().add(left);
		
		VBox center = buildCenterInfo();
		center.setPrefWidth(stageX/3);
		info.getChildren().add(center);

		VBox right = buildRightInfo();
		info.getChildren().add(right);
		return info; 
	}
	private VBox buildLeftInfo() { 
		Person p = manSys.getCurrentUser();

		leftInfoName = new Label("\t\t" + p.getName()); 
		leftInfoDOB = new Label("\t\t" + p.getBirthDate().toString()); 
		leftInfoAddress = new Label("\t\t" + p.getAddress()); 
		leftInfoTitle = new Label("Receptionist:");
		leftInfoTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

		VBox left = new VBox(1.5, leftInfoTitle, leftInfoName, leftInfoDOB, leftInfoAddress);
		left.setPadding(new Insets(10, 10, 10, 10));
		return left; 
	}
	private VBox buildCenterInfo() {
		for(Appointment a: manSys.calendar) {
			if(a.getDate().compareTo(LocalDate.now()) != -1)
				recep.setAppt(a);
		}
		Appointment app = recep.appt;
		Label title = new Label("Appointment:");
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
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
		VBox vb = new VBox(1.5, title, rightAppID, rightPatient, rightDoctor, rightReason, rightDate, rightTime, rightTemp, rightBloodP, rightHeight, rightWeight);
		return vb;
	}
	private VBox buildRightInfo() { 
		rightInfoLabel = new Label("Tasks");
		rightInfoLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		checkin = new Label("checkin");
		checkout = new Label("checkout");
		Button createApp = new Button("Create Appointment");
		createApp.setOnAction(e -> {
			mApp.changeScene(new CreateAppointmentScreen(mApp, manSys)); 
		});
		Button reSchedule = new Button("Modify Appointment");
		reSchedule.setOnAction(e -> {
			mApp.changeScene(new ModifyAppointmentScreen(mApp, manSys)); 
		});
		
		HBox checkInBox = buildCheckin();
		HBox checkOutBox = buildCheckout();
		VBox right = new VBox(1.5, rightInfoLabel, reSchedule, createApp, checkInBox, checkOutBox);
		right.setPadding(new Insets(10, 10, 10, 10));
		return right; 
	}
	
	private HBox buildCheckin() {
		checkin = new Label("Checkin: \t");
		checkin.setFont(Font.font("Verdana", 15));

		Label outLabel = new Label("");
		roomNum = new Label("Room Number: ");
		roomNum.setFont(Font.font("Verdana", 15));
		outLabel.setTextFill(Color.web("#FF0000"));
		txtRoomNum = new TextField();
		txtRoomNum.setPromptText("Enter Room Number");
		Button checkInBtn = new Button("Checkin");
		checkInBtn.setOnAction(e -> {
			String id = recep.appt.getID();
			int roomNum = Integer.parseInt(txtRoomNum.getText());
			txtRoomNum.clear();
			AppointmentData appData = null;
			for(AppointmentData ad : manSys.appointmentData) {
				if(ad.getID().contentEquals(id)) {
					appData = ad;
					try {
						recep.checkIn(roomNum, manSys);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					outLabel.setTextFill(Color.web("#7FFFD4"));
					outLabel.setText("Success!");
					break;
				}
				outLabel.setTextFill(Color.web("#FF0000"));
				outLabel.setText("Error: Could not find the Appointment!");				
			}
		});
		HBox right = new HBox();
		right.setSpacing(10);
		right.getChildren().addAll(checkin,txtRoomNum, checkInBtn, outLabel);
		return right; 
	}
	
	private HBox buildCheckout() {
		checkout = new Label("Checkout: \t");
		checkout.setFont(Font.font("Verdana", 15));
		Label outLabel = new Label("");
		outLabel.setTextFill(Color.web("#FF0000"));
		Button checkOutBtn = new Button("Checkout");
		checkOutBtn.setOnAction(e -> {
			try {
				recep.checkOut();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			outLabel.setTextFill(Color.web("#FF0000"));			
		});
		HBox right = new HBox();
		right.setSpacing(10);
		right.getChildren().addAll(checkout,checkOutBtn, outLabel);
		return right; 
	}
	
	
	private VBox buildAppsPanel() {
		ListView<Appointment> appointments = new ListView<Appointment>();
		
		appointments.setItems(FXCollections.observableArrayList(manSys.calendar));
		appointments.setOnMouseClicked(e -> {
			Appointment newApp = appointments.getSelectionModel().getSelectedItem();
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
				rightTemp = new Label("Temp:\t");
				rightBloodP = new Label("Blood Pressure:\t");
				rightHeight = new Label("Height:\t");
				rightWeight = new Label("Weight:\t");
			}
			recep.setAppt(newApp);
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