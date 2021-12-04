package CODE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	
	public void AddRelation(RelationInfo rel) {
		rel_info.add(rel);
		cmp++;
	}
	
	
	public void Init() throws FileNotFoundException, IOException, ClassNotFoundException {
		String path = DBParams.DBPath + "\\Catalog.def";
		FileInputStream catalog_file = null;
		
		try {
			catalog_file = new FileInputStream(path);
		} catch (Exception e) {
			Finish();
		}finally {
			catalog_file = new FileInputStream(path);
			ObjectInputStream object = new ObjectInputStream(catalog_file);
			INSTANCE = (Catalog) object.readObject();
			object.close();	
		}
		
	}
	public void Finish() throws FileNotFoundException, IOException {
		String path = DBParams.DBPath + "\\Catalog.def";
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path));
		
		output.writeObject(Catalog.getInstance());
		output.close();
	}
	public void reset(){
		cmp=0;
		rel_info.clear();
	}
	
}
