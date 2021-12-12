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


	/*
		Scanner sc = new Scanner(System.in);
		Catalog cat = Catalog.getInstance();

		*/
		/*
		Catalog cat = Catalog.getInstance();
		DiskManager disk = DiskManager.getInstance();
		FileManager FM = FileManager.getInstance();
		BufferManager BM = BufferManager.getInstance();
		PageId headerPage = FM.createHeaderPage(); //Creation d'une headerPage
		ColInfo[] col = new ColInfo[2];
		col[0] = new ColInfo("A","string2");
		//col[1] = new ColInfo("B","int");
		RelationInfo rel = new RelationInfo("R",1,col,headerPage);
		cat.AddRelation(rel);
		String[] values = {"aa"};
		Record record = new Record(rel,values);
		Rid rid =FM.InsertRecordIntoRelation(rel,record);*/
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

		/*String cmd1,cmd2,cmd3,cmd4,cmd5,cmd6,cmd7,cmd8,cmd9,cmd10;


		cmd2 = "CREATE RELATION R (C1:int,C2:string3,C3:int)";
		cmd3 = "INSERT INTO R RECORD (1,aab,2)";
		cmd4 = "INSERT INTO R RECORD (2,abc,2)";

		cmd6 = "SELECTMONO * FROM R";

		DROPDBCommand drop = new DROPDBCommand();
		drop.Execute();


		CreateRelationCommand create = new CreateRelationCommand(cmd2);
		create.Execute();

		INSERTCommand insert1 = new INSERTCommand(cmd3);
		insert1.Execute();
		SELECTMONOCommand select = new SELECTMONOCommand(cmd6);
		select.Execute(true);

		INSERTCommand insert2 = new INSERTCommand(cmd4);
		insert2.Execute();*/




		/*Catalog cat = Catalog.getInstance();
		DiskManager disk = DiskManager.getInstance();
		FileManager FM = FileManager.getInstance();
		BufferManager BM = BufferManager.getInstance();
		PageId headerPage = FM.createHeaderPage(); //Creation d'une headerPage
		ColInfo[] col = new ColInfo[2];
		col[0] = new ColInfo("A","string2");
		//col[1] = new ColInfo("B","int");
		RelationInfo rel = new RelationInfo("R",1,col,headerPage);
		cat.AddRelation(rel);
		String[] values = {"aa"};
		Record record = new Record(rel,values);
		*//*String [] values2 = {"3","4"};
		Record record2 = new Record(rel,values2);

*/

  		/*Rid rid =FM.InsertRecordIntoRelation(rel,record);
	//	Rid rid2 = FM.InsertRecordIntoRelation(rel,record2);



	*//*	String cmd = "UPDATE R SET A=8 WHERE A>=1";
		UPDATECommand update = new UPDATECommand(cmd);
		update.Execute();
*//*
		String cmd2 = "SELECTMONO * FROM R";
		SELECTMONOCommand a = new SELECTMONOCommand(cmd2);
		a.Execute(true);
*/

		/*System.out.print(listOfRecords.get(0).getValues()[0]);
		System.out.println(listOfRecords.get(0).getValues()[1]);
		System.out.print(listOfRecords.get(1).getValues()[0]);
		System.out.println(listOfRecords.get(1).getValues()[1]);*/


		//cat.Finish();*/


		
	}

}
