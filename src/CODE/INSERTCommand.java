package CODE;


import java.io.IOException;


public class INSERTCommand {
    private RelationInfo rel;
    private Record record;

    public INSERTCommand(String ch){
        /*Tested*/
        String[] parsing = ch.split(" ");
        Catalog cat = Catalog.getInstance();
        this.rel = cat.getRelationWithName(parsing[2]);
        if(rel.getName() != null) {
            String[] prevalues = parsing[4].split("[\\(\\)]");
            String[] values = prevalues[1].split(",");
            this.record = new Record(this.rel,values);
        }
    }

    public void Execute() throws IOException {
        if(this.rel != null){
            FileManager FM = FileManager.getInstance();
            Rid rid =FM.InsertRecordIntoRelation(rel,this.record);
            System.out.println("Le record à bien ete ajoutée");
        }else {
            System.err.println("La relation demandé n'existe pas ");
        }

        }

        public RelationInfo getRelation(){return this.rel;}
        public Record getRecord(){return this.record;}
}
