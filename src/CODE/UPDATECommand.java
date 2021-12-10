package CODE;

import java.io.IOException;
import java.util.ArrayList;

public class UPDATECommand {
    private String request;
    private RelationInfo rel;
    private ArrayList<Record> recordsToUpdate;
    private ArrayList<String> column;
    private ArrayList<String> values;
    private ArrayList<Integer> postOfOp;
    private String[] ops;




    public UPDATECommand(String ch){
        String nameOfRelation = ch.split(" ")[1];
        Catalog catalog = Catalog.getInstance();
        this.rel = catalog.getRelationWithName(nameOfRelation);
        String[] afterWhere = ch.split("WHERE");
        this.request = "SELECTMONO * FROM" + nameOfRelation + " WHERE " + afterWhere[1];
        String afterSet = ch.split("SET")[1];
        String ColumnsToEdit = afterSet.split("WHERE")[0];

        ops = new String[]{"<=", ">=","=","<", ">", "<>"};
        int posOP = -1;



    }

    public void Execute() throws IOException {
        if(this.rel != null){
            SELECTMONOCommand select = new SELECTMONOCommand(this.request);
            select.Execute(false);
            this.recordsToUpdate = select.GetRecordsConditions();



        }


    }
}
