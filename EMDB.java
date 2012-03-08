import java.io.File;

/*
 * 
 * CLASS NAME: 				EMDB
 * CLASS DESCRIPTION: 		Wrapper class for different Database implementation.
 * 							
 * 
 */


public class EMDB extends EMDB_sqlite{
	
	

	public boolean testFile(){
		File findFile = new File(this.DBNAME);
		return findFile.isFile();
	}

	
}
