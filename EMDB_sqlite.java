import java.util.*;
import java.sql.*;

import org.sqlite.SQLiteJDBCLoader;

public class EMDB_sqlite implements EMDB{
	
	/*
	 * Settings
	 */
	private String DBNAME = "";
	private Connection DBCON = null;
	
	
	/*
	 * Constructor
	 */

	public EMDB_sqlite(){	
	}
	
	
	/*
	 * Setup and Test
	 */
	

	public void set_name(String name){
		DBNAME = name;
	}
	

	//to initialize DB if first run
	private void init(){
		
	}

	
	//testing purposes
	public void out(String msg){
		System.out.println(msg);
	}
	public void test(){
		  out(String.format("running in %s mode", SQLiteJDBCLoader.isNativeMode() ? "native" : "pure-java"));
		  out("end");
	}
	
	
	
	
	
	/*
	 * General Controls
	 */

	public void connect() throws SQLException {
		DBCON = DriverManager.getConnection("jdbc:sqlite:"+DBNAME);
		
		//if first time
		if (false){
			init();
		}
		
	}
	
	public void close() {
		// TODO Auto-generated method stub
		
	}

	
	
	/*
	 * Software specific
	 */
	
	public void get_events(){
		
	}
	
	public void get_registrants(){
		
	}
	
	public void get_venues(){
		
	}
	

	
	
}
