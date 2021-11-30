package CODE;



import java.io.IOException;

public class CreateRelationCommand {
    private String relName;
    private int nbr_col;
    private ColInfo[] col;

    public CreateRelationCommand(String command){

    }


    public void Execute() throws IOException {
        FileManager fm = FileManager.getInstance();
        Catalog catalog = Catalog.getInstance();
        PageId headerPage = fm.createHeaderPage();

        RelationInfo rel = new RelationInfo(this.relName,this.nbr_col,this.col,headerPage);
        catalog.AddRelation(rel);
    }
}
