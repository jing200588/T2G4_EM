package advertise.email;

import javax.mail.PasswordAuthentication;

import sun.misc.BASE64Encoder;

public class MailAuthenticator extends javax.mail.Authenticator {
	
	private PasswordAuthentication authentication = null;
	private String username;
	private String password;
	
	
	
	
	/**
	 * Constructor
	 */
	public MailAuthenticator(){
	}
	
	/**
	 * Constructor
	 * @param aUser
	 * @param aPass
	 */
	public MailAuthenticator(String aUser, String aPass) {
		this.setAuth(aUser, aPass);
	}

	
	/**
	 * Set authentication parameters
	 * @param aUser
	 * @param aPass
	 */
	protected void setAuth(String aUser, String aPass){
		this.username = aUser;
		this.password = aPass;
	}
	
	
	
	/**
	 * Start authentication process
	 */
	protected void initAuth(){
		this.authentication =  new PasswordAuthentication(this.username, this.password);
	}

	
	/**
	 * Get the authentication session
	 * @return
	 */
	protected PasswordAuthentication getAuth(){
		return this.authentication;
	}
	
	
}