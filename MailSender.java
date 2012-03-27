import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Mail Sender for connecting to SMTP
 * @author JunZhi
 *
 */
public class MailSender {
	
	//global connection
	private Socket s;
	private OutputStream os;
	private DataOutputStream serverWriter;
	private InputStreamReader isrServer;
	private BufferedReader serverReader;

	
	//configs
	private String smtp_server = "";
	private int smtp_port = 25;
	private String username = "";
	private String password = "";
	
	//inputs
	private String field_From = "";
	private String field_To = "";
	private String field_Subject = "";
	private String field_Message = "";
	

	
	
	/**
	 * Construct.
	 */
	public MailSender(){

		
	}
	
	
	
	
	
	
	
	
/*
 * Connectivity Options / Cache
 * - Server Details
 * - User Details
 */
	
	/**
	 * Set up server details.
	 * @param smtp
	 * @param port
	 */
	public void server(String smtp, int port){
		smtp_server = smtp;
		smtp_port = port;
	}
	
	
	/**
	 * Set user details.
	 * @param user
	 * @param pass
	 */
	public void user(String user, String pass){
		username = Base64Coder.encodeString(user);
		password = Base64Coder.encodeString(pass);
	}	
	
	
	
	
	
	
	
	
/*
 * Session Management
 * - Server Connect
 * - Server Disconnect
 * - User Login
 */
	
	/**
	 * Open the Connection.
	 */
	public void connect(){
		try {
			s = new Socket(smtp_server, smtp_port);
			
			os= s.getOutputStream();
			serverWriter = new DataOutputStream(os); 
			isrServer = new InputStreamReader(s.getInputStream());
			serverReader = new BufferedReader(isrServer);	
			
			
			serverReader.readLine();

			query("EHLO "+smtp_server);
		
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
		
	
	}
	
	
	
	/**
	 * Disconnect from the server.
	 */
	public void disconnect(){
		
		try {
			s.close();
			
		} catch (IOException e) {
		}
	}


	/**
	 * Authenticate.
	 */
	public void login(){
		
		try {
			query("AUTH LOGIN " + username);
			//print();
			
			serverReader.readLine();
			
			query(password);
			//print();
		
		} catch (IOException e) {
		}
	
	}

	/**
	 * Logout.
	 */
	public void logout(){
		query("QUIT");
		//print();
	}
	
	
	
	
/*
 * Settings input
 * - Fields
 * ---- From
 * ---- To
 * ---- Subject
 * ---- Message
 */
	
	/**
	 * Set the sender.
	 * @param from_field
	 */
	public void set_From(String from_field){
		field_From = from_field;
	}

	/**
	 * Set the receiver.
	 * @param to_field
	 */
	public void set_To(String to_field){
		field_To = to_field;		
	}

	/**
	 * Set Subject.
	 * @param subject_field
	 */
	public void set_Subject(String subject_field){
		field_Subject = subject_field;
	}

	/**
	 * Add the message.
	 * @param message_field
	 */
	public void set_Message(String message_field){
		field_Message += message_field + "\r\n";
	}
	
	/**
	 * Reset the message.
	 */
	public void clear_Message(){
		field_Message = "";
	}

	
	
	
	

/*
 * Send / receive / printfunctions
 * - Query
 * - print to screen
 * - send messages
 */	
	
	/**
	 * Write into the data stream
	 * @param data
	 */
	public void query(String data){
		try {
			serverWriter.flush();
			if (data.length() > 0){
				serverWriter.writeBytes(data+"\r\n");
			}
			
		} catch (IOException e) {
		}
		
		
	}
	
	
	/**
	 * Get server response.
	 */
	public void print(){
		
		try {
			String response;
			response = serverReader.readLine();
			while (true){
				System.out.println(response);
				if (!serverReader.ready())
					break;
				response = serverReader.readLine();
			}
			
		} catch (IOException e) {
		}

	}
	
	
	/** 
	 * Get a single server response.
	 * @return
	 */
	public String getOne(){
		String response = "";
		try {
			response = serverReader.readLine();
			
		} catch (IOException e) {
		}
		return response;
	}
	
	
	/**
	 * Get multiple server response.
	 * @return
	 */
	public Queue<String> getQueue(){
		Queue<String> store = new LinkedList<String>();
		
		try {
			String response;
			response = serverReader.readLine();
			while (true){
				store.add(response);
				if (!serverReader.ready())
					break;
				response = serverReader.readLine();
			}
		} catch (IOException e) {	
		}
		
		return store;
	} 
	
	
	/**
	 * Collate and send message
	 */
	public void send(){
		query("MAIL FROM:"+field_From);
		//print();
		query("RCPT TO:"+field_To);
		//print();
		
		query("DATA");
		//print();
		
		String data;
		data  = "From:" + field_From + "\r\n";
		data += "To:" + field_To + "\r\n";
		data += "Subject:" + field_Subject + "\r\n";
		data += field_Message;
		query(data);
		
		query(".");
		//print();
	}
	
	/**
	 * Send Sender
	 */
	public void send_from(){
		query("MAIL FROM:"+field_From);
	}
	/**
	 * Send Receiver
	 */
	public void send_to(){
		query("RCPT TO:"+field_To);
	}
	/**
	 * Set the data
	 */
	public void set_data(){
		query("DATA");
	}
	/**
	 * Send the data
	 */
	public void send_data(){
		String data;
		data  = "From:" + field_From + "\r\n";
		data += "To:" + field_To + "\r\n";
		data += "Subject:" + field_Subject + "\r\n";
		data += field_Message;
		data += ".";
		
		query(data);
	}


	
}