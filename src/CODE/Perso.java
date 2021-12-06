package CODE;

import java.nio.ByteBuffer;

public class Perso {

	public static void main(String args[]) {
		String cmd = "SELECTMONO * FROM nomRelation WHERE nomColonne1=valeur1 AND nomColonne2OPvaleur2 ";
		String nomDeRelation = cmd.split(" ")[3];
		String afterWhere = cmd.split("WHERE")[1];
		String[] condition = afterWhere.split("AND");

		String[] ops = {"=","<",">","<=",">=","<>"};
		boolean OPfounded =false;
		for(int i=0;i< ops.length;i++){
			if(condition[0].contains(ops[i])){
			OPfounded = true;
			System.out.println("L'operation " + ops[i] + " à été trouvé");
				break;
			}
		}

	}
}
