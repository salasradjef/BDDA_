package miniSGBD;

import java.io.IOException;

public class Main {
		static void affichage(Frame[] f) {
		BufferManager BM = BufferManager.getInstance();
		System.out.println("========== bufferPool case 1 ==========");
		System.out.println("fileId " + BM.getBpool()[0].getPID().getFileIdx() + "    idPage ==>"
				+ BM.getBpool()[0].getPID().getPageIdx());
		System.out.println("pinCount :  " + BM.getBpool()[0].getPin_count());
		System.out.println("dirty : " + BM.getBpool()[0].getDirty());
		System.out.println("tempFree : " + BM.getBpool()[0].getTemps_free());

		System.out.println("========== bufferPool case 2 ==========");
		System.out.println("fileId " + BM.getBpool()[1].getPID().getFileIdx() + "    idPage ==>"
				+ BM.getBpool()[1].getPID().getPageIdx());
		System.out.println("pinCount :  " + BM.getBpool()[1].getPin_count());
		System.out.println("dirty : " + BM.getBpool()[1].getDirty());
		System.out.println("tempFree : " + BM.getBpool()[1].getTemps_free());
		
		System.out.println();
	}

	public static void main(String[] args) throws IOException {

		DBParams.DBPath = args[0];
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFile = 4;
		DBParams.frameCount = 2;

		DiskManager disk = DiskManager.getInstance();

		byte[] tb = new byte[DBParams.pageSize];
		byte[] tb2 = new byte[DBParams.pageSize];
		byte[] tb3 = new byte[DBParams.pageSize];

		PageId page =null;
		PageId page2 = null;
		PageId page3 = null;
		PageId page4 = null;
		PageId page5 = null;
		PageId page6 = null;

		tb[0] = 1;
		tb[1] = 2;
		tb[2] = 3;

		tb2[0] = 4;
		tb2[1] = 5;
		tb2[2] = 6;

		tb3[0] = 7;
		tb3[1] = 8;
		tb3[2] = 12;

		page=disk.AllocPage();
		page2=disk.AllocPage();
		page3=disk.AllocPage();
		page4=disk.AllocPage();
		page5=disk.AllocPage();
		page6=disk.AllocPage();
		disk.WritePage(page, tb);
		disk.WritePage(page2, tb2);
		disk.WritePage(page3, tb3);

		System.out.println("ttssssst   "+page2.getPageIdx()+"   "+page2.getFileIdx());
		System.out.println("ttssssst   "+page3.getPageIdx()+"   "+page3.getFileIdx());
		byte[] tb4 = new byte[DBParams.pageSize];
		byte[] tb5 = new byte[DBParams.pageSize];
		byte[] tb6 = new byte[DBParams.pageSize];

		disk.ReadPage(page, tb4);
		disk.ReadPage(page2, tb5);
		disk.ReadPage(page3, tb6);
		System.out.println(tb4[0]);
		System.out.println(tb5[2]);
		System.out.println(tb6[1]);

		 //disk.clean_all();

		BufferManager BM = BufferManager.getInstance();

		byte[] tb7 = new byte[DBParams.pageSize];
		byte[] tb8 = new byte[DBParams.pageSize];
		byte[] tb9 = new byte[DBParams.pageSize];


		tb7 = BM.getPage(page);
		System.out.println("page 2 :");
		tb8 = BM.getPage(page2);
		affichage(BM.getBpool());
		BM.FreePage(page2, 1);
		System.out.println("free page 2 :");
		affichage(BM.getBpool());
		System.out.println("page 3 :");
		tb9 = BM.getPage(page3);
		affichage(BM.getBpool());

		System.out.println(tb9[0]);

		// tb = BM.GetPage(page);
		// tb2 = BM.GetPage(page2);
		// tb3 = BM.GetPage(page3);
		// System.out.println(tb2[0]);
		// BM.FreePage(page2, 0);
		// tb3 = BM.GetPage(page3);

	}
}
