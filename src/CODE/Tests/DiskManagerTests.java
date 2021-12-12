package CODE.Tests;

import CODE.DBParams;
import CODE.DiskManager;
import CODE.PageId;

import java.io.IOException;

public class DiskManagerTests {

    public static void main(String[] args) throws IOException {
        DiskManager disk = DiskManager.getInstance();
        PageId page = disk.AllocPage(); //Alouer une page
        byte[] buff = new byte[DBParams.pageSize];
        buff[0] = 1;
        buff[1] = 2;
        buff[2] = 3;

        disk.WritePage(page,buff); //ecriture de la page


        byte[] tmp = new byte[DBParams.pageSize];
        disk.ReadPage(page,tmp);
        System.out.println(tmp[0]); // Resultat attendu == 1
        System.out.println(tmp[1]); // Resultat attendu == 2
        System.out.println(tmp[2]); // Resultat attendu == 3

        disk.clean_all();
        System.out.println("Le dossier DB a été vidé");




    }
}
