package CODE;



import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException  {
		
		DBParams.DBPath = args[0];
		DBParams.pageSize =4096;
		DBParams.maxPagesPerFile = 4; 
		DBParams.frameCount = 2;



		DBManager dbM = DBManager.getInstance();
		Scanner sc = new Scanner(System.in);
		dbM.Init();
		while(true){

			String cmd = sc.nextLine();
			if(cmd.equals("EXIT")){
				dbM.Finish();
				System.out.println("bye");
				break;
			}
			dbM.ProcessCommand(cmd);
		}





	}

}
