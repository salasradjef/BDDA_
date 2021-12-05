package CODE;

import java.nio.ByteBuffer;

public class Perso {

	public static void main(String args[]) {
		ByteBuffer buff = ByteBuffer.allocate(200);
		buff.putInt(2);
		buff.putInt(1);
		buff.putInt(0);
		buff.putInt(7);
		int z=-1;
		buff.position(0);
		for(int i=0;i<4;i++){
			z = buff.getInt();

			if(z == 0){
				buff.position(buff.position() - 4);
				buff.putInt(17);

			}
		}




		buff.position(0);
		for(int i = 0;i<4;i++){
			System.out.println(buff.getInt());
		}

	}
}
