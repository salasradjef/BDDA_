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
		Catalog catalog = Catalog.getInstance();
		BufferManager BM = BufferManager.getInstance();
		catalog.Finish();
		BM.FlushAll();
		
	}
	
	public void ProcessCommand(String ch) {
		
	}
	
}
