package program;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

import dialog.errormessageDialog;

import venue.BookedVenueInfo;

public class InputFilterDialog extends Dialog {
	
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	FilterComposite filterCompo;
	
	private Vector<BookedVenueInfo> m_listBookedVenue;
	private String[] output;
	/**
	 * Constructor:
	 * 
	 * @param parent - Shell
	 * @param initialObj - EventFlowEntry
	 * @param listBookedVenue - listBookedVenue
	 */
	public InputFilterDialog(Shell parent, int style, Vector<BookedVenueInfo> listBookedVenue)
	{
		super(parent, style);
		setText("Filter functionality");
		m_listBookedVenue = listBookedVenue;
	}
	
	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return output - String[] if users click OK button.
	 * @return null otherwise.
	 */
	public String[] open() 
	{
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		shell.pack();
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) 
		{
			if (!display.readAndDispatch()) 
			{
				display.sleep();
			}
		}
		
		// Return the entered value, or null
		return output;
	}
	
	/**
	 * Creates the dialog's contents
	 * 
	 * @param shell - the dialog window
	 */
	private void createContents(final Shell shell) 
	{
		shell.setLayout(new GridLayout(2, true));
		shell.setSize(500, 400);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setSize(500, 400);
		
		
		filterCompo = new FilterComposite(composite, SWT.BORDER, m_listBookedVenue);
		filterCompo.setBounds(10, 10, 325, 211);
		toolkit.adapt(filterCompo);
		toolkit.paintBordersFor(filterCompo);
		
		// Create the OK button and add a handler
		Button ok = new Button(composite, SWT.PUSH);
		ok.setText("OK");
		ok.setBounds(79, 227, 75, 25);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try
				{
					output = filterCompo.readInput();
					shell.close();
				}
				catch(Exception exception)
				{
					errormessageDialog errorBoard = new errormessageDialog(new Shell(), exception.getMessage());
					errorBoard.open();
				}
			}
		});
		toolkit.adapt(ok, true, true);
		
		// Create the cancel button and add a handler
		Button cancel = new Button(composite, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setBounds(217, 227, 75, 25);
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				output = null;
				shell.close();
			}
		});
		toolkit.adapt(cancel, true, true);
		// Set the OK button as the default, so
		// user can type input and press Enter
		// to dismiss
		shell.setDefaultButton(ok);
	}
}
