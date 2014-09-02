package logic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.TreeSet;

public class Initializer {

	public InitObject init() {
		FileInputStream in = openFile();
		InitObject initObject = loadInitSetup(in);
		closeFile(in);
		return initObject;
	}

	// open file and return inputstream to this file
	private FileInputStream openFile() {
		String FILE = "input/input.txt";
		FileInputStream in = null;

		// opening file

		try {
			in = new FileInputStream(FILE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return in;
	}

	// close fileinputstream
	private void closeFile(FileInputStream in) {

		// closing inputstream

		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// read whole file, create hashmap and put all words to hash map
	private InitObject loadInitSetup(FileInputStream in) {

		// open bufferedreader
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		}

		InitObject init = new InitObject();

		String line;

		try {
			line = br.readLine();
			String[] dimensions = line.split("x");
			init.mapSizeX = Integer.parseInt(dimensions[0]);
			init.mapSizeY = Integer.parseInt(dimensions[1]);

			line = br.readLine();
			init.startPoint = Integer.parseInt(line);

			line = br.readLine();
			init.pocetHladanychPokladov = Integer.parseInt(line);

			line = br.readLine();
			String[] numbers = line.split(" ");

			init.poklady = new TreeSet<>();
			for (String number : numbers) {
				init.poklady.add(Integer.parseInt(number));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return init;

	}

}
