package CODE;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Perso {

	public static void main(String[] args) {
		String toParse = "CREATE RELATION R (X:int,C2:float,BLA:string10)";
		String[] parsed = toParse.split(" ");
		/*for(int i = 0 ; i<parsed.length;i++){
			System.out.print(i);
			System.out.println(parsed[i]);
		}*/
		String  between = parsed[3].split("[\\(\\)]")[1];
		String[] data = between.split(",");
		String[] col = data[0].split(":");
		String[] col2 = data[1].split(":");

	}
	


}
