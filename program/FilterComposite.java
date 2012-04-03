package program;

import java.util.Vector;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

import venue.HelperFunctions;
import venue.InputDateTimeComposite;
import venue.BookedVenueInfo;
import venue.TimeSlot;

public class FilterComposite extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private InputDateTimeComposite inputDateTimeCompo;
	private Text textActivity;
	private Combo comboVenue;
	private Button btnVenue;
	private Button btnActivity;
	private Button btnDuration;
	private Vector<BookedVenueInfo> m_listBookedVenue;

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
		m_listBookedVenue = listBookedVenue;
		String[] m_venueName = new String[listBookedVenue.size() + 1];
		for(int index = 0; index < m_listBookedVenue.size(); index++)
		{
			m_venueName[index] = m_listBookedVenue.get(index).getName();
		}
		m_venueName[m_venueName.length - 1] = InputEventFlowEntry.OTHERVENUE;
		
		inputDateTimeCompo = new InputDateTimeComposite(this, SWT.NONE);
		inputDateTimeCompo.setBounds(0, 32, 313, 95);
		toolkit.adapt(inputDateTimeCompo);
		toolkit.paintBordersFor(inputDateTimeCompo);
		inputDateTimeCompo.setEnabled(true);
		
		textActivity = new Text(this, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		textActivity.setBounds(111, 134, 202, 21);
		toolkit.adapt(textActivity, true, true);
		
		comboVenue = new Combo(this, SWT.READ_ONLY);
		comboVenue.setBounds(111, 173, 101, 23);
		comboVenue.setItems(m_venueName);
		toolkit.adapt(comboVenue);
		toolkit.paintBordersFor(comboVenue);
		
		btnVenue = new Button(this, SWT.CHECK);
		btnVenue.setBounds(10, 175, 57, 16);
		toolkit.adapt(btnVenue, true, true);
		btnVenue.setText("Venue:");
		
		btnActivity = new Button(this, SWT.CHECK);
		btnActivity.setBounds(10, 137, 93, 16);
		toolkit.adapt(btnActivity, true, true);
		btnActivity.setText("Activity name:");
		
		btnDuration = new Button(this, SWT.CHECK);
		btnDuration.setBounds(10, 19, 93, 16);
		toolkit.adapt(btnDuration, true, true);
		btnDuration.setText("Duration:");
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
			TimeSlot inputTimeSlot = inputDateTimeCompo.readInputFields();
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
			String activityStr = HelperFunctions.removeRedundantWhiteSpace(textActivity.getText());
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
}
