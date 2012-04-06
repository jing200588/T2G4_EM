package dialog;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class DeleteConfirmDialog extends ErrorMessageDialog {
	public final static String STATE_TYPE = "delconfirm";
	private String m_btnOneText;
	private String m_btnTwoText;
	
	String state;
	/**
	 * Description: Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DeleteConfirmDialog(Shell parent, String type, String message) {
		super(parent, message);
		setText("Confirmation");
		state = type;
	}

	public DeleteConfirmDialog(Shell parent, String header, String message, String btnOneText, String btnTwoText) {
		super(parent, message);
		setText(header);
//		m_message = message;
		m_btnOneText = btnOneText;
		m_btnTwoText = btnTwoText;
	}

	
	/**
	 * Description: Create contents of the dialog.
	 */
	protected void createContents() {
		super.createContents();
		
		if (state.compareToIgnoreCase(STATE_TYPE) == 0)
			lblPrompt.setText("Are you sure you want to permanently delete " + string + "?" );
			
		
		/************************************************************
		 * 'NO' BUTTON arrangement
		 ***********************************************************/
		fd_btnOne.left = new FormAttachment(70, -50);
		fd_btnOne.right = new FormAttachment(70, 0);
		fd_btnOne.top = new FormAttachment(lblPrompt, 50);
		btnOne.setLayoutData(fd_btnOne);
		btnOne.setText("No");
		
		/************************************************************
		 * 'YES' BUTTON EVENT LISTENER
		 ***********************************************************/
		Button btnYes = new Button(mainComp, SWT.NONE);
		btnYes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = 1;
				shell.close();
			}
		});
		
		FormData fd_btnYes = new FormData();
		fd_btnYes.right = new FormAttachment(30, 50);
		fd_btnYes.top = new FormAttachment(lblPrompt, 50);
		fd_btnYes.left = new FormAttachment(30, 0);
		btnYes.setLayoutData(fd_btnYes);
		btnYes.setText("Yes");

	}
}
