package advertise.email;

import java.util.Queue;
import java.util.StringTokenizer;

import dialog.errormessageDialog;
import emdb.EMDBSettings;
import event.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class ViewEmailAds extends Composite {
	private Eventitem currentEvent;
	private final FormToolkit formToolkit = new FormToolkit(Display.getCurrent());
	private Text txtToInputBox;
	private Text txtSubjectInputBox;
	private Composite compMain;
	private Label lblTo;
	private Label lblSubject;
	private Label lblMessage;
	private Text txtMessageInputBox;
	private Label lblError;


	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEmailAds(Composite parent, int style, Eventitem input_ei, final String aUser, final String aDomain, final String aPass) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				formToolkit.dispose();
			}
		});
		currentEvent = input_ei;


		formToolkit.adapt(this);
		formToolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		Form formViewEmailAds = formToolkit.createForm(this);
		formViewEmailAds.setBounds(0, 0, 700, 400);
		formViewEmailAds.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(formViewEmailAds);
		formViewEmailAds.setText("Email Advertising");
		formViewEmailAds.getBody().setLayout(new FormLayout());
		
		/**********************************************************************************
		 * Overview Budget optimization composite 
		 **********************************************************************************/
		compMain = new Composite(formViewEmailAds.getBody(), SWT.NONE);
		FormData fd_compMain = new FormData();
		fd_compMain.top = new FormAttachment(40, -160);
		fd_compMain.bottom = new FormAttachment (50, 180);
		fd_compMain.left = new FormAttachment(50, -350);
		fd_compMain.right = new FormAttachment(50, 350);
		
		
		compMain.setLayoutData(fd_compMain);
		formToolkit.adapt(compMain);
		formToolkit.paintBordersFor(compMain);

		
		lblError  = new Label(compMain, SWT.None);
		lblError.setBounds(10, 0,  474, 60);
		formToolkit.adapt(lblError, true, true);
		
		
		lblTo = new Label(compMain, SWT.NONE);
		lblTo.setBounds(10, 86, 55, 15);
		formToolkit.adapt(lblTo, true, true);
		lblTo.setText("To:");
		
		lblSubject = new Label(compMain, SWT.NONE);
		lblSubject.setBounds(10, 124, 55, 15);
		formToolkit.adapt(lblSubject, true, true);
		lblSubject.setText("Subject:");
		
		lblMessage = new Label(compMain, SWT.NONE);
		lblMessage.setBounds(10, 168, 55, 15);
		formToolkit.adapt(lblMessage, true, true);
		lblMessage.setText("Message:");
		
		txtToInputBox = new Text(compMain, SWT.BORDER);
		txtToInputBox.setBounds(71, 83, 419, 21);
		formToolkit.adapt(txtToInputBox, true, true);
		
		txtSubjectInputBox = new Text(compMain, SWT.BORDER);
		txtSubjectInputBox.setBounds(71, 124, 419, 21);
		formToolkit.adapt(txtSubjectInputBox, true, true);
		
		txtMessageInputBox = new Text(compMain, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtMessageInputBox.setBounds(71, 168, 419, 144);
		formToolkit.adapt(txtMessageInputBox, true, true);
		

		Button btnSend = new Button(compMain, SWT.NONE);
		btnSend.setBounds(415, 335, 75, 25);
		formToolkit.adapt(btnSend, true, true);
		btnSend.setText("Send");

		
		

		txtSubjectInputBox.setText("<ADV> Join us at " + input_ei.getName());
		
		String newMsg = "";
		
		newMsg += "Date: " + input_ei.getStartDateTime().getDateRepresentation() + " - " + input_ei.getEndDateTime().getDateRepresentation();
		newMsg += "\n\n" + input_ei.getDescription();
		
		txtMessageInputBox.setText(newMsg);
		
		btnSend.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				
				
				
				String errorMsg = "";
				boolean sendOK = true;
				boolean allOK = true;
				
				
				if(txtToInputBox.getText().trim().isEmpty()){
					errorMsg += "\nPlease include a recipient";
					sendOK = false;
				}
				
				if(txtSubjectInputBox.getText().trim().isEmpty()){
					errorMsg += "\nPlease fill in a Subject";
					sendOK = false;
				}
				
				if(txtMessageInputBox.getText().trim().isEmpty()){
					errorMsg += "\nPlease fill in a Message";
					sendOK = false;
				}
				
				if (sendOK){
					if(EMDBSettings.DEVELOPMENT){
						EMDBSettings.dMsg("<EMMS> SENDING EMAIL...");
					}
					StringTokenizer toToken = new StringTokenizer(txtToInputBox.getText(), ",");
					String[] to = new String[toToken.countTokens()];
					for (int i = 0; i < to.length; i++) {
						to[i] = toToken.nextToken();
					}
					
					
					/*
					 * Send Mail Process
					 */
					
					String response = "";
					MailSender smail = new MailSender();
					smail.server("smtp.nus.edu.sg", 25);
					smail.connect();
					
					if(EMDBSettings.DEVELOPMENT){
						EMDBSettings.dMsg("<EMMS> "+ smail.getOne());
					}
					smail.clearServerResponse();
					
					smail.user(aDomain+"/"+aUser, aPass);
					smail.login();
					
					response = smail.getOne();
					if ( response.substring(0,3).compareTo("235") == 0){
						if(EMDBSettings.DEVELOPMENT){
							EMDBSettings.dMsg("<EMMS> "+ response);
						}
						
						smail.setFrom(aUser+"@nus.edu.sg");
						smail.sendFrom();
						response =  smail.getOne();
						if(EMDBSettings.DEVELOPMENT){EMDBSettings.dMsg("<EMMS> "+ response);}
						
						if (response.substring(0,3).compareTo("250") == 0){
						
							for (int i=0; i<to.length; i++){
								smail.setTo(to[i]);
								smail.sendTo();
								responseOut(smail.getQueue());
							}
							
							smail.clearMessage();
						
							
							smail.setSubject(txtSubjectInputBox.getText());
							smail.setMessage(txtMessageInputBox.getText());
							smail.setData();
							responseOut(smail.getQueue());
							
							smail.sendData();
							response =  smail.getOne();
							if(EMDBSettings.DEVELOPMENT){EMDBSettings.dMsg("<EMMS> "+ response);}
							
							smail.logout();
							if(EMDBSettings.DEVELOPMENT){EMDBSettings.dMsg("<EMMS> "+ smail.getOne());}
							
							if (response.substring(0, 3).compareTo("250") != 0){
								allOK = false;
							}
						}
					}
					
					
					
					
				/*

					
					 for (int i=0; i<to.length; i++){
						smail.setTo(to[i]);
						smail.sendTo();
						if(EMDBSettings.DEVELOPMENT){
							EMDBSettings.dMsg("<EMMS> "+ smail.getOne());
						}
			         }
					 
					 
					smail.setSubject(txtSubjectInputBox.getText());
					smail.clearMessage();
					smail.setMessage(txtMessageInputBox.getText());
					smail.clearServerResponse();
					
					smail.setData();
					smail.sendData();
					response = smail.getOne();
					
					if (response.substring(0, 3).compareTo("250") != 0){
						if(EMDBSettings.DEVELOPMENT){
							EMDBSettings.dMsg("<EMMS> "+ response);
						}
						allOK = false;
					}*/
					
				}else{
					if(EMDBSettings.DEVELOPMENT){
						EMDBSettings.dMsg("<EMMS> EMPTY FIELD...");
					}
					
					allOK = false;
				}

				if (!allOK){
					errormessageDialog messageBoard = new errormessageDialog(new Shell(),
							"An Error has occurred in sending your email",
							"Error");
					messageBoard.open();
				}
				
				lblError.setText(errorMsg);
			}
		});
	}
	
	
	
	
	private void responseOut(Queue<String> store){
		if (EMDBSettings.DEVELOPMENT){while(true){
			if (store.isEmpty() || store.peek() == null)
				break;
			EMDBSettings.dMsg("<EMMS> " + store.poll());
		}}
	}
}
