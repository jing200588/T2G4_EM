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


public class InputEventFlowEntry extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text textActivity;
	private Text textNote;
	
	private Vector<BookedVenueInfo> m_listBookedVenue;
	private String[] m_venueName;
	private DateTime dateFrom;
	private DateTime timeFrom;
	private DateTime dateTo;
	private DateTime timeTo;
	private Combo comboVenue;
	private Label lblNewLabel_1;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public InputEventFlowEntry(Composite parent, int style, Vector<BookedVenueInfo> listBookedVenue) {
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
		m_venueName = new String[listBookedVenue.size()];
		for(int index = 0; index < m_venueName.length; index++)
		{
			m_venueName[index] = m_listBookedVenue.get(index).getName();
		}
		
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
		
		lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setBounds(10, 146, 55, 15);
		toolkit.adapt(lblNewLabel_1, true, true);
		lblNewLabel_1.setText("(Optional)");

	}
}
