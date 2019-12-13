package screens;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sprint2.Appointment;
import sprint2.DatabaseDriver;
import sprint2.Doctor;
import sprint2.ManagementApp;
import sprint2.ManagementSystem;
import sprint2.Nurse;
import sprint2.Patient;
import sprint2.Person;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CreateAppointmentScreen extends Pane{
	private ManagementApp mApp;
	private ManagementSystem manSys;
	LoginScreen login;
	ComboBox<Patient> patientCombo;
	ComboBox<Doctor> doctorCombo;
	Patient pat;
	Doctor doc;
	LocalDate date;

	// Text fields for the Doctor screen. 
	private TextField txtPatient, txtDoctor, txtDate, txtTime, txtReason;

	public CreateAppointmentScreen() {}

	public CreateAppointmentScreen(ManagementApp stage, ManagementSystem manSys)
	{
		this.mApp = stage;
		this.manSys = manSys;
		createNodes();		
	}

	private void createNodes() {
		  if(getChildren() != null) 
			  getChildren().clear();
		
		Pane base = new Pane();
		BorderPane bp = buildAppBorders();
		base.getChildren().add(bp);
		VBox vb = buildAppCenter();
		vb.setAlignment(Pos.CENTER);
		bp.setCenter(vb);
		getChildren().addAll(base);	
	}

	private BorderPane buildAppBorders() {
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(160, 0, 0, 750));		
		return bp;
	}

	private VBox buildAppCenter() {
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().add(buildAppLabel());
		vb.getChildren().add(buildInformationLabel());
		vb.getChildren().add(buildPatient());
		vb.getChildren().add(buildDoctor());
		vb.getChildren().add(buildDate());
		vb.getChildren().add(buildTime());
		vb.getChildren().add(buildReason());
		vb.getChildren().add(buildAppButton());
		vb.getChildren().add(buildLogoutButton());
		vb.getChildren().add(buildBackButton());
		return vb;
	}

	private Label buildAppLabel() {
		Label lbl = new Label("Create Appointment");
		lbl.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		return lbl;
	}
	
	//Below methods are for inputVitals method
		private Label buildInformationLabel() {
			Label lblWelc = new Label("Patient Information");
			lblWelc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			return lblWelc;
		}
		
		private HBox buildPatient() {
			HBox hb = new HBox();
			patientCombo = new ComboBox<Patient>();
			ArrayList<Patient> patients = new ArrayList<>();
			for(Person p: manSys.people) {
				if(p instanceof Patient) {
					patients.add((Patient)p);
				}
			}
			patientCombo.setItems(FXCollections.observableArrayList(patients));
			patientCombo.setPromptText("Select a Patient");
			patientCombo.setOnAction(e ->{
				pat = patientCombo.getSelectionModel().getSelectedItem();
			});
			hb.getChildren().add(new Label("\tChoose Patient:\t"));
//			txtPatient = new TextField();
//			txtPatient.setPromptText("Enter Patient");
			hb.getChildren().add(patientCombo);
			return hb;
		}
		
		private HBox buildDoctor() {
			HBox hb = new HBox();
			doctorCombo = new ComboBox<Doctor>();
			ArrayList<Doctor> doctors = new ArrayList<>();
			for(Person d: manSys.people) {
				if(d instanceof Doctor) {
					doctors.add((Doctor)d);
				}
			}
			doctorCombo.setItems(FXCollections.observableArrayList(doctors));
			doctorCombo.setPromptText("Select a Doctor");
			doctorCombo.setOnAction(e ->{
				doc = doctorCombo.getSelectionModel().getSelectedItem();
			});
			hb.getChildren().add(new Label("\tChoose Doctor:\t"));
//			txtPatient = new TextField();
//			txtPatient.setPromptText("Enter Patient");
			hb.getChildren().add(doctorCombo);
			return hb;
		}
		
		private HBox buildDate() {
			HBox hb = new HBox();
			hb.getChildren().add(new Label("\tChoose Date:\t"));
			DatePicker d = new DatePicker(LocalDate.now());
			d.setOnAction( e ->{
				date = d.getValue();
			});
//			txtDate = new TextField();
//			txtDate.setPromptText("Enter Date");
			hb.getChildren().add(d);
			return hb;
		}
		
		private HBox buildTime() {
			HBox hb = new HBox();
			hb.getChildren().add(new Label("\tEnter Time:\t"));
			txtTime = new TextField();
			txtTime.setPromptText("Enter Time");
			hb.getChildren().add(txtTime);
			return hb;
		}
		
		private HBox buildReason() {
			HBox hb = new HBox();
			hb.getChildren().add(new Label("\tEnter Reason:\t"));
			txtReason = new TextField();
			txtReason.setPromptText("Enter Reason");
			hb.getChildren().add(txtReason);
			return hb;
		}
		
		private VBox buildAppButton() {
			VBox vBox = new VBox();
			vBox.setAlignment(Pos.CENTER);
			Button btn = new Button("Submit Appointment");
			Label lbl = new Label("");
			lbl.setTextFill(Color.web("#FF0000"));
			btn.setOnAction(e -> {
				Connection conn = null;
				String patientName,docName,apptReason;
				LocalDate apptDate;
				LocalTime apptTime;
				boolean isPatientEmpty = patientCombo.getSelectionModel().isEmpty();
				boolean isDoctorEmpty = doctorCombo.getSelectionModel().isEmpty();
				if(isPatientEmpty || isDoctorEmpty ||
					txtTime.getText().isEmpty() || txtReason.getText().isEmpty()){
					lbl.setText("Please fill in all fields"); 
				} 
				else {
					
//					apptDate = LocalDate.parse(txtDate.getText());
					apptTime = LocalTime.parse(txtTime.getCharacters());
					apptReason = txtReason.getText();
					try {
						conn = DatabaseDriver.getConnection();
						Statement stmt = conn.createStatement();
//						ResultSet rs = stmt.executeQuery("SELECT * FROM Patients WHERE Name='"+patientName+"'");
//						Patient pat =  (Patient) manSys.makePerson('p',rs);
//						ResultSet rs = stmt.executeQuery("SELECT * FROM Doctors WHERE Name='"+docName+"'");
//						Doctor doc = (Doctor) manSys.makePerson('d', rs);
						Appointment appt = new Appointment(pat, doc, date ,apptTime,apptReason, manSys);
						manSys.saveAppointment(appt);
//						txtPatient.clear();
//						txtDoctor.clear();
//						txtDate.clear();
//						txtTime.clear();
//						txtReason.clear();
						lbl.setText("Appointment successfully created!");
						conn.close();
						mApp.changeScene(new ReceptionistScreen(mApp, manSys));
					}
					catch (Exception e1) {
						e1.printStackTrace();
					}					
				}
				
			});
			vBox.getChildren().add(btn);
			vBox.getChildren().add(lbl);
			return vBox;
		}


	private HBox buildLogoutButton() {
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);
		Button btn = new Button("Log Out");
		btn.setOnAction(e -> {
			mApp.setPerson(null);
			mApp.loginPane();
		});
		hBox.getChildren().addAll(btn);
		return hBox;
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
	
	
}
