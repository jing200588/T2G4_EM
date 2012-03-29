import java.util.Scanner;


/**
 * Tools and Test Suite for EMDB Classes and Database.
 * @author JunZhi
 *
 */
public class EMDBIITools {
	

	/**
	 * Shortcut to print out strings. (DUPLICATE FROM BASE)
	 * @param msg
	 */
	private static void dMsg(String msg){
		System.out.println(msg);
	}
	
	
	
	public static void main(String[] args) {
		dMsg("*****************************************	\n");
		dMsg("EM Database Tools and Test Suite.	");
		dMsg("EMDB Version " + EMDBSettings.EMDB_VERSION		+	"\n");
		dMsg("(c) Gerald Some Rights reserved	\n");
		dMsg("*****************************************	\n");	
		
		
		
		Scanner sc = new Scanner(System.in);
		EMDBII db = new EMDBII();
	}
}