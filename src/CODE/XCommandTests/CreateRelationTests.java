package CODE.XCommandTests;

import CODE.Catalog;
import CODE.CreateRelationCommand;

import java.io.IOException;

public class CreateRelationTests {
    public static void main(String[] args) throws IOException {

        String cmd = "CREATE RELATION R (A:int,B:string2)";
        try{
            CreateRelationCommand create = new CreateRelationCommand(cmd);
            create.Execute();
            Catalog catalog = Catalog.getInstance();
            System.out.println("Size of catalog = " + catalog.getRel_info().size()); // Resultat attendu 1

        }catch (Exception e){
            e.getMessage();
        }
    }
}
