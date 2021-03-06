package reversi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import reversi.Board;
import reversi.GameFlow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

public class FXMLController implements Initializable{

	@FXML
	private Pane root;
	
	private GameFlow game;
	
	private int size;
	private String firstColor;
	private String secondColor;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//getting game's settings from the settings file
		setGameSettings();
		
		this.game = new GameFlow(this.size, this.firstColor, this.secondColor);
		
		//the screen's settings
		this.game.setPrefWidth(500);
		this.game.setPrefHeight(500);
		root.getChildren().add(this.game);
		
		//setting the game's screen background
		Image im = new Image(getClass().getResource("/back2.jpeg").toExternalForm());
		Background bg = new Background(new BackgroundImage(im,
		        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          new BackgroundSize(root.getPrefWidth(), root.getPrefHeight(), false, false, false, true)));
		
		root.setBackground(bg);
		
		try {
			this.game.run(this.firstColor, this.secondColor);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setGameSettings() {
		FileReader settingsFile = null;
		BufferedReader br = null;
		
		try {
			settingsFile = new FileReader("settings.txt");
			br = new BufferedReader(settingsFile);
			
			String temp = br.readLine();
			if (temp == null) { //the file is empty
				SettingsWriter sw = new SettingsWriter();
				//writing to the file the default values
				sw.updateSettings(Board.DEFAULT_LEGTH, "Black", "White");
				temp = br.readLine();
			} 
			
			//board size
			this.size = Integer.valueOf(temp.substring(12, temp.length()));
				
			//first player color
			temp = br.readLine();
			this.firstColor = temp.substring(13, temp.length());
				
			//second player color
			temp = br.readLine();
			this.secondColor = temp.substring(14, temp.length());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally { //closing the file
			try {
				if (br != null)
					br.close();
				if (settingsFile != null)
					settingsFile.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}