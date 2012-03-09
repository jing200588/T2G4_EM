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
		this.set_debug(EMSettings.DEVELOPMENT);
		
		this.connect();
		//test file exists if not create
		
		if (this.EMDB_DEBUGGING){
			this.out("****************************************");
			this.out("STARTING SYSTEM CHECK");
		}
		
		if ( this.testFile() ){
			if (this.EMDB_DEBUGGING){
				this.out("DATABASE FILE FOUND");
			}
			
			
			
			//test if correct database number 
			//if not init
			//TO DO: Test Schema
			if (this.verify_table_count(false) == 0){
				
				if (this.EMDB_DEBUGGING){
					this.out("DATABASE NOT INITIALIZED....");
					this.out("INITIALIZING....");
				}
				
				this.init();
				
				this.add_prepare("venue");
				this.add_venue("DR1 (COM1-B-14B)", "", "", 6, 0, true);
				this.add_venue("DR2 (COM1-B-14A)", "", "", 6, 0, true);
				this.add_venue("DR3 (COM1-B-07)", "", "", 5, 0, true);
				this.add_venue("DR4 (COM1-B-06)", "", "", 5, 0, true);
				this.add_venue("DR5 (ICUBE-03-18)", "", "", 8, 0, true);
				this.add_venue("DR6 (COM2-02-12)", "", "", 8, 0, true);
				this.add_venue("DR7 (COM2-03-14)", "", "", 8, 0, true);
				this.add_venue("DR8 (COM2-03-15)", "", "", 8, 0, true);
				this.add_venue("DR9 (COM2-04-06)", "", "", 8, 0, true);
				this.add_venue("DR10 (COM2-02-24)", "", "", 8, 0, true);
				this.add_venue("DR11 (COM2-02-23)", "", "", 8, 0, true);
				this.add_venue("Executive Classroom (COM2-04-02)", "", "", 25, 0, true);
				this.add_venue("MR1 (COM1-03-19)", "", "", 7, 0, true);
				this.add_venue("MR2 (COM1-03-28)", "", "", 7, 0, true);
				this.add_venue("MR3 (COM2-02-26)", "", "", 7, 0, true);
				this.add_venue("MR4 (COM1-01-22)", "", "", 7, 0, true);
				this.add_venue("MR5 (COM1-01-18)", "", "", 7, 0, true);
				this.add_venue("MR6 (AS6-05-10)", "", "", 8, 0, true);
				this.add_venue("MR7 (ICUBE-03-01)", "", "", 12, 0, true);
				this.add_venue("MR8 (ICUBE-03-48)", "", "", 12, 0, true);
				this.add_venue("MR9 (ICUBE-03-49)", "", "", 12, 0, true);
				this.add_venue("Video Conferencing Room (COM1-02-13)", "", "", 30, 0, true);
				this.add_batch_commit();
			};
			
			
			
			
		}
		this.disconnect();
		if (this.EMDB_DEBUGGING){
			this.out("END SYSTEM CHECK");
			this.out("****************************************");
		}
	}
	
	

	public boolean testFile(){
		File findFile = new File(this.DBNAME);
		return findFile.isFile();
	}

	
}
