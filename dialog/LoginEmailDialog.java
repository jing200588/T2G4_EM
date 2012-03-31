package dialog;

import emdb.EMDBSettings;
import event.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;

import advertise.email.MailSender;


public class LoginEmailDialog extends Dialog {

	protected Object objResult;
	protected Shell shellMain;
	private Text txtUsername;
	private Text txtPassword;
	private Button btnLogin;
	private Button btnCancel;
	private Label lblPassword;
	private String username;
	private String password;
	private String domain;
	private boolean loginSuccessful;
	private Eventitem currentEvent;
	protected errormessageDialog errordiag;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public LoginEmailDialog(Shell parent, int style, Eventitem item) {
		super(parent, style);
		setText("SWT Dialog");
		currentEvent = item;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shellMain.open();
		shellMain.layout();
		Display display = getParent().getDisplay();
		while (!shellMain.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return objResult;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shellMain = new Shell(getParent(), getStyle());
		shellMain.setSize(450, 300);
		shellMain.setText(getText());		
		shellMain.setLayout(new FormLayout());
		
		Composite composite = new Composite(shellMain, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 41);
		fd_composite.left = new FormAttachment(0, 21);
		fd_composite.right = new FormAttachment(95, -1);
		composite.setLayoutData(fd_composite);
		
		Label lblHeader = new Label(composite, SWT.NONE);
		GridData gd_lblHeader = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_lblHeader.widthHint = 140;
		lblHeader.setLayoutData(gd_lblHeader);
		lblHeader.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
		lblHeader.setText("Login to NUSNET");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
	    new Label(composite, SWT.NONE);
	    
	    
	    Label lblDomains = new Label(composite, SWT.NONE);
	    lblDomains.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
	    lblDomains.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
	    lblDomains.setText("Domain:");   
	    
	    String[] domains = new String[]{"NUSSTU", "NUSSTF"};
	    final Combo comboDomain = new Combo(composite, SWT.VERTICAL| SWT.BORDER  |SWT.READ_ONLY);
	    comboDomain.setItems(domains);
	    GridData gd_comboDomain = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
	    gd_comboDomain.widthHint = 188;
	    comboDomain.setLayoutData(gd_comboDomain);
	    
	    Label lblUsername = new Label(composite, SWT.NONE);
	    lblUsername.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
	    lblUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
	    lblUsername.setText("Username:");
	    
	    txtUsername = new Text(composite, SWT.BORDER);
	    GridData gd_txtUsername = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
	    gd_txtUsername.widthHint = 188;
	    txtUsername.setLayoutData(gd_txtUsername);
		
		lblPassword = new Label(composite, SWT.NONE);
	    lblPassword.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword.setText("Password");
		
		txtPassword = new Text(composite, SWT.BORDER);
		txtPassword.setEchoChar('*');
		GridData gd_txtPassword = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtPassword.widthHint = 188;
		txtPassword.setLayoutData(gd_txtPassword);
		
		btnLogin = new Button(shellMain, SWT.NONE);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				loginSuccessful = false; //alway reset to false
				
				try {
					
					if(comboDomain.getText().length() == 0) throw new Exception("Please choose a domain.");
					domain = comboDomain.getText();
					System.out.println(domain);
					
					
					txtUsername.getText().trim();
					if(txtUsername.getText().length() == 0) throw new Exception("Username must not be empty.");
					username = txtUsername.getText();
					txtPassword.getText().trim();
					if(txtPassword.getText().length() == 0) throw new Exception("Password must not be empty.");
					password = txtPassword.getText();
				
					//TODO: DO THE NECESSARY CHECKING AND THEN LOGIN USE A BOOLEAN FUNCTION.
					loginSuccessful = false; //Change this to your method of login.
					
					
					String user = "";
					if (domain.compareTo("No Domain") == 0)
						user = username;
					else
						user = domain + "\\" + username;
					
					
					
					System.out.println(user);
					MailSender mail = new MailSender();
					mail.server("smtp.nus.edu.sg", 25);
					mail.connect();
					mail.clearServerResponse();
					mail.user(user, password);
					mail.login();
					String response = mail.getOne();
					
					if (EMDBSettings.DEVELOPMENT){
						EMDBSettings.dMsg(response, "<Mail TS> ");
					}
					
					
					if (response.compareTo("235 2.7.0 Authentication successful") == 0){
						loginSuccessful = true;
					}else{
						loginSuccessful = false;
					}
					mail.logout();
					mail.print();
					mail.disconnect();
					 
					/*
					loginSuccessful = true;
					String user = "dummy";
					*/
					if(loginSuccessful == true) {
						shellMain.close();
						ViewMain.EmailAds(currentEvent, username, domain, password);//need to store the sesson somewhere anot? If yes I am not sure how you do it actually. 
					}
					else {
						throw new Exception("Incorrect Username/Password.");
					}
				
				} catch (Exception ex) {
					errordiag = new errormessageDialog(new Shell(), ex.getMessage());
					errordiag.open();
				}
				
				
			}
		});
		FormData fd_btnLogin = new FormData();
		fd_btnLogin.height = 25;
		fd_btnLogin.width = 100;
		fd_btnLogin.bottom = new FormAttachment(95, 0);
		btnLogin.setLayoutData(fd_btnLogin);
		btnLogin.setText("Login");
		
		btnCancel = new Button(shellMain, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shellMain.close();
			}
		});
		btnCancel.setSize(new Point(100, 250));
		fd_btnLogin.right = new FormAttachment(btnCancel, -20);
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.height = 25;
		fd_btnCancel.width = 100;
		fd_btnCancel.right = new FormAttachment(95, -4);
		fd_btnCancel.bottom = new FormAttachment(95, 0);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");

	}
}
