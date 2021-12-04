package CODE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class BATCHINSERTCommand {
    private RelationInfo rel;
    private ArrayList<String> csvValues;

    public BATCHINSERTCommand(String ch) {
        String[] parsing = ch.split(" ");
        Catalog cat = Catalog.getInstance();
        this.rel = cat.getRelationWithName(parsing[2]);
        String path = "C:\\\\Users\\\\INFOTECH\\\\eclipse-workspace\\\\IDE_PROJET_RADJEF_NAITAIMER\\\\src\\\\" + parsing[5];
        csvValues = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                csvValues.add(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void Execute() throws IOException {
        if(this.rel != null){
            for(int i=0;i< csvValues.size();i++){
                String cmd = "INSERT INTO " + this.rel.getName() +" RECORD (" +csvValues.get(i) +")";
                INSERTCommand inst = new INSERTCommand(cmd);
                inst.Execute();
            }
        }
    }
}
