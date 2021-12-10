package CODE;

import java.util.HashMap;

public class Perso {

	public static void main(String args[]) {
		String cmd = "UPDATE R SET C2=rrr WHERE C3>=2";
		String[] tab = cmd.split("SET");
		String columns = tab[1].split("WHERE")[0];
		System.out.println(columns);

	}
}
