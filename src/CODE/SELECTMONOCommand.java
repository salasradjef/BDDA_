package CODE;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SELECTMONOCommand {
        private RelationInfo rel;
        private ArrayList<Record> all_records;
        private ArrayList<String> column;
        private ArrayList<String> values;
        private ArrayList<Integer> posOfOP;
        private String[] ops;
      

    public SELECTMONOCommand(String ch) throws IOException {
        String nomDeRelation = ch.split(" ")[3];
        Catalog catalog = Catalog.getInstance();
        this.rel = catalog.getRelationWithName(nomDeRelation);
        this.all_records = new ArrayList<>();

        //Conditions
        String afterWheres[] = ch.split("WHERE");

        if (afterWheres.length > 1) {
            ops = new String[]{"=", "<", ">", "<=", ">=", "<>"};
            int posOP = -1;
            String afterWhere = afterWheres[1];
            String[] condition = afterWhere.split("AND");
            this.column = new ArrayList<>();
            this.values = new ArrayList<>();
            this.posOfOP = new ArrayList<>();
            for(int i=0;i< condition.length;i++){
                for(int j=0;j<ops.length;j++) {
                    if(condition[i].contains(ops[j])){
                        posOP = j;
                        break;
                    }
                }
                    String[] columnANDvalues = condition[i].split(ops[posOP]);
                    posOfOP.add(posOP);
                    column.add(columnANDvalues[0]);
                    values.add(columnANDvalues[1]);


            }

        }
    }
    public void Execute() throws IOException {
        if(this.rel != null){
            FileManager FM = FileManager.getInstance();
            this.all_records= FM.getAllRecords(rel);

            if(this.column.size() > 0){
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


        ArrayList<Record> uCanShowThem = new ArrayList<>();
        for(int i=0;i<this.all_records.size();i++){
            String[] valuesOfRecord = this.all_records.get(i).getValues();
            String op = this.ops[this.posOfOP.get(i)];
            if(op.equals("=")){


            }else if(op.equals("<")){

            }else if(op.equals(">")){

            }else if(op.equals("<=")){

            }else if(op.equals(">=")){

            }else if(op.equals("<>")){

            }
            boolean hesGood = false;


        }

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
