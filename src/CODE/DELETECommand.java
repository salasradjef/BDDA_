package CODE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DELETECommand {
    private String request;
    private RelationInfo rel;
    private ArrayList<Record> recordsToDelete;



    public DELETECommand(String ch) {
        String nomDeRelation = ch.split(" ")[2];
        Catalog catalog = Catalog.getInstance();
        this.rel = catalog.getRelationWithName(nomDeRelation);
        String[] afterWhere = ch.split("WHERE");
        String conversion = "SELECTMONO * FROM " + nomDeRelation + afterWhere[1];
        this.request = conversion;


    }

    public void Execute() throws IOException {
        if(this.rel != null){
            SELECTMONOCommand select = new SELECTMONOCommand(this.request);
            this.recordsToDelete = select.GetRecordsConditions();
            System.out.println("le nombre de record a supp = "+ this.recordsToDelete.size());
        }
    }


    public void deleteRecord(Record record){


    }
}

