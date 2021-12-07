package CODE;

import java.util.HashMap;

public class Perso {

	public static void main(String args[]) {
		HashMap<String,String[]> condAndParams = new HashMap<>();
		condAndParams.put("=", null);
		condAndParams.put("=",new String[]{"B","2"});

		System.out.println(condAndParams.get("=")[1]);;

		/*String cmd = "SELECTMONO * FROM nomRelation WHERE nomColonne1=valeur1 AND nomColonne2OPvaleur2 ";
		String cm = "SELECTMONO * FROM R";
		String nomDeRelation = cmd.split(" ")[3];
		String[] afterWhere = cmd.split("WHERE");
		System.out.println(afterWhere.length);
		System.out.println(afterWhere[0]);*/



		/*String[] condition = afterWhere.split("AND");
		System.out.println(afterWhere);*/
	/*	String[] ops = {"=","<",">","<=",">=","<>"};
		boolean OPfounded =false;
		for(int i=0;i< ops.length;i++){
			if(condition[0].contains(ops[i])){
			OPfounded = true;
			System.out.println("L'operation " + ops[i] + " à été trouvé");
				break;
			}
		}*/

	}
}
