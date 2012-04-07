package event;

import venue.*;
import dialog.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;


public class ViewEditEventParticulars extends ViewEventParticulars {
	/**
	 * Description: Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEditEventParticulars(Composite parent, int style, final EventItem curevent) {
		super(parent, style);
		EventParticularsForm.getHead().setFont(SWTResourceManager.getFont("Lithos Pro Regular", 20, SWT.BOLD));
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		EventParticularsForm.setText("Event Particulars");
		
		/************************************************************
		 * CONFIRM EDIT EVENT LISTENER
		 ***********************************************************/
		btnConfirm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
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
					curevent.setName(eventname);
					curevent.setDescription(txtDescription.getText());
					curevent.setStartTime(StartTime.getHours(), StartTime.getMinutes());
					curevent.setEndTime(EndTime.getHours(), EndTime.getMinutes());
					curevent.setStartDate(StartDate.getYear(), StartDate.getMonth()+1, StartDate.getDay());
					curevent.setEndDate(EndDate.getYear(), EndDate.getMonth()+1, EndDate.getDay());
					
					ModelEvent.UpdateParticulars(curevent);
					ViewMain.UpdateTable();
					ViewMain.ReturnView();
				}
				catch(Exception exception)
				{
					errordiag = new ErrorMessageDialog(new Shell(), exception.getMessage());
					errordiag.open();
				}
			}
		});
		btnConfirm.setText("Confirm");
		
		/************************************************************
		 * GO BACK EVENT LISTENER
		 ***********************************************************/
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.ReturnView();
			}
		});
				
		setParticulars(curevent.getName(), curevent.getStartDateTime(), curevent.getEndDateTime(), curevent.getDescription());
	}
	
	/************************************************************
	 * SET PARTICULARS to event item
	 ***********************************************************/
	/**
	 * Description: Stores the event particulars into the event item
	 * @param name
	 * @param startdate
	 * @param starttime
	 * @param enddate
	 * @param endtime
	 * @param descript
	 */
	public void setParticulars(String name, MyDateTime startDateTime, MyDateTime endDateTime, String descript) {
		txtEventTitle.setText(name);
		
		StartDate.setYear(startDateTime.getYear());
		StartDate.setMonth(startDateTime.getMonth() - 1);
		StartDate.setDay(startDateTime.getDay());
		
		StartTime.setHours(startDateTime.getHour());
		StartTime.setMinutes(startDateTime.getMinute());
		
		EndDate.setYear(endDateTime.getYear());
		EndDate.setMonth(endDateTime.getMonth() - 1);
		EndDate.setDay(endDateTime.getDay());
		
		EndTime.setHours(endDateTime.getHour());
		EndTime.setMinutes(endDateTime.getMinute());
		
		txtDescription.setText(descript);	
	}
}

