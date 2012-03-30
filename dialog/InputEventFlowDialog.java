package dialog;

import program.*;
import venue.*;

import java.util.Vector;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * This class allows users to enter inputs and view information regarding the EventFlowEntry object.
 */
public class InputEventFlowDialog extends Dialog {
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	
	InputEventFlowEntry inputEventFlowBox;
	
	private EventFlowEntry initialInput;
	private EventFlowEntry modifiedInput;
	private Vector<BookedVenueInfo> m_listBookedVenue;

	/**
	 * Constructor:
	 * 
	 * @param parent - Shell
	 * @param initialObj - EventFlowEntry
	 * @param listBookedVenue - listBookedVenue
	 */
	public InputEventFlowDialog(Shell parent, EventFlowEntry initialObj, Vector<BookedVenueInfo> listBookedVenue) 
	{
		// Pass the default styles here
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Event Flow Entry");
		
		initialInput = initialObj;		// It can be null. Note that we do not modify initialInput.
										// It is used for display purpose only.
		modifiedInput = null;
		m_listBookedVenue = listBookedVenue;
	}

	/**
	 * Constructor: 
	 * 
	 * @param parent - Shell
	 * @param style - int
	 * @param initialObj - EventFlowEntry
	 * @param listBookedVenue - listBookedVenue
	 */
	public InputEventFlowDialog(Shell parent, int style, EventFlowEntry initialObj, Vector<BookedVenueInfo> listBookedVenue) 
	{
		// Let users override the default styles
		super(parent, style);
		setText("Event Flow Entry");
	
		initialInput = initialObj;		// It can be null. Note that we do not modify initialInput.
										// It is used for display purpose only.
		modifiedInput = null;
		m_listBookedVenue = listBookedVenue;
	}

	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return newEventFlowEntry if users click OK button.
	 * @return null otherwise.
	 */
	public EventFlowEntry open() 
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
		return modifiedInput;
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
		
		
		inputEventFlowBox = new InputEventFlowEntry(composite, SWT.BORDER, m_listBookedVenue);
		inputEventFlowBox.setBounds(10, 10, 308, 202);
		toolkit.adapt(inputEventFlowBox);
		toolkit.paintBordersFor(inputEventFlowBox);
		
		try
		{
			if(initialInput != null)
				inputEventFlowBox.displayInput(initialInput);
		}
		catch(Exception exception)
		{
			// Nothing to be handled here!
		}
		
		// Create the OK button and add a handler
		Button ok = new Button(composite, SWT.PUSH);
		ok.setText("OK");
		ok.setBounds(79, 227, 75, 25);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try
				{
					modifiedInput = inputEventFlowBox.readInput();
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
				modifiedInput = null;
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
