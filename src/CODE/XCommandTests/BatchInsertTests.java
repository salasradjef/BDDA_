package CODE.XCommandTests;

import CODE.BATCHINSERTCommand;
import CODE.CreateRelationCommand;

import java.io.IOException;

public class BatchInsertTests {

    public static void main(String[] args) throws IOException {
        /*Cree une relation*/
        String cmd = "CREATE RELATION S (C1:string2,C2:int,C3:string4,C4:float,C5:string5,C6:int,C7:int,C8:int)";
        CreateRelationCommand create = new CreateRelationCommand(cmd);
        create.Execute();

        BATCHINSERTCommand batchinsert = new BATCHINSERTCommand("BATCHINSERT INTO S FROM FILE S1.csv");
        batchinsert.Execute();



    }
}
