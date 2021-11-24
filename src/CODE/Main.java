package CODE;


import java.io.*;
import java.nio.ByteBuffer;
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
	
		
		
		FileManager a = FileManager.getInstance();
		/*DiskManager disk = DiskManager.getInstance();
		BufferManager BM = BufferManager.getInstance();
		
		PageId page = disk.AllocPage();
		PageId page2 = disk.AllocPage();
		
		byte[] bufferPage1 = BM.getPage(page);
		ByteBuffer buffer1 = ByteBuffer.wrap(bufferPage1);		
		
		byte[] buffPage2 = BM.getPage(page2);
		ByteBuffer buffer2 = ByteBuffer.wrap(buffPage2);
		
		
		
		a.writePageIdToPageBuffer(page,buffer2,true);
		
		*/
		PageId headerPage = a.createHeaderPage();
		
		
	}

}
