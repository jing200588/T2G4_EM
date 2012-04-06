package dialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class TwoChoiceDialog extends Dialog {
	private String choice;
	private Shell shell;
	private String m_message;
	private String m_btnOneText;
	private String m_btnTwoText;
	private Composite composite;
	private Button btnOne;
	private Button btnTwo;
	private FormData fd_btnOne;
	private FormData fd_btnTwo;
	private Label messageBox;
	private Label warningSign;
	
	/**
	 * Description: Create the dialog.
	 * @param parent
	 * @param style
	 */
	public TwoChoiceDialog(Shell parent, String header, String message, String btnOneText, String btnTwoText) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText(header);
		m_message = message;
		m_btnOneText = btnOneText;
		m_btnTwoText = btnTwoText;
	}

	/**
	 * Open the dialog.
	 * @return choice - the text of the button a user chooses
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return choice;
	}
	
	/**
	 * Description: Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 200);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		//Prompt label
		messageBox = new Label(composite, SWT.WRAP | SWT.CENTER);
		FormData fd_Prompt = new FormData();
		fd_Prompt.top = new FormAttachment(30, 0);
		fd_Prompt.left = new FormAttachment(20, 0);
		fd_Prompt.right = new FormAttachment(90, 0);
		messageBox.setLayoutData(fd_Prompt);
		messageBox.setText(m_message);		
		
		//warning sign label
		warningSign = new Label(composite, SWT.NONE);
		warningSign.setImage(Display.getDefault().getSystemImage(SWT.ICON_WARNING));
		FormData fd_warningsign = new FormData();
		fd_warningsign.top = new FormAttachment(25, 0);
		fd_warningsign.right = new FormAttachment(messageBox, -6);
		warningSign.setLayoutData(fd_warningsign);
		/************************************************************
		 * FIRST BUTTON arrangement
		 ***********************************************************/
		btnOne = new Button(composite, SWT.NONE);
		btnOne.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				choice = m_btnOneText;
				shell.close();
			}
		});
		fd_btnOne = new FormData();
		fd_btnOne.right = new FormAttachment(30, 50);
		fd_btnOne.top = new FormAttachment(messageBox, 50);
		fd_btnOne.left = new FormAttachment(30, 0);
		btnOne.setLayoutData(fd_btnOne);
		btnOne.setText(m_btnOneText);
		
		/************************************************************
		 * SECOND BUTTON EVENT LISTENER
		 ***********************************************************/
		btnTwo = new Button(composite, SWT.NONE);
		btnTwo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				choice = m_btnTwoText;
				shell.close();
			}
		});
		fd_btnTwo = new FormData();
		fd_btnTwo.left = new FormAttachment(70, -50);
		fd_btnTwo.right = new FormAttachment(70, 0);
		fd_btnTwo.top = new FormAttachment(messageBox, 50);
		btnTwo.setLayoutData(fd_btnTwo);
		btnTwo.setText(m_btnTwoText);
		btnTwo.pack();
		btnOne.pack();

	}
}

