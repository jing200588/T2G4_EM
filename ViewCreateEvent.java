import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class ViewCreateEvent extends ViewEventParticulars {	
	/**
	 * Description: Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewCreateEvent(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		EventParticularsForm.setText("Create Event");
		
		/**********************************************************************************************
		 * 
		 * CREATE EVENT BUTTON
		 * 
		 *********************************************************************************************/
		btnConfirm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//Exception handling for event name
				String eventname = txtEventTitle.getText().trim();
				if (eventname.equals("")) {
					errordiag = new errormessageDialog(new Shell(), "Event name should not consist of only whitespaces. Please try again.");
					errordiag.open();
				}
				
				else {
				eventname = eventname.replaceAll("  ", "");	//removes excess whitespaces between the names
				
				Eventitem newevent = new Eventitem(eventname, StartDate.getYear(), StartDate.getMonth()+1, StartDate.getDay(),
						EndDate.getYear(), EndDate.getMonth()+1, EndDate.getDay(), StartTime.getHours(), StartTime.getMinutes(), EndTime.getHours(), EndTime.getMinutes());
				ModelEvent.CreateEvent(newevent);
				ViewMain.UpdateTable();
				}
			}
		});
		btnConfirm.setText("Create !");

		/************************************************************
		 *
		 *GO BACK BUTTON
		 *
		 ***********************************************************/
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.Homepage();
			}
		});
		
	}
}
