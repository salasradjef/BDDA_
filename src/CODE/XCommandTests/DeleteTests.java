package CODE.XCommandTests;

import CODE.CreateRelationCommand;
import CODE.DELETECommand;
import CODE.INSERTCommand;
import CODE.SELECTMONOCommand;

import java.io.IOException;

public class DeleteTests {

    public static void main(String[] args) throws IOException {
        /*Cree une relation*/
        String cmd = "CREATE RELATION R (A:int,B:string2)";
        CreateRelationCommand create = new CreateRelationCommand(cmd);
        create.Execute();
        /*Inserer deux records a R*/
        String cmd2 = "INSERT INTO R RECORD (1,cc)";
        INSERTCommand insert = new INSERTCommand(cmd2);
        insert.Execute();
        String cmd3 = "INSERT INTO R RECORD (2,aa)";
        INSERTCommand insert2 = new INSERTCommand(cmd2);
        insert2.Execute();

        String cmd4 = "DELETE FROM R WHERE A=1";
        DELETECommand delete = new DELETECommand(cmd4);
        delete.Execute();


        String cmd5 = "SELECTMONO * FROM R";
        SELECTMONOCommand select = new SELECTMONOCommand(cmd);
        select.Execute(true); // Resultat attendu 1 tuple
    }
}
