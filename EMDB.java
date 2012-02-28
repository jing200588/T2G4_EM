import java.sql.SQLException;


public interface EMDB {
	
	//Set up db name
	void set_name(String name);
	
	//connection control
	void connect() throws SQLException;
	void close();
}
