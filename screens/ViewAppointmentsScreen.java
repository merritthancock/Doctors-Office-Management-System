/*
 * package screens;
 * 
 * import java.sql.Connection; import java.sql.ResultSet; import
 * java.sql.Statement; import java.time.LocalDate; import java.time.LocalTime;
 * import java.util.Comparator;
 * 
 * import javafx.collections.FXCollections; import javafx.geometry.Insets;
 * import javafx.geometry.Pos; import javafx.scene.control.Button; import
 * javafx.scene.control.ComboBox; import javafx.scene.control.Label; import
 * javafx.scene.control.ListView; import javafx.scene.control.TextField; import
 * javafx.scene.layout.BorderPane; import javafx.scene.layout.HBox; import
 * javafx.scene.layout.Pane; import javafx.scene.layout.VBox; import
 * javafx.scene.paint.Color; import javafx.scene.text.Font; import
 * javafx.scene.text.FontWeight; import sprint2.Appointment; import
 * sprint2.DatabaseDriver; import sprint2.Doctor; import sprint2.ManagementApp;
 * import sprint2.ManagementSystem; import sprint2.Nurse; import
 * sprint2.Patient; import sprint2.Person;
 * 
 * public class ViewAppointmentsScreen extends Pane{ private ManagementApp mApp;
 * private ManagementSystem manSys; LoginScreen login; Person user; private
 * double stageX, stageY;
 * 
 * // Text fields for the Doctor screen. private TextField txt;
 * 
 * public ViewAppointmentsScreen() {}
 * 
 * public ViewAppointmentsScreen(Person user, ManagementApp stage,
 * ManagementSystem manSys) { this.mApp = stage; this.manSys = manSys;
 * this.stageX = mApp.getStageX(); this.stageY = mApp.getStageY(); this.user =
 * manSys.getCurrentUser(); createNodes(); }
 * 
 * 
 * private void createNodes() {
 * 
 * if(getChildren() != null) getChildren().clear();
 * 
 * Pane base = new Pane(); BorderPane bp = buildAppBorders();
 * base.getChildren().add(bp); VBox vb = buildAppCenter();
 * vb.setAlignment(Pos.CENTER); bp.setCenter(vb); getChildren().addAll(base);
 * 
 * 
 * //HBox info = buildInfoPanel(); VBox apps = buildAppsPanel();
 * 
 * VBox base = new VBox(apps); Pane view = new Pane(base);
 * getChildren().add(view); }
 * 
 * private BorderPane buildAppBorders() { BorderPane bp = new BorderPane();
 * bp.setPadding(new Insets(160, 0, 0, 750)); return bp; }
 * 
 * private VBox buildAppCenter() { VBox vb = new VBox(20);
 * vb.setAlignment(Pos.CENTER); vb.getChildren().add(buildAppLabel());
 * vb.getChildren().add(buildOptions());
 * vb.getChildren().add(buildLogoutButton());
 * vb.getChildren().add(buildBackButton());
 * 
 * //vb.getChildren().add(testButton());
 * 
 * return vb; }
 * 
 * private VBox buildAppsPanel() { ListView<Appointment> appointments = new
 * ListView<Appointment>();
 * appointments.setItems(FXCollections.observableArrayList(user.getAppointments(
 * ))); appointments.setOnMouseClicked(e -> { Appointment newApp =
 * appointments.getSelectionModel().getSelectedItem(); }) ; HBox btnLogout =
 * buildLogoutButton(); HBox btnBack = buildBackButton();
 * 
 * VBox apps = new VBox(1.5, appointments, btnBack, btnLogout);
 * apps.setAlignment(Pos.CENTER); apps.setPrefWidth(stageX); apps.setPadding(new
 * Insets(10, 100, 10, 100)); return apps; }
 * 
 * private Label buildAppLabel() { Label lbl = new
 * Label("Viewing all appointments"); lbl.setFont(Font.font("Verdana",
 * FontWeight.BOLD, 30)); return lbl; }
 * 
 * private HBox buildOptions() { HBox hb = new HBox();
 * hb.setAlignment(Pos.CENTER); hb.setPadding(new Insets(15, 12, 15, 12));
 * hb.setSpacing(15); //Button viewAppts = new Button("View Appointments");
 * Button createAppts = new Button("Create Appointment");
 * 
 * 
 * viewAppts.setOnAction( e -> { //new screen to view all appointments
 * 
 * });
 * 
 * 
 * createAppts.setOnAction( e -> { mApp.changeScene(new
 * CreateAppointmentScreen(mApp, manSys)); });
 * 
 * hb.getChildren().addAll(createAppts); return hb; }
 * 
 * private HBox buildLogoutButton() { HBox hBox = new HBox(20);
 * hBox.setAlignment(Pos.CENTER); Button btn = new Button("Log Out");
 * btn.setOnAction(e -> { mApp.setPerson(null); mApp.loginPane(); });
 * 
 * hBox.getChildren().addAll(btn); return hBox; }
 * 
 * private HBox buildBackButton() { HBox hBox = new HBox(20);
 * hBox.setAlignment(Pos.CENTER); Button btn = new Button("Back");
 * btn.setOnAction(e -> { mApp.backPane(); });
 * 
 * hBox.getChildren().addAll(btn); return hBox; } }
 */