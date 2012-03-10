import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class ViewCreateEvent extends Composite {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtNewText;
	private DateTime StartDate;
	private DateTime EndDate;

	/**
	 * Description: Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewCreateEvent(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		//Create Event form
		Form CreateEventForm = formToolkit.createForm(this);
		CreateEventForm.getBody().setBackground(SWTResourceManager.getColor(255, 255, 255));
		CreateEventForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(CreateEventForm);
		CreateEventForm.setText("Create Event");
		CreateEventForm.getBody().setLayout(new FormLayout());
		
		//Body Composite
		Composite composite = new Composite(CreateEventForm.getBody(), SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(50, -100);
		fd_composite.bottom = new FormAttachment (50, -10);
		fd_composite.left = new FormAttachment(50, -200);
		fd_composite.right = new FormAttachment(50, 200);
		composite.setLayoutData(fd_composite);
		formToolkit.adapt(composite);
		formToolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(3, false));
		
		//Event name label
		Label lblNewLabel = formToolkit.createLabel(composite, "Event Title:", SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		
		//Event name textbox
		txtNewText = formToolkit.createText(composite, "New Text", SWT.NONE);
		GridData gd_txtNewText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtNewText.widthHint = 219;
		txtNewText.setLayoutData(gd_txtNewText);
		txtNewText.setText("");
		
		//Start Date label
		Label lblStartDate = formToolkit.createLabel(composite, "Start Date and Time:", SWT.NONE);
		lblStartDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStartDate.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		StartDate = new DateTime(composite, SWT.BORDER | SWT.DROP_DOWN);
		formToolkit.adapt(StartDate);
		formToolkit.paintBordersFor(StartDate);
		
		//Start Time widget
		final DateTime StartTime = new DateTime(composite, SWT.BORDER | SWT.TIME | SWT.SHORT);		
		StartTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		StartTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(StartTime);
		formToolkit.paintBordersFor(StartTime);
		
		//End Date label
		Label lblEndDate = formToolkit.createLabel(composite, "End Date and Time:", SWT.NONE);
		lblEndDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEndDate.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		EndDate = new DateTime(composite, SWT.BORDER | SWT.DROP_DOWN);
		formToolkit.adapt(EndDate);
		formToolkit.paintBordersFor(EndDate);
		
		//End Date widget
		final DateTime EndTime = new DateTime(composite, SWT.BORDER | SWT.TIME | SWT.SHORT);
		EndTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(EndTime);
		formToolkit.paintBordersFor(EndTime);
		
		/**********************************************************************************************
		 * 
		 * CREATE EVENT BUTTON
		 * 
		 *********************************************************************************************/
		Button btnCreate = new Button(CreateEventForm.getBody(), SWT.NONE);
		btnCreate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Eventitem newevent = new Eventitem(txtNewText.getText(), StartDate.getYear(), StartDate.getMonth(), StartDate.getDay(),
						EndDate.getYear(), EndDate.getMonth(), EndDate.getDay(), StartTime.getHours(), StartTime.getMinutes(), EndTime.getHours(), EndTime.getMinutes());
				ModelEvent.CreateEvent(newevent);
				ViewMain.UpdateTable();
			}
		});

		btnCreate.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		FormData fd_btnCreate = new FormData();
		fd_btnCreate.top = new FormAttachment(composite, 16);
		fd_btnCreate.right = new FormAttachment(50, 70);
		fd_btnCreate.left = new FormAttachment(composite, 0, SWT.LEFT);
		btnCreate.setLayoutData(fd_btnCreate);
		formToolkit.adapt(btnCreate, true, true);
		btnCreate.setText("Create !");

		/************************************************************
		 *
		 *GO BACK BUTTON
		 *
		 ***********************************************************/
		Button btnBack = new Button(CreateEventForm.getBody(), SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewMain.Homepage();
			}
		});
		FormData fd_btnBack = new FormData();
		fd_btnBack.top = new FormAttachment(composite, 16);
		fd_btnBack.right = new FormAttachment(composite, 0, SWT.RIGHT);
		fd_btnBack.left = new FormAttachment(btnCreate, 26);
		btnBack.setLayoutData(fd_btnBack);
		formToolkit.adapt(btnBack, true, true);
		btnBack.setText("Back");
		
		
	}

	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
