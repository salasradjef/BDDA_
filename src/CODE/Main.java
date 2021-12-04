package CODE;


import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException  {
		
		DBParams.DBPath = args[0];
		DBParams.pageSize =4096;
		DBParams.maxPagesPerFile = 4; 
		DBParams.frameCount = 2;


		BufferManager BM = BufferManager.getInstance();
		FileManager a = FileManager.getInstance();
		PageId headerPage = a.createHeaderPage();

		ColInfo colInfo = new ColInfo("math", "String");
		ColInfo colInfo2 = new ColInfo("prog", "int");

		ColInfo[] col = new ColInfo[2];
		col[0] = colInfo;
		col[1] = colInfo2;
		String[] val = {"abc","2"};
		RelationInfo relInfo = new RelationInfo("etudiant", 2, col,headerPage);
		PageId dataPage = a.addDataPage(relInfo);
		Record rec = new Record(relInfo);
		rec.setValues(val);

		ByteBuffer headerBuffer = a.byteToBuffer(BM.getPage(headerPage));
		PageId first = a.readPageIdFromPageBuffer(headerBuffer,true);
		PageId second = a.readPageIdFromPageBuffer(headerBuffer,false);
		BM.FreePage(headerPage,0);
		System.out.println(headerPage.toString());

		System.out.println("Data page = " + dataPage);
		PageId freePage = a.getFreeDataPageId(relInfo);
		System.out.println("Une page libre" + freePage);


		/*DiskManager disk = DiskManager.getInstance();



		/*PageId page = disk.AllocPage();
		PageId page2 = disk.AllocPage();
		
		byte[] bufferPage1 = BM.getPage(page);
		ByteBuffer buffer1 = ByteBuffer.wrap(bufferPage1);		
		
		byte[] buffPage2 = BM.getPage(page2);
		ByteBuffer buffer2 = ByteBuffer.wrap(buffPage2);
		
		
		
		a.writePageIdToPageBuffer(page,buffer2,true);
		
		*/
		
		


		

	
		
		
		
	}

}
