package CODE;


import javax.management.relation.Relation;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException  {
		
		DBParams.DBPath = args[0];
		DBParams.pageSize =4096;
		DBParams.maxPagesPerFile = 4; 
		DBParams.frameCount = 2;


	/*	DBManager dbM = DBManager.getInstance();
		Scanner sc = new Scanner(System.in);
		Catalog cat = Catalog.getInstance();

		//dbM.Init();

		while(true){
			String cmd = sc.nextLine();
			if(cmd.equals("EXIT")){
				dbM.Finish();
				System.out.println("bye");
				break;
			}
			dbM.ProcessCommand(cmd);
		}*/
		Catalog cat = Catalog.getInstance();
		DiskManager disk = DiskManager.getInstance();
		FileManager FM = FileManager.getInstance();
		BufferManager BM = BufferManager.getInstance();
		PageId headerPage = FM.createHeaderPage(); //Creation d'une headerPage
		ColInfo[] col = new ColInfo[2];
		col[0] = new ColInfo("A","int");
		col[1] = new ColInfo("B","int");
		RelationInfo rel = new RelationInfo("R",2,col,headerPage);
		cat.AddRelation(rel);
		String[] values = {"1", "2"};
		Record record = new Record(rel,values);
		String [] values2 = {"3","4"};
		Record record2 = new Record(rel,values2);



		Rid rid =FM.InsertRecordIntoRelation(rel,record);
		Rid rid2 = FM.InsertRecordIntoRelation(rel,record2);
		String cmd = "SELECTMONO * FROM R WHERE A=1 AND B=2";
		SELECTMONOCommand a = new SELECTMONOCommand(cmd);
		a.Execute();
		ArrayList<Record> listOfRecords = new ArrayList<>();
		listOfRecords = FM.getAllRecords(rel);
		/*System.out.print(listOfRecords.get(0).getValues()[0]);
		System.out.println(listOfRecords.get(0).getValues()[1]);
		System.out.print(listOfRecords.get(1).getValues()[0]);
		System.out.println(listOfRecords.get(1).getValues()[1]);*/


		//cat.Finish();*/


		
	}

}
