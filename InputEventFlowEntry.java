import java.util.Vector;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class InputEventFlowEntry extends Composite {
	public final String OTHERVENUE = "Other";
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text textActivity;
	private Text textNote;
	
	private Vector<BookedVenueInfo> m_listBookedVenue;
	private String[] m_venueName;
	private boolean isVenueChosen;
	
	private DateTime dateFrom;
	private DateTime timeFrom;
	private DateTime dateTo;
	private DateTime timeTo;
	private Combo comboVenue;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */ 
	public InputEventFlowEntry(Composite parent, int style, Vector<BookedVenueInfo> listBookedVenue ) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		/* Initialize some variables */
		isVenueChosen = false;
		m_listBookedVenue = listBookedVenue;
		m_venueName = new String[listBookedVenue.size() + 1];
		for(int index = 0; index < m_listBookedVenue.size(); index++)
		{
			m_venueName[index] = m_listBookedVenue.get(index).getName();
		}
		m_venueName[m_venueName.length - 1] = OTHERVENUE;
		
		Label lblActivityName = new Label(this, SWT.NONE);
		lblActivityName.setBounds(10, 10, 83, 15);
		toolkit.adapt(lblActivityName, true, true);
		lblActivityName.setText("Activity Name:");
		
		textActivity = new Text(this, SWT.BORDER);
		textActivity.setBounds(99, 7, 76, 21);
		toolkit.adapt(textActivity, true, true);
		
		Label lblStartDateTime = new Label(this, SWT.NONE);
		lblStartDateTime.setBounds(10, 43, 89, 15);
		toolkit.adapt(lblStartDateTime, true, true);
		lblStartDateTime.setText("Start Date Time:");
		
		dateFrom = new DateTime(this, SWT.DROP_DOWN);
		dateFrom.setBounds(99, 36, 80, 24);
		toolkit.adapt(dateFrom);
		toolkit.paintBordersFor(dateFrom);
		
		timeFrom = new DateTime(this, SWT.DROP_DOWN | SWT.TIME | SWT.SHORT);
		timeFrom.setBounds(185, 36, 80, 24);
		toolkit.adapt(timeFrom);
		toolkit.paintBordersFor(timeFrom);
		
		Label lblEndDateTime = new Label(this, SWT.NONE);
		lblEndDateTime.setText("End Date Time:");
		lblEndDateTime.setBounds(10, 71, 89, 15);
		toolkit.adapt(lblEndDateTime, true, true);
		
		dateTo = new DateTime(this, SWT.DROP_DOWN);
		dateTo.setBounds(99, 64, 80, 24);
		toolkit.adapt(dateTo);
		toolkit.paintBordersFor(dateTo);
		
		timeTo = new DateTime(this, SWT.DROP_DOWN | SWT.TIME | SWT.SHORT);
		timeTo.setBounds(185, 64, 80, 24);
		toolkit.adapt(timeTo);
		toolkit.paintBordersFor(timeTo);
		
		comboVenue = new Combo(this, SWT.READ_ONLY);
		comboVenue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isVenueChosen = true;
			}
		});
		comboVenue.setBounds(99, 93, 91, 23);
		comboVenue.setItems(m_venueName);
		toolkit.adapt(comboVenue);
		toolkit.paintBordersFor(comboVenue);
		
		Label lblVenue = new Label(this, SWT.NONE);
		lblVenue.setBounds(10, 96, 55, 15);
		toolkit.adapt(lblVenue, true, true);
		lblVenue.setText("Venue:");
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(10, 129, 89, 15);
		toolkit.adapt(lblNewLabel, true, true);
		lblNewLabel.setText("Other notes:\r\n(Optional)");
		
		textNote = new Text(this, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		textNote.setBounds(99, 129, 209, 66);
		toolkit.adapt(textNote, true, true);
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setBounds(10, 146, 55, 15);
		toolkit.adapt(lblNewLabel_1, true, true);
		lblNewLabel_1.setText("(Optional)");

	}
	
	/**
	 * Read inputs entered by the user and build an EventFlowEntry object based on them.
	 * 
	 * @return an EventFlowEntry object based on the inputs the user enters
	 * @throws Exception if there is a compulsory field that the user forgets to type in.
	 */
	public EventFlowEntry readInput() throws Exception
	{
		// Check if all compulsory fields are filled
		if(isVenueChosen == false)
			throw new Exception("You have not chosen venue of the activity!");
		
		String activityName = textActivity.getText();
		if(activityName == null || activityName.equals(""))
			throw new Exception("You have not enter the name of the activity!");
		
		MyDateTime dateTimeFrom = readDateTime(dateFrom, timeFrom);
		MyDateTime dateTimeTo = readDateTime(dateTo, timeTo);
		if(dateTimeFrom.compareTo(dateTimeTo) > 0)
			throw new Exception("The starting date time and ending date time are not in chronological order!");
		if(dateTimeFrom.compareTo(dateTimeTo) == 0)
			throw new Exception("The starting date time and ending date time are the same!");
		
		// Find the index of the chosen venue.
		String chosenVenue = comboVenue.getText();
		int chosenVenueID = 0;
		if(chosenVenue.equals(OTHERVENUE) == true)
			chosenVenueID = -1;
		else
			chosenVenueID = comboVenue.indexOf(chosenVenue);
		
		// Return the EventFlowEntry object
		return new EventFlowEntry(new TimeSlot(dateTimeFrom, dateTimeTo), activityName, chosenVenue,
				chosenVenueID, textNote.getText());
	}
	
	/**
	 * Display information given in the input EventFlowEntry object.
	 * 
	 * @param obj - EventFlowEntry
	 * @throws exception if the venue is not consistent with the list of booked venues of the event.
	 * 
	 * Assumption: the inputs given in obj are correct.
	 */
	public void displayInput(EventFlowEntry obj) throws Exception
	{
		if(obj == null)
			return;
		
		// Check venue
		int venueID = comboVenue.indexOf(obj.getVenueName());
		if(venueID < 0)
			comboVenue.setText(OTHERVENUE);
		else
			comboVenue.setText(obj.getVenueName());
		
		textActivity.setText(obj.getActivityName());
		textNote.setText(obj.getUserNote());
		
		// Set date time
		setDateTime(dateFrom, timeFrom, obj.getDuration().getStartDateTime());
		setDateTime(dateTo, timeTo, obj.getDuration().getEndDateTime());
		
		isVenueChosen = true;
	}
	/**
	 * Returns a MyDateTime object based on the inputs given in two DateTime objects which are
	 * dateCompo (of type DATE) and timeCompo (of type TIME).
	 * 
	 * @param dateCompo - DateTime
	 * @param timeCompo - DateTime
	 * @return dateTime - MyDateTime
	 */
	private MyDateTime readDateTime(DateTime dateCompo, DateTime timeCompo)
	{
		// Note that DateTime objects return month from 0 to 11 while in MyDateTime objects, month
		// has value from 1 to 12!
		return new MyDateTime(dateCompo.getYear(), dateCompo.getMonth() + 1,
							dateCompo.getDay(), timeCompo.getHours(), timeCompo.getMinutes());
	}
	
	/**
	 * Set the two DateTime objects which are dateCompo (of type DATE) and timeCompo (of type TIME)
	 * based on the values given in the MyDateTime object.
	 * 
	 * @param dateCompo
	 * @param timeCompo
	 * @param inputDateTime
	 */
	private void setDateTime(DateTime dateCompo, DateTime timeCompo, MyDateTime inputDateTime)
	{
		// Note that DateTime objects return month from 0 to 11 while in MyDateTime objects, month
		// has value from 1 to 12!
		dateCompo.setDate(inputDateTime.getYear(), inputDateTime.getMonth() - 1, inputDateTime.getDay());
		timeCompo.setHours(inputDateTime.getHour());
		timeCompo.setMinutes(inputDateTime.getMinute());
	}
}
