package screens;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
import sprint2.Appointment;
import sprint2.AppointmentData;
import sprint2.ManagementApp;
import sprint2.ManagementSystem;
import sprint2.Nurse;
import sprint2.Person;
import sprint2.Receptionist;

public class ModifyAppointmentScreen extends Pane{
	private ManagementApp mApp;
	private ManagementSystem manSys;
	private Receptionist recep;
	private Label leftInfoName, leftInfoDOB, leftInfoAddress, leftInfoTitle;
	private Label rescheduleLabel, appIDReschedule, cancelLabel;
	private Label rightDoctor, rightReason, rightDate, rightTime, rightTemp, rightBloodP, rightHeight, rightWeight, rightPatient, rightAppID;
	private TextField appIDCancel, rescheduleTime, rescheduleDate;
	private  double stageX, stageY;
	private LocalDate date = LocalDate.now();
	
	public ModifyAppointmentScreen(ManagementApp stage, ManagementSystem manSys)
	{
		mApp = stage;
		this.manSys = manSys;
		this.recep = (Receptionist)manSys.getCurrentUser();
		this.stageX = mApp.getStageX();
		this.stageY = mApp.getStageY();
		createNodes();				
	}
	private void createNodes() {			
		if(getChildren() != null) 
			  getChildren().clear();
		
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
		leftInfoTitle = new Label("Modify Appointment:");
		leftInfoTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

		VBox left = new VBox(1.5, leftInfoTitle, leftInfoName, leftInfoDOB, leftInfoAddress);
		left.setPadding(new Insets(10, 10, 10, 10));
		return left; 
	}
	private VBox buildCenterInfo() {
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
		VBox rescheduleBox = buildReschedule();
		VBox cancelBox = buildCancel();
		VBox right = new VBox(1.5, rescheduleBox, cancelBox);
		right.setPadding(new Insets(10, 10, 10, 10));
		return right; 
	}
	private VBox buildReschedule() {
		rescheduleLabel = new Label("Reschedule");
		rescheduleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		Label outRescheduleLabel = new Label("");
		Label idLabel = new Label("Appointment ID: \t");
		idLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		Label timeLabel = new Label("Enter new Time: \t");
		Label dateLabel = new Label("Enter new Date: \t");
		outRescheduleLabel.setTextFill(Color.web("#FF0000"));
		appIDReschedule = new Label(recep.appt.getID());
		appIDReschedule.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		rescheduleTime = new TextField();
		rescheduleTime.setPromptText("Enter new Time: ");
		HBox rescheduleDate = buildDate();
		Button rescheduleBtn = new Button("Reschedule");
		rescheduleBtn.setOnAction(e -> {
			//String id = appIDReschedule.getText();
			Appointment app = recep.appt;
			LocalTime time;

			time = LocalTime.parse(rescheduleTime.getCharacters());
			rescheduleTime.clear();
//			for(Appointment app : manSys.calendar) {
//				if(app.getID().contentEquals(id)) {
					try {
						recep.rescheduleAppointment(app, date, time);
					} catch (Exception e1) {
						System.out.println("Couldn't reschedule appointment.");
						outRescheduleLabel.setTextFill(Color.web("#FF0000"));
						outRescheduleLabel.setText("Error: Could not reschedule the Appointment!");
					}
					outRescheduleLabel.setTextFill(Color.web("#7FFFD4"));
					createNodes();
					outRescheduleLabel.setText("Success!");
					//break;
				//}
					mApp.changeScene(new ReceptionistScreen(mApp, manSys)); 
			
		});
		HBox appBox = new HBox();
		HBox timeBox = new HBox();
		HBox dateBox = new HBox();
		appBox.getChildren().addAll(idLabel, appIDReschedule);
		timeBox.getChildren().addAll(timeLabel, rescheduleTime);
		dateBox.getChildren().addAll(dateLabel, rescheduleDate);
		VBox rescheduleBox = new VBox( appBox, rescheduleLabel, timeBox, dateBox, outRescheduleLabel, rescheduleBtn);
		//VBox rescheduleBox = new VBox();
		return rescheduleBox;
	}
	private HBox buildDate() {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("\tChoose Date:\t"));
		DatePicker d = new DatePicker(LocalDate.now());
		d.setOnAction( e ->{
			date = d.getValue();
		});
//		txtDate = new TextField();
//		txtDate.setPromptText("Enter Date");
		hb.getChildren().add(d);
		return hb;
	}
	
	private VBox buildCancel() {
		cancelLabel = new Label("\tCancellation");
		cancelLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		Label outCancelLabel = new Label("");
		outCancelLabel.setTextFill(Color.web("#FF0000"));

		Button cancelBtn = new Button("Remove Appointment");
		cancelBtn.setOnAction(e -> {
			Appointment app = recep.appt;
//			String id = appIDCancel.getText();
//			appIDCancel.clear();
//			for(Appointment app : manSys.calendar) {
//				if(app.getID().contentEquals(id)) {
					try {
						recep.cancelAppointment(app);
					} catch (Exception e1) {
						System.out.println("Error cancelling appointment");
						outCancelLabel.setTextFill(Color.web("#FF0000"));
						outCancelLabel.setText("\t\tError: Could not find the Appointment!");
					}
					outCancelLabel.setTextFill(Color.web("#7FFFD4"));
					createNodes();
					outCancelLabel.setText("Success!");
//					break;
					mApp.changeScene(new ReceptionistScreen(mApp, manSys));
//				}
				
//			}
		});
		HBox cBox = new HBox();
		cBox.getChildren().addAll(cancelBtn);
		VBox cancelBox = new VBox(cancelLabel, cBox, outCancelLabel);
		return cancelBox;
	}
	
	private VBox buildAppsPanel() {
		ListView<Appointment> appointments = new ListView<Appointment>();
		appointments.setItems(FXCollections.observableArrayList(manSys.calendar));
		appointments.setOnMouseClicked(e -> {
			Appointment newApp = appointments.getSelectionModel().getSelectedItem();
			recep.setAppt(newApp);
			rightAppID = new Label("Appointment ID:\t" + newApp.getID());
			rightDoctor = new Label("Doctor:\t" + newApp.getDoctor().getName());
			rightPatient = new Label("Patient:\t" + newApp.getPatient().getName());
			rightReason = new Label("Reason:\t" + newApp.getReason());
			rightDate = new Label("Date:\t" + newApp.getDate());
			rightTime = new Label("Time:\t" + newApp.getTime());
			rightTemp = new Label("Temp:\t");
			rightBloodP = new Label("Blood Pressure:\t");
			rightHeight = new Label("Height:\t");
			rightWeight = new Label("Weight:\t");
			if(newApp.getAppData() != null) {
				AppointmentData appData = newApp.getAppData();
				rightTemp.setText("Temp:\t"+ String.valueOf(appData.getTemp()));
				rightBloodP.setText("Blood Pressure: "+ appData.getPBloodP());
				rightHeight.setText("Height:\t" + String.valueOf(appData.getHeight()));
				rightWeight.setText("Weight:\t" + String.valueOf(appData.getWeight()));
			}
			appIDReschedule.setText(recep.appt.getID());
		})	;	 
		VBox btnLogout = buildLogoutButton();
		HBox btnBack = buildBackButton();
		HBox btns = new HBox();
		btns.getChildren().addAll(btnBack, btnLogout);
		VBox apps = new VBox(1.5, appointments, btns);
		apps.setAlignment(Pos.CENTER);
		apps.setPrefWidth(stageX);
		apps.setPadding(new Insets(10, 100, 10, 100));
		return apps;
	}
	private HBox buildBackButton() {
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);
		Button btn = new Button("Back");
		btn.setOnAction(e -> {
			mApp.backPane(); 
		});
		hBox.getChildren().addAll(btn);
		return hBox;
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


