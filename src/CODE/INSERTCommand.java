package CODE;


import java.io.IOException;


public class INSERTCommand {
    private RelationInfo rel;
    private Record record;

    public INSERTCommand(String ch){
        String[] parsing = ch.split(" ");
        Catalog cat = Catalog.getInstance();
        this.rel = cat.getRelationWithName(parsing[2]); // Charger la relation depuis le catalog
        if(rel.getName() != null) {
            String[] prevalues = parsing[4].split("[\\(\\)]");
            String[] values = prevalues[1].split(","); // Lire les values passe via le CLI
            this.record = new Record(this.rel,values);
        }
    }

    public void Execute() throws IOException {
        if(this.rel != null){
            FileManager FM = FileManager.getInstance();
            Rid rid =FM.InsertRecordIntoRelation(rel,this.record);
            System.out.println("Le record a bien ete ajoutae");
        }else {
            System.err.println("La relation demande n'existe pas ");
        }

        }

        public RelationInfo getRelation(){return this.rel;}
        public Record getRecord(){return this.record;}
}
