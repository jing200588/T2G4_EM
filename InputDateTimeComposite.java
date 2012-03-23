import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class InputDateTimeComposite extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private DateTime dateTimeFrom;
	private DateTime dateTimeTo;
	private Combo comboTimeFrom;
	private Combo comboTimeTo;
	private boolean hasChosenTimeFrom;
	private boolean hasChosenTimeTo;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public InputDateTimeComposite(Composite parent, int style) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		// Initialize some variables
		hasChosenTimeFrom = false;
		hasChosenTimeTo = false;
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(10, 10, 303, 90);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		
		Label label = new Label(composite, SWT.NONE);
		label.setText("Preferred Time Slot:");
		label.setBounds(10, 10, 104, 15);
		toolkit.adapt(label, true, true);
		
		dateTimeFrom = new DateTime(composite, SWT.DROP_DOWN);
		dateTimeFrom.setEnabled(false);
		dateTimeFrom.setBounds(86, 31, 85, 24);
		toolkit.adapt(dateTimeFrom);
		toolkit.paintBordersFor(dateTimeFrom);
		
		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setText("From:");
		label_1.setBounds(39, 32, 41, 15);
		toolkit.adapt(label_1, true, true);
		
		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setText("at");
		label_2.setBounds(177, 31, 15, 15);
		toolkit.adapt(label_2, true, true);
		
		Label label_3 = new Label(composite, SWT.NONE);
		label_3.setText("To:");
		label_3.setBounds(39, 61, 41, 15);
		toolkit.adapt(label_3, true, true);
		
		dateTimeTo = new DateTime(composite, SWT.DROP_DOWN);
		dateTimeTo.setEnabled(false);
		dateTimeTo.setBounds(86, 60, 85, 24);
		toolkit.adapt(dateTimeTo);
		toolkit.paintBordersFor(dateTimeTo);
		
		Label label_4 = new Label(composite, SWT.NONE);
		label_4.setText("at");
		label_4.setBounds(177, 60, 15, 15);
		toolkit.adapt(label_4, true, true);
		
		comboTimeFrom = new Combo(composite, SWT.READ_ONLY);
		comboTimeFrom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hasChosenTimeFrom = true;
			}
		});
		comboTimeFrom.setEnabled(false);
		comboTimeFrom.setItems(new String[] {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"});
		comboTimeFrom.setBounds(205, 31, 91, 23);
		toolkit.adapt(comboTimeFrom);
		toolkit.paintBordersFor(comboTimeFrom);
		
		comboTimeTo = new Combo(composite, SWT.READ_ONLY);
		comboTimeTo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hasChosenTimeTo = true;
			}
		});
		comboTimeTo.setEnabled(false);
		comboTimeTo.setItems(new String[] {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"});
		comboTimeTo.setBounds(205, 61, 91, 23);
		toolkit.adapt(comboTimeTo);
		toolkit.paintBordersFor(comboTimeTo);
	}
	
	/**
	 * Set all components in InputDateTimeComposite to the mode specified by
	 * the input boolean variable.
	 * If isEnable = true, then all components are set to enabled. Otherwise,
	 * they are set to disabled.
	 * 
	 * @param isEnable - boolean
	 */
	public void setEnabled(boolean isEnabled)
	{
		dateTimeFrom.setEnabled(isEnabled);
		dateTimeTo.setEnabled(isEnabled);
		comboTimeFrom.setEnabled(isEnabled);
		comboTimeTo.setEnabled(isEnabled);
	}
	
	/**
	 * 
	 * @return inputTimeSlot - TimeSlot object.
	 * @throws Exception if some fields are not chosen, or the input is not
	 * 		valid (e.g. starting date time and ending date time are not
	 * 		in chronological order)
	 */
	public TimeSlot readInputFields() throws Exception
	{
		if(hasChosenTimeFrom == false)
			throw new Exception("You have not selected the starting time!");
		if(hasChosenTimeTo == false)
			throw new Exception("You have not selected the ending time!");
		
		String[] timeFrom = comboTimeFrom.getText().split(":");
		String[] timeTo = comboTimeTo.getText().split(":");
		
		// Note that in DateTime object, month is from 0 to 11
		// while in our MyDateTime object, month is from 1 to 12 
		MyDateTime start = new MyDateTime(dateTimeFrom.getYear(),
				dateTimeFrom.getMonth() + 1, dateTimeFrom.getDay(),
				Integer.parseInt(timeFrom[0]), Integer.parseInt(timeFrom[1]));
		MyDateTime end = new MyDateTime(dateTimeTo.getYear(),
				dateTimeTo.getMonth() + 1, dateTimeTo.getDay(),
				Integer.parseInt(timeTo[0]), Integer.parseInt(timeTo[1]));
		if(start.compareTo(end) == 0)
			throw new Exception("Starting date time and ending date time are the same!");
		if(start.compareTo(end) > 0)
			throw new Exception("Starting date time and ending date time are not in the chronological order!");
		
		return new TimeSlot(start, end);
	}
	
	/**
	 * Display the input time slot.
	 * Assumption: wantedTimeSlot is a valid TimeSlot object (e.g. it is not
	 * 		null).
	 * 
	 * @param inputTimeSlot
	 */
	public void displayInputTimeSlot(TimeSlot wantedTimeSlot)
	{
		// Note that in DateTime object, month is from 0 to 11
		// while in our MyDateTime object, month is from 1 to 12 
		dateTimeFrom.setDate(wantedTimeSlot.getStartDateTime().getYear(), 
				wantedTimeSlot.getStartDateTime().getMonth() - 1,
				wantedTimeSlot.getStartDateTime().getDay());
		comboTimeFrom.setText(wantedTimeSlot.getStartDateTime().getTimeRepresentation());
		
		dateTimeTo.setDate(wantedTimeSlot.getEndDateTime().getYear(), 
				wantedTimeSlot.getEndDateTime().getMonth() - 1,
				wantedTimeSlot.getEndDateTime().getDay());
		comboTimeTo.setText(wantedTimeSlot.getEndDateTime().getTimeRepresentation());
		
		hasChosenTimeTo = hasChosenTimeFrom = true;
	}
	
	/**
	 * Reset some components in InputDateTimeCompos
	 */
	public void reset()
	{
		comboTimeFrom.setText("");
		comboTimeTo.setText("");
		hasChosenTimeTo = hasChosenTimeFrom = false;
	}
	public static void main(String[] args){
		Display display = new Display();
		display = Display.getDefault();
		Shell shell = new Shell();
		shell.setLayout(new FillLayout());
		shell.setText("Booking System");
		shell.setSize(629, 606);
		InputDateTimeComposite calc = new InputDateTimeComposite(shell, SWT.NONE);
		calc.pack();
		calc.setEnabled(true);
		shell.pack();
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()) display.sleep();
		} 

	} 
}
