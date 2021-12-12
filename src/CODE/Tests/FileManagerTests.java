package CODE.Tests;

import CODE.*;

import java.io.IOException;
import java.nio.ByteBuffer;

public class FileManagerTests {

    public void testCreateHeaderPage() throws IOException {

        FileManager a = FileManager.getInstance();
        PageId headerPage = a.createHeaderPage();
        BufferManager BM = BufferManager.getInstance();
        ByteBuffer headerBuffer = a.byteToBuffer(BM.getPage(headerPage));
        PageId first = a.readPageIdFromPageBuffer(headerBuffer,true);
        PageId second = a.readPageIdFromPageBuffer(headerBuffer,false);

        System.out.println(headerPage.toString());
        System.out.println(first.toString());
        System.out.print(second.toString());
    }

    public void testAddDataPage() throws IOException {

        BufferManager BM = BufferManager.getInstance();
        FileManager a = FileManager.getInstance();
        PageId headerPage = a.createHeaderPage();

        ColInfo colInfo = new ColInfo("math", "int");
        ColInfo colInfo2 = new ColInfo("prog", "int");
        ColInfo colInfo3 = new ColInfo("mention", "String");
        ColInfo[] col = new ColInfo[3];
        col[0] = colInfo;
        col[1] = colInfo2;
        col[2] = colInfo3;
        RelationInfo relInfo = new RelationInfo("etudiant", 3, col,headerPage);
        PageId dataPage = a.addDataPage(relInfo);


        ByteBuffer headerBuffer = a.byteToBuffer(BM.getPage(headerPage));
        PageId first = a.readPageIdFromPageBuffer(headerBuffer,true);
        PageId second = a.readPageIdFromPageBuffer(headerBuffer,false);
        BM.FreePage(headerPage,0);
        System.out.println(headerPage.toString());
        System.out.println(first.toString());
        System.out.print(second.toString());
        System.out.println("Data page = " + dataPage);
    }


    public void testGetFreePage() throws IOException {

        BufferManager BM = BufferManager.getInstance();
        FileManager a = FileManager.getInstance();
        PageId headerPage = a.createHeaderPage();

        ColInfo colInfo = new ColInfo("math", "int");
        ColInfo colInfo2 = new ColInfo("prog", "int");
        ColInfo colInfo3 = new ColInfo("mention", "String");
        ColInfo[] col = new ColInfo[3];
        col[0] = colInfo;
        col[1] = colInfo2;
        col[2] = colInfo3;
        RelationInfo relInfo = new RelationInfo("etudiant", 3, col,headerPage);
        PageId dataPage = a.addDataPage(relInfo);


        ByteBuffer headerBuffer = a.byteToBuffer(BM.getPage(headerPage));
        PageId first = a.readPageIdFromPageBuffer(headerBuffer,true);
        PageId second = a.readPageIdFromPageBuffer(headerBuffer,false);
        BM.FreePage(headerPage,0);
        System.out.println(headerPage.toString());

        System.out.println("Data page = " + dataPage);
        PageId freePage = a.getFreeDataPageId(relInfo);
        System.out.println("Une page libre" + freePage.toString());
    }
}