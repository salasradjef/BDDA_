package CODE;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Perso {

	public static void main(String args[]) {

		ByteBuffer tst = ByteBuffer.allocate(60);
		String[] tmp = {"a","b"};
		tst.position(0);
		tst.putInt(12);
		tst.putChar(tmp[0].charAt(0));tst.putChar(tmp[1].charAt(0));
		tst.putInt(1);


		tst.position(0);
		System.out.println(tst.getInt());
		System.out.println(tst.getChar());
		System.out.println(tst.getChar());
		System.out.println(tst.getInt());

	}

}
