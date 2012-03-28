import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;


public class ViewEditEventParticulars extends ViewEventParticulars {
	/**
	 * Description: Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEditEventParticulars(Composite parent, int style, final Eventitem curevent) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		EventParticularsForm.setText("Event Particulars");
		
		/************************************************************
		 * CONFIRM EDIT EVENT LISTENER
		 ***********************************************************/
		btnConfirm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//Exception handling for event name
				String eventname = txtEventTitle.getText().trim();
				/*
				//Exception handling for date/time difference from booked venue.
				Vector<Date> venueStartdate = new Vector<Date>();
				Vector<Date> venueEnddate = new Vector<Date>();
				Vector<Time> venueStarttime = new Vector<Time>();
				Vector<Time> venueEndtime = new Vector<Time>();
				
				for (int i=0; i<curevent.getBVI_list().size(); i++) {
					venueStartdate.add(new Date(curevent.getBVI_list().get(i).getStartDateString()));
					venueEnddate.add(new Date(curevent.getBVI_list().get(i).getEndDateString()));
					venueStarttime.add(new Time(curevent.getBVI_list().get(i).getStartHourString()));
					venueEndtime.add(new Time(curevent.getBVI_list().get(i).getEndHourString()));
				}
				
				for (int i=0; i<venueStartdate.size(); i++) {
				//	if (venueStartdate.get(i).get_year())
					
				}*/
				if (eventname.equals("")) {
					errormessageDialog errordiag = new errormessageDialog(new Shell(), "Event name should not consist of only whitespaces. Please try again.");
					errordiag.open();
				}
				
				
				else {
					eventname = eventname.replaceAll("  ", "");	//removes excess whitespaces between the names
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

