package miniSGBD;

import java.io.IOException;

public class BufferManagerTests {

	static void testGetPage(PageId page) throws IOException {
		BufferManager BM = BufferManager.getInstance();

		byte[] tb = new byte[DBParams.pageSize];

		tb = BM.getPage(page);
		
		System.out.println(tb[0]);



	}



	static void FlushAll() {

	}

	public static void main(String[] args) throws IOException {
		
		DBParams.DBPath = args[0];
		DBParams.pageSize = 4096;
		DBParams.frameCount = 2;
		
		byte[] tb = new byte[DBParams.pageSize];
		byte[] tb2 = new byte[DBParams.pageSize];
		byte[] tb3 = new byte[DBParams.pageSize];
		

		PageId page = new PageId(0, 0);
		PageId page2 = new PageId(0, 1);
		PageId page3 = new PageId(1, 2);

		
		BufferManager BM = BufferManager.getInstance();
		
		testGetPage(page);
		System.out.println("GetPage(page)");
		System.out.println("pinCount : case[0]  ==> "+BM.getBpool()[0].getPin_count());
		System.out.println("dirty : case[0] ==> "+BM.getBpool()[0].getDirty());
		System.out.println("getTempFree :case[0]  ==> "+BM.getBpool()[0].getTemps_free());
		System.out.println("fileId "+BM.getBpool()[0].getPID().getFileIdx()+"    idPage ==>"+ BM.getBpool()[0].getPID().getPageIdx());
		System.out.println();
		System.out.println("GetPage(page2)");
		testGetPage(page2);
		
		System.out.println("pinCount : case[1] ==> "+BM.getBpool()[1].getPin_count());
		System.out.println("dirty : case[1] ==> "+BM.getBpool()[1].getDirty());
		System.out.println("getTempFree :case[1]  ==> "+BM.getBpool()[1].getTemps_free());
		System.out.println("fileId :  ==>"+BM.getBpool()[1].getPID().getFileIdx()+"    idPage  ==>"+ BM.getBpool()[1].getPID().getPageIdx());
		System.out.println();
		
		System.out.println("FreePage(page2, 1)");
		
		//BM.FreePage(page2, 1);
		System.out.println("pinCount :  case[1] ==> "+BM.getBpool()[1].getPin_count());
		System.out.println("dirty : case[1]   ==> "+BM.getBpool()[1].getDirty());
		System.out.println("getTempFree :case[1]  ==> "+BM.getBpool()[1].getTemps_free());
		System.out.println("fileId :  ==> "+BM.getBpool()[1].getPID().getFileIdx()+"     idPage ==> "+ BM.getBpool()[1].getPID().getPageIdx());
		System.out.println();
		
		testGetPage(page3);
		System.out.println("page3");
		System.out.println("pinCount :  case[1] ==> "+BM.getBpool()[1].getPin_count());
		System.out.println("dirty : case[1]   ==> "+BM.getBpool()[1].getDirty());
		System.out.println("getTempFree :case[1]  ==> "+BM.getBpool()[1].getTemps_free());
		System.out.println("fileId :  ==> "+BM.getBpool()[1].getPID().getFileIdx()+"     idPage ==> "+ BM.getBpool()[1].getPID().getPageIdx());
		System.out.println();
		
		System.out.println("GetPage(page2)");
		System.out.println("pinCount :  case[1] ==> "+BM.getBpool()[1].getPin_count());
		System.out.println("dirty : case[1]   ==> "+BM.getBpool()[1].getDirty());
		System.out.println("getTempFree :case[1]  ==> "+BM.getBpool()[1].getTemps_free());
		System.out.println("fileId :  ==> "+BM.getBpool()[1].getPID().getFileIdx()+"     idPage ==> "+ BM.getBpool()[1].getPID().getPageIdx());
		System.out.println();
		
		BM.FreePage(page, 1);

	}
	

}
