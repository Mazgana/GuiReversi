package reversi;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SettingsController implements Initializable{
	@FXML
	private ChoiceBox<String> firstPlayerChoice;

	@FXML
	private ChoiceBox<String> sizeChoice;
	
	@FXML
	private GridPane root;
	
	@FXML
	private Button saveBtn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		root.setPrefWidth(500);
		root.setPrefHeight(500);
		ArrayList<String> players = new ArrayList<String>();
		players.add("white");
		players.add("black");
		ObservableList<String> list = FXCollections.observableArrayList(players);
		firstPlayerChoice.setItems(list);
		
		ArrayList<String> sizes = new ArrayList<String>(Arrays.asList("4x4","6x6","8x8","10x10","12x12", "14x14", "16x16", "18x18", "20x20"));
		ObservableList<String> sizeList = FXCollections.observableArrayList(sizes);
		sizeChoice.setItems(sizeList);
		
		saveBtn.setOnAction(event-> {
		    Stage stage = (Stage) saveBtn.getScene().getWindow();
		    stage.close();
		});
	}
}