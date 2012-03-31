package advertise.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class ModelMailSender {
	 Properties props;
     Session mailConnection;
     Message msg;

	
	public ModelMailSender(String aHost, int aPort){
		 this.props = new Properties();
		 this.props.put("mail.smtp.host", aHost);
		 this.props.put("mail.smtp.port", aPort);
	         
		 this.mailConnection = Session.getInstance(props, null);
	}
}
