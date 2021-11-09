package CODE;


import java.io.*;
public class Main {

	public static void main(String[] args) throws IOException  {
		
		DBParams.DBPath = args[0];
		DBParams.pageSize =4096;
		DBParams.maxPagesPerFile = 4; 
		DBParams.frameCount = 2;

		
	/*	byte[] tb = new byte[DBParams.pageSize];
		byte[] tb2 = new byte[DBParams.pageSize];
		byte[] tb3 = new byte[DBParams.pageSize];
		DiskManager disk = DiskManager.getInstance();
	
		
		PageId tst = disk.AllocPage();
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
	
		
		DiskManager disk = DiskManager.getInstance();
		PageId tst = disk.AllocPage();
		PageId tst2 = disk.AllocPage();
		System.out.println(tst.getPageIdx());
		disk.clean_all();
		
	}

}
