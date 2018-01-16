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
	private ChoiceBox<String> colChoice1;

	@FXML
	private ChoiceBox<String> sizeChoice;
	
	@FXML
	private ChoiceBox<String> colChoice2;
	
	@FXML
	private GridPane root;
	
	@FXML
	private Button saveBtn;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		root.setPrefWidth(500);
		root.setPrefHeight(500);
		
		ArrayList<String> sizes = new ArrayList<String>(Arrays.asList("4x4","6x6","8x8","10x10","12x12", "14x14", "16x16", "18x18", "20x20"));
		ObservableList<String> sizeList = FXCollections.observableArrayList(sizes);
		sizeChoice.setValue("8x8");
		sizeChoice.setItems(sizeList);

		
		ArrayList<String> colors = new ArrayList<String>(Arrays.asList("red","blue","green","grey","black","white"));
		ObservableList<String> colorList = FXCollections.observableArrayList(colors);
		colChoice1.setValue("white");
		colChoice1.setItems(colorList);
		colChoice2.setValue("black");
		colChoice2.setItems(colorList);
		
		SettingsWriter sw = new SettingsWriter();
		
		saveBtn.setOnAction(event-> {
			String size = sizeChoice.getValue();
			String color1 = colChoice1.getValue();
			String color2 = colChoice2.getValue();
			if (color1.equals(color2)) {
// add message cannot have same color
				return;
			}
			sw.updateSettings(size, color1, color2);
		    Stage stage = (Stage) saveBtn.getScene().getWindow();
		    stage.close();
		});
	}
}
/*
 * TO_DO
 * adding default choice on bar? current?
 * adding color bars
 * checking colors are different' else alert and return
 * close option?
 * writing saved choices to  file
 * nice background and style:)
*/