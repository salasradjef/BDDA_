package CODE;


import java.io.FileInputStream;
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
		rel_info = new ArrayList<>();
	}
	
	public void AddRelation(RelationInfo rel) {
		rel_info.add(rel);
		cmp++;
	}
	
	
	public void Init() throws  IOException, ClassNotFoundException {
		String path = DBParams.DBPath + "\\Catalog.def";
		FileInputStream catalog_file;
		
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


	public void Finish()  {
		String path = DBParams.DBPath + "\\Catalog.def";
		ObjectOutputStream output = null;
		try {
			output = new ObjectOutputStream(new FileOutputStream(path));
			output.writeObject(Catalog.getInstance());
			output.close();
		} catch (IOException e) {
			System.err.println("Erreur IO Exception Catalog" + e.getMessage());
		}

	}

	//Methode qui permet de tout remmetre a 0 dans le catalog
	public void reset(){
		cmp=0;
		rel_info.clear();
	}


	//Methode qui permet de charger une relation depuis le catalog avec son nom
	public RelationInfo getRelationWithName(String name){
		boolean trv = false;
		int i=0;
		while(!trv && i<this.cmp){
			if(this.rel_info.get(i).getName().equals(name)){
				trv = false;
				return this.rel_info.get(i);
			}
			i++;
		}
		return null;
	}


	/*Getters and Setters*/
	public ArrayList<RelationInfo> getRel_info() {
		return rel_info;
	}

	public void setRel_info(ArrayList<RelationInfo> rel_info) {
		this.rel_info = rel_info;
	}
	
}
