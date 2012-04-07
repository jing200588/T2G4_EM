package dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;


public class ErrorMessageDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	protected String string;
	protected Label lblPrompt;
	protected Composite mainComp;
	protected Button btnOne;
	protected FormData fd_btnOne;
	protected Label warningSign;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 * @wbp.parser.constructor
	 */
	public ErrorMessageDialog(Shell parent, String msg) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Error");
		string = msg;
		result = 0;
	}

	public ErrorMessageDialog(Shell parent, String msg, String header) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText(header);
		string = msg;
		result = 0;
	}
	/**
	 * Open the dialog.
	 * @return the result
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
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	protected void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 200);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		mainComp = new Composite(shell, SWT.NONE);
		mainComp.setLayout(new FormLayout());
		
		//Prompt label
		lblPrompt = new Label(mainComp, SWT.WRAP | SWT.CENTER);
		lblPrompt.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		FormData fd_Prompt = new FormData();
		fd_Prompt.top = new FormAttachment(30, 0);
		fd_Prompt.left = new FormAttachment(20, 0);
		fd_Prompt.right = new FormAttachment(90, 0);
		lblPrompt.setLayoutData(fd_Prompt);
		lblPrompt.setText(string);
		
		/************************************************************
		 * 'OK' BUTTON EVENT LISTENER
		 ***********************************************************/
		btnOne = new Button(mainComp, SWT.NONE);
		btnOne.setFont(SWTResourceManager.getFont("Maiandra GD", 10, SWT.NORMAL));
		btnOne.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		fd_btnOne = new FormData();
		fd_btnOne.top = new FormAttachment(lblPrompt, 55);
		fd_btnOne.left = new FormAttachment(41, 0);
		fd_btnOne.right = new FormAttachment(59, 0);
		btnOne.setLayoutData(fd_btnOne);
		btnOne.setText("OK");
		
		//warning sign label
		warningSign = new Label(mainComp, SWT.NONE);
		warningSign.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		warningSign.setImage(Display.getDefault().getSystemImage(SWT.ICON_WARNING));
		FormData fd_warningsign = new FormData();
		fd_warningsign.top = new FormAttachment(25, 0);
		fd_warningsign.right = new FormAttachment(lblPrompt, -6);
		warningSign.setLayoutData(fd_warningsign);
		

	}

}
