package reversi;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/*
 * This class controls the settings scene 
 */
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
	
	private int boardSize;
	private String color1;
	private String color2;
	
	//initializing setting with all controls (3 choice boxes for settings and a save button).
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//setting layout/size
		root.setPrefWidth(600);
		root.setPrefHeight(600);
		
		//setting background with image
		Image im = new Image(getClass().getResource("back.png").toExternalForm());
		Background bg = new Background(new BackgroundImage(im,
		        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          new BackgroundSize(root.getPrefWidth(), root.getPrefHeight(), false, false, false, true)));
		root.setBackground(bg);
		
		//adding choice box for board sizes
		ArrayList<String> sizes = new ArrayList<String>(Arrays.asList("4x4","6x6","8x8","10x10","12x12", "14x14", "16x16", "18x18", "20x20"));
		ObservableList<String> sizeList = FXCollections.observableArrayList(sizes);
		sizeChoice.setValue("8x8");
		sizeChoice.setItems(sizeList);

		//adding choice  boxes for choosing both players colors
		ArrayList<String> colors = new ArrayList<String>(Arrays.asList("Black", "Blue", "Brown", "Green", "Grey", "Light green", "Light purple", "Orange", "Purple", "Red", "White", "Yellow"));
		ObservableList<String> colorList = FXCollections.observableArrayList(colors);
		colChoice1.setValue("Black");
		colChoice1.setItems(colorList);
		colChoice2.setValue("White");
		colChoice2.setItems(colorList);
		
		//setting default values
		this.boardSize = Board.DEFAULT_LEGTH;
		this.color1 = "Black";
		this.color2 = "White";
		
		SettingsWriter sw = new SettingsWriter();
		
		//save event when save button pressed will call on updating file
		saveBtn.setOnAction(event-> {
			//getting user choices from boxes
			String size = sizeChoice.getValue();
			this.boardSize = Integer.valueOf(size.split("x")[0]);
			this.color1 = colChoice1.getValue();
			this.color2 = colChoice2.getValue();
			
			//alerting when user chooses same color for both players
			if (color1.equals(color2)) {
				Alert alert = new Alert(AlertType.NONE, "OOPS! the two players can't play the same color..\n Chose another color.");
				alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
				alert.showAndWait();
				return;
			}
			//updating file
			sw.updateSettings(boardSize, color1, color2);
		    Stage stage = (Stage) saveBtn.getScene().getWindow();
		    stage.close();
		});
	}
}
