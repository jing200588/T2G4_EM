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


public class EventParticulars extends Composite {
	private Text txtNewText;
	private DateTime StartDate;
	private DateTime EndDate;
	private Text text;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public EventParticulars(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		Composite composite = new Composite(this, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(50, -100);
		
		fd_composite.left = new FormAttachment(50, -200);
		fd_composite.right = new FormAttachment(50, 200);
		composite.setLayoutData(fd_composite);
		//formToolkit.adapt(composite);
		//formToolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(3, false));
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setText("Event Title:");
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		
		txtNewText = new Text(composite, SWT.NONE);
		GridData gd_txtNewText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtNewText.widthHint = 219;
		txtNewText.setLayoutData(gd_txtNewText);
		txtNewText.setText("");
		
		Label lblStartDate = new Label(composite, SWT.NONE);
		lblStartDate.setText("Start Date and Time:");
		lblStartDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStartDate.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		
		StartDate = new DateTime(composite, SWT.BORDER | SWT.DROP_DOWN);
		//formToolkit.adapt(StartDate);
		//formToolkit.paintBordersFor(StartDate);
		
		final DateTime StartTime = new DateTime(composite, SWT.BORDER | SWT.TIME | SWT.SHORT);		
		StartTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		StartTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		//formToolkit.adapt(StartTime);
		//formToolkit.paintBordersFor(StartTime);
		
		Label lblEndDate = new Label(composite, SWT.NONE);
		lblEndDate.setText("End Date and Time:");
		lblEndDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEndDate.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		
		EndDate = new DateTime(composite, SWT.BORDER | SWT.DROP_DOWN);
		//formToolkit.adapt(EndDate);
		//formToolkit.paintBordersFor(EndDate);
		
		final DateTime EndTime = new DateTime(composite, SWT.BORDER | SWT.TIME | SWT.SHORT);
		EndTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		//formToolkit.adapt(EndTime);
		//formToolkit.paintBordersFor(EndTime);
		
		Button btnCreate = new Button(this, SWT.NONE);
		btnCreate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			
			}
		});

		
		btnCreate.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		FormData fd_btnCreate = new FormData();
		fd_btnCreate.top = new FormAttachment(composite, 30);
		fd_btnCreate.right = new FormAttachment(50, 70);
		fd_btnCreate.left = new FormAttachment(composite, 0, SWT.LEFT);
		btnCreate.setLayoutData(fd_btnCreate);
		//formToolkit.adapt(btnCreate, true, true);
		btnCreate.setText("Confirm");
		
		Button btnBack = new Button(this, SWT.NONE);
		FormData fd_btnBack = new FormData();
		fd_btnBack.top = new FormAttachment(composite, 30);
		fd_btnBack.right = new FormAttachment(composite, 0, SWT.RIGHT);
		fd_btnBack.left = new FormAttachment(btnCreate, 26);
		
		
		Label lblDescription = new Label(composite, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDescription.setFont(SWTResourceManager.getFont("Hobo Std", 12, SWT.NORMAL));
		lblDescription.setText("Description:");
		
		text = new Text(composite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_text.heightHint = 79;
		gd_text.widthHint = 250;
		text.setLayoutData(gd_text);
		btnBack.setLayoutData(fd_btnBack);
		//formToolkit.adapt(btnBack, true, true);
		btnBack.setText("Back");
		
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
