package CODE;



import java.io.IOException;

public class CreateRelationCommand {
    private String relName;
    private int nbr_col;
    private ColInfo[] col;

    public CreateRelationCommand(String command){
        /*Tested*/
        String[] infos = command.split(" ");
        this.relName = infos[2]; //set le nom de la relation
        String  between = infos[3].split("[\\(\\)]")[1];
        String[] colInfo = between.split(",");
        this.nbr_col = colInfo.length; //set le nombre de colonne


        col = new ColInfo[this.nbr_col];
        for(int i=0;i<this.nbr_col;i++){
            String[] colu = colInfo[i].split(":");
            col[i] = new ColInfo(colu[0],colu[1]);
        }

    }


    public void Execute() throws IOException {
        /*All tests passed*/
        FileManager fm = FileManager.getInstance();
        Catalog catalog = Catalog.getInstance();
        PageId headerPage = fm.createHeaderPage();

        RelationInfo rel = new RelationInfo(this.relName,this.nbr_col,this.col,headerPage);
        catalog.AddRelation(rel);
        System.out.println("La relation "+ this.relName+" à bien été ajoutée");
    }
}
