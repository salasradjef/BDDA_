package CODE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class SELECTMONOCommand {
        private RelationInfo rel;
        private ArrayList<Record> all_records;
        private ArrayList<String> column; //column that i got from CLI
        private ArrayList<String> values; //values that i got from CLI
        private ArrayList<Integer> posOfOP; //position of the operation in the ops array
        private String[] ops;
        private  boolean ok = true; //control boolean


    public SELECTMONOCommand(String ch) {
        String nomDeRelation = ch.split(" ")[3];
        Catalog catalog = Catalog.getInstance();
        this.rel = catalog.getRelationWithName(nomDeRelation);
        this.all_records = new ArrayList<>();

        //Conditions
        String afterWheres[] = ch.split("WHERE");

        if(!(afterWheres.length >20)) {
            if (afterWheres.length > 1) {
                ops = new String[]{"<=", ">=", "=", "<", ">", "<>"};
                int posOP = -1;
                String afterWhere = afterWheres[1];
                String[] condition = afterWhere.split("AND");
                this.column = new ArrayList<>();
                this.values = new ArrayList<>();
                this.posOfOP = new ArrayList<>();
                for (int i = 0; i < condition.length; i++) {
                    for (int j = 0; j < ops.length; j++) {
                        String tmp = condition[i].strip();
                        if (tmp.contains(ops[j])) {
                            posOP = j;
                            break;
                        }
                    }
                    String[] columnANDvalues = condition[i].strip().split(ops[posOP]);
                    posOfOP.add(posOP);
                    column.add(columnANDvalues[0]);
                    values.add(columnANDvalues[1]);
                }

            }
        }else{
            this.ok =false;
        }
    }




    /*
    * Methode qui permet d'executer la selection
    * boolean print permet de savoir si il faut afficher sur le terminal le resultat de la selection ou pas
    * */
    public void Execute(boolean print) throws IOException {
        if(this.rel != null){
           if(this.ok){
                FileManager FM = FileManager.getInstance();
                this.all_records= FM.getAllRecords(rel);
                ArrayList<Record> tmp = null;
                if(this.column != null){
                   tmp = GetRecordsConditions();
                }
                if(print){
                    print(tmp);
                }
           }else {
               System.out.println("Erreur");
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




    //methode qui permet juste d'afficher une liste de records
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



    //Methode qui permet de recuperer les records selon certaines conditions
    public ArrayList<Record> GetRecordsConditions(){
        ColInfo[] columnTypes;
        //Pour chaque record on compare
        ArrayList<Record> uCanShowThem = new ArrayList<>();
        for(int i=0;i<this.all_records.size();i++){


            boolean[] allGood = new boolean[this.column.size()];
            Arrays.fill(allGood, Boolean.FALSE);
            columnTypes = this.all_records.get(i).getRelInfo().getCol();

            String[] valuesOfRecord = this.all_records.get(i).getValues();
            for(int j =0;j<column.size();j++){
                //this.values are the values(CLI) that i have to compare to ValuesOfRecord(valuesOfRecord ==> values of my record)
                int ID_column_In_Values = getIDofColumn(column.get(j));
                String columnType = columnTypes[ID_column_In_Values].getCol_type();
                String[] pw = columnType.split("string");
                String op = this.ops[this.posOfOP.get(j)];
                if(columnType.equals("int")){

                    if(op.equals("=")){
                        if(Integer.valueOf(valuesOfRecord[ID_column_In_Values]) == Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }
                    }else if(op.equals("<")){
                        if(Integer.valueOf(valuesOfRecord[ID_column_In_Values]) < Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }
                    }else if(op.equals(">")){
                        if(Integer.valueOf(valuesOfRecord[ID_column_In_Values]) > Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals("<=")){
                        if(Integer.valueOf(valuesOfRecord[ID_column_In_Values]) <= Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals(">=")){
                        if(Integer.valueOf(valuesOfRecord[ID_column_In_Values]) >= Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals("<>")){
                        if(Integer.valueOf(valuesOfRecord[ID_column_In_Values]) != Integer.valueOf(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }

                }else if(columnType.equals("float")){
                    if(op.equals("=")){
                        if(Float.parseFloat(valuesOfRecord[ID_column_In_Values]) == Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }


                    }else if(op.equals("<")){
                        if(Float.parseFloat(valuesOfRecord[ID_column_In_Values]) < Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals(">")){
                        if(Float.parseFloat(valuesOfRecord[ID_column_In_Values]) > Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals("<=")){
                        if(Float.parseFloat(valuesOfRecord[ID_column_In_Values]) <= Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals(">=")){
                        if(Float.parseFloat(valuesOfRecord[ID_column_In_Values]) >= Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }else if(op.equals("<>")){
                        if(Float.parseFloat(valuesOfRecord[ID_column_In_Values]) != Float.parseFloat(this.values.get(j).strip()) ){
                            allGood[j] = true;
                        }

                    }

                }else if(columnType.contains("string")){
                    if(op.equals("=")){
                        if(valuesOfRecord[ID_column_In_Values].equals(this.values.get(j).strip())){
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

    //Methode qui permet de retrouver l'id(position) d'un column dans une relation en fonction de son
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
