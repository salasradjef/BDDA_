package CODE.Tests;

import CODE.*;

import java.io.IOException;

public class CatalogTests {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Catalog catalog = Catalog.getInstance();


        ColInfo colInfo = new ColInfo("math", "int");
        ColInfo colInfo2 = new ColInfo("prog", "int");
        ColInfo[] col = new ColInfo[2];
        col[0] = colInfo;
        col[1] = colInfo2;
        PageId headerPage = FileManager.getInstance().createHeaderPage();

        RelationInfo relInfo = new RelationInfo("Etudiant",2,col,headerPage);
        catalog.AddRelation(relInfo);
        catalog.Finish();

        catalog.Init();
        System.out.println("Le nombre de relation enregistre avant le finish = " + catalog.getRel_info().size());



    }


}
