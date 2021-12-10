package CODE;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class SELECTMONOCommand {
        private RelationInfo rel;
        private ArrayList<Record> all_records;
        private ArrayList<String> column;
        private ArrayList<String> values;
        private ArrayList<Integer> posOfOP;
        private String[] ops;
      

    public SELECTMONOCommand(String ch) {
        String nomDeRelation = ch.split(" ")[3];
        Catalog catalog = Catalog.getInstance();
        this.rel = catalog.getRelationWithName(nomDeRelation);
        this.all_records = new ArrayList<>();

        //Conditions
        String afterWheres[] = ch.split("WHERE");

        if (afterWheres.length > 1) {
            ops = new String[]{"<=", ">=","=","<", ">", "<>"};
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






    public void Execute(boolean print) throws IOException {
        if(this.rel != null){
            FileManager FM = FileManager.getInstance();
            this.all_records= FM.getAllRecords(rel);
            ArrayList<Record> tmp = new ArrayList<>();
            if(this.column.size() > 0){
                tmp = GetRecordsConditions();
            }
            if(print){
                print(tmp);
            }
        }else {
            System.err.println("La Relation demandé n'existe pas");
        }
    }

    public void print(ArrayList<Record> tmp){
        if(tmp == null){
            showRecordsValues(this.all_records);
        }else {
            showRecordsValues(tmp);
        }


    }


    public void showRecordsValues(ArrayList<Record> listOfRecord){
        System.out.println("Total de records = " + listOfRecord.size());

        for(int i=0;i<listOfRecord.size();i++){

        String[] values = listOfRecord.get(i).getValues();
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

    public ArrayList<Record> GetRecordsConditions(){
        ColInfo[] columnTypes;
        //Pour chaque record on va compare


        ArrayList<Record> uCanShowThem = new ArrayList<>();
        for(int i=0;i<this.all_records.size();i++){

            //boolean[] hesGood = false;
            boolean[] allGood = new boolean[this.column.size()];
            Arrays.fill(allGood, Boolean.FALSE);
            columnTypes = this.all_records.get(i).getRelInfo().getCol();

            String[] valuesOfRecord = this.all_records.get(i).getValues();
            for(int j =0;j<valuesOfRecord.length;j++){
                //this.values are the values(CLI) that i have to compare to ValuesOfRecord(valuesOfRecord ==> values of my record)
                int ID_column_In_Values = getIDofColumn(column.get(j));
                String columnType = columnTypes[ID_column_In_Values].getCol_type();
                String[] pw = columnType.split("string");
                String op = this.ops[this.posOfOP.get(j)];
                if(columnType.equals("int")){

                    if(op.equals("=")){
                        if(Integer.valueOf(valuesOfRecord[j]) == Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }
                    }else if(op.equals("<")){
                        if(Integer.valueOf(valuesOfRecord[j]) < Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }
                    }else if(op.equals(">")){
                        if(Integer.valueOf(valuesOfRecord[j]) > Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals("<=")){
                        if(Integer.valueOf(valuesOfRecord[j]) <= Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals(">=")){
                        if(Integer.valueOf(valuesOfRecord[j]) >= Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals("<>")){
                        if(Integer.valueOf(valuesOfRecord[j]) != Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }

                }else if(columnType.equals("float")){
                    if(op.equals("=")){
                        if(Float.parseFloat(valuesOfRecord[j]) == Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }


                    }else if(op.equals("<")){
                        if(Float.parseFloat(valuesOfRecord[j]) < Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals(">")){
                        if(Float.parseFloat(valuesOfRecord[j]) > Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals("<=")){
                        if(Float.parseFloat(valuesOfRecord[j]) <= Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals(">=")){
                        if(Float.parseFloat(valuesOfRecord[j]) >= Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals("<>")){
                        if(Float.parseFloat(valuesOfRecord[j]) != Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }

                }else if(pw[0].equals("string")){
                    if(op.equals("=")){
                        if(valuesOfRecord[j].equals(this.values.get(j).strip())){
                            allGood[j] = true;
                        }

                    }else{
                        System.out.println("Une erreur de syntaxe");
                    }

                }

            }

            boolean itsOkay = true;
            for(int z=0;z<this.column.size();z++){
                if(!allGood[z]){
                    itsOkay = false;
                }
            }
            if(itsOkay){
                uCanShowThem.add(this.all_records.get(i));

            }

        }
        return uCanShowThem;

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
