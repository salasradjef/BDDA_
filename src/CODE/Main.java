package CODE;


import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException  {
		
		DBParams.DBPath = args[0];
		DBParams.pageSize =4096;
		DBParams.maxPagesPerFile = 4; 
		DBParams.frameCount = 2;


		DBManager dbM = DBManager.getInstance();
		Scanner sc = new Scanner(System.in);

		Catalog cat = Catalog.getInstance();

		//dbM.Init();

		while(true){
			String cmd = sc.nextLine();
			if(cmd.equals("QUIT")){
				dbM.Finish();
				System.out.println("bye");
				break;
			}
			dbM.ProcessCommand(cmd);
		}

		
		
		
	}

}
