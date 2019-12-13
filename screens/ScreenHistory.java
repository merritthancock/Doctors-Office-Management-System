
package screens;
import javafx.scene.Scene;
import java.util.*;

public class ScreenHistory 
{
	//Used to store each pane
	private ArrayList<Scene> screenHistory = new ArrayList<>(); 
	
	public ScreenHistory(){}
	
	public void add(Scene s) { 
		screenHistory.add(s); 
	}
	
	public void remove(Scene s) { 
		screenHistory.remove(s); 
	}
	
	public int size() { 
		return screenHistory.size(); 
	}
	
	public void clear() { 
		screenHistory.clear(); 
	}
	
	public ArrayList<Scene> getList(){ return this.screenHistory; }
	
	public Scene getLastScreen(){
		Scene pane;
		int size = screenHistory.size();

		if(screenHistory.size() > 1)
		{
			pane = screenHistory.get(size - 2);
			//if(screenHistory.size() != 1)
				screenHistory.remove(size - 1);
		}
		else
			pane = screenHistory.get(0);
		return pane;				
	}
	
	public Scene getLoginScreen() {
		int size = screenHistory.size();
		for(int i = (size - 1); i > 0; i--) {
			screenHistory.remove(i);		
		}
		Scene pane = screenHistory.get(0);
		return pane;
	}
}
