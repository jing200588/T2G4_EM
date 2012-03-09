import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.wb.swt.SWTResourceManager;


public class ViewEventParticulars extends Composite {
	private Text txtNewText;
	private DateTime StartDate;
	private DateTime EndDate;
	private DateTime StartTime;
	private DateTime EndTime;
	private Text text;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEventParticulars(Composite parent, int style, final Eventitem curevent, final int eventindex) {
		super(parent, style);
		setLayout(new FormLayout());

		/************************************************************
		 * MAIN COMPOSITE
		 ***********************************************************/
		Composite maincomp = new Composite(this, SWT.NONE);
		FormData fd_maincomp = new FormData();
		fd_maincomp.top = new FormAttachment(50, -100);
		fd_maincomp.left = new FormAttachment(50, -200);
		fd_maincomp.right = new FormAttachment(50, 200);
		maincomp.setLayoutData(fd_maincomp);
		//formToolkit.adapt(composite);
		//formToolkit.paintBordersFor(composite);
		maincomp.setLayout(new GridLayout(3, false));
		
		//Event Name label
		Label lblNewLabel = new Label(maincomp, SWT.NONE);
		lblNewLabel.setText("Event Title:");
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		
		//Event Name textbox
		txtNewText = new Text(maincomp, SWT.BORDER);
		txtNewText.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		GridData gd_txtNewText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtNewText.widthHint = 219;
		txtNewText.setLayoutData(gd_txtNewText);
		txtNewText.setText("");
		
		//Start Date label
		Label lblStartDate = new Label(maincomp, SWT.NONE);
		lblStartDate.setText("Start Date and Time:");
		lblStartDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStartDate.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		
		//Start Date widget
		StartDate = new DateTime(maincomp, SWT.BORDER | SWT.DROP_DOWN);
		//formToolkit.adapt(StartDate);
		//formToolkit.paintBordersFor(StartDate);
		
		//Start Time widget
		StartTime = new DateTime(maincomp, SWT.BORDER | SWT.TIME | SWT.SHORT);		
		StartTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		StartTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		//formToolkit.adapt(StartTime);
		//formToolkit.paintBordersFor(StartTime);
		
		//End Date label
		Label lblEndDate = new Label(maincomp, SWT.NONE);
		lblEndDate.setText("End Date and Time:");
		lblEndDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEndDate.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		
		//End Date widget
		EndDate = new DateTime(maincomp, SWT.BORDER | SWT.DROP_DOWN);
		//formToolkit.adapt(EndDate);
		//formToolkit.paintBordersFor(EndDate);
		
		//End time widget
		EndTime = new DateTime(maincomp, SWT.BORDER | SWT.TIME | SWT.SHORT);
		EndTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		//formToolkit.adapt(EndTime);
		//formToolkit.paintBordersFor(EndTime);
		
		/************************************************************
		 * CONFIRM EDIT EVENT LISTENER
		 ***********************************************************/
		Button btnConfirm = new Button(this, SWT.NONE);
		btnConfirm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				curevent.setName(txtNewText.getText());
				curevent.setDescription(text.getText());
				if (StartTime.getMinutes() == 0)
					curevent.setStartTime(StartTime.getHours() + ":" + StartTime.getMinutes()+0);
				else 
					curevent.setStartTime(StartTime.getHours() + ":" + StartTime.getMinutes());
				if (EndTime.getMinutes() == 0)
					curevent.setEndTime(EndTime.getHours() + ":" + EndTime.getMinutes()+0);
				else
					curevent.setEndTime(EndTime.getHours() + ":" + EndTime.getMinutes());
				curevent.setStartDate(StartDate.getDay() + "-" + (StartDate.getMonth()+1) + "-" + StartDate.getYear());
				curevent.setEndDate(EndDate.getDay() + "-" + (EndDate.getMonth()+1) + "-" + EndDate.getYear());
				
				ModelEvent.UpdateParticulars(curevent, eventindex);
				ViewMain.UpdateTable();
				ViewMain.ReturnView();
			}
		});

		
		btnConfirm.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		FormData fd_btnConfirm = new FormData();
		fd_btnConfirm.top = new FormAttachment(maincomp, 30);
		fd_btnConfirm.right = new FormAttachment(50, 70);
		fd_btnConfirm.left = new FormAttachment(maincomp, 0, SWT.LEFT);
		btnConfirm.setLayoutData(fd_btnConfirm);
		//formToolkit.adapt(btnCreate, true, true);
		btnConfirm.setText("Confirm");
		
		/************************************************************
		 * GO BACK EVENT LISTENER
		 ***********************************************************/
		Button btnBack = new Button(this, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.ReturnView();
			}
		});
		FormData fd_btnBack = new FormData();
		fd_btnBack.top = new FormAttachment(maincomp, 30);
		fd_btnBack.right = new FormAttachment(maincomp, 0, SWT.RIGHT);
		fd_btnBack.left = new FormAttachment(btnConfirm, 26);
		
		//Description label
		Label lblDescription = new Label(maincomp, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDescription.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		lblDescription.setText("Description:");
		
		//Description textbox
		text = new Text(maincomp, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_text.heightHint = 79;
		gd_text.widthHint = 250;
		text.setLayoutData(gd_text);
		btnBack.setLayoutData(fd_btnBack);
		//formToolkit.adapt(btnBack, true, true);
		btnBack.setText("Back");
		
		//Event Particulars label
		Label lblEventParticulars = new Label(this, SWT.NONE);
		lblEventParticulars.setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		FormData fd_lblEventParticulars = new FormData();
		fd_lblEventParticulars.top = new FormAttachment(0, 10);
		fd_lblEventParticulars.left = new FormAttachment(0, 10);
		lblEventParticulars.setLayoutData(fd_lblEventParticulars);
		lblEventParticulars.setText("Event Particulars");
				
		setParticulars(curevent.getName(), curevent.getStartDate(), curevent.getStartTime(), curevent.getEndDate(), curevent.getEndTime(), curevent.getDescription());
	}
	
	/************************************************************
	 * SET PARTICULARS to event item
	 ***********************************************************/
	public void setParticulars(String name, String startdate, String starttime, String enddate, String endtime, String descript) {
		txtNewText.setText(name);
		String arr[] = startdate.split("-");
		String arr1[] = starttime.split(":");
		StartDate.setYear(Integer.parseInt(arr[2]));
		StartDate.setMonth(Integer.parseInt(arr[1])-1);
		StartDate.setDay(Integer.parseInt(arr[0]));
		StartTime.setHours(Integer.parseInt(arr1[0]));
		StartTime.setMinutes(Integer.parseInt(arr1[1]));
		
		String arr2[] = enddate.split("-");
		String arr3[] = endtime.split(":");
		EndDate.setYear(Integer.parseInt(arr2[2]));
		EndDate.setMonth(Integer.parseInt(arr2[1])-1);
		EndDate.setDay(Integer.parseInt(arr2[0]));
		EndTime.setHours(Integer.parseInt(arr3[0]));
		EndTime.setMinutes(Integer.parseInt(arr3[1]));
		text.setText(descript);	
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

