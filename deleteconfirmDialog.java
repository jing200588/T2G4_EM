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
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public deleteconfirmDialog(Shell parent, int style, String Ename) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Delete Confirmation");
		name = Ename;
	}

	/**
	 * Open the dialog.
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
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 200);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		Label Prompt = new Label(composite, SWT.WRAP);
		FormData fd_Prompt = new FormData();
		fd_Prompt.top = new FormAttachment(30, 0);
		fd_Prompt.left = new FormAttachment(10, 0);
		fd_Prompt.right = new FormAttachment(90, 0);
		Prompt.setLayoutData(fd_Prompt);
		Prompt.setText("Are you sure you want to permanently delete " + name + "?" );
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = 0;
				shell.close();
			}
		});
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.left = new FormAttachment(70, -50);
		fd_btnNewButton.right = new FormAttachment(70, 0);
		fd_btnNewButton.top = new FormAttachment(Prompt, 50);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("No");
		
		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = 1;
				shell.close();
			}
		});
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.right = new FormAttachment(30, 50);
		fd_btnCancel.top = new FormAttachment(Prompt, 50);
		fd_btnCancel.left = new FormAttachment(30, 0);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Yes");
		

	}

}
