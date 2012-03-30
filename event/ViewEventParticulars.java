package event;

import dialog.*;

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

public class ViewEventParticulars extends Composite {
	protected FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	protected Text txtEventTitle;
	protected DateTime StartDate;
	protected DateTime EndDate;
	protected DateTime StartTime;
	protected DateTime EndTime;
	protected Text txtDescription;
	protected Button btnConfirm;
	protected Button btnBack;
	protected Label lblStartDate;
	protected Label lblEndDate;
	protected Label lblNewLabel;
	protected Label lblDescription;
	protected Form EventParticularsForm;
	
	
	protected errormessageDialog errordiag;
	
	/**
	 * Description: Create the composite.
	 * @param parent
	 * @param style
	 */
	public ViewEventParticulars(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		//Create Event form
		EventParticularsForm = formToolkit.createForm(this);
		EventParticularsForm.getHead().setForeground(SWTResourceManager.getColor(SWT.COLOR_INFO_FOREGROUND));
		EventParticularsForm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		EventParticularsForm.getBody().setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		EventParticularsForm.getHead().setFont(SWTResourceManager.getFont("Hobo Std", 20, SWT.BOLD));
		formToolkit.paintBordersFor(EventParticularsForm);
//		CreateEventForm.setText("Create Event");
		EventParticularsForm.getBody().setLayout(new FormLayout());
		
		//Body Composite
		Composite composite = new Composite(EventParticularsForm.getBody(), SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(50, -100);
		fd_composite.left = new FormAttachment(50, -200);
		fd_composite.right = new FormAttachment(50, 200);
		composite.setLayoutData(fd_composite);
		formToolkit.adapt(composite);
		formToolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(3, false));
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		//Event name label
		lblNewLabel = formToolkit.createLabel(composite, "Event Title:", SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		
		//Event name textbox
		txtEventTitle = formToolkit.createText(composite, "New Text", SWT.NONE);
		txtEventTitle.setText("");
		txtEventTitle.setMessage("eg. Fundraising Bazzar");
		GridData gd_txtEventTitle = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtEventTitle.widthHint = 219;
		txtEventTitle.setLayoutData(gd_txtEventTitle);
		
		//Start Date label
		lblStartDate = formToolkit.createLabel(composite, "Start Date and Time:", SWT.NONE);
		lblStartDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblStartDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStartDate.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		StartDate = new DateTime(composite, SWT.BORDER | SWT.DROP_DOWN);
		formToolkit.adapt(StartDate);
		formToolkit.paintBordersFor(StartDate);
		
		//Start Time widget
		StartTime = new DateTime(composite, SWT.BORDER | SWT.TIME | SWT.SHORT);		
		StartTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		StartTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(StartTime);
		formToolkit.paintBordersFor(StartTime);
		
		//End Date label
		lblEndDate = formToolkit.createLabel(composite, "End Date and Time:", SWT.NONE);
		lblEndDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblEndDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEndDate.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		EndDate = new DateTime(composite, SWT.BORDER | SWT.DROP_DOWN);
		formToolkit.adapt(EndDate);
		formToolkit.paintBordersFor(EndDate);
		
		//End Date widget
		EndTime = new DateTime(composite, SWT.BORDER | SWT.TIME | SWT.SHORT);
		EndTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(EndTime);
		formToolkit.paintBordersFor(EndTime);
		
		//Description label
		lblDescription = new Label(composite, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDescription.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		lblDescription.setText("Description:");
		
		//Description textbox
		txtDescription = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_txtDescription.heightHint = 79;
		gd_txtDescription.widthHint = 250;
		txtDescription.setLayoutData(gd_txtDescription);
		
		
		/**********************************************************************************************
		 * 
		 * CREATE EVENT BUTTON
		 * 
		 *********************************************************************************************/
		btnConfirm = new Button(EventParticularsForm.getBody(), SWT.NONE);

		btnConfirm.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		FormData fd_btnConfirm = new FormData();
		fd_btnConfirm.top = new FormAttachment(composite, 16);
		fd_btnConfirm.right = new FormAttachment(50, 70);
		fd_btnConfirm.left = new FormAttachment(composite, 0, SWT.LEFT);
		btnConfirm.setLayoutData(fd_btnConfirm);
		formToolkit.adapt(btnConfirm, true, true);

		/************************************************************
		 *
		 *GO BACK BUTTON
		 *
		 ***********************************************************/
		btnBack = new Button(EventParticularsForm.getBody(), SWT.NONE);
		
		FormData fd_btnBack = new FormData();
		fd_btnBack.top = new FormAttachment(composite, 16);
		fd_btnBack.right = new FormAttachment(composite, 0, SWT.RIGHT);
		fd_btnBack.left = new FormAttachment(btnConfirm, 26);
		btnBack.setLayoutData(fd_btnBack);
		btnBack.setText("Back");
		formToolkit.adapt(btnBack, true, true);
	}

	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

