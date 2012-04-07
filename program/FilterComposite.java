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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class FilterComposite extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private InputDateTimeComposite compInputDateTime;
	private Text txtActivity;
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
		setLayout(new GridLayout(2, false));
		
		btnDuration = new Button(this, SWT.CHECK);
		btnDuration.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		toolkit.adapt(btnDuration, true, true);
		btnDuration.setText("Duration:");
		new Label(this, SWT.NONE);
		
		compInputDateTime = new InputDateTimeComposite(this, SWT.NONE);
		compInputDateTime.setFont(SWTResourceManager.getFont("Maiandra GD", 9, SWT.NORMAL));
		compInputDateTime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		toolkit.adapt(compInputDateTime);
		toolkit.paintBordersFor(compInputDateTime);
		compInputDateTime.setEnabled(true);
		
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
		compInputDateTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnDuration.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnActivity.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		btnVenue.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
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
			TimeSlot inputTimeSlot = compInputDateTime.readInputFields();
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
}
