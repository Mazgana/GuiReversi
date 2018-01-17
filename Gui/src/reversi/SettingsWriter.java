package reversi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsWriter {
	private FileWriter file = null;
	private BufferedWriter br = null;
	
	public SettingsWriter() {
			try {
				this.file = new FileWriter("settings.txt", false);
				this.br = new BufferedWriter(this.file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	};
	
	public void updateSettings(int size, String color1, String color2) {
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
			try {
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
