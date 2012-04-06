package event;

import dialog.*;
import venue.*;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;


public class ViewCreateEvent extends ViewEventParticulars {	
	/**
	 * Description: Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewCreateEvent(Composite parent, int style) {
		super(parent, style);
		EventParticularsForm.getHead().setFont(SWTResourceManager.getFont("Lithos Pro Regular", 20, SWT.BOLD));
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
				try
				{
					String eventname = txtEventTitle.getText().trim();
					if (eventname.equals("")) {
						throw new Exception("Event name should not consist of only whitespaces. Please try again.");
					}
				
				
					eventname = eventname.replaceAll("  ", "");	//removes excess whitespaces between the names
					
					MyDateTime startDT = new MyDateTime(StartDate.getYear(), StartDate.getMonth()+1, StartDate.getDay(), StartTime.getHours(), StartTime.getMinutes());
					MyDateTime endDT = new MyDateTime(EndDate.getYear(), EndDate.getMonth()+1, EndDate.getDay(), EndTime.getHours(), EndTime.getMinutes());
					int compareResult = startDT.compareTo(endDT);
					if(compareResult == 0)
						throw new Exception("Start and end date time are the same!");
					if(compareResult > 0)
						throw new Exception("Start and end date time are not in chronological order");
					if(startDT.compareTo(MyDateTime.getCurrentDateTime()) < 0)
						throw new Exception("Start date time of an event must not be in the past");
					
					EventItem newevent = new EventItem(eventname, StartDate.getYear(), StartDate.getMonth()+1, StartDate.getDay(),
							EndDate.getYear(), EndDate.getMonth()+1, EndDate.getDay(), StartTime.getHours(), StartTime.getMinutes(), EndTime.getHours(), EndTime.getMinutes(), txtDescription.getText());
					ModelEvent.CreateEvent(newevent);
					ViewMain.UpdateTable();
				}
				catch(Exception exception)
				{
					errordiag = new ErrorMessageDialog(new Shell(), exception.getMessage());
					errordiag.open();
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
