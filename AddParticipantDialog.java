import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class AddParticipantDialog extends Dialog {

	protected Object result;
	protected Shell shlAddParticipant;
	private Text txtName;
	private Text txtMatricNo;
	private Text txtContact;
	private Text txtEmailAddress;
	private Text txtRemarks;
	private Text txtHomeAddress;
	private Button btnCreate;
	private Button btnCancel;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AddParticipantDialog(Shell parent) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAddParticipant.open();
		shlAddParticipant.layout();
		Display display = getParent().getDisplay();
		while (!shlAddParticipant.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlAddParticipant = new Shell(getParent(), getStyle());
		shlAddParticipant.setSize(400, 450);
		shlAddParticipant.setText("Add New Participant");
		shlAddParticipant.setLayout(new FormLayout());
		
		Composite composite = new Composite(shlAddParticipant, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(5);
		fd_composite.left = new FormAttachment(5, 0);
		fd_composite.right = new FormAttachment(95, 0);
		composite.setLayoutData(fd_composite);
		
		Label lblName = new Label(composite, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name:");
		
		txtName = new Text(composite, SWT.BORDER);
		txtName.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		GridData gd_txtName = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_txtName.widthHint = 188;
		txtName.setLayoutData(gd_txtName);
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblMatricNo = new Label(composite, SWT.NONE);
		lblMatricNo.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblMatricNo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMatricNo.setText("Matric No.:");
		
		txtMatricNo = new Text(composite, SWT.BORDER);
		txtMatricNo.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		GridData gd_txtMatricNo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtMatricNo.widthHint = 188;
		txtMatricNo.setLayoutData(gd_txtMatricNo);
		
	    new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblContact = new Label(composite, SWT.NONE);
		lblContact.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblContact.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblContact.setText("Contact:");
		
		txtContact = new Text(composite, SWT.BORDER);
		txtContact.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		GridData gd_txtContact = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_txtContact.widthHint = 188;
		txtContact.setLayoutData(gd_txtContact);
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblEmailAddress = new Label(composite, SWT.NONE);
		lblEmailAddress.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblEmailAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmailAddress.setText("Email Address:");
		
		txtEmailAddress = new Text(composite, SWT.BORDER);
		txtEmailAddress.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		GridData gd_txtEmailAddress = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtEmailAddress.widthHint = 188;
		txtEmailAddress.setLayoutData(gd_txtEmailAddress);
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblHomeAddress = new Label(composite, SWT.NONE);
		lblHomeAddress.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblHomeAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHomeAddress.setText("Home Address:");
		
		txtHomeAddress = new Text(composite, SWT.BORDER);
		txtHomeAddress.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		GridData gd_txtHomeAddress = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtHomeAddress.widthHint = 188;
		txtHomeAddress.setLayoutData(gd_txtHomeAddress);
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblRemarks = new Label(composite, SWT.NONE);
		lblRemarks.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblRemarks.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRemarks.setText("Remarks:");
		
		txtRemarks = new Text(composite, SWT.BORDER | SWT.MULTI);
		txtRemarks.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		GridData gd_txtRemarks = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtRemarks.heightHint = 63;
		gd_txtRemarks.widthHint = 188;
		txtRemarks.setLayoutData(gd_txtRemarks);
		
		btnCreate = new Button(shlAddParticipant, SWT.NONE);
		btnCreate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewParticipantList.addParticipant(new Participant(txtName.getText(), txtMatricNo.getText(), txtContact.getText(), txtEmailAddress.getText(),
						txtHomeAddress.getText(), txtRemarks.getText()));
				shlAddParticipant.close();				
			}
		});
		FormData fd_btnCreate = new FormData();
		fd_btnCreate.height = 25;
		fd_btnCreate.width = 100;
		fd_btnCreate.bottom = new FormAttachment(95, 0);
		btnCreate.setLayoutData(fd_btnCreate);
		btnCreate.setText("Create");
		
		btnCancel = new Button(shlAddParticipant, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlAddParticipant.close();
			}
		});
		btnCancel.setSize(new Point(100, 250));
		fd_btnCreate.right = new FormAttachment(btnCancel, -20);
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.height = 25;
		fd_btnCancel.width = 100;
		fd_btnCancel.right = new FormAttachment(95, -4);
		fd_btnCancel.bottom = new FormAttachment(95, 0);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");

	}
}
