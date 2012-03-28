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


public class messageDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	protected String concern;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public messageDialog(Shell parent, String msg) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Message");
		concern = msg;
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
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 200);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		//Prompt label
		Label Prompt = new Label(composite, SWT.WRAP | SWT.CENTER);
		FormData fd_Prompt = new FormData();
		fd_Prompt.bottom = new FormAttachment(30, 15);
		fd_Prompt.top = new FormAttachment(30, 0);
		fd_Prompt.left = new FormAttachment(20, 0);
		fd_Prompt.right = new FormAttachment(90, 0);
		Prompt.setLayoutData(fd_Prompt);
//		Prompt.setText(concern);
		
		/************************************************************
		 * 'OK' BUTTON EVENT LISTENER
		 ***********************************************************/
		Button btnOne = new Button(composite, SWT.NONE);
		btnOne.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = 0;
				shell.close();
			}
		});
		FormData fd_btnOne = new FormData();
		fd_btnOne.top = new FormAttachment(Prompt, 55);
		fd_btnOne.left = new FormAttachment(41, 0);
		fd_btnOne.right = new FormAttachment(59, 0);
		btnOne.setLayoutData(fd_btnOne);
//		btnOk.setText("OK");
		
		//warning sign label
		Label warningsign = new Label(composite, SWT.NONE);
		warningsign.setImage(Display.getDefault().getSystemImage(SWT.ICON_WARNING));
		FormData fd_warningsign = new FormData();
		fd_warningsign.right = new FormAttachment(Prompt, -6);
		fd_warningsign.top = new FormAttachment(25, 0);
		warningsign.setLayoutData(fd_warningsign);
		

	}

}
