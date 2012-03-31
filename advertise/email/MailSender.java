package advertise.email;

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
	private String smtpServer = "";
	private int smtpPort = 25;
	private String username = "";
	private String password = "";
	
	//inputs
	private String fieldFrom = "";
	private String fieldTo = "";
	private String fieldSubject = "";
	private String fieldMessage = "";
	

	
	
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
	public void server(String aSmtp, int aPort){
		this.smtpServer = aSmtp;
		this.smtpPort = aPort;
	}
	
	
	/**
	 * Set user details.
	 * @param user
	 * @param pass
	 */
	public void user(String aUser, String aPass){
		this.username = Base64Coder.encodeString(aUser);
		this.password = Base64Coder.encodeString(aPass);
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
			this.s = new Socket(smtpServer, smtpPort);
			
			this.os= s.getOutputStream();
			this.serverWriter = new DataOutputStream(os); 
			this.isrServer = new InputStreamReader(s.getInputStream());
			this.serverReader = new BufferedReader(isrServer);	
			
			
			this.serverReader.readLine();

			query("EHLO "+this.smtpServer);
		
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
		
	
	}
	
	
	
	/**
	 * Disconnect from the server.
	 */
	public void disconnect(){
		
		try {
			this.s.close();
			
		} catch (IOException e) {
		}
	}


	/**
	 * Authenticate.
	 */
	public void login(){
		
		try {
			query("AUTH LOGIN " + this.username);
			//print();
			
			this.serverReader.readLine();
			
			query(this.password);
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
	public void setFrom(String aFromfield){
		this.fieldFrom = aFromfield;
	}

	/**
	 * Set the receiver.
	 * @param to_field
	 */
	public void setTo(String aFieldTo){
		this.fieldTo = aFieldTo;		
	}

	/**
	 * Set Subject.
	 * @param subject_field
	 */
	public void setSubject(String aFieldSubject){
		this.fieldSubject = aFieldSubject;
	}

	/**
	 * Add the message.
	 * @param message_field
	 */
	public void setMessage(String aFieldMessage){
		this.fieldMessage += aFieldMessage + "\r\n";
	}
	
	/**
	 * Reset the message.
	 */
	public void clearMessage(){
		this.fieldMessage = "";
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
	public void query(String aData){
		try {
			this.serverWriter.flush();
			if (aData.length() > 0){
				this.serverWriter.writeBytes(aData+"\r\n");
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
			response = this.serverReader.readLine();
			while (true){
				System.out.println(response);
				if (!this.serverReader.ready())
					break;
				response = this.serverReader.readLine();
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
			response = this.serverReader.readLine();
			
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
			response = this.serverReader.readLine();
			while (true){
				store.add(response);
				if (!this.serverReader.ready())
					break;
				response = this.serverReader.readLine();
			}
		} catch (IOException e) {	
		}
		
		return store;
	} 
	
	
	
	
	
	

	
	/**
	 * Clear server responses
	 */
	public void clearServerResponse(){
		this.getQueue();
	}
	
	
	
	
	
	
	/**
	 * Collate and send message
	 */
	public void send(){
		query("MAIL FROM:"+this.fieldFrom);
		//print();
		query("RCPT TO:"+this.fieldTo);
		//print();
		
		query("DATA");
		//print();
		
		String data;
		data  = "From:" + this.fieldFrom + "\r\n";
		data += "To:" + this.fieldTo + "\r\n";
		data += "Subject:" + this.fieldSubject + "\r\n";
		data += this.fieldMessage;
		query(data);
		
		query(".");
		//print();
	}
	
	/**
	 * Send Sender
	 */
	public void sendFrom(){
		query("MAIL FROM:"+this.fieldFrom);
	}
	/**
	 * Send Receiver
	 */
	public void sendTo(){
		query("RCPT TO:"+this.fieldTo);
	}
	/**
	 * Set the data
	 */
	public void setData(){
		query("DATA");
	}
	/**
	 * Send the data
	 */
	public void sendData(){
		String data;
		data  = "From:" + this.fieldFrom + "\r\n";
		//data += "To:" + this.fieldTo + "\r\n";
		data += "Subject:" + this.fieldSubject + "\r\n";
		data += this.fieldMessage;
		data += ".";
		
		query(data);
	}

	
	

	
}