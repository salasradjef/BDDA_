package CODE;

import java.io.File;
import java.nio.ByteBuffer;

public class Perso {

	public static void main(String[] args) {
		ByteBuffer buff = ByteBuffer.allocate(12);
		buff.position(0);
		
		
		buff.putInt(12);
		buff.putInt(0);
		buff.putInt(19);
		
		buff.position(0);
	
		
		
		
		int s; int z;
		for(int i = 0; i<buff.capacity() / 4 ; i++) {
			
			if(buff.getInt()== 0) {
				System.out.println(buff.getInt());
			}
		}
		
		/*buff.position(0);
		
		for(int i = 0 ; i< buff.capacity()/4;i++) {
			System.out.println(buff.getInt());
		}*/
	}
	


}
