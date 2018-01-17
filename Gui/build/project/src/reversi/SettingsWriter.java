package reversi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/*
 * Settings writer gets reversi settings strings and writes them to file
 */
public class SettingsWriter {
	private FileWriter file = null;
	private BufferedWriter br = null;
	
	//constructor
	public SettingsWriter() {
			try {
				//opening file to write to
				this.file = new FileWriter("settings.txt", false);
				this.br = new BufferedWriter(this.file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	};
	
	//this functions writes given settings to file
	public void updateSettings(int size, String color1, String color2) {
		//format for writing in file
		String boardSize = "board size: " + size;
		String firstColor = "first color: " + color1;
		String secondColor = "second color: " + color2;
		try {
			br.write(boardSize);
			br.newLine();
			
			br.write(firstColor);
			br.newLine();
			
			br.write(secondColor);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {//closing file if writing failed
				if (this.br != null)
					this.br.close();
				if (this.file != null)
					this.file.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
