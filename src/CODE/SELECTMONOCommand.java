package CODE;

import java.io.IOException;
import java.util.ArrayList;

public class SELECTMONOCommand {
        private RelationInfo rel;
        private ArrayList<Record> all_records;

    public SELECTMONOCommand(String ch){
        String nomDeRelation = ch.split(" ")[3];
        Catalog catalog = Catalog.getInstance();
        this.rel = catalog.getRelationWithName(nomDeRelation);
        this.all_records = new ArrayList<>();
    }

    public void Execute() throws IOException {
        if(this.rel != null){
            FileManager FM = FileManager.getInstance();
            this.all_records = FM.getAllRecords(rel);
            showRecordsValues();


        }else {
            System.err.println("La Relation demandé n'existe pas");
        }
    }

    public void showRecordsValues(){
        System.out.print("Total de records = " + this.all_records.size());
        for(int i=0;i<this.all_records.size();i++){
            String[] values = this.all_records.get(i).getValues();
            System.out.print(i + ")" + "Values = ");
            for(int j=0;j< values.length;j++){
                System.out.print(values[j] + " : ");
            }
            System.out.println();
        }
    }
}
