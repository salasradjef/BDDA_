import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Catalog implements Serializable {

	private static Catalog INSTANCE;
	private ArrayList<RelationInfo> rel_info;
	private int cmp;
	public static Catalog getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Catalog();
			
		}
		
		return INSTANCE;
	}
	
	private Catalog() {
		rel_info = new ArrayList<RelationInfo>();
	}
	
	private void AddRelation(RelationInfo rel) {
		rel_info.add(rel);
		cmp++;
	}
	
	
	public void Init() throws FileNotFoundException, IOException, ClassNotFoundException {
		String path = DBParams.DBPath + "\\Catalog.def";
		ObjectInputStream object = new ObjectInputStream(new FileInputStream(path));
		INSTANCE = (Catalog) object.readObject();
		object.close();
	}
	public void Finish() throws FileNotFoundException, IOException {
		String path = DBParams.DBPath + "\\Catalog.def";
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path));
		
		output.writeObject(Catalog.getInstance());
		output.close();
	}
	
}
