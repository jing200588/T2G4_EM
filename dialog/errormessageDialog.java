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


public class ErrorMessageDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	protected String string;
	protected Label Prompt;
	protected Composite composite;
	protected Button btnOne;
	protected FormData fd_btnOne;
	protected Label warningsign;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
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
		
		composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		//Prompt label
		Prompt = new Label(composite, SWT.WRAP | SWT.CENTER);
		FormData fd_Prompt = new FormData();
		fd_Prompt.top = new FormAttachment(30, 0);
		fd_Prompt.left = new FormAttachment(20, 0);
		fd_Prompt.right = new FormAttachment(90, 0);
		Prompt.setLayoutData(fd_Prompt);
		Prompt.setText(string);
		
		/************************************************************
		 * 'OK' BUTTON EVENT LISTENER
		 ***********************************************************/
		btnOne = new Button(composite, SWT.NONE);
		btnOne.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		fd_btnOne = new FormData();
		fd_btnOne.top = new FormAttachment(Prompt, 55);
		fd_btnOne.left = new FormAttachment(41, 0);
		fd_btnOne.right = new FormAttachment(59, 0);
		btnOne.setLayoutData(fd_btnOne);
		btnOne.setText("OK");
		
		//warning sign label
		warningsign = new Label(composite, SWT.NONE);
		warningsign.setImage(Display.getDefault().getSystemImage(SWT.ICON_WARNING));
		FormData fd_warningsign = new FormData();
		fd_warningsign.top = new FormAttachment(25, 0);
		fd_warningsign.right = new FormAttachment(Prompt, -6);
		warningsign.setLayoutData(fd_warningsign);
		

	}

}
