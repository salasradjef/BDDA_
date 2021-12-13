package CODE.XCommandTests;
import CODE.CreateRelationCommand;
import CODE.INSERTCommand;

import java.io.IOException;

public class InsertTests {

    public static void main(String[] args) throws IOException {

        /*Cree une relation*/
        String cmd = "CREATE RELATION R (A:int,B:string2)";
        CreateRelationCommand create = new CreateRelationCommand(cmd);
        create.Execute();
        /*Inserer un record a une relation*/
        String cmd2 = "INSERT INTO R RECORD (1,cc)";
        INSERTCommand insert = new INSERTCommand(cmd2);
        insert.Execute();
    }
}
