package sprint2;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import screens.*;

public class ManagementApp extends Application {
	private ScreenHistory sceneHistory = new ScreenHistory();
	private Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
	Scene scene1;
	Pane mainPane;
	Stage mainStage;
	ManagementSystem manSys = new ManagementSystem();
	Person person;

	double stageX = bounds.getWidth();
	double stageY = bounds.getHeight();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			mainStage = primaryStage;
			mainStage.setMaximized(true);
			mainStage.setResizable(false);
			changeScene(new LoginScreen(this, manSys));

			  primaryStage.show();
			 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getStageX(){ return this.stageX; }
	public double getStageY(){ return this.stageY; }

	public void changeScene(Pane pane) {
		mainPane = pane;
		scene1 = new Scene(mainPane, stageX, stageY);
		sceneHistory.add(scene1);
		mainStage.setScene(scene1);
		mainStage.setMaximized(true);
		mainStage.setResizable(false);
		mainStage.show();
	}	

	public void backPane(){		
		
		scene1 = sceneHistory.getLastScreen();
		//scene1.createNodes();
		//sceneHistory.remove(sceneHistory.getList().get(sceneHistory.size() - 1));		
		  mainStage.setScene(scene1); 
		  mainStage.setMaximized(true);
		  mainStage.setResizable(false);
		  mainStage.show();

	}

	public void loginPane(){
		scene1 = sceneHistory.getLoginScreen();
		mainStage.setScene(scene1);
		mainStage.setMaximized(true);
		mainStage.setResizable(false);
		mainStage.show();		
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	public Person getPerson() {
		return person;
	}
}