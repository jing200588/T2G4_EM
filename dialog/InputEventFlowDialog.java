package dialog;

import program.*;
import venue.*;

import java.util.Vector;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

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
	 * @wbp.parser.constructor
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
		shell.setSize(500, 400);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setSize(500, 400);
		composite.setLayout(new GridLayout(2, false));
		
		
		inputEventFlowBox = new InputEventFlowEntry(composite, SWT.BORDER, m_listBookedVenue);
		GridData gd_inputEventFlowBox = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_inputEventFlowBox.heightHint = 219;
		inputEventFlowBox.setLayoutData(gd_inputEventFlowBox);
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
		GridData gd_ok = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_ok.widthHint = 80;
		ok.setLayoutData(gd_ok);
		ok.setText("OK");
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try
				{
					modifiedInput = inputEventFlowBox.readInput();
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
				modifiedInput = null;
				shell.close();
			}
		});
		toolkit.adapt(cancel, true, true);
		// Set the OK button as the default, so
		// user can type input and press Enter
		// to dismiss
		shell.setDefaultButton(ok);
		
		inputEventFlowBox.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	}
}
