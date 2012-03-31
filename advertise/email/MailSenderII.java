package advertise.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import emdb.EMDBSettings;



public class MailSenderII extends MailAuthenticator{

	
	private String smtpServer;
	private String domain;
	private Integer port;
	private String user;
	private String authUser;
	private String password;
	private String from;
	//private String auth;
	

	private Properties prepareProperties(){
		   Properties props = new Properties();
		   props.setProperty("mail.smtp.host", this.smtpServer);
		   props.setProperty("mail.smtp.port", "25");
		   return props;
	}

	
	private MimeMessage prepareMessage(
			Session aMailSession,
			String aCharset,
			String aSubject,
			String aHtmlMessage,
			String[] aRecipient
	){
        MimeMessage message = null;
        try {
        	
            message = new MimeMessage(aMailSession);
           
            
            if (EMDBSettings.DEVELOPMENT){
            	EMDBSettings.dMsg("<EMMS> From: " +  this.from);
            	EMDBSettings.dMsg("<EMMS> Subject: " + aSubject);
            }
            
            message.setFrom(new InternetAddress(this.from));
            message.setSubject(aSubject);
            
            for (int i=0; i<aRecipient.length; i++){
                if (EMDBSettings.DEVELOPMENT){
                	EMDBSettings.dMsg("<EMMS> Recipient: " + aRecipient[i]);
                }
                
                message.addRecipient(
                			Message.RecipientType.TO, 
                			new InternetAddress(aRecipient[i])
                		);
            }
            
            //message.setContent(aHtmlMessage, "text/html; charset=\" "+ aCharset + "\"");
            
        } catch (Exception e) {
        	if (EMDBSettings.DEVELOPMENT){
        		EMDBSettings.dMsg("<EMMS> ERROR IN PREPARING MESSAGE");
        		EMDBSettings.dMsg(e.getMessage());
        	}
        }
        return message;
	} 
	
	
	
	
	
	public void setConnection(String aSmtpServer, int aPort, String aDomain, String aUser, String aPass){
		this.smtpServer = aSmtpServer;
		this.port = aPort;
		this.domain = aDomain;
		this.user = aUser;
		this.password = aPass;
		
        this.from = user;
        if (domain.compareTo("NUSSTU") == 0 || domain.compareTo("NUSSTF") == 0){
        	 this.from +="@nus.edu.sg";
        	 this.authUser = this.domain+"\\"+this.user;
        }
	}
	
	
	
	
	public String sendEmail(String aSubject,String aHtmlMessage, String[] aTo) {
		
		String msg = "OK";
		
        Transport transport = null;
        
        try {
            Properties props = prepareProperties();
           
            Session mailSession = Session.getDefaultInstance(props, new MailAuthenticator(this.from, this.password));
            
		            
            
            transport =  mailSession.getTransport("smtp");
            MimeMessage message = prepareMessage(
            							mailSession, 
            							"ISO-8859-2",
                                        aSubject, 
                                        aHtmlMessage, 
                                      	aTo
                                 	);
            
            
           /* if (domain.compareTo("NUSSTU") == 0 || domain.compareTo("NUSSTF") == 0){
            	
            }else{
            	transport.connect(this.user, this.password);
            }*/
            //transport.connect(this.domain+"\\"+this.user, this.password);
            transport.connect();
            Transport.send(message);
            
            
        } catch (Exception e) {    
        	if (EMDBSettings.DEVELOPMENT){
        		EMDBSettings.dMsg("<EMMS> ERROR IN SENDING MAIL");
        		EMDBSettings.dMsg(e.getMessage());
        	}
        	msg = "\nError in Sending Message";
        	
        } finally{
        	
            try {
                transport.close();
            } catch (MessagingException e) {
            	EMDBSettings.dMsg("<EMMS> ERROR IN CLOSING TRANSPORT");
        		EMDBSettings.dMsg(e.getMessage());		
            }
        }
        
        return msg;
        
        
    }
	
	
	

	
	
	
	
	
}
