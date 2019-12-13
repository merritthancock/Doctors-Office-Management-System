package screens;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.print.Doc;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import sprint2.Doctor;
import sprint2.ManagementApp;
import sprint2.Nurse;
import sprint2.Patient;
import sprint2.Person;

public class DoctorScreen extends Pane {

	private ManagementApp mApp;
	private ManagementSystem manSys;
	Doctor doctor;
	Patient pat;
	AppointmentData appData;
	Appointment appt;
	private VBox rightInfo;
	Appointment app;

	Label updateSuccess;
	// Text fields for the Doctor screen.
	private TextField txtInstruct, txtPrescribe, txtDiagnose;

	private Label rightAppID, rightDoctor, rightPatient, rightReason, rightDate, rightTime, 
	rightTemp, rightHeight, rightWeight, rightBloodP,
	rightDiagnosis, rightInstructions, rightPrescription;

	private double stageX, stageY;

	public DoctorScreen() {	}

	public DoctorScreen(ManagementApp stage, ManagementSystem manSys) {
		mApp = stage;
		this.manSys = manSys;
		this.doctor = (Doctor) manSys.getCurrentUser();

		this.stageX = mApp.getStageX();
		this.stageY = mApp.getStageY();

		Appointment app = manSys.calendar.get(0);

		createNodes();
	}

	private void createNodes() {

		HBox info = buildInfoPanel();

		VBox apps = buildAppsPanel();
		VBox base = new VBox(20);
		base.getChildren().addAll(info, apps);
		//base.setPadding(new Insets(10, 10, 10, 10));
		Pane view = new Pane(base);

		getChildren().add(view);
	}

	private HBox buildInfoPanel() {
		HBox hbox = new HBox(20);
		hbox.setPrefHeight(stageY / 3);

		VBox vboxLeft = buildDocCenter();
		vboxLeft.setPrefWidth(stageX / 2);
		hbox.getChildren().add(vboxLeft);

		rightInfo = buildRightInfo(app);
		//farRightInfo = buildDocTaskInfo(appData);
		hbox.getChildren().addAll(rightInfo);

		return hbox;
	}

	private VBox buildDocCenter() {
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().add(buildDocLabel());
		vb.getChildren().add(buildInstruct());
		vb.getChildren().add(buildInstructButton());
		vb.getChildren().add(buildPrescribe());
		vb.getChildren().add(buildPrescribeButton());
		vb.getChildren().add(buildDiagnose());
		vb.getChildren().add(buildDiagnoseButton());

		return vb;
	}

	private Label buildDocLabel() {
		Label lblWelc = new Label("Welcome, Dr. " + manSys.getCurrentUser().getName());
		lblWelc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		lblWelc.setAlignment(Pos.CENTER);
		return lblWelc;
	}

	private VBox buildRightInfo(Appointment app) {
		app = manSys.calendar.get(0);
		VBox vBox = new VBox(20);

		doctor.setAppt(app);

		Label rightApp = new Label("Appointment Details:");
		rightApp.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

		rightAppID = new Label("Appointment ID:\t\t" + app.getID());
		rightAppID.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

		rightDoctor = new Label("Doctor:\t\t" + app.getDoctor().getName());
		rightPatient = new Label("Patient:\t\t" + app.getPatient().getName());
		rightReason = new Label("Reason:\t\t" + app.getReason());
		rightDate = new Label("Date:\t\t" + app.getDate());
		rightTime = new Label("Time:\t\t" + app.getTime());

		rightTemp = new Label("");
		rightBloodP = new Label("");
		rightHeight = new Label("");
		rightWeight = new Label("");


		rightDiagnosis = new Label(""); 
		rightInstructions = new Label("");
		rightPrescription = new Label("");


		if(app.getAppData() != null) {
			AppointmentData appData = app.getAppData();

			if(appData.getHeight() != 0) {
				rightTemp.setText("Temp:\t\t" + appData.getTemp() + "");
				rightBloodP.setText("Blood Pressure: "+ appData.getPBloodP());
				rightHeight.setText("Height:\t\t" + String.valueOf(appData.getHeight()));
				rightWeight.setText("Weight:\t\t" + String.valueOf(appData.getWeight()));	
			}

			//vBox.getChildren().add(buildDocTaskInfo(appData));


			if(appData.getInstructions() != null) {
				rightInstructions.setText("Instructions:\t\t" + appData.getInstructions() + ""); 
			}
			if(appData.getDiagnosis() != null) {
				rightDiagnosis.setText("Diagnosis:\t\t" + appData.getDiagnosis() + ""); 
			}
			if(appData.getPrescription() != null) {
				rightPrescription.setText("Prescription:\t\t" + appData.getPrescription() + ""); 
			}		
		}

		VBox right = new VBox(1.5, rightApp, rightAppID, rightDoctor, rightPatient, rightReason, rightDate, rightTime, 
				rightTemp, rightBloodP, rightHeight, rightWeight,
				rightInstructions, rightDiagnosis, rightPrescription);
		right.setPadding(new Insets(10, 10, 10, 10));
		return right;
	}

