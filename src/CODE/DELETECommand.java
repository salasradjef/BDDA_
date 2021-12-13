package CODE;

import java.io.File;
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
        this.rel = catalog.getRelationWithName(nomDeRelation); //Charger la relation depuis le catalog
        String[] afterWhere = ch.split("WHERE");
        String conversion = "SELECTMONO * FROM " + nomDeRelation +" WHERE" + afterWhere[1]; //Convertir en requette SELECT
        this.request = conversion;


    }

    public void Execute() throws IOException {
        if(this.rel != null){
            SELECTMONOCommand select = new SELECTMONOCommand(this.request);
            select.Execute(false);
            this.recordsToDelete = select.GetRecordsConditions(); //liste de records a supprime
            deleteRecord();
            System.out.println("Le nombre de records supprimé est de " + this.recordsToDelete.size());

            }
    }


    public void deleteRecord() throws IOException {
        for(int i = 0 ;i<this.recordsToDelete.size();i++){
            FileManager FM = FileManager.getInstance();
            FM.deleteRecord(this.recordsToDelete.get(i));
        }

    }
}

