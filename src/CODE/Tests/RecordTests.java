package CODE.Tests;

import CODE.ColInfo;
import CODE.FileManager;
import CODE.PageId;
import CODE.Record;
import CODE.RelationInfo;

import java.io.IOException;
import java.nio.ByteBuffer;

public class RecordTests {

    public static void main(String[] args) throws IOException {
        ColInfo colInfo = new ColInfo("math", "int");
        ColInfo colInfo2 = new ColInfo("prog", "int");
        ColInfo[] col = new ColInfo[2];
        col[0] = colInfo;
        col[1] = colInfo2;
        PageId headerPage = FileManager.getInstance().createHeaderPage();

        RelationInfo relInfo = new RelationInfo("Etudiant",2,col,headerPage);

        String[] values = {"1","2"};
        Record rec1 = new Record(relInfo,values);

        /*Test writeToBuffer*/

        ByteBuffer buff = ByteBuffer.allocate(20); // 20 juste pour le test sur un record
        rec1.writeToBuffer(buff,0); // Ecriture dans le buffer les valeurs du record
        /*Lecture dans le meme buffer*/
        buff.position(0);
        System.out.println(buff.getInt()); // Resultat attendu 1
        System.out.println(buff.getInt()); // Resultat attendu 2





        /*Test readFromBuffer*/
        ByteBuffer buff2 = ByteBuffer.allocate(20); //Ecrire deux valeurs dans un buffer
        buff2.position(0);


        buff2.putInt(3);
        buff2.putInt(4);
        rec1.readFromBuffer(buff2,0);

        System.out.println("Values du records" + rec1.getValues()[0]); // Resultat attendu 3
        System.out.println("Values du records" + rec1.getValues()[1]); // Resultat attendu 4










    }
}
