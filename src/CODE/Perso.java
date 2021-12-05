package CODE;

import java.nio.ByteBuffer;

public class Perso {

	public static void main(String args[]) {
		String cmd = "SELECTMONO * FROM nomRelation WHERE nomColonne1OPvaleur1 AND nomColonne2OPvaleur2 ";
		String nomDeRelation = cmd.split(" ")[3];
		String afterWhere = cmd.split("WHERE")[1];
		String[] condition = afterWhere.split("AND");
		System.out.println(condition[0]);

	}
}
