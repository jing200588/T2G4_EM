import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class deleteconfirmDialog extends Dialog {

	protected int result;
	protected Shell shell;
	protected String name;

	/**
	 * Description: Create the dialog.
	 * @param parent
	 * @param style
	 */
	public deleteconfirmDialog(Shell parent, int style, String Ename) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Delete Confirmation");
		name = Ename;
	}

	/**
	 * Description: Open the dialog.
	 * @return the result
	 */
	public int open() {
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
	 * Description: Create contents of the dialog.
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
		fd_Prompt.top = new FormAttachment(30, 0);
		fd_Prompt.left = new FormAttachment(20, 0);
		fd_Prompt.right = new FormAttachment(90, 0);
		Prompt.setLayoutData(fd_Prompt);
		Prompt.setText("Are you sure you want to permanently delete " + name + "?" );
		
		/************************************************************
		 * 'NO' BUTTON EVENT LISTENER
		 ***********************************************************/
		Button btnNo = new Button(composite, SWT.NONE);
		btnNo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = 0;
				shell.close();
			}
		});
		FormData fd_btnNo = new FormData();
		fd_btnNo.left = new FormAttachment(70, -50);
		fd_btnNo.right = new FormAttachment(70, 0);
		fd_btnNo.top = new FormAttachment(Prompt, 50);
		btnNo.setLayoutData(fd_btnNo);
		btnNo.setText("No");
		
		/************************************************************
		 * 'YES' BUTTON EVENT LISTENER
		 ***********************************************************/
		Button btnYes = new Button(composite, SWT.NONE);
		btnYes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = 1;
				shell.close();
			}
		});
		FormData fd_btnYes = new FormData();
		fd_btnYes.right = new FormAttachment(30, 50);
		fd_btnYes.top = new FormAttachment(Prompt, 50);
		fd_btnYes.left = new FormAttachment(30, 0);
		btnYes.setLayoutData(fd_btnYes);
		btnYes.setText("Yes");
		
		//warning sign label
		Label warningsign = new Label(composite, SWT.NONE);
		warningsign.setImage(Display.getDefault().getSystemImage(SWT.ICON_WARNING));
		FormData fd_warningsign = new FormData();
		fd_warningsign.top = new FormAttachment(25, 0);
		fd_warningsign.right = new FormAttachment(Prompt, -6);
		warningsign.setLayoutData(fd_warningsign);
		

	}

}
