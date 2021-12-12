package CODE.Tests;

import CODE.ColInfo;
import CODE.FileManager;
import CODE.PageId;
import CODE.RelationInfo;

import java.io.IOException;

public class RelationInfoTests {

    public static void main(String[] args) throws IOException {

        ColInfo colInfo = new ColInfo("math", "int");
        ColInfo colInfo2 = new ColInfo("prog", "int");
        ColInfo[] col = new ColInfo[2];
        col[0] = colInfo;
        col[1] = colInfo2;
        PageId headerPage = FileManager.getInstance().createHeaderPage();

        RelationInfo relInfo = new RelationInfo("Etudiant",2,col,headerPage);
        System.out.println("Record size = " + relInfo.getRecordSize());
        System.out.println("Number of slots = " + relInfo.getSlotCount());

    }
}
