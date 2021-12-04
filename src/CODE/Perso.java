package CODE;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Perso {

	public static void main(String[] args) {
		/*String fileName = "C:\\Users\\INFOTECH\\eclipse-workspace\\IDE_PROJET_RADJEF_NAITAIMER\\src\\S1.csv";
		List<String> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				records.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(records.get(0));*/

		String cmd ="BATCHINSERT INTO S FROM FILE S1.csv";
		String cmdss[] = cmd.split(" ");
		for(int i=0;i<cmdss.length;i++){
			System.out.print(i);
			System.out.println(cmdss[i]);
		}

	}
	


}
