import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class EmailLoginDialog extends Dialog {
	private Text txtUsername;
	private Text txtPassword;
	private Composite container;
	private Button btnLogin;
	private Button btnCancel;
	private String username;
	private String password;
	private boolean loginSuccessful;
	private Eventitem cevent;
	protected errormessageDialog errordiag;
	

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public EmailLoginDialog(Shell parentShell, Eventitem currentEvent) {
		super(parentShell);
		parentShell.setText("Email Login");
		cevent = currentEvent;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		container = (Composite) super.createDialogArea(parent);
		GridLayout gl_container = (GridLayout) container.getLayout();
		gl_container.numColumns = 2;
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblLoginToNusnet = new Label(container, SWT.NONE);
		lblLoginToNusnet.setText("Login to NUSNET");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblUsername = new Label(container, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUsername.setText("Username: ");
		
		txtUsername = new Text(container, SWT.BORDER);
		txtUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblPassword = new Label(container, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword.setText("Password:");
		
		txtPassword = new Text(container, SWT.BORDER);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		loginSuccessful = false;
		btnLogin = createButton(parent, IDialogConstants.OK_ID, "Login",
				true);
		btnLogin.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				username = txtUsername.getText();
				password = txtPassword.getText();
				
				//TODO: DO THE NECESSARY CHECKING AND THEN LOGIN USE A BOOLEAN FUNCTION.
				loginSuccessful = true; //Change this to your method of login.
				
				if(loginSuccessful == true) {
					close();
					ViewMain.EmailAds(cevent);//need to store the sesson somewhere anot? If yes I am not sure how you do it actually. 
				}
				else {
					close();
					errordiag = new errormessageDialog(new Shell(), "Incorrect Username/Password.");
					errordiag.open();
				}
			}
		});
		btnCancel = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				close();
			}
		});
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

}
