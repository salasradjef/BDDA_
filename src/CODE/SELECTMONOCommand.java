package CODE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SELECTMONOCommand {
        private RelationInfo rel;
        private ArrayList<Record> all_records;
        private HashMap<String,String[]> conditions;

    public SELECTMONOCommand(String ch){
        String nomDeRelation = ch.split(" ")[3];
        Catalog catalog = Catalog.getInstance();
        this.rel = catalog.getRelationWithName(nomDeRelation);
        this.all_records = new ArrayList<>();
        conditions = new HashMap<>();
        //Conditions
        String afterWheres[] = ch.split("WHERE");
        if(afterWheres.length >1){
            String afterWhere = afterWheres[1];
            String[] condition = afterWhere.split("AND");
            String[] ops = {"=","<",">","<=",">=","<>"};
            for(int i = 0 ; i<condition.length;i++){
                for(int j=0;j<ops.length;j++){
                    if(condition[i].contains(ops[j])){
                        String[] colmn = condition[i].split(ops[j]);
                        conditions.put(ops[j],colmn);
                        break;
                    }
                }
            }

        }
    }

    public void Execute() throws IOException {
        if(this.rel != null){
            FileManager FM = FileManager.getInstance();
            this.all_records= FM.getAllRecords(rel);
            System.out.println(conditions.size());
            if(conditions.size() > 0){
                showRecordsValuesConditions();
            }else {
                showRecordsValues();
            }


        }else {
            System.err.println("La Relation demandé n'existe pas");
        }
    }


    public void showRecordsValues(){
        System.out.println("Total de records = " + this.all_records.size());
        String[] values;
        for(int i=0;i<this.all_records.size();i++){
             values= this.all_records.get(i).getValues();

            System.out.print(i + ")" + "Values = ");
            for(int j=0;j< values.length;j++){
                if(j == values.length -1){
                    System.out.print(values[j] + ".");

                }else {
                    System.out.print(values[j] + " : ");
                }
            }
            System.out.println();
        }
    }

    public void showRecordsValuesConditions(){
        ArrayList<Record> tmp = new ArrayList<>();
        ArrayList<String> cond = new ArrayList<>();
        for ( String key : conditions.keySet() ) {
            cond.add(key);
        }
        for(int i=0;i<cond.size();i++){

            String[] params = this.conditions.get(cond.get(i));

        }
        System.out.println("Affichage avec conditions");
    }


    public int getIDofColumn(String column){
        ColInfo[] cl = rel.getCol();
        for(int i = 0 ; i< cl.length;i++){
            String Colname = cl[i].getCol_name();
            if(Colname.equals(column)){
                return i;
            }
        }
        return -1;
    }
}
