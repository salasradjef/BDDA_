package CODE;

import java.util.HashMap;

public class Perso {

	public static void main(String args[]) {
		String cmd = "DELETE FROM R WHERE C3=2";
		String nameOfRelation = cmd.split(" ")[2];

		String[] afterWhere = cmd.split("WHERE");
		String conversion = "SELECTMONO * FROM " + nameOfRelation + afterWhere[1];
		System.out.println(conversion);

	}
}
