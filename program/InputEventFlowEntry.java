package program;

import venue.*;

import java.util.HashMap;
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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

public class InputEventFlowEntry extends Composite {
	public static final String OTHERVENUE = "Other";
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text txtTextActivity;
	private Text txtTextNote;
	
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
		setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		/* Initialize some variables */
		isVenueChosen = false;
		
		// Select distinct BookedVenueInfo objects (based on venueID)
		HashMap<Integer, Integer> hashTable = new HashMap<Integer, Integer>();
		m_listBookedVenue = new Vector<BookedVenueInfo>();
		if(listBookedVenue.size() > 0)
		{
			m_listBookedVenue.add(listBookedVenue.get(0));
			hashTable.put(listBookedVenue.get(0).getVenueID(), 0);
			for(int index = 1; index < listBookedVenue.size(); index++)
			{
				if(hashTable.containsKey(listBookedVenue.get(index).getVenueID()) == false)
				{
					m_listBookedVenue.add(listBookedVenue.get(index));
					hashTable.put(listBookedVenue.get(index).getVenueID(), 0);
				}
			}
		}
		
		m_venueName = new String[m_listBookedVenue.size() + 1];
		for(int index = 0; index < m_listBookedVenue.size(); index++)
		{
			m_venueName[index] = m_listBookedVenue.get(index).getName();
		}
		m_venueName[m_venueName.length - 1] = OTHERVENUE;
		setLayout(new GridLayout(3, false));
		
		Label lblActivityName = new Label(this, SWT.NONE);
		lblActivityName.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(lblActivityName, true, true);
		lblActivityName.setText("Activity Name:");
		
		txtTextActivity = new Text(this, SWT.BORDER);
		txtTextActivity.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		txtTextActivity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		toolkit.adapt(txtTextActivity, true, true);
		
		Label lblStartDateTime = new Label(this, SWT.NONE);
		lblStartDateTime.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(lblStartDateTime, true, true);
		lblStartDateTime.setText("Start Date Time:");
		
		dateFrom = new DateTime(this, SWT.DROP_DOWN);
		dateFrom.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(dateFrom);
		toolkit.paintBordersFor(dateFrom);
		
		timeFrom = new DateTime(this, SWT.DROP_DOWN | SWT.TIME | SWT.SHORT);
		timeFrom.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		timeFrom.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(timeFrom);
		toolkit.paintBordersFor(timeFrom);
		
		Label lblEndDateTime = new Label(this, SWT.NONE);
		lblEndDateTime.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		lblEndDateTime.setText("End Date Time:");
		toolkit.adapt(lblEndDateTime, true, true);
		
		dateTo = new DateTime(this, SWT.DROP_DOWN);
		dateTo.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(dateTo);
		toolkit.paintBordersFor(dateTo);
		
		timeTo = new DateTime(this, SWT.DROP_DOWN | SWT.TIME | SWT.SHORT);
		timeTo.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		timeTo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(timeTo);
		toolkit.paintBordersFor(timeTo);
		
		Label lblVenue = new Label(this, SWT.NONE);
		lblVenue.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(lblVenue, true, true);
		lblVenue.setText("Venue:");
		
		comboVenue = new Combo(this, SWT.READ_ONLY);
		comboVenue.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		comboVenue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		comboVenue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isVenueChosen = true;
			}
		});
		comboVenue.setItems(m_venueName);
		toolkit.adapt(comboVenue);
		toolkit.paintBordersFor(comboVenue);
		
		Label lblOtherNotes = new Label(this, SWT.NONE);
		lblOtherNotes.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(lblOtherNotes, true, true);
		lblOtherNotes.setText("Other notes:\r\n(Optional)");
		
		txtTextNote = new Text(this, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtTextNote.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		txtTextNote.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		toolkit.adapt(txtTextNote, true, true);

		
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblActivityName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblStartDateTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblEndDateTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblVenue.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblOtherNotes.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));


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
		
		String activityName = HelperFunctions.convertMultiToSingleLine(txtTextActivity.getText());
		txtTextActivity.setText(activityName);	
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
			chosenVenueID = m_listBookedVenue.get(comboVenue.indexOf(chosenVenue)).getVenueID();
		
		// Return the EventFlowEntry object
		return new EventFlowEntry(new TimeSlot(dateTimeFrom, dateTimeTo), activityName, chosenVenue,
				chosenVenueID, HelperFunctions.convertMultiToSingleLine(txtTextNote.getText()));
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
		int venueIndex = comboVenue.indexOf(obj.getVenueName());
		if(venueIndex < 0)
			comboVenue.setText(OTHERVENUE);
		else
			comboVenue.setText(obj.getVenueName());
		
		txtTextActivity.setText(obj.getActivityName());
		txtTextNote.setText(obj.getUserNote());
		
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
