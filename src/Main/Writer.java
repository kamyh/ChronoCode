package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	public Writer(String path) {
		this.path = path;
		initFile();
	}

	private void initFile() {
		try {

			File file = new File(this.path);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("");
			bw.close();

			System.out.println("Logs Created");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String entry) {
		try {

			// TODO open files explorer
			File file = new File(this.path);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(entry + "\n");
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String path;

	public static void main(String[] args) throws Exception {
		Writer w = new Writer("./test.txt");
		w.write("entry");

	}

}
