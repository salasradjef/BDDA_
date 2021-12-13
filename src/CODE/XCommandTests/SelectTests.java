package CODE.XCommandTests;

import CODE.CreateRelationCommand;
import CODE.INSERTCommand;
import CODE.SELECTMONOCommand;

import java.io.IOException;

public class SelectTests {

    public static void main(String[] args) throws IOException {

        /*Cree une relation*/
        String cmd = "CREATE RELATION R (A:int,B:string2)";
        CreateRelationCommand create = new CreateRelationCommand(cmd);
        create.Execute();

        /*Inserer des records*/
        INSERTCommand insert = new INSERTCommand("INSERT INTO R RECORD (1,aa)");
        insert.Execute();
        INSERTCommand insert2 = new INSERTCommand("INSERT INTO R RECORD (2,bb)");
        insert2.Execute();

        /*Afficher les records*/
        SELECTMONOCommand select = new SELECTMONOCommand("SELECTMONO * FROM R"); // selectione tous les records d'une relation
        select.Execute(true);


        SELECTMONOCommand select2 = new SELECTMONOCommand("SELECTMONO * FROM R WHERE a=1"); // selectione tous les records avec condition
        select2.Execute(true);







    }
}
