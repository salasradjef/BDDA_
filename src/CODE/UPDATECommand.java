package CODE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class UPDATECommand {
    private String request;
    private RelationInfo rel;
    private ArrayList<Record> recordsToUpdate;
    private ArrayList<String> column;
    private ArrayList<String> values;





    public UPDATECommand(String ch){
        String nameOfRelation = ch.split(" ")[1];
        Catalog catalog = Catalog.getInstance();
        this.rel = catalog.getRelationWithName(nameOfRelation);
        String[] afterWhere = ch.split("WHERE");
        this.request = "SELECTMONO * FROM " + nameOfRelation + " WHERE " + afterWhere[1];
        String afterSet = ch.split("SET")[1];
        String ColumnsToEdit = afterSet.split("WHERE")[0];
        String [] conditions = ColumnsToEdit.split(",");
        this.column = new ArrayList<>();
        this.values = new ArrayList<>();

        for(int i=0;i<conditions.length;i++){
            String[] columnANDvalue = conditions[i].split("=");
            column.add(columnANDvalue[0]);
            values.add(columnANDvalue[1]);
        }

    }

    public void Execute() throws IOException {
        if(this.rel != null){
            SELECTMONOCommand select = new SELECTMONOCommand(this.request);
            select.Execute(false);
            this.recordsToUpdate = select.GetRecordsConditions();
            FileManager FM = FileManager.getInstance();
            for(int i=0;i<this.recordsToUpdate.size();i++){
                String values[] = new String[this.rel.getNbr_col()];
                String actualValues[]  = this.recordsToUpdate.get(i).getValues();
                Arrays.fill(values,null);
                for(int j=0;j<column.size();j++){
                    int posOfColum = getIDofColumn(column.get(j));
                    values[posOfColum] = this.values.get(j);
                }

                for(int z=0;z<this.rel.getNbr_col();z++){
                    if(values[z] == null){
                        values[z] = actualValues[z];
                    }
                }

                FM.editRecord(this.recordsToUpdate.get(i),values);
                System.out.println("Le record a bien été modifié");
            }


        }

    }


    public int getIDofColumn(String column){
        ColInfo[] cl = rel.getCol();
        column = column.strip();
        for(int i = 0 ; i< cl.length;i++){
            String Colname = cl[i].getCol_name();
            if(Colname.equals(column)){
                return i;
            }
        }
        return -1;
    }
}