	private VBox buildAppsPanel() {
		ListView<Appointment> appointments = new ListView<Appointment>();

		ArrayList<Appointment> futureApps = new ArrayList<>();
		for (Appointment a : manSys.calendar) {
			if (a.getDoctor().getName().equals(manSys.getCurrentUser().getName())) {
				if (a.getDate().compareTo(LocalDate.now()) != -1)
					futureApps.add(a);
			}
		}
		appointments.setItems(FXCollections.observableArrayList(futureApps));
		appointments.setOnMouseClicked(e -> {
			Appointment newApp = appointments.getSelectionModel().getSelectedItem();
			doctor.setAppt(newApp);
			updateSuccess.setVisible(false);

			rightAppID.setText("Appointment ID:\t\t" + newApp.getID());
			rightDoctor.setText("Doctor:\t\t" + newApp.getDoctor().getName());
			rightPatient.setText("Patient:\t\t" + newApp.getPatient().getName());
			rightReason.setText("Reason:\t\t" + newApp.getReason());
			rightDate.setText("Date:\t\t" + newApp.getDate());
			rightTime.setText("Time:\t\t" + newApp.getTime());

			if(newApp.getAppData() != null) {
				AppointmentData appData = newApp.getAppData();

				if(appData.getHeight() != 0) {
					rightTemp.setText("Temp:\t\t" + appData.getTemp() + "");
					rightBloodP.setText("Blood Pressure: "+ appData.getPBloodP());
					rightHeight.setText("Height:\t\t" + String.valueOf(appData.getHeight()));
					rightWeight.setText("Weight:\t\t" + String.valueOf(appData.getWeight()));	
				}else {
					rightTemp.setText("");
					rightBloodP.setText("");
					rightHeight.setText("");
					rightWeight.setText("");
				}


				if(appData.getInstructions() != null) {
					rightInstructions.setText("Instructions:\t\t" + appData.getInstructions() + "");
				}else
					rightInstructions.setText("");

				if(appData.getDiagnosis() != null) {
					rightDiagnosis.setText("Diagnosis:\t\t" + appData.getDiagnosis() + "");
				}else
					rightDiagnosis.setText("");

				if(appData.getPrescription() != null) {			
					rightPrescription.setText("Prescription:\t\t" + appData.getPrescription() + "");
				}else
					rightPrescription.setText("");

			}

		});			


		HBox hBoxBottomButtons = createAppBtn();
		hBoxBottomButtons.getChildren().add(buildLogoutButton());
		VBox apps = new VBox(1.5, appointments, hBoxBottomButtons);
		apps.setAlignment(Pos.CENTER);
		apps.setPrefWidth(stageX);
		apps.setPadding(new Insets(10, 100, 10, 100));
		return apps;
	}

