package client;
import java.util.Scanner;

public class Input {

	public static int collectIntInput(Scanner sc) {
		// parse first index of passed string, is action. return command
		int i = sc.nextInt();
		return i;	
	}
	
	public static String[] credentials(Scanner sc) {
		String a = sc.next();
		String b = sc.next();
		return new String[] {a,b};
	}
	
	public static String string(Scanner sc) {
		String a = sc.next();
		return a;
	}
	
}
