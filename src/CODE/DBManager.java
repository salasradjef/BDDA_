package CODE;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DBManager {

private static DBManager INSTANCE;
	
	public static DBManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new DBManager();
		}
		return INSTANCE;
	}
	
	public void Init() throws FileNotFoundException, ClassNotFoundException, IOException {
		Catalog catalog = Catalog.getInstance();
		catalog.Init();
	}
	
	public void Finish() throws FileNotFoundException, IOException {
		try{
			Catalog catalog = Catalog.getInstance();
			BufferManager BM = BufferManager.getInstance();

			catalog.Finish();
			BM.FlushAll();
		}catch(IOException e ){
			System.err.println("Fichier introuvable");
		}

		
	}
	
	public void ProcessCommand(String ch) throws IOException {
		String parsing = ch.split(" ")[0];
		switch (parsing){
			case "CREATE":
				CreateRelationCommand inst = new CreateRelationCommand(ch);
				inst.Execute();
				break;
			case "DROPDB":
				DROPDBCommand drop = new DROPDBCommand();
				drop.Execute();
				break;
			case "INSERT":
				INSERTCommand insert = new INSERTCommand(ch);
				insert.Execute();

				break;
			case "BATCHINSERT":
				BATCHINSERTCommand batchInsert = new BATCHINSERTCommand(ch);
				batchInsert.Execute();
				System.out.println("Tu veux la commande BATCHINSERT?");
				break;
			case "SELECTMONO":
				SELECTMONOCommand select = new SELECTMONOCommand(ch);
				select.Execute(true);
				break;
			case "DELETE":
				System.out.println("Tu veux la commande DELETE");
				break;
			default:
				System.err.println("La commande passé n'existe pas :)");
				break;
		}
	}
	
}