	private HBox createAppBtn() {

		HBox hBox = new HBox(20);
		hBox.setPadding(new Insets(20, 0, 0, 20));
		hBox.setAlignment(Pos.CENTER);
		Button createAppts = new Button("Create Appointment");
		hBox.getChildren().add(createAppts);

		createAppts.setOnAction(e -> {
			// new screen to create an appointment
			mApp.changeScene(new CreateAppointmentScreen(mApp, manSys));
		});
		return hBox;
	}

	private HBox buildInstruct() {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("\tInstructions:\t\t"));
		txtInstruct = new TextField();
		txtInstruct.setPromptText("Enter Instructions");
		hb.getChildren().add(txtInstruct);
		hb.setAlignment(Pos.CENTER);
		return hb;
	}

	private VBox buildInstructButton() {
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		Button btn = new Button("Submit Instructions");
		Label lbl = new Label("");
		lbl.setTextFill(Color.web("#FF0000"));

		btn.setOnAction( e -> {
			//doctor.instruct(appt, txtInstruct.getText());
			String instructions;
			String id = doctor.appt.getID().toString();
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

			instructions = txtInstruct.getText().toString();

			try {
				doctor.instruct(doctor.appt, instructions);
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
			txtInstruct.clear();
			updateSuccess.setVisible(true);
		});
		updateSuccess = new Label("Operation Successful!");
		updateSuccess.setTextFill(Color.web("#FF0000"));

		vBox.getChildren().add(btn);
		vBox.getChildren().add(lbl);

		updateSuccess.setVisible(false);

		return vBox;
	}

	private HBox buildPrescribe() {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("\tPrescribe:\t\t\t"));
		txtPrescribe = new TextField();
		txtPrescribe.setPromptText("Enter Prescription");
		hb.getChildren().add(txtPrescribe);
		hb.setAlignment(Pos.CENTER);
		return hb;
	}

	private VBox buildPrescribeButton() {
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		Button btn = new Button("Set Prescription");
		Label lbl = new Label("");
		lbl.setTextFill(Color.web("#FF0000"));

		btn.setOnAction( e -> {
			//doctor.instruct(appt, txtInstruct.getText());
			String prescription;
			String id = doctor.appt.getID().toString();
			System.out.println(id);
			AppointmentData appData = null;
			updateSuccess = new Label("Operation Successful!");
			updateSuccess.setVisible(false);

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

			prescription = txtPrescribe.getText().toString();

			try {
				doctor.prescribe(doctor.appt, prescription);
				updateSuccess.setVisible(true);
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
			txtPrescribe.clear();
		});

		updateSuccess.setTextFill(Color.web("#FF0000"));

		vBox.getChildren().addAll(btn, updateSuccess);
		vBox.getChildren().add(lbl);

		updateSuccess.setVisible(false);

		return vBox;
	}

	private HBox buildDiagnose() {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("\tDiagnose:\t\t\t"));
		txtDiagnose = new TextField();
		txtDiagnose.setPromptText("Enter Diagnosis");
		hb.getChildren().add(txtDiagnose);
		hb.setAlignment(Pos.CENTER);
		return hb;
	}

	private VBox buildDiagnoseButton() {
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		Button btn = new Button("Set Diagnosis");
		Label lbl = new Label("");
		lbl.setTextFill(Color.web("#FF0000"));

		btn.setOnAction( e -> {
			//doctor.instruct(appt, txtInstruct.getText());
			String diagnosis;
			String id = doctor.appt.getID().toString();
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

			diagnosis = txtDiagnose.getText().toString();

			try {
				doctor.diagnose(doctor.appt, diagnosis);
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
			txtDiagnose.clear();
			updateSuccess.setVisible(true);
		});
		updateSuccess = new Label("Operation Successful!");
		updateSuccess.setTextFill(Color.web("#FF0000"));

		vBox.getChildren().addAll(btn, updateSuccess);
		vBox.getChildren().add(lbl);

		updateSuccess.setVisible(false);

		return vBox;
	}

	private Button buildLogoutButton() {

		Button btn = new Button("Log Out");

		btn.setOnAction(e -> {
			mApp.setPerson(null);
			mApp.loginPane();
		});
		// hBox.getChildren().add(btn);
		return btn;
	}
}
