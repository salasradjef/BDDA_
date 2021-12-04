package CODE;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Perso {

	public static void main(String[] args) {
		ArrayList<String> a1 = new ArrayList<>();
		ArrayList<String> a2 = new ArrayList<>();


		a1.add("hey");
		a1.add("hello");


		a2.add("mybad");
		a2.addAll(a1);

		for(int i = 0;i<a2.size();i++){
			System.out.print(a2.get(i));
		}
	}
	


}
