import java.io.File;

/*
 * 
 * CLASS NAME: 				EMDB
 * CLASS DESCRIPTION: 		Wrapper class for different Database implementation.
 * 							
 * 
 */


public class EMDB extends EMDB_sqlite{
	
	
	
	

	
	public void system_check(){
		this.connect();
		//test file exists if not create
		if ( this.testFile() ){
			
			
			//test if correct database number 
			//if not init
			//TO DO: Test Schema
			if (this.verify_table_count(false) == 0){
				this.init();
			};
			
			
			
			
		}else{
			this.init();
		}
		this.disconnect();
		
	}
	
	

	public boolean testFile(){
		File findFile = new File(this.DBNAME);
		return findFile.isFile();
	}

	
}
