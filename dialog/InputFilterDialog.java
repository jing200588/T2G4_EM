package dialog;

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

import program.FilterComposite;


import venue.BookedVenueInfo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

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
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
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
	//	shell.setLayout(new GridLayout(2, true));
		shell.setSize(500, 400);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setSize(500, 400);
		composite.setLayout(new GridLayout(2, false));
		
		
		filterCompo = new FilterComposite(composite, SWT.BORDER, m_listBookedVenue);
		filterCompo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		toolkit.adapt(filterCompo);
		toolkit.paintBordersFor(filterCompo);
		
		// Create the OK button and add a handler
		Button ok = new Button(composite, SWT.PUSH);
		GridData gd_ok = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_ok.widthHint = 80;
		ok.setLayoutData(gd_ok);
		ok.setText("OK");
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try
				{
					output = filterCompo.readInput();
					
					boolean allNull = true;
					for(int index = 0; index < output.length; index++)
						if(output[index] != null)
						{
							allNull = false;
						}
					if(allNull == true)
					{
						TwoChoiceDialog warning = new TwoChoiceDialog(new Shell(), "Warning",
								"You have not chosen any criterion yet! Do you want to continue?", "Yes", "No");
						String choice = (String) warning.open();
						if(choice.equals("Yes") == false)
							return;
						else
							output = null;
					}
					shell.close();
				}
				catch(Exception exception)
				{
					ErrorMessageDialog errorBoard = new ErrorMessageDialog(new Shell(), exception.getMessage());
					errorBoard.open();
				}
			}
		});
		toolkit.adapt(ok, true, true);
		
		// Create the cancel button and add a handler
		Button cancel = new Button(composite, SWT.PUSH);
		GridData gd_cancel = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_cancel.widthHint = 80;
		cancel.setLayoutData(gd_cancel);
		cancel.setText("Cancel");
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

		ok.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		cancel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		filterCompo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));

	}
}
