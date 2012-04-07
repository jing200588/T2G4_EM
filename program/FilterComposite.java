package program;

import java.util.HashMap;
import java.util.Vector;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

import venue.HelperFunctions;
import venue.BookedVenueInfo;
import venue.MyDateTime;
import venue.TimeSlot;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class FilterComposite extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text txtActivity;
	private Combo comboVenue;
	private Button btnVenue;
	private Button btnActivity;
	private Button btnDuration;
	private Vector<BookedVenueInfo> m_listBookedVenue;
	private DateTime dateFrom;
	private DateTime dateTo;
	private DateTime timeFrom;
	private DateTime timeTo;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FilterComposite(Composite parent, int style, Vector<BookedVenueInfo> listBookedVenue) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		/* Initialize some variables */
		
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
	
		String[] m_venueName = new String[m_listBookedVenue.size() + 1];
		for(int index = 0; index < m_listBookedVenue.size(); index++)
		{
			m_venueName[index] = m_listBookedVenue.get(index).getName();
		}
		m_venueName[m_venueName.length - 1] = InputEventFlowEntry.OTHERVENUE;
		setLayout(new GridLayout(2, false));
		
		btnDuration = new Button(this, SWT.CHECK);
		btnDuration.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(btnDuration, true, true);
		btnDuration.setText("Duration:");
		new Label(this, SWT.NONE);
		
		Composite composite = new Composite(this, SWT.NONE);
		
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		
		
		Label lblStartDateTime = new Label(composite, SWT.NONE);
		lblStartDateTime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblStartDateTime.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(lblStartDateTime, true, true);
		lblStartDateTime.setText("Start Date Time:");
		
		dateFrom = new DateTime(composite, SWT.DROP_DOWN);
		dateFrom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		dateFrom.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(dateFrom);
		toolkit.paintBordersFor(dateFrom);
		
		timeFrom = new DateTime(composite, SWT.DROP_DOWN | SWT.TIME | SWT.SHORT);
		timeFrom.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		timeFrom.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(timeFrom);
		toolkit.paintBordersFor(timeFrom);
		
		Label lblEndDateTime = new Label(composite, SWT.NONE);
		
		lblEndDateTime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblEndDateTime.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		lblEndDateTime.setText("End Date Time:");
		toolkit.adapt(lblEndDateTime, true, true);
		
		dateTo = new DateTime(composite, SWT.DROP_DOWN);
		dateTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		dateTo.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(dateTo);
		toolkit.paintBordersFor(dateTo);
		
		timeTo = new DateTime(composite, SWT.DROP_DOWN | SWT.TIME | SWT.SHORT);
		timeTo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		timeTo.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(timeTo);
		toolkit.paintBordersFor(timeTo);
		
		btnActivity = new Button(this, SWT.CHECK);
		btnActivity.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(btnActivity, true, true);
		btnActivity.setText("Activity name:");
		
		txtActivity = new Text(this, SWT.BORDER | SWT.MULTI);
		txtActivity.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		txtActivity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtActivity, true, true);
		
		btnVenue = new Button(this, SWT.CHECK);
		btnVenue.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(btnVenue, true, true);
		btnVenue.setText("Venue:");
		
		comboVenue = new Combo(this, SWT.READ_ONLY);
		comboVenue.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		comboVenue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboVenue.setItems(m_venueName);
		toolkit.adapt(comboVenue);
		toolkit.paintBordersFor(comboVenue);
		

		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnDuration.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnActivity.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnVenue.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblEndDateTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblStartDateTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
	}
	
	/**
	 * Reads input entered by the user. Those fields that the user does not choose will be set to
	 * 		null.
	 * @return listStr - String[] where
	 * 		1) String[0] is the string representing the start date time.
	 * 		2) String[1] is the string representing the end date time.
	 * 		3) String[2] is the string representing the activity name.
	 * 		4) String[3] is the string representing the venue ID.
	 * @throws Exception if the user forgets to enter some compulsory fields or if the input is incorrect. 
	 */
	public String[] readInput() throws Exception
	{
		String[] returnStrArr = new String[4];
		
		// Read duration
		if(btnDuration.getSelection() == true)
		{
			MyDateTime dateTimeFrom = readDateTime(dateFrom, timeFrom);
			MyDateTime dateTimeTo = readDateTime(dateTo, timeTo);
			if(dateTimeFrom.compareTo(dateTimeTo) > 0)
				throw new Exception("The starting date time and ending date time are not in chronological order!");
			if(dateTimeFrom.compareTo(dateTimeTo) == 0)
				throw new Exception("The starting date time and ending date time are the same!");
			
			TimeSlot inputTimeSlot = new TimeSlot(dateTimeFrom, dateTimeTo);
			returnStrArr[0] = inputTimeSlot.getStartDateTime().getDateTimeRepresentation();
			returnStrArr[1] = inputTimeSlot.getEndDateTime().getDateTimeRepresentation();
		}
		else
		{
			returnStrArr[0] = null;
			returnStrArr[1] = null;
		}
		
		// Read activity name
		if(btnActivity.getSelection() == true)
		{
			String activityStr = HelperFunctions.removeRedundantWhiteSpace(txtActivity.getText());
			if(activityStr == null || activityStr.equals("") == true)
				throw new Exception("You have not entered the activity name!");
			returnStrArr[2] = activityStr;
		}
		else
			returnStrArr[2] = null;
		
		// Read venue name
		if(btnVenue.getSelection() == true)
		{
			int chosenIndex = comboVenue.getSelectionIndex();
			if(chosenIndex < 0)
				throw new Exception("You have not chosen the venue!");
			if(chosenIndex == m_listBookedVenue.size())
				returnStrArr[3] = Integer.toString(-1);
			else
				returnStrArr[3] = Integer.toString(m_listBookedVenue.get(chosenIndex).getVenueID());
		}
		else
			returnStrArr[3] = null;
		
		return returnStrArr;
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
}
