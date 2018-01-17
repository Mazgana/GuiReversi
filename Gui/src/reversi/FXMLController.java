package reversi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class FXMLController implements Initializable{

	@FXML
	private Pane root;
	
	private GameFlow game;
	
	private String opening;
	private int size;
	private String firstColor;
	private String secondColor;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//getting game's settings from the settings file
		setGameSettings();
		
		this.game = new GameFlow(this.opening, this.size, this.firstColor, this.secondColor);
		this.game.setPrefWidth(500);
		this.game.setPrefHeight(500);
		root.getChildren().add(this.game);
		
		try {
			this.game.run(this.firstColor, this.secondColor);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setGameSettings() {
		FileReader settingsFile = null;
		BufferedReader br = null;
		
		try {
			settingsFile = new FileReader("settings.txt");
			br = new BufferedReader(settingsFile);
			
			//Opening player
			String temp = br.readLine();
			this.opening = temp.substring(16, temp.length());
			
			//board size
			temp = br.readLine();
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
		} finally {
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