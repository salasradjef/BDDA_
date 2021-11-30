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

		
	/*	byte[] tb = new byte[DBParams.pageSize];
		byte[] tb2 = new byte[DBParams.pageSize];
		byte[] tb3 = new byte[DBParams.pageSize];
		DiskManager disk = DiskManager.getInstance();
	
		
		//PageId tst = disk.AllocPage();
		PageId page = new PageId(0,0);
		PageId page2 = new PageId(0,1);
		PageId page3 = new PageId(0,2);
		
		
		
		
		BufferManager BM = BufferManager.getInstance();
		tb = BM.getPage(page);
		tb2 = BM.getPage(page2);
		tb3 = BM.getPage(page3);
		
		System.out.println(tb[0]);
		System.out.println(tb2[0]);
		System.out.print(tb3[0]);*/
	
		
		
		/*FileManager a = FileManager.getInstance();
		DiskManager disk = DiskManager.getInstance();
		BufferManager BM = BufferManager.getInstance();
		
		/*PageId page = disk.AllocPage();
		PageId page2 = disk.AllocPage();
		
		byte[] bufferPage1 = BM.getPage(page);
		ByteBuffer buffer1 = ByteBuffer.wrap(bufferPage1);		
		
		byte[] buffPage2 = BM.getPage(page2);
		ByteBuffer buffer2 = ByteBuffer.wrap(buffPage2);
		
		
		
		a.writePageIdToPageBuffer(page,buffer2,true);
		
		*/
		
		
		/*Test IsNotFull*/
		/*
		ColInfo colInfo = new ColInfo("math", "int");
		ColInfo colInfo2 = new ColInfo("prog", "int");
		ColInfo colInfo3 = new ColInfo("mention", "String");
		ColInfo[] col = new ColInfo[3];
		col[0] = colInfo;
		col[1] = colInfo;
		col[2] = colInfo;
		
		PageId Page = disk.AllocPage();
		RelationInfo relInfo = new RelationInfo("etudiant", 3, col,Page);
		ByteBuffer page = a.byteToBuffer(BM.getPage(Page)); 
		page.position(16);
		for(int i = 0 ;i<relInfo.getSlotCount();i++) {
			page.putInt(1);
	
		}

		
		BM.FreePage(Page, 1);
		
		System.out.println(a.isNotFull(Page, relInfo));*/
		DBManager dbM = DBManager.getInstance();
		Scanner sc = new Scanner(System.in);
		while(true){
			String cmd = sc.nextLine();
			if(cmd.equals("QUIT")){
				dbM.Finish();
				System.out.println("bye");
			}
			dbM.ProcessCommand(cmd);
		}
		
		
		
	}

}
